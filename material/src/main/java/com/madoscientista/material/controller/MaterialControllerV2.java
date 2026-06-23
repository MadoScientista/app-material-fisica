package com.madoscientista.material.controller;

import java.util.HashSet;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.material.assembler.MaterialAssembler;
import com.madoscientista.material.dto.materialDTO.RequestMaterialDTO;
import com.madoscientista.material.dto.materialDTO.ResponseMaterialDTO;
import com.madoscientista.material.mapper.MaterialMapper;
import com.madoscientista.material.model.ItemEjercicio;
import com.madoscientista.material.model.Material;
import com.madoscientista.material.service.ItemEjercicioService;
import com.madoscientista.material.service.MaterialService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Materiales V2", description = "API de gestion de Material con HATEOAS")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/materiales")
public class MaterialControllerV2 {

    private final MaterialService mService;
    private final ItemEjercicioService ieService;
    private final MaterialMapper mMapper;
    private final MaterialAssembler assembler;

    // --------------------------------------------------------
    // ------------------ Seccion GET -------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Obtener todos los materiales disponibles",
        description = "Retorna todos los materiales disponibles en la plataforma")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de materiales encontrada",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseMaterialDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron materiales",
            content = @Content)
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ResponseMaterialDTO>>> getMateriales(){
        log.info("Lista de materiales disponibles en la plataforma solicitada");
        List<Material> materiales = mService.getMateriales();

        if(materiales.isEmpty()){
            log.info("No se encontraron materiales en la plataforma");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        log.info("Lista de materiales encontrada");
        CollectionModel<EntityModel<ResponseMaterialDTO>> dtoList = assembler.toCollectionModel(materiales);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Obtiene un material por su id",
        description = "Retorna un material filtrado por el ID indicado en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Material encontrado",
            content = @Content(schema = @Schema(implementation = ResponseMaterialDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Material no encontrado",
            content = @Content)
    })
    @GetMapping("{idMaterial}")
    public ResponseEntity<EntityModel<ResponseMaterialDTO>> getMaterialById(@PathVariable Long idMaterial){
        log.info("Material con id: " + idMaterial + " solicitado");
        Material m = mService.getMaterialById(idMaterial);

        if(m == null){
            log.info("Material no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        EntityModel<ResponseMaterialDTO> dto = assembler.toModel(m);
        log.debug("Material encontrado: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Operation(
        summary = "Obtener materiales creados por un usuario",
        description = "Retorna una lista de materiales creados por un usuario identificado por su ID en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de materiales retornada exito",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseMaterialDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontro la lista de materiales o usuario no existe",
            content = @Content)
    })
    @GetMapping("usuario/{idUsuarioCreador}")
    public ResponseEntity<CollectionModel<EntityModel<ResponseMaterialDTO>>> getMaterialByIdeUsuarioCreador(
        @PathVariable Long idUsuarioCreador){

            log.info("Lista de materiales del usuario id: " + idUsuarioCreador + " solicitada");
            List<Material> materiales = mService.getMaterialByUsuarioCreador(idUsuarioCreador);

            if(materiales.isEmpty()){
                log.info("Lista no encontrada");
                return ResponseEntity.notFound().build();
            }

            CollectionModel<EntityModel<ResponseMaterialDTO>> dtoList = assembler.toCollectionModel(materiales);
            log.debug("Lista encontrada: {}", dtoList);
            return ResponseEntity.ok(dtoList);
        }

    // --------------------------------------------------------
    // ------------------ Seccion POST ------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Crear un nuevo material",
        description = "Crea un nuevo material a partir de los datos del DTO enviado en el cuerpo de la peticion")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Material creado con exito",
            content = @Content(schema = @Schema(implementation = ResponseMaterialDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada invalidos",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<ResponseMaterialDTO>> postMaterial(@Valid @RequestBody RequestMaterialDTO request){
        log.debug("Solicitud de creacion de material: {}", request);
        List<ItemEjercicio> ieList = ieService.getItemEjercicioByIdIn(
            new HashSet<>(request.getIdItemsEjercicios()));

        if(ieList.isEmpty()){
            log.info("Lista de ejercicios no encontrados");
            return ResponseEntity.badRequest().build();
        }

        Material m = mMapper.toEntity(request, ieList);
        Material mCreado = mService.postMaterial(m);
        EntityModel<ResponseMaterialDTO> dto = assembler.toModel(mCreado);
        log.debug("Material creado con exito: {}", dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // --------------------------------------------------------
    // ------------------ Seccion PUT -------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Actualizar materiales",
        description = "Actualiza el material identificado por su ID en la ruta con los datos del DTO del cuerpo de la peticion")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Material actualizado correctamente",
            content = @Content(schema = @Schema(implementation = ResponseMaterialDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Material no encontrado o ID invalido",
            content = @Content)
    })
    @PutMapping("{idMaterial}")
    public ResponseEntity<EntityModel<ResponseMaterialDTO>> actualizarItemEjercicios(
        @PathVariable Long idMaterial,
        @Valid @RequestBody RequestMaterialDTO request){

        log.debug("Solicitud de actualizacion de material: {}", request);
        List<ItemEjercicio> ieList = ieService.getItemEjercicioByIdIn(
            new HashSet<>(request.getIdItemsEjercicios()));

        if(ieList == null){
            log.info("Lista de items de ejercicios no encontrados");
            return ResponseEntity.notFound().build();
        }

        Material m = mMapper.toEntity(request, ieList);
        Material mActualizado = mService.actualizarEjercicios(idMaterial, m);

        if(mActualizado == null){
            log.info("Material no encontrado");
            return ResponseEntity.notFound().build();
        }

        EntityModel<ResponseMaterialDTO> dto = assembler.toModel(mActualizado);
        log.debug("Material actualizado con exito: {}", dto);

        return ResponseEntity.ok(dto);
    }
}
