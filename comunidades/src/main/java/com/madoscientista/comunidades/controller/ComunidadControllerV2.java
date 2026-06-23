package com.madoscientista.comunidades.controller;

import java.util.List;
import java.util.Set;

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

import com.madoscientista.comunidades.assembler.ComunidadAssembler;
import com.madoscientista.comunidades.client.EjercicioClient;
import com.madoscientista.comunidades.dto.comunidadDTO.RequestComunidadDTO;
import com.madoscientista.comunidades.dto.comunidadDTO.ResponseComunidadDTO;
import com.madoscientista.comunidades.dto.ejercicioDTO.ResponseEjercicioDTO;
import com.madoscientista.comunidades.mapper.ComunidadMapper;
import com.madoscientista.comunidades.model.Comunidad;
import com.madoscientista.comunidades.service.ComunidadService;

import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Comunidades V2", description = "API de comunidades con HATEOAS")
@RestController
@RequestMapping("api/v2/comunidades")
@RequiredArgsConstructor
public class ComunidadControllerV2 {

    private final ComunidadService cService;
    private final EjercicioClient eClient;
    private final ComunidadMapper cMapper;
    private final ComunidadAssembler assembler;

    // --------------------------------------------------------
    // ------------------ Seccion GET -------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Obtener comunidades",
        description = "Retorna una lista de las comunidades disponibles en la base de datos")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de comunidades obtenidas exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseComunidadDTO.class)))),
        @ApiResponse(
            responseCode = "204",
            description = "No se han encontrado comunidades",
            content = @Content)
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ResponseComunidadDTO>>> getComunidades(){
        List<Comunidad> comunidades = cService.getComunidades();

        if(comunidades.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        CollectionModel<EntityModel<ResponseComunidadDTO>> dtoList = assembler.toCollectionModel(comunidades);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Filtrar comunidades por ID",
        description = "Retorna una comunidad indicando el ID de la misma en el path")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Retorna una comunidad filtrada por su ID",
            content = @Content(schema = @Schema(implementation = ResponseComunidadDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontro comunidad con el ID indicado",
            content = @Content)
    })
    @GetMapping("{idComunidad}")
    public ResponseEntity<EntityModel<ResponseComunidadDTO>> getComunidadById(
        @Parameter(description = "ID de la comunidad a buscar", example = "4")
        @PathVariable Long idComunidad){

        Comunidad comunidad = cService.getComunidadById(idComunidad);

        if(comunidad == null){
            return ResponseEntity.notFound().build();
        }
        EntityModel<ResponseComunidadDTO> dto = assembler.toModel(comunidad);
        return ResponseEntity.ok(dto);
    }

    @Operation(
        summary = "Obtener los miembros de una comunidad",
        description = "Retorna una lista de los ID de los miembros de una comunidad cuyo ID es indicada en el path")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Retorna los miembros de la comunidad filtrada por ID",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Long.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "La comunidad no tiene miembros o la comunidad no existe",
            content = @Content)
    })
    @GetMapping("{idComunidad}/usuarios")
    public ResponseEntity<Set<Long>> getMiembrosDeComunidad(
        @Parameter(description = "ID de la comunidad a filtrar", example = "8")
        @PathVariable Long idComunidad){

        Set<Long> idMiembros = cService.getMiembrosDeComunidad(idComunidad);

        if(idMiembros == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(idMiembros);
    }

    // --------------------------------------------------------
    // ------------------ Seccion POST ------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Crear una nueva comunidad",
        description = "Crea una comunidad y retorna informacion que incluye ID y fecha de creacion")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Retorna la comunidad creada con exito",
            content = @Content(schema = @Schema(implementation = ResponseComunidadDTO.class)))
    })
    @PostMapping
    public ResponseEntity<EntityModel<ResponseComunidadDTO>> postComunidad(@Valid @RequestBody RequestComunidadDTO request){
        Comunidad comunidad = cMapper.toEntity(request);
        Comunidad creada = cService.postComunidad(comunidad);
        EntityModel<ResponseComunidadDTO> response = assembler.toModel(creada);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // --------------------------------------------------------
    // ------------------ Seccion PUT -------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Agregar miembros a una comunidad",
        description = "Permite agregar miembros a una comunidad indicando en el path el ID de la comunidad")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Se ha actualizado exitosamente la comunidad",
            content = @Content(schema = @Schema(implementation = ResponseComunidadDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Error en el cuerpo de la solicitud o no se encontro la comunidad",
            content = @Content)
    })
    @PutMapping("/agregar-miembros/{idComunidad}")
    public ResponseEntity<EntityModel<ResponseComunidadDTO>> agregarMiembrosAComunidad(
        @Parameter(description = "ID de la comunidad a la que se agregaran los miembros", example = "4")
        @PathVariable Long idComunidad,
        @RequestBody Set<Long> idMiembros){

        Comunidad comunidad = cService.agregarMiembrosAComunidad(idComunidad, idMiembros);

        if(comunidad != null){
            EntityModel<ResponseComunidadDTO> response = assembler.toModel(comunidad);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
    }

    @Operation(
        summary = "Eliminar miembros de una comunidad",
        description = "Permite eliminar miembros a una comunidad indicando en el path el ID de la comunidad, y en el cuerpo de la peticion una lista de los ID de los miembros")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Se han eliminado los miembros de la comunidad exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseComunidadDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Error en el cuerpo de la solicitud o no se encontro la comunidad",
            content = @Content)
    })
    @PutMapping("/eliminar-miembros/{idComunidad}")
    public ResponseEntity<EntityModel<ResponseComunidadDTO>> eliminarMiembrosDeComunidad(
        @PathVariable Long idComunidad,
        @RequestBody Set<Long> idMiembros){

        Comunidad comunidad = cService.eliminarMiembrosDeComunidad(idComunidad, idMiembros);

        if(comunidad != null){
            EntityModel<ResponseComunidadDTO> response = assembler.toModel(comunidad);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
    }

    // --------------------------------------------------------
    // ------------------ Seccion Ejercicios ------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Obtener lista de ejercicios de la comunidad",
        description = "Lista los ejercicios almacenados por los miembros de la comunidad filtrada por ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de los ejercicios almacenados por los miembros de la comunidad",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseEjercicioDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron ejercicios para la comunidad",
            content = @Content)
    })
    @GetMapping("ejercicios/{idComunidad}")
    public ResponseEntity<List<ResponseEjercicioDTO>> listarEjerciciosDeComunidad(@PathVariable Long idComunidad){
        Set<Long> idUsuarios = cService.getMiembrosDeComunidad(idComunidad);

        if(idUsuarios == null){
            return ResponseEntity.notFound().build();
        }

        try{
            List<ResponseEjercicioDTO> listaEjercicios = eClient.listarEjerciciosDeUsuarios(idUsuarios).getBody();
            return ResponseEntity.ok(listaEjercicios);
        }catch(FeignException e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
    }
}
