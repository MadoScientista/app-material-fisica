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

import com.madoscientista.generador_ejercicios.assembler.PlantillaEnunciadoAssembler;
import com.madoscientista.generador_ejercicios.dto.plantillaEnunciadoDTO.ResponsePlantillaEnunciadoDTO;
import com.madoscientista.generador_ejercicios.model.PlantillaEnunciado;
import com.madoscientista.generador_ejercicios.service.PlantillaEnunciadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Plantillas V2")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/plantillas")
public class PlantillaEnunciadoControllerV2 {

    private final PlantillaEnunciadoService service;
    private final PlantillaEnunciadoAssembler assembler;

    @Operation(summary = "Obtener todas las plantillas", description = "Lista de todas las plantillas disponibles")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Plantillas encontradas"),
        @ApiResponse(responseCode = "404", description = "No se encontraron plantillas")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ResponsePlantillaEnunciadoDTO>>> getPlantillas() {
        log.info("Se solicitaron las plantillas disponibles");
        List<PlantillaEnunciado> plantillas = service.getPlantillas();

        if (plantillas == null || plantillas.isEmpty()) {
            log.info("No se encontraron plantillas");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        CollectionModel<EntityModel<ResponsePlantillaEnunciadoDTO>> response = assembler.toCollectionModel(plantillas);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener plantilla por ID", description = "Retorna una plantilla filtrada por su id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Plantilla encontrada"),
        @ApiResponse(responseCode = "404", description = "No se encontro la plantilla")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ResponsePlantillaEnunciadoDTO>> getPlantillaById(
            @Parameter(description = "ID de la plantilla", example = "10")
            @PathVariable long id) {
        log.info("Se solicito la plantilla con id: " + id);
        PlantillaEnunciado plantilla = service.getPlantillaById(id);

        if (plantilla == null) {
            log.info("No se encontro la plantilla");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        log.info("Plantilla encontrada");
        return ResponseEntity.ok(assembler.toModel(plantilla));
    }

    @Operation(summary = "Obtiene plantillas de un tema", description = "Retorna una lista de plantillas filtrada por su tema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de plantillas obtenida"),
        @ApiResponse(responseCode = "404", description = "No se encontraron plantillas para el tema indicado")
    })
    @GetMapping("/temas/{tema}")
    public ResponseEntity<CollectionModel<EntityModel<ResponsePlantillaEnunciadoDTO>>> getPlantillaByTema(
            @Parameter(description = "Tema del ejercicio", example = "MRU")
            @PathVariable String tema) {
        log.info("Se solicito la lista de plantillas para el tema: " + tema);
        List<PlantillaEnunciado> plantillas = service.getPlantillasByTema(tema);

        if (plantillas == null || plantillas.isEmpty()) {
            log.info("No se encontraron plantillas");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        log.info("Plantillas encontradas");
        return ResponseEntity.ok(assembler.toCollectionModel(plantillas));
    }
}
