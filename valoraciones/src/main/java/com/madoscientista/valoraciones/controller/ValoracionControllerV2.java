package com.madoscientista.valoraciones.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.valoraciones.assembler.ValoracionAssembler;
import com.madoscientista.valoraciones.dto.ValoracionDTO.PromedioValoracionDTO;
import com.madoscientista.valoraciones.dto.ValoracionDTO.RequestValoracionDTO;
import com.madoscientista.valoraciones.dto.ValoracionDTO.ResponseValoracionDTO;
import com.madoscientista.valoraciones.model.Valoracion;
import com.madoscientista.valoraciones.service.ValoracionService;

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
@Tag(name = "Valoraciones V2", description = "API de valoraciones con HATEOAS")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/valoraciones")
public class ValoracionControllerV2 {

    private final ValoracionService service;
    private final ValoracionAssembler assembler;

    @Operation(
        summary = "Obtener valoracion por ID",
        description = "Retorna una valoracion segun su ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Valoracion encontrada exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseValoracionDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontro valoracion con el ID indicado",
            content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ResponseValoracionDTO>> getValoracionById(
            @Parameter(description = "ID de la valoracion", example = "8")
            @PathVariable Long id) {
        log.info("Solicitud de valoracion id: " + id);
        Valoracion valoracion = service.getValoracionById(id);
        if (valoracion == null) {
            log.info("Valoracion no encontrada");
            return ResponseEntity.notFound().build();
        }
        log.info("Valoracion encontrada");
        EntityModel<ResponseValoracionDTO> response = assembler.toModel(valoracion);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Obtener valoraciones por ID de ejercicio",
        description = "Retorna todas las valoraciones de un ejercicio segun su ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de valoraciones obtenida exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseValoracionDTO.class))))
    })
    @GetMapping("/ejercicio/{idEjercicio}")
    public ResponseEntity<CollectionModel<EntityModel<ResponseValoracionDTO>>> getValoracionesByEjercicio(
            @Parameter(description = "ID del ejercicio", example = "15")
            @PathVariable Long idEjercicio) {
        log.info("Solicitud de valoraciones del ejercicio id: " + idEjercicio);
        List<Valoracion> valoraciones = service.getValoracionesByEjercicio(idEjercicio);
        CollectionModel<EntityModel<ResponseValoracionDTO>> dtoList = assembler.toCollectionModel(valoraciones);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Obtener valoraciones por ID de usuario",
        description = "Retorna todas las valoraciones realizadas por un usuario segun su ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de valoraciones obtenida exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseValoracionDTO.class))))
    })
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<CollectionModel<EntityModel<ResponseValoracionDTO>>> getValoracionesByUsuario(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Long idUsuario) {
        log.info("Solicitud de valoraciones del usuario id: " + idUsuario);
        List<Valoracion> valoraciones = service.getValoracionesByUsuario(idUsuario);
        CollectionModel<EntityModel<ResponseValoracionDTO>> dtoList = assembler.toCollectionModel(valoraciones);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Obtener promedio de valoraciones de un ejercicio",
        description = "Retorna el promedio de puntuacion y el total de valoraciones de un ejercicio")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Promedio obtenido exitosamente",
            content = @Content(schema = @Schema(implementation = PromedioValoracionDTO.class)))
    })
    @GetMapping("/promedio/{idEjercicio}")
    public ResponseEntity<PromedioValoracionDTO> getPromedioByEjercicio(
            @Parameter(description = "ID del ejercicio", example = "15")
            @PathVariable Long idEjercicio) {
        log.info("Solicitud de promedio del ejercicio id: " + idEjercicio);
        PromedioValoracionDTO promedio = service.getPromedioByEjercicio(idEjercicio);
        return ResponseEntity.ok(promedio);
    }

    @Operation(
        summary = "Crear una nueva valoracion",
        description = "Crea una valoracion para un ejercicio. Si el usuario ya valoro este ejercicio, debe usar PUT.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Valoracion creada exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseValoracionDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "El usuario ya ha valorado este ejercicio. Use PUT para actualizar.",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<ResponseValoracionDTO>> postValoracion(@Valid @RequestBody RequestValoracionDTO request) {
        log.info("Solicitud creacion de una nueva valoracion");
        Valoracion nuevaValoracion = service.postValoracion(request);
        if (nuevaValoracion == null) {
            log.info("No se pudo crear la valoracion: el usuario ya valoro este ejercicio");
            return ResponseEntity.badRequest().build();
        }
        EntityModel<ResponseValoracionDTO> response = assembler.toModel(nuevaValoracion);
        log.debug("Valoracion creada", response);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Actualizar una valoracion",
        description = "Actualiza la puntuacion o comentario de una valoracion existente")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Valoracion actualizada exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseValoracionDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontro valoracion con el ID indicado",
            content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ResponseValoracionDTO>> putValoracion(
            @Parameter(description = "ID de la valoracion a actualizar", example = "8")
            @PathVariable Long id,
            @Valid @RequestBody RequestValoracionDTO request) {
        log.info("Solicitud actualizacion de valoracion id: " + id);
        Valoracion actualizada = service.putValoracion(id, request);
        if (actualizada == null) {
            log.info("Valoracion no encontrada");
            return ResponseEntity.notFound().build();
        }
        EntityModel<ResponseValoracionDTO> response = assembler.toModel(actualizada);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Eliminar una valoracion",
        description = "Elimina una valoracion segun su ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Valoracion eliminada correctamente",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontro valoracion con el ID indicado",
            content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteValoracion(
            @Parameter(description = "ID de la valoracion a eliminar", example = "8")
            @PathVariable Long id) {
        log.info("Solicitud eliminacion de valoracion id: " + id);
        boolean eliminada = service.deleteValoracion(id);
        if (!eliminada) {
            log.info("Valoracion no encontrada");
            return ResponseEntity.notFound().build();
        }
        log.info("Valoracion eliminada");
        return ResponseEntity.noContent().build();
    }
}
