package com.madoscientista.valoraciones.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.valoraciones.dto.ValoracionDTO.PromedioValoracionDTO;
import com.madoscientista.valoraciones.dto.ValoracionDTO.RequestValoracionDTO;
import com.madoscientista.valoraciones.dto.ValoracionDTO.ResponseValoracionDTO;
import com.madoscientista.valoraciones.mapper.ValoracionMapper;
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
@Tag(name = "Valoraciones", description = "API de valoraciones")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/valoraciones")
public class ValoracionController {

    private final ValoracionService service;
    private final ValoracionMapper valoracionMapper;

    @Operation(
        summary = "Obtener valoración por ID",
        description = "Retorna una valoración según su ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Valoración encontrada exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseValoracionDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontró valoración con el ID indicado",
            content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getValoracionById(
            @Parameter(description = "ID de la valoración", example = "8")
            @PathVariable Long id) {
        log.info("Solicitud de valoración id: " + id);
        ResponseValoracionDTO response = valoracionMapper.toDTO(service.getValoracionById(id));
        if (response == null) {
            log.info("Valoración no encontrada");
            return ResponseEntity.notFound().build();
        }
        log.info("Valoración encontrada");
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Obtener valoraciones por ID de ejercicio",
        description = "Retorna todas las valoraciones de un ejercicio según su ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de valoraciones obtenida exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseValoracionDTO.class))))
    })
    @GetMapping("/ejercicio/{idEjercicio}")
    public List<ResponseValoracionDTO> getValoracionesByEjercicio(
            @Parameter(description = "ID del ejercicio", example = "15")
            @PathVariable Long idEjercicio) {
        log.info("Solicitud de valoraciones del ejercicio id: " + idEjercicio);
        return valoracionMapper.toDTOList(service.getValoracionesByEjercicio(idEjercicio));
    }

    @Operation(
        summary = "Obtener valoraciones por ID de usuario",
        description = "Retorna todas las valoraciones realizadas por un usuario según su ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de valoraciones obtenida exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseValoracionDTO.class))))
    })
    @GetMapping("/usuario/{idUsuario}")
    public List<ResponseValoracionDTO> getValoracionesByUsuario(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Long idUsuario) {
        log.info("Solicitud de valoraciones del usuario id: " + idUsuario);
        return valoracionMapper.toDTOList(service.getValoracionesByUsuario(idUsuario));
    }

    @Operation(
        summary = "Obtener promedio de valoraciones de un ejercicio",
        description = "Retorna el promedio de puntuación y el total de valoraciones de un ejercicio")
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
        summary = "Crear una nueva valoración",
        description = "Crea una valoración para un ejercicio. Si el usuario ya valoró este ejercicio, debe usar PUT.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Valoración creada exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseValoracionDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "El usuario ya ha valorado este ejercicio. Use PUT para actualizar.",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> postValoracion(@Valid @RequestBody RequestValoracionDTO request) {
        log.info("Solicitud creación de una nueva valoración");
        Valoracion nuevaValoracion = service.postValoracion(request);
        if (nuevaValoracion == null) {
            log.info("No se pudo crear la valoración: el usuario ya valoró este ejercicio");
            return ResponseEntity.badRequest()
                .body("El usuario ya ha valorado este ejercicio. Use PUT para actualizar.");
        }
        ResponseValoracionDTO response = valoracionMapper.toDTO(nuevaValoracion);
        log.debug("Valoración creada", response);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Actualizar una valoración",
        description = "Actualiza la puntuación y/o comentario de una valoración existente")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Valoración actualizada exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseValoracionDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontró valoración con el ID indicado",
            content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> putValoracion(
            @Parameter(description = "ID de la valoración a actualizar", example = "8")
            @PathVariable Long id,
            @Valid @RequestBody RequestValoracionDTO request) {
        log.info("Solicitud actualización de valoración id: " + id);
        Valoracion actualizada = service.putValoracion(id, request);
        if (actualizada == null) {
            log.info("Valoración no encontrada");
            return ResponseEntity.notFound().build();
        }
        ResponseValoracionDTO response = valoracionMapper.toDTO(actualizada);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Eliminar una valoración",
        description = "Elimina una valoración según su ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Valoración eliminada correctamente",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontró valoración con el ID indicado",
            content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteValoracion(
            @Parameter(description = "ID de la valoración a eliminar", example = "8")
            @PathVariable Long id) {
        log.info("Solicitud eliminación de valoración id: " + id);
        boolean eliminada = service.deleteValoracion(id);
        if (!eliminada) {
            log.info("Valoración no encontrada");
            return ResponseEntity.notFound().build();
        }
        log.info("Valoración eliminada");
        return ResponseEntity.ok("Valoración eliminada correctamente");
    }
}
