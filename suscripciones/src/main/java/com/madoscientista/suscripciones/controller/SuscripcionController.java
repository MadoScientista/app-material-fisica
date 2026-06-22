package com.madoscientista.suscripciones.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.suscripciones.dto.SuscripcionDTO.RequestSuscripcionDTO;
import com.madoscientista.suscripciones.dto.SuscripcionDTO.ResponseSuscripcionDTO;
import com.madoscientista.suscripciones.mapper.SuscripcionMapper;
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
@Tag(name = "Suscripciones", description = "API de suscripciones")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/suscripciones")
public class SuscripcionController {

    private final SuscripcionService service;
    private final SuscripcionMapper suscripcionMapper;

    // ------------------------------------------------------
    // ---------------- Sección GET -------------------------
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
    public List<ResponseSuscripcionDTO> getSuscripcionesActivas() {
        log.info("Solicitud de las notificaciones activas");
        return suscripcionMapper.toDTOList(service.getSuscripcionesActivas());
    }

    @Operation(
        summary = "Obtener usuarios con suscripciones activas",
        description = "Retorna una lista de IDs de usuarios que tienen una suscripción activa")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de IDs de usuarios con suscripción activa",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Long.class))))
    })
    @GetMapping("/usuarios-activos")
    public ResponseEntity<?> getUsuariosConSuscripcionesActivas() {
        log.info("Solicitud de id de usuarios con suscripciones activas");
        List<Long> usuariosActivos = service.getUsuariosConSuscripcionesActivas();
        return ResponseEntity.ok(usuariosActivos);
    }

    @Operation(
        summary = "Obtener suscripción por ID de usuario",
        description = "Retorna la suscripción de un usuario según su ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Suscripción encontrada exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseSuscripcionDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontró suscripción para el ID de usuario indicado",
            content = @Content)
    })
    @GetMapping("/{idUsuario}")
    public ResponseEntity<?> getSuscripcionByUsuarioId(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Long idUsuario) {
        log.info("Solicitud de la suscripción del usuario id: " + idUsuario);
        ResponseSuscripcionDTO response = suscripcionMapper.toDTO(service.getSuscripcionByUsuarioId(idUsuario));
        if (response == null) {
            log.info("Suscripción no encontrada");
            return ResponseEntity.notFound().build();
        }
        log.info("Suscripción encontrada");
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Obtener máximo de ejercicios permitidos",
        description = "Retorna el número máximo de ejercicios que puede generar un usuario según su suscripción")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Máximo de ejercicios permitidos obtenido exitosamente",
            content = @Content(schema = @Schema(implementation = Long.class)))
    })
    @GetMapping("/max-ejercicios/{idUsuario}")
    public ResponseEntity<Long> getMaxEjerciciosByUsuarioId(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Long idUsuario) {
        log.info("Solicittud de número máximo de ejercicios para el usuario id: " + idUsuario);
        Long maxEjercicios = service.getMaxEjerciciosByUsuarioId(idUsuario);
        return ResponseEntity.ok(maxEjercicios);
    }

    // ------------------------------------------------------
    // ---------------- Sección POST ------------------------
    // ------------------------------------------------------

    @Operation(
        summary = "Crear una nueva suscripción",
        description = "Crea una suscripción para un usuario indicando el tipo de suscripción")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Suscripción creada exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseSuscripcionDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "No se pudo crear la suscripción. Verifique que el tipo de suscripción sea válido.",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> postSuscripcion(@Valid @RequestBody RequestSuscripcionDTO request){
        log.info("Solicitud creación de una nueva suscripçión");
        Suscripcion nuevaSuscripcion = service.postSuscripcion(request.getIdUsuario(), request.getNombreTipoSuscripcion());
        ResponseSuscripcionDTO response = suscripcionMapper.toDTO(nuevaSuscripcion);
        if(response == null){
            log.info("No se pudo crear la suscripción");
            return ResponseEntity.badRequest().body("No se pudo crear la suscripción. Verifique que el tipo de suscripción sea válido.");
        }
        log.debug("Suscripción creada", response);
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
    public List<ResponseSuscripcionDTO> listSuscripcionesByUsuarioIds(@Valid @RequestBody List<Long> idUsuarios) {
        log.info("Solicitud de todas las suscripciones del usuario id: ", idUsuarios);
        return suscripcionMapper.toDTOList(service.getSuscripcionesByUsuarioIds(idUsuarios));
    }

    // --------------------------------------------------------
    // ---------------- Sección PUT ---------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Cancelar suscripción de un usuario",
        description = "Cancela la suscripción activa de un usuario")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Suscripción cancelada exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseSuscripcionDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "No se pudo cancelar la suscripción. Verifique que el usuario tenga una suscripción activa.",
            content = @Content)
    })
    @PutMapping("/{idUsuario}")
    public ResponseEntity<?> cancelarSuscripcion(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Long idUsuario){
        Suscripcion suscripcionCancelada = service.cancelarSuscripcion(idUsuario);
        if(suscripcionCancelada == null){
            return ResponseEntity.badRequest().body("No se pudo cancelar la suscripción. Verifique que el usuario tenga una suscripción activa.");
        }
        ResponseSuscripcionDTO response = suscripcionMapper.toDTO(suscripcionCancelada);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Actualizar tipo de suscripción",
        description = "Actualiza el tipo de suscripción de un usuario")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Suscripción actualizada exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseSuscripcionDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "No se pudo actualizar la suscripción. Verifique que el usuario tenga una suscripción activa y que el nuevo tipo sea válido.",
            content = @Content)
    })
    @PutMapping("/actualizar/{idUsuario}")
    public ResponseEntity<?> actualizarSuscripcion(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Long idUsuario,
            @Valid @RequestBody RequestSuscripcionDTO request){
        Suscripcion suscripcionActualizada = service.actualizarSuscripcion(idUsuario, request.getNombreTipoSuscripcion());
        if(suscripcionActualizada == null){
            return ResponseEntity.badRequest().body("No se pudo actualizar la suscripción. Verifique que el usuario tenga una suscripción activa y que el nuevo tipo de suscripción sea válido.");
        }
        ResponseSuscripcionDTO response = suscripcionMapper.toDTO(suscripcionActualizada);
        return ResponseEntity.ok(response);
    }
}

