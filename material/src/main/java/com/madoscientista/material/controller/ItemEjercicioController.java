package com.madoscientista.material.controller;

import java.util.List;

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

@Tag(name = "Item Ejercicio", description = "API de gestión de items de ejercios")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/item-ejercicios")
public class ItemEjercicioController {

    private final ItemEjercicioService ieService;
    private final ItemEjercicioMapper ieMapper;


    // ----------------------------------------------------------------------------------------------
    // -------------------------------------- Sección GET -------------------------------------------
    // ----------------------------------------------------------------------------------------------

    // ------------------------- Obtener todos los ítems de ejercicios ------------------------------

    @Operation(
        summary = "Obtenera todos los ítems de ejercicios",
        description = "Retorna todos los ítems de ejercicios disponibles en la plataforma"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de ítems de ejercicios encontrada",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseItemEjercicioDTO.class)))),
        @ApiResponse(
            responseCode = "400",
            description = "Error en la solicitud o no se encontraron ítems de ejercicios",
            content = @Content
        )
    })

    @GetMapping
    public ResponseEntity<List<ResponseItemEjercicioDTO>> getItemEjercicios(){
        log.info("Lista de items de ejercicios solicitada");
        List<ItemEjercicio> itemEjercicioList = ieService.getItemEjercicios();

        if(itemEjercicioList == null){
            log.info("No se encontraron items de ejercicios");
            return ResponseEntity.notFound().build();
        }
        log.info("Iteme ejercicios encontrados");
        List<ResponseItemEjercicioDTO> dtoList = ieMapper.toDTOList(itemEjercicioList);

        return ResponseEntity.ok(dtoList);
    }



    // ----------------------------------- Filtrar ítem de ejercicios por ID -------------------------------

    @Operation(
        summary = "Filtrar ítem de ejercicios por ID",
        description = "Filtra un ítem de ejercicios considerando el ID en la ruta"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Ítem de ejercicio encontrado",
            content = @Content(schema = @Schema(implementation = ResponseItemEjercicioDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Ítem de ejercicio no encontrado",
            content = @Content
        )
    })
    @GetMapping("{idItemEjercicio}")
    public ResponseEntity<ResponseItemEjercicioDTO> getItemEjercicioById(@PathVariable Long idItemEjercicio){
        log.info("Ejercicio id: " + idItemEjercicio + " solicitado" );
        ItemEjercicio ie = ieService.getItemEjercicioById(idItemEjercicio);

        if(ie == null){
            log.info("No se encontró el ejercicio con id: " + idItemEjercicio);
            return ResponseEntity.notFound().build();
        }

        log.info("Ejercicio encontrado");
        ResponseItemEjercicioDTO dto = ieMapper.toDTO(ie);
        return ResponseEntity.ok(dto);
    }

    // ---------------------------- Obtener ítems ejercicios de un usuario ------------------------------

    @Operation(
        summary = "Obtener ítems ejercicios de un usuario",
        description = "Retorna la lista de ejercicios creados y almacenados por un usuario indicando su ID en la ruta"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Ejercicios encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseItemEjercicioDTO.class)))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron ejercicios o el usuario no existe",
            content = @Content
        )
    })
    @GetMapping("usuario/{idUsuarioCreador}")
    public ResponseEntity<List<ResponseItemEjercicioDTO>> getItemEjercicioByIdUsuarioCreador(
        @PathVariable Long idUsuarioCreador){

        log.info("Lista de items de ejercicios del usuario id: " + idUsuarioCreador + " solicitados");
        List<ItemEjercicio> ieList = ieService.getItemEjercicioByIdUsuarioCreador(idUsuarioCreador);

        if(ieList.isEmpty()){
            log.info("Lista de items de ejercicios no encontrada o vacía");
            return ResponseEntity.notFound().build();
        }

        log.info("Lista de items encontrada");
        List<ResponseItemEjercicioDTO> dtoList = ieMapper.toDTOList(ieList);
        return ResponseEntity.ok(dtoList);
    }

    // -----------------------------------------------------------------------------------------------
    // -------------------------------------- Sección POST -------------------------------------------
    // -----------------------------------------------------------------------------------------------

    // ------------------------------- Crea un ítem de ejercicios ------------------------------------

    @Operation(
        summary = "Crear un ítem de ejercicios",
        description = "Crea un ejercicio y lo almacena en la plataforma"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "El ítem de ejercicios se ha creado exitósamente",
            content = @Content(schema = @Schema(implementation = ResponseItemEjercicioDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "No se ha podido crear el ejercicio",
            content = @Content
        )
    })
    @PostMapping
    public ResponseEntity<ResponseItemEjercicioDTO> postItemEjercicio(
        @Valid @RequestBody RequestItemEjercicioDTO request){

        log.debug("Solicitud de creación de item: {}", request);
        ItemEjercicio ie = ieMapper.toEntity(request);

        ItemEjercicio ieCreado = ieService.postItemEjercicio(ie);
        log.info("Item de ejercicio almacenado en DB");

        if(ieCreado == null){
            return ResponseEntity.internalServerError().build();
        }

        ResponseItemEjercicioDTO dto = ieMapper.toDTO(ieCreado);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);        
    }

    
    // ------------------------- Crear varios ítems de ejercicios -----------------------------

    @Operation(
        summary = "Crear varios ítems de ejercicios",
        description = "Endpoint para crear varios ítems de ejercicios a partir de una lista de DTOs"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Lista de ítems de ejercicios creada con éxito",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseItemEjercicioDTO.class)))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "No se ha podido crear la lista de ítems de ejercicios",
            content = @Content
        )
    })
    @PostMapping("lista")
    public ResponseEntity<List<ResponseItemEjercicioDTO>> postItemEjercicio(
        @Valid 
        @RequestBody 
        List<RequestItemEjercicioDTO> request){

        log.debug("Solicitud de creación de una lista de items: {}", request);
        List<ItemEjercicio> ieList = ieMapper.toEntities(request);

        List<ItemEjercicio> ieListCreada = ieService.postListaItemEjercicio(ieList);
        log.info("Lista de items almacenados en DB");

        if(ieListCreada.isEmpty()){
            return ResponseEntity.internalServerError().build();
        }

        List<ResponseItemEjercicioDTO> dtoList = ieMapper.toDTOList(ieListCreada);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoList);        
    }


    // -----------------------------------------------------------------------------------------------
    // -------------------------------------- Sección DELETE -----------------------------------------
    // -----------------------------------------------------------------------------------------------

    // ---------------------------------- Elimina un item de ejercicio -------------------------------

    @Operation(
        summary = "Elimina un item de ejercicio filtrado por ID",
        description = "Elimina un ítem de ejercicio filtrado por el ID indicado en la ruta"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Ítem de ejercicio eliminado existosamente",
            content = @Content(schema = @Schema(implementation = ResponseItemEjercicioDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Ítem de ejercicio no encontrado",
            content = @Content
        )
    })
    @DeleteMapping("{idItemEjercicio}")
    public ResponseEntity<ResponseItemEjercicioDTO> deleteItemEjercicio(@PathVariable Long idItemEjercicio){
        
        log.info("Solicitud de eliminación del item de ejercicio id: " + idItemEjercicio);
        ItemEjercicio ie = ieService.getItemEjercicioById(idItemEjercicio);

        if(ie == null){
            log.info("Item de ejercicio no encontrado");
            return ResponseEntity.notFound().build();
        }

        log.info("Item de ejercicio eliminado con éxito");
        ResponseItemEjercicioDTO dto = ieMapper.toDTO(ie);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(dto);
    }

    // -----------------------------------------------------------------------------------------------
    // -------------------------------------- Sección PUT --------------------------------------------
    // -----------------------------------------------------------------------------------------------

    // ----------------- Actualizar Ítem de ejercicio filtrado por ID --------------------------------

    @Operation(
        summary = "Actualizar ítem de ejercicio filtrado por ID",
        description = "Actualiza un ítem de ejercicio filtrando por el ID indicado en la ruta"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Ítem de ejercicio actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseItemEjercicioDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Ítem de ejercicio no encontrado",
            content = @Content
        )
    })
    @PutMapping("{idItemEjercicio}")
    public ResponseEntity<ResponseItemEjercicioDTO> putItemEjercicio(
        @PathVariable Long idItemEjercicio,
        @Valid @RequestBody RequestItemEjercicioDTO request){

            log.debug("Solicitud de actualización del item: {}", request);
            ItemEjercicio ie = ieMapper.toEntity(request);
            ItemEjercicio ieActualizado = ieService.putItemEjercicio(idItemEjercicio, ie);

            if(ieActualizado == null){
                log.info("Ejercicio no encontrado");
                return ResponseEntity.notFound().build();
            }

            log.info("Ejercicio actualizado con éxito");
            ResponseItemEjercicioDTO dto = ieMapper.toDTO(ieActualizado);
            return ResponseEntity.ok(dto);
        }


}
