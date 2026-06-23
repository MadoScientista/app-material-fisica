package com.madoscientista.generador_ejercicios.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.generador_ejercicios.assembler.ContextoFisicoAssembler;
import com.madoscientista.generador_ejercicios.dto.contextoFisicoDTO.ResponseContextoFisicoDTO;
import com.madoscientista.generador_ejercicios.model.ContextoFisico;
import com.madoscientista.generador_ejercicios.service.ContextoFisicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Contexto Fisico V2")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/contextos-fisicos")
public class ContextoFisicoControllerV2 {

    private final ContextoFisicoService service;
    private final ContextoFisicoAssembler assembler;

    @Operation(summary = "Obtener todos los contextos fisicos disponbibles en DB")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de contextos fisicos obtenida exitosamente"),
        @ApiResponse(responseCode = "404", description = "No se encontraron contextos fisicos")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ResponseContextoFisicoDTO>>> getContextos() {
        log.info("Contexto fisico solicitado");
        List<ContextoFisico> contextos = service.getContextos();

        if (contextos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        CollectionModel<EntityModel<ResponseContextoFisicoDTO>> response = assembler.toCollectionModel(contextos);
        log.debug("Contextos encontrados: {}", response);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener contexto fisico por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contexto fisico encontrado"),
        @ApiResponse(responseCode = "404", description = "No se encontro el contexto fisico")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ResponseContextoFisicoDTO>> getContextoById(
            @Parameter(description = "ID del contexto fisico", example = "1")
            @PathVariable int id) {
        log.info("Se solicito el contexto fisico con id: " + id);
        ContextoFisico contexto = service.getContextoFisicoById(id);

        if (contexto == null) {
            log.info("No se encontro el contexto fisico");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(assembler.toModel(contexto));
    }
}
