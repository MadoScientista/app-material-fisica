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
        List<PlantillaEnunciado> plantillas = service.getPlantillas();
        List<ResponsePlantillaEnunciadoDTO> respuesta = new ArrayList<>();
        
        if(plantillas != null){
            for(PlantillaEnunciado p : plantillas){
                respuesta.add(mapper.build(p));
            }
            return ResponseEntity.ok(respuesta);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron plantillas");
    }

    // Retorna una plantilla filtrada por su id
    @GetMapping("/{id}")
    public ResponseEntity<?> getPlantillaById(@PathVariable long id){
        PlantillaEnunciado plantilla = service.getPlantillaById(id);
        
        if(plantilla != null){
            return ResponseEntity.ok(mapper.build(plantilla));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la plantilla con id: " + id);
    }

    // Retorna plantillas filtradas por tema
    @GetMapping("/temas/{tema}")
    public ResponseEntity<?> getPlantillaByTema(@PathVariable String tema){
        List<PlantillaEnunciado> plantillas = service.getPlantillasByTema(tema);

        if(plantillas != null && !plantillas.isEmpty()){
            List<ResponsePlantillaEnunciadoDTO> respuesta = new ArrayList<>();
            for(PlantillaEnunciado p : plantillas){
                respuesta.add(mapper.build(p));
            }
            return ResponseEntity.ok(respuesta);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron plantillas para el tema: " + tema);
    }

    

}
