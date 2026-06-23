package com.madoscientista.material.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.material.assembler.ItemEjercicioAssembler;
import com.madoscientista.material.dto.itemEjercicioDTO.RequestItemEjercicioDTO;
import com.madoscientista.material.dto.itemEjercicioDTO.ResponseItemEjercicioDTO;
import com.madoscientista.material.mapper.ItemEjercicioMapper;
import com.madoscientista.material.model.ItemEjercicio;
import com.madoscientista.material.service.ItemEjercicioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Items Ejercicio V2", description = "API de gestion de items de ejercicios con HATEOAS")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/item-ejercicios")
public class ItemEjercicioControllerV2 {

    private final ItemEjercicioService ieService;
    private final ItemEjercicioMapper ieMapper;
    private final ItemEjercicioAssembler assembler;

    // --------------------------------------------------------
    // ------------------ Seccion GET -------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Obtenera todos los items de ejercicios",
        description = "Retorna todos los items de ejercicios disponibles en la plataforma")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de items de ejercicios encontrada",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseItemEjercicioDTO.class)))),
        @ApiResponse(
            responseCode = "400",
            description = "Error en la solicitud o no se encontraron items de ejercicios",
            content = @Content)
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ResponseItemEjercicioDTO>>> getItemEjercicios(){
        log.info("Lista de items de ejercicios solicitada");
        List<ItemEjercicio> itemEjercicioList = ieService.getItemEjercicios();

        if(itemEjercicioList == null){
            log.info("No se encontraron items de ejercicios");
            return ResponseEntity.notFound().build();
        }
        log.info("Iteme ejercicios encontrados");
        CollectionModel<EntityModel<ResponseItemEjercicioDTO>> dtoList = assembler.toCollectionModel(itemEjercicioList);

        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Filtrar item de ejercicios por ID",
        description = "Filtra un item de ejercicios considerando el ID en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Item de ejercicio encontrado",
            content = @Content(schema = @Schema(implementation = ResponseItemEjercicioDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Item de ejercicio no encontrado",
            content = @Content)
    })
    @GetMapping("{idItemEjercicio}")
    public ResponseEntity<EntityModel<ResponseItemEjercicioDTO>> getItemEjercicioById(@PathVariable Long idItemEjercicio){
        log.info("Ejercicio id: " + idItemEjercicio + " solicitado");
        ItemEjercicio ie = ieService.getItemEjercicioById(idItemEjercicio);

        if(ie == null){
            log.info("No se encontro el ejercicio con id: " + idItemEjercicio);
            return ResponseEntity.notFound().build();
        }

        log.info("Ejercicio encontrado");
        EntityModel<ResponseItemEjercicioDTO> dto = assembler.toModel(ie);
        return ResponseEntity.ok(dto);
    }

    @Operation(
        summary = "Obtener items ejercicios de un usuario",
        description = "Retorna la lista de ejercicios creados y almacenados por un usuario indicando su ID en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Ejercicios encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseItemEjercicioDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron ejercicios o el usuario no existe",
            content = @Content)
    })
    @GetMapping("usuario/{idUsuarioCreador}")
    public ResponseEntity<CollectionModel<EntityModel<ResponseItemEjercicioDTO>>> getItemEjercicioByIdUsuarioCreador(
        @PathVariable Long idUsuarioCreador){

        log.info("Lista de items de ejercicios del usuario id: " + idUsuarioCreador + " solicitados");
        List<ItemEjercicio> ieList = ieService.getItemEjercicioByIdUsuarioCreador(idUsuarioCreador);

        if(ieList.isEmpty()){
            log.info("Lista de items de ejercicios no encontrada o vacia");
            return ResponseEntity.notFound().build();
        }

        log.info("Lista de items encontrada");
        CollectionModel<EntityModel<ResponseItemEjercicioDTO>> dtoList = assembler.toCollectionModel(ieList);
        return ResponseEntity.ok(dtoList);
    }

    // --------------------------------------------------------
    // ------------------ Seccion POST ------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Crear un item de ejercicios",
        description = "Crea un ejercicio y lo almacena en la plataforma")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "El item de ejercicios se ha creado exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseItemEjercicioDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "No se ha podido crear el ejercicio",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<ResponseItemEjercicioDTO>> postItemEjercicio(
        @Valid @RequestBody RequestItemEjercicioDTO request){

        log.debug("Solicitud de creacion de item: {}", request);
        ItemEjercicio ie = ieMapper.toEntity(request);

        ItemEjercicio ieCreado = ieService.postItemEjercicio(ie);
        log.info("Item de ejercicio almacenado en DB");

        if(ieCreado == null){
            return ResponseEntity.internalServerError().build();
        }

        EntityModel<ResponseItemEjercicioDTO> dto = assembler.toModel(ieCreado);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Operation(
        summary = "Crear varios items de ejercicios",
        description = "Endpoint para crear varios items de ejercicios a partir de una lista de DTOs")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Lista de items de ejercicios creada con exito",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseItemEjercicioDTO.class)))),
        @ApiResponse(
            responseCode = "400",
            description = "No se ha podido crear la lista de items de ejercicios",
            content = @Content)
    })
    @PostMapping("lista")
    public ResponseEntity<CollectionModel<EntityModel<ResponseItemEjercicioDTO>>> postItemEjercicioLista(
        @Valid @RequestBody List<RequestItemEjercicioDTO> request){

        log.debug("Solicitud de creacion de una lista de items: {}", request);
        List<ItemEjercicio> ieList = ieMapper.toEntities(request);

        List<ItemEjercicio> ieListCreada = ieService.postListaItemEjercicio(ieList);
        log.info("Lista de items almacenados en DB");

        if(ieListCreada.isEmpty()){
            return ResponseEntity.internalServerError().build();
        }

        CollectionModel<EntityModel<ResponseItemEjercicioDTO>> dtoList = assembler.toCollectionModel(ieListCreada);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoList);
    }

    // --------------------------------------------------------
    // ------------------ Seccion DELETE -----------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Elimina un item de ejercicio filtrado por ID",
        description = "Elimina un item de ejercicio filtrado por el ID indicado en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Item de ejercicio eliminado existosamente",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "Item de ejercicio no encontrado",
            content = @Content)
    })
    @DeleteMapping("{idItemEjercicio}")
    public ResponseEntity<Void> deleteItemEjercicio(@PathVariable Long idItemEjercicio){

        log.info("Solicitud de eliminacion del item de ejercicio id: " + idItemEjercicio);
        ItemEjercicio ie = ieService.getItemEjercicioById(idItemEjercicio);

        if(ie == null){
            log.info("Item de ejercicio no encontrado");
            return ResponseEntity.notFound().build();
        }

        ieService.deleteItemEjercicioById(idItemEjercicio);
        log.info("Item de ejercicio eliminado con exito");
        return ResponseEntity.noContent().build();
    }

    // --------------------------------------------------------
    // ------------------ Seccion PUT -------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Actualizar item de ejercicio filtrado por ID",
        description = "Actualiza un item de ejercicio filtrando por el ID indicado en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Item de ejercicio actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseItemEjercicioDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Item de ejercicio no encontrado",
            content = @Content)
    })
    @PutMapping("{idItemEjercicio}")
    public ResponseEntity<EntityModel<ResponseItemEjercicioDTO>> putItemEjercicio(
        @PathVariable Long idItemEjercicio,
        @Valid @RequestBody RequestItemEjercicioDTO request){

            log.debug("Solicitud de actualizacion del item: {}", request);
            ItemEjercicio ie = ieMapper.toEntity(request);
            ItemEjercicio ieActualizado = ieService.putItemEjercicio(idItemEjercicio, ie);

            if(ieActualizado == null){
                log.info("Ejercicio no encontrado");
                return ResponseEntity.notFound().build();
            }

            log.info("Ejercicio actualizado con exito");
            EntityModel<ResponseItemEjercicioDTO> dto = assembler.toModel(ieActualizado);
            return ResponseEntity.ok(dto);
        }
}
