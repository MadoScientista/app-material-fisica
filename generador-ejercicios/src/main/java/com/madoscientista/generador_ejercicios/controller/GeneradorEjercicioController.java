package com.madoscientista.generador_ejercicios.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.generador_ejercicios.dto.ejercicioDTO.RequestEjercicioDTO;
import com.madoscientista.generador_ejercicios.mapper.EjercicioMapper;
import com.madoscientista.generador_ejercicios.model.EjercicioFisica;
import com.madoscientista.generador_ejercicios.service.EjercicioFisicaService;

@RestController
@RequestMapping("/api/v1/generar-ejercicio")
public class GeneradorEjercicioController {

    @Autowired
    private EjercicioFisicaService service;
    
    private static final EjercicioMapper ejercicioMapper = new EjercicioMapper();


    // ------------------------------------------------------------
    // --------------- GENERAR EJERCICIO MRU ----------------------
    // ------------------------------------------------------------

    @PostMapping
    public ResponseEntity<?> getEjercicioMRU(@RequestBody RequestEjercicioDTO request){

        EjercicioFisica ejercicio = service.getEjercicio(
            request.getTema(),
            request.getContexto(),
            request.getIncognita(),
            request.getDificultad(),
            request.isResultadoPositivo()
        );

        if(ejercicio == null){
            return ResponseEntity.status(500).body("Error al generar el ejercicio");   
        }

        return ResponseEntity.ok(ejercicioMapper.build(ejercicio));
    }
}
