package com.madoscientista.material.controller;

import java.util.HashSet;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

@Tag(name = "Material", description = "API para gestión de Material")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/materiales")
public class MaterialController {

    private final MaterialService mService;
    private final ItemEjercicioService ieService;
    private final MaterialMapper mMapper;

    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    // Retorna todos los materiales disponibles en la plataforma

    @Operation(
        summary = "Obtener todos los materiales disponibles",
        description = "Retorna todos los materiales disponibles en la plataforma"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de materiales encontrada",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseMaterialDTO.class)))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron materiales",
            content = @Content
        )
    })
    @GetMapping
    public ResponseEntity<List<ResponseMaterialDTO>> getMateriales(){
        log.info("Lista de materiales disponibles en la plataforma solicitada");
        List<Material> materiales = mService.getMateriales();

        if(materiales.isEmpty()){
            log.info("No se encontraron materiales en la plataforma");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        log.info("Lista de materiales encontrada");
        List<ResponseMaterialDTO> dtoList = mMapper.toDTOList(materiales); 
        return ResponseEntity.ok(dtoList);
    }

    // Retorna un material filtrado por id

    @Operation(
        summary = "Obtiene un material por su id",
        description = "Retorna un material filtrado por el ID indicado en la ruta"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Material encontrado",
            content = @Content(schema = @Schema(implementation = ResponseMaterialDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Material no encontrado",
            content = @Content
        )
    })
    @GetMapping("{idMaterial}")
    public ResponseEntity<ResponseMaterialDTO> getMaterialById(@PathVariable Long idMaterial){

        log.info("Material con id: " + idMaterial + " solicitado" );
        Material m = mService.getMaterialById(idMaterial);

        if(m == null){
            log.info("Material no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ResponseMaterialDTO dto = mMapper.toDTO(m);
        log.debug("Material encontrado: {}", dto);

        return ResponseEntity.ok(dto);
    }

    // ------------------------ Obtener materiales creados por un usuario --------------------------
    @Operation(
        summary = "Obtener materiales creados por un usuario",
        description = "Retorna una lista de materiales creados por un usuario identificado por su ID en la ruta"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de materiales retornada exito",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseMaterialDTO.class)))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontro la lista de materiales o usuario no existe",
            content = @Content
        )
    })
    @GetMapping("usuario/{idUsuarioCreador}")
    public ResponseEntity<List<ResponseMaterialDTO>> getMaterialByIdeUsuarioCreador(
        @PathVariable Long idUsuarioCreador){

            log.info("Lista de materiales del usuario id: " + idUsuarioCreador + " solicitada");
            List<Material> materiales = mService.getMaterialByUsuarioCreador(idUsuarioCreador);

            if(materiales.isEmpty()){
                log.info("Lista no encontrada");
                return ResponseEntity.notFound().build();
            }

            List<ResponseMaterialDTO> dtoList = mMapper.toDTOList(materiales);
            log.debug("Lista encontrada: {}", dtoList);
            return ResponseEntity.ok(dtoList);
        }


    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------
    
    // ----------------------------- Crear Material -----------------------------

    @Operation(
        summary = "Crear un nuevo material", 
        description = "Crea un nuevo material a partir de los datos del DTO enviado en el cuerpo de la petición"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", 
            description = "Material creado con éxito",
            content = @Content(schema = @Schema(implementation = ResponseMaterialDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de entrada inválidos", 
            content = @Content
        )
    })
    @PostMapping
    public ResponseEntity<ResponseMaterialDTO> postMaterial(@Valid @RequestBody RequestMaterialDTO request){
        log.debug("Solicitud de creación de material: {}", request);
        List<ItemEjercicio> ieList = ieService.getItemEjercicioByIdIn(
            new HashSet<>(request.getIdItemsEjercicios()));

        if(ieList.isEmpty()){
            log.info("Lista de ejercicios no encontrados");
            return ResponseEntity.badRequest().build();
        }

        Material m = mMapper.toEntity(request, ieList);
        Material mCreado = mService.postMaterial(m);
        ResponseMaterialDTO dto = mMapper.toDTO(mCreado);
        log.debug("Material creado con éxito: {}", dto);

        // Retorna el código HTTP 201 (CREATED)
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        
    }


    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    // ------------------------------------- Actualizar material -----------------------------------------

    @Operation(
        summary = "Actualizar materiales",
        description = "Actualiza el material identificado por su ID en la ruta con los datos del DTO del cuerpo de la petición"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Material actualizado correctamente", 
            content = @Content(schema = @Schema(implementation = ResponseMaterialDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Material no encontrado o ID inválido", 
            content = @Content
        )
    })
    @PutMapping("{idMaterial}")
    public ResponseEntity<ResponseMaterialDTO> actualizarItemEjercicios(
        @PathVariable Long idMaterial, 
        @Valid @RequestBody RequestMaterialDTO request){

        log.debug("Solicitud de actualización de material: {}", request);
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

        ResponseMaterialDTO dto = mMapper.toDTO(mActualizado);
        log.debug("Material actualizado con éxito: {}", dto);

        return ResponseEntity.ok(dto);
    }

}
