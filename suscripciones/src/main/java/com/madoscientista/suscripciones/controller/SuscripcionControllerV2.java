package com.madoscientista.suscripciones.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.suscripciones.assembler.SuscripcionAssembler;
import com.madoscientista.suscripciones.dto.SuscripcionDTO.RequestSuscripcionDTO;
import com.madoscientista.suscripciones.dto.SuscripcionDTO.ResponseSuscripcionDTO;
import com.madoscientista.suscripciones.model.Suscripcion;
import com.madoscientista.suscripciones.service.SuscripcionService;

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
@Tag(name = "Suscripciones V2", description = "API de suscripciones con HATEOAS")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/suscripciones")
public class SuscripcionControllerV2 {

    private final SuscripcionService service;
    private final SuscripcionAssembler assembler;

    // ------------------------------------------------------
    // ---------------- Seccion GET -------------------------
    // ------------------------------------------------------

    @Operation(
        summary = "Obtener suscripciones activas",
        description = "Retorna una lista de las suscripciones activas en la base de datos")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de suscripciones activas obtenidas exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseSuscripcionDTO.class))))
    })
    @GetMapping("/activas")
    public ResponseEntity<CollectionModel<EntityModel<ResponseSuscripcionDTO>>> getSuscripcionesActivas() {
        log.info("Solicitud de las notificaciones activas");
        List<Suscripcion> suscripciones = service.getSuscripcionesActivas();
        CollectionModel<EntityModel<ResponseSuscripcionDTO>> dtoList = assembler.toCollectionModel(suscripciones);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Obtener usuarios con suscripciones activas",
        description = "Retorna una lista de IDs de usuarios que tienen una suscripcion activa")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de IDs de usuarios con suscripcion activa",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Long.class))))
    })
    @GetMapping("/usuarios-activos")
    public ResponseEntity<List<Long>> getUsuariosConSuscripcionesActivas() {
        log.info("Solicitud de id de usuarios con suscripciones activas");
        List<Long> usuariosActivos = service.getUsuariosConSuscripcionesActivas();
        return ResponseEntity.ok(usuariosActivos);
    }

    @Operation(
        summary = "Obtener suscripcion por ID de usuario",
        description = "Retorna la suscripcion de un usuario segun su ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Suscripcion encontrada exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseSuscripcionDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontro suscripcion para el ID de usuario indicado",
            content = @Content)
    })
    @GetMapping("/{idUsuario}")
    public ResponseEntity<EntityModel<ResponseSuscripcionDTO>> getSuscripcionByUsuarioId(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Long idUsuario) {
        log.info("Solicitud de la suscripcion del usuario id: " + idUsuario);
        Suscripcion suscripcion = service.getSuscripcionByUsuarioId(idUsuario);
        if (suscripcion == null) {
            log.info("Suscripcion no encontrada");
            return ResponseEntity.notFound().build();
        }
        log.info("Suscripcion encontrada");
        EntityModel<ResponseSuscripcionDTO> response = assembler.toModel(suscripcion);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Obtener maximo de ejercicios permitidos",
        description = "Retorna el numero maximo de ejercicios que puede generar un usuario segun su suscripcion")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Maximo de ejercicios permitidos obtenido exitosamente",
            content = @Content(schema = @Schema(implementation = Long.class)))
    })
    @GetMapping("/max-ejercicios/{idUsuario}")
    public ResponseEntity<Long> getMaxEjerciciosByUsuarioId(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Long idUsuario) {
        log.info("Solicitud de numero maximo de ejercicios para el usuario id: " + idUsuario);
        Long maxEjercicios = service.getMaxEjerciciosByUsuarioId(idUsuario);
        return ResponseEntity.ok(maxEjercicios);
    }

    // ------------------------------------------------------
    // ---------------- Seccion POST ------------------------
    // ------------------------------------------------------

    @Operation(
        summary = "Crear una nueva suscripcion",
        description = "Crea una suscripcion para un usuario indicando el tipo de suscripcion")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Suscripcion creada exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseSuscripcionDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "No se pudo crear la suscripcion. Verifique que el tipo de suscripcion sea valido.",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<ResponseSuscripcionDTO>> postSuscripcion(@Valid @RequestBody RequestSuscripcionDTO request){
        log.info("Solicitud creacion de una nueva suscripcion");
        Suscripcion nuevaSuscripcion = service.postSuscripcion(request.getIdUsuario(), request.getNombreTipoSuscripcion());
        if(nuevaSuscripcion == null){
            log.info("No se pudo crear la suscripcion");
            return ResponseEntity.badRequest().build();
        }
        EntityModel<ResponseSuscripcionDTO> response = assembler.toModel(nuevaSuscripcion);
        log.debug("Suscripcion creada", response);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Obtener suscripciones por lista de IDs de usuarios",
        description = "Retorna las suscripciones de una lista de IDs de usuarios")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de suscripciones obtenida exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseSuscripcionDTO.class))))
    })
    @PostMapping("/usuarios")
    public ResponseEntity<CollectionModel<EntityModel<ResponseSuscripcionDTO>>> listSuscripcionesByUsuarioIds(@Valid @RequestBody List<Long> idUsuarios) {
        log.info("Solicitud de todas las suscripciones del usuario id: " + idUsuarios);
        List<Suscripcion> suscripciones = service.getSuscripcionesByUsuarioIds(idUsuarios);
        CollectionModel<EntityModel<ResponseSuscripcionDTO>> dtoList = assembler.toCollectionModel(suscripciones);
        return ResponseEntity.ok(dtoList);
    }

    // --------------------------------------------------------
    // ---------------- Seccion PUT ---------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Cancelar suscripcion de un usuario",
        description = "Cancela la suscripcion activa de un usuario")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Suscripcion cancelada exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseSuscripcionDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "No se pudo cancelar la suscripcion. Verifique que el usuario tenga una suscripcion activa.",
            content = @Content)
    })
    @PutMapping("/{idUsuario}")
    public ResponseEntity<EntityModel<ResponseSuscripcionDTO>> cancelarSuscripcion(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Long idUsuario){
        Suscripcion suscripcionCancelada = service.cancelarSuscripcion(idUsuario);
        if(suscripcionCancelada == null){
            return ResponseEntity.badRequest().build();
        }
        EntityModel<ResponseSuscripcionDTO> response = assembler.toModel(suscripcionCancelada);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Actualizar tipo de suscripcion",
        description = "Actualiza el tipo de suscripcion de un usuario")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Suscripcion actualizada exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseSuscripcionDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "No se pudo actualizar la suscripcion. Verifique que el usuario tenga una suscripcion activa y que el nuevo tipo sea valido.",
            content = @Content)
    })
    @PutMapping("/actualizar/{idUsuario}")
    public ResponseEntity<EntityModel<ResponseSuscripcionDTO>> actualizarSuscripcion(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Long idUsuario,
            @Valid @RequestBody RequestSuscripcionDTO request){
        Suscripcion suscripcionActualizada = service.actualizarSuscripcion(idUsuario, request.getNombreTipoSuscripcion());
        if(suscripcionActualizada == null){
            return ResponseEntity.badRequest().build();
        }
        EntityModel<ResponseSuscripcionDTO> response = assembler.toModel(suscripcionActualizada);
        return ResponseEntity.ok(response);
    }
}
