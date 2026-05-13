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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/contextos-fisicos")
public class ContextoFisicoController {

    @Autowired
    private ContextoFisicoService service;

    @Autowired
    private ContextoFisicoMapper mapper;
    
    //-------------------------------------------------------------
    //------------------------- SECCIÓN GET -----------------------
    //-------------------------------------------------------------

    @GetMapping
    public ResponseEntity<?> getContextos(){
        log.info("Contexto físico solicitado");
        List<ContextoFisico> contextos = service.getContextos();

        if(contextos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay contextos disponibles");
        }

        List<ResponseContextoFisicoDTO> response = contextos.stream().map(c -> mapper.build(c)).toList();

        log.debug("Contextos encontrados: ", response);
        return ResponseEntity.ok(response);
        
    }
}
