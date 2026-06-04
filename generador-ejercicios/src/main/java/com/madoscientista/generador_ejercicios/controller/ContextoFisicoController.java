package com.madoscientista.generador_ejercicios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.generador_ejercicios.dto.contextoFisicoDTO.ResponseContextoFisicoDTO;
import com.madoscientista.generador_ejercicios.mapper.ContextoFisicoMapper;
import com.madoscientista.generador_ejercicios.model.ContextoFisico;
import com.madoscientista.generador_ejercicios.service.ContextoFisicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name="Contexto Físico")
@Slf4j
@RestController
@RequestMapping("api/v1/contextos-fisicos")
public class ContextoFisicoController {

    // Inyección de servicios
    @Autowired
    private ContextoFisicoService service;

    // Inyección de mappers
    @Autowired
    private ContextoFisicoMapper mapper;
    
    //-------------------------------------------------------------
    //------------------------- SECCIÓN GET -----------------------
    //-------------------------------------------------------------

    //--------------- Obtener todos los contextos físicos -------------------
    @Operation(summary = "Obtener todos los contextos fisicos disponbibles en DB")
    @ApiResponses({
        @ApiResponse(responseCode="200", description="Lista de contextos fisicos obtenida exitosamente"),
        @ApiResponse(responseCode="204", description="No se encontraron contextos fisicos")
    })
    
    @GetMapping
    public ResponseEntity<List<ResponseContextoFisicoDTO>> getContextos(){
        log.info("Contexto físico solicitado");
        List<ContextoFisico> contextos = service.getContextos();

        if(contextos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<ResponseContextoFisicoDTO> response = contextos.stream().map(c -> mapper.build(c)).toList();

        log.debug("Contextos encontrados: ", response);
        return ResponseEntity.ok(response);
        
    }
}
