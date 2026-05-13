package com.madoscientista.generador_ejercicios.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.generador_ejercicios.dto.plantillaEnunciadoDTO.ResponsePlantillaEnunciadoDTO;
import com.madoscientista.generador_ejercicios.mapper.PlantillaEnunciadoMapper;
import com.madoscientista.generador_ejercicios.model.PlantillaEnunciado;
import com.madoscientista.generador_ejercicios.service.PlantillaEnunciadoService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("api/v1/plantillas")
public class PlantillaEnunciadoController {

    @Autowired
    PlantillaEnunciadoService service;

    @Autowired
    PlantillaEnunciadoMapper mapper;

    //-------------------------------------------------------------
    //------------------------- SECCIÓN GET -----------------------
    //-------------------------------------------------------------


    // Retorna todas las plantillas disponibles
    @GetMapping
    public ResponseEntity<?> getPlantillas(){

        log.info("Se solicitaron las plantillas disponibles");

        List<PlantillaEnunciado> plantillas = service.getPlantillas();
        List<ResponsePlantillaEnunciadoDTO> respuesta = new ArrayList<>();
        
        if(plantillas != null){
            for(PlantillaEnunciado p : plantillas){
                respuesta.add(mapper.build(p));
            }
            return ResponseEntity.ok(respuesta);
        }

        log.info("No se encontraron plantillas");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron plantillas");
    }

    // Retorna una plantilla filtrada por su id
    @GetMapping("/{id}")
    public ResponseEntity<?> getPlantillaById(@PathVariable long id){

        log.info("Se solicitó la plantilla con id: " + id);

        PlantillaEnunciado plantilla = service.getPlantillaById(id);
        
        if(plantilla != null){
            log.info("Plantilla encontrada");
            return ResponseEntity.ok(mapper.build(plantilla));
        }

        log.info("No se encontró la plantilla");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la plantilla con id: " + id);
    }

    // Retorna plantillas filtradas por tema
    @GetMapping("/temas/{tema}")
    public ResponseEntity<?> getPlantillaByTema(@PathVariable String tema){

        log.info("Se solicitó la lista de plantillas para el tema: " + tema);
        List<PlantillaEnunciado> plantillas = service.getPlantillasByTema(tema);

        if(plantillas != null && !plantillas.isEmpty()){
            List<ResponsePlantillaEnunciadoDTO> respuesta = new ArrayList<>();
            for(PlantillaEnunciado p : plantillas){
                respuesta.add(mapper.build(p));
            }

            log.info("Plantillas encontradas");
            return ResponseEntity.ok(respuesta);
        }

        log.info("No se encontraron plantillas");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron plantillas para el tema: " + tema);
    }

    

}
