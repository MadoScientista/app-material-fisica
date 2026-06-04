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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name="Plantillas")
@Slf4j
@RestController
@RequestMapping("api/v1/plantillas")
public class PlantillaEnunciadoController {

    // Inyección de servicios
    @Autowired
    PlantillaEnunciadoService service;

    // Inyección de mappers
    @Autowired
    PlantillaEnunciadoMapper mapper;



    //-------------------------------------------------------------
    //------------------------- SECCIÓN GET -----------------------
    //-------------------------------------------------------------

    // ---------------- Obtener todas las plantillas --------------
    @Operation(summary = "Obtener todas las plantillas", description = "Lista de todas las plantillas disponibles")
    @ApiResponses({
        @ApiResponse(responseCode="200", description="Plantillas encontradas"),
        @ApiResponse(responseCode="404", description="No se encontraron plantillas")
    })
    @GetMapping
    public ResponseEntity<List<ResponsePlantillaEnunciadoDTO>> getPlantillas(){

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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    // ---------------- Obtener plantillas por ID --------------
    @Operation(summary="Obtener plantilla por ID", description="Retorna una plantilla filtrada por su id")
    @ApiResponses({
        @ApiResponse(responseCode="200", description="Plantilla encontrada"),
        @ApiResponse(responseCode="404", description="No se encontró la plantilla")
    })

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePlantillaEnunciadoDTO> getPlantillaById(
        @Parameter(name="id", description="ID de la plantilla", example="10")
        @PathVariable long id){

        log.info("Se solicitó la plantilla con id: " + id);

        PlantillaEnunciado plantilla = service.getPlantillaById(id);
        
        if(plantilla != null){
            log.info("Plantilla encontrada");
            return ResponseEntity.ok(mapper.build(plantilla));
        }

        log.info("No se encontró la plantilla");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    // ---------------- Filtrar plantillas por tema ---------------------
    @Operation(summary="Obtiene plantillas de un tema", description="Retorna una lista de plantillas filtrada por su tema")
    @ApiResponses({
        @ApiResponse(responseCode="200", description="Lista de plantillas obtenida"),
        @ApiResponse(responseCode="404", description="No se encontraron plantillas para el tema indicado")
    })

    @GetMapping("/temas/{tema}")
    public ResponseEntity<List<ResponsePlantillaEnunciadoDTO>> getPlantillaByTema(
        @Parameter(description="Tema del ejercicio", example ="MRU")
        @PathVariable String tema){

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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    

}
