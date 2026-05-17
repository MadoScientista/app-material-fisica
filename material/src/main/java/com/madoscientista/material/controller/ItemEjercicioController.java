package com.madoscientista.material.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/item-ejercicios")
public class ItemEjercicioController {


    // Inyección de item ejercicio service
    @Autowired
    ItemEjercicioService ieService;

    // Inyección de item de ejercicio mapper
    @Autowired
    ItemEjercicioMapper ieMapper;


    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    // Retorna todos los items de ejercicios de la plataforma
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

    // Retorna un iteme de ejercicio filtrado por id
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

    // Retorna todos los items de ejercicios creados por un usuario
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

    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // Crea un item de ejercicio
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

        // Crea un item de ejercicio
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


    // --------------------------------------------------------
    // ------------------ Sección DELETE ----------------------
    // --------------------------------------------------------

    // Elimina un item de ejercicio
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

    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    // Actualiza un item de ejercicio filtrado por id
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
