package com.madoscientista.generador_ejercicios.controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.generador_ejercicios.assembler.EjercicioAssembler;
import com.madoscientista.generador_ejercicios.dto.ejercicioDTO.RequestEjercicioDTO;
import com.madoscientista.generador_ejercicios.dto.ejercicioDTO.ResponseEjercicioDTO;
import com.madoscientista.generador_ejercicios.mapper.EjercicioMapper;
import com.madoscientista.generador_ejercicios.model.EjercicioFisica;
import com.madoscientista.generador_ejercicios.service.EjercicioFisicaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Ejercicios V2")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/generar-ejercicio")
public class GeneradorEjercicioControllerV2 {

    private final EjercicioFisicaService service;
    private final EjercicioMapper ejercicioMapper;
    private final EjercicioAssembler assembler;

    @Operation(summary = "Generar un ejercicio MRU", description = "Genera un ejercicio de movimiento rectilíneo uniforme")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Ejercicio generado correctamente",
            content = @Content(schema = @Schema(implementation = ResponseEjercicioDTO.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Error al generar el ejercicio",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<ResponseEjercicioDTO>> getEjercicioMRU(@Valid @RequestBody RequestEjercicioDTO request) {
        log.info("Ejercicio solicitado");
        EjercicioFisica ejercicio = service.getEjercicio(
            request.getTema(),
            request.getContexto(),
            request.getIncognita(),
            request.getDificultad(),
            request.isResultadoPositivo()
        );

        if (ejercicio == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        ResponseEjercicioDTO dto = ejercicioMapper.toDTO(ejercicio);
        EntityModel<ResponseEjercicioDTO> response = assembler.toModel(dto, request);

        log.debug("Ejercicio entregado: {}", response);
        return ResponseEntity.ok(response);
    }
}
