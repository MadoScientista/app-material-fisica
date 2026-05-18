package com.madoscientista.usuarios.controller;

import java.util.List;
import java.util.Set;

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

import com.madoscientista.usuarios.dto.ejercicioDTO.RequestEjercicioDTO;
import com.madoscientista.usuarios.dto.ejercicioDTO.ResponseEjercicioDTO;
import com.madoscientista.usuarios.mapper.EjercicioMapper;
import com.madoscientista.usuarios.model.Ejercicio;
import com.madoscientista.usuarios.service.EjercicioService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/ejercicios")
public class EjercicioController {

    @Autowired
    private EjercicioService service;

    @Autowired
    private EjercicioMapper ejercicioMapper;


    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // Genera un ejercicio nuevo a partir de los datos del request y el id del usuario que lo crea
    @PostMapping("usuario/{id}")
    public ResponseEntity<ResponseEjercicioDTO> postGenerarEjercicio(@Valid @RequestBody RequestEjercicioDTO request, @PathVariable Long id){
        log.info("Solicitud de creación de un ejercicio");
        Ejercicio ejercicio = service.postEjercicio(request, id);

        if(ejercicio != null){
            log.info("Ejercicio creado con éxito");
            return ResponseEntity.status(HttpStatus.CREATED).body(ejercicioMapper.toDTO(ejercicio));
        }

        log.info("No se pudo crear el ejercicio");
        return ResponseEntity.badRequest().build();
    }

    // Retorna una lista de ejercicios creados por un Set de usuarios
    @PostMapping("usuarios")
    public ResponseEntity<List<ResponseEjercicioDTO>> listarEjerciciosDeUsuarios(@Valid @RequestBody Set<Long> idEjercicio){
        List<Ejercicio> listaEjercicios = service.listarEjerciciosDeUSuarios(idEjercicio);

        List<ResponseEjercicioDTO> response = ejercicioMapper.toDTOList(listaEjercicios);
        return ResponseEntity.ok(response);
    }

    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    // Comparte un ejercicio con una lista de usuarios
    @PutMapping("/compartir/{idCreador}/{idEjercicio}")
    public ResponseEntity<List<Long>> putCompartirEjercicio(@PathVariable Long idCreador, @PathVariable Long idEjercicio, @Valid @RequestBody List<Long> idsUsuarios){
        log.info("Solicitud para compartir el ejercicio id: " + idEjercicio);
        Ejercicio compartido = service.compartirEjercicio(idCreador, idEjercicio, idsUsuarios);

        if(compartido != null){
            log.info("Ejercicio compartido");
            return ResponseEntity.ok(idsUsuarios);
        }

        log.info("No se pudo compartir el ejercicio");
        return ResponseEntity.badRequest().build();
    }

    // Deja de compartir un ejercicio con una lista de usuarios
    @PutMapping("/dejar-compartir/{idCreador}/{idEjercicio}")
    public ResponseEntity<List<Long>> putDejarCompartirEjercicio(@PathVariable Long idCreador, @PathVariable Long idEjercicio,@Valid @RequestBody List<Long> idsUsuarios){
        log.info("Solicitud para dejar de compartir el ejercicio id: " + idEjercicio);
        Ejercicio resultado = service.dejarDeCompartirEjercicio(idEjercicio, idCreador, idsUsuarios);

        if(resultado != null){
            log.info("Operación exitosa");
            return ResponseEntity.ok(idsUsuarios);
        }

        log.info("Operación fallida");
        return ResponseEntity.badRequest().build();
    }


    // --------------------------------------------------------
    // ------------------ Sección DELETE ----------------------
    // --------------------------------------------------------

    // Elimina un ejercicio creado por un usuario según el ID del ejercicio
    @DeleteMapping("usuario/{idUsuario}/{idEjercicio}")
    public ResponseEntity<ResponseEjercicioDTO> deleteEjercicio(@PathVariable Long idUsuario, @PathVariable Long idEjercicio){
        log.info("Solicitud de eliminación del ejercicio id: " + idEjercicio);
        boolean eliminado = service.deleteEjercicio(idUsuario, idEjercicio);

        if(eliminado){
            log.info("Ejercicio eliminado con éxito");
            return ResponseEntity.noContent().build();
        }

        log.info("No se pudo eliminar el ejercicio");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    // Retorna un ejercicio por su id
    @GetMapping("{idEjercicio}")
    public ResponseEntity<ResponseEjercicioDTO> getEjercicioById(@PathVariable Long idEjercicio){
        log.info("Solicitud de ejercicio id: " + idEjercicio);
        Ejercicio ejercicio = service.getEjercicioById(idEjercicio);

        if(ejercicio == null){
            log.info("Ejercicio no encontrado");
            return ResponseEntity.noContent().build();
        }

        ResponseEjercicioDTO dto = ejercicioMapper.toDTO(ejercicio);
        log.debug("Ejercicio encontrado: {} ", dto);
        return ResponseEntity.ok(dto);

    }
    // Retorna una lista de todos los ejercicios disponibles en la plataforma
    @GetMapping
    public ResponseEntity<List<ResponseEjercicioDTO>> getEjercicios(){
        log.info("Solicitud de la lista de ejercicios disponibles en la plataforma");
        List<Ejercicio> ejercicios = service.getEjercicios();

        if(ejercicios.isEmpty()){
            log.info("No se encontraron ejercicios en BD");
            return ResponseEntity.notFound().build();
        }

        log.info("Ejercicios encontrados");
        List<ResponseEjercicioDTO> dtoList = ejercicioMapper.toDTOList(ejercicios);
        return ResponseEntity.ok(dtoList);
    }

    // Retorna una lista de ejercicios creados por un usuario según el ID del usuario
    @GetMapping("creados/{id}")
    public ResponseEntity<List<ResponseEjercicioDTO>> getEjerciciosCreadosByUsuario(@PathVariable Long id){
        log.info("Solicitud de ejercicios creados y almacenados por el usuario id: " + id);
        List<Ejercicio> ejercicios = service.getEjerciciosCreadosByUsuario(id);

        if(ejercicios.isEmpty()){
            log.info("Ejercicios no encontrados");
            return ResponseEntity.notFound().build();
        }
        log.debug("Ejercicios encontrados", ejercicios);
        return ResponseEntity.ok(ejercicioMapper.toDTOList(ejercicios));
    }


    // Retorna una lista con los ejercicios compartidos con el usuario según el ID del usuario
    @GetMapping("compartidos/{id}")
    public ResponseEntity<List<ResponseEjercicioDTO>> getEjerciciosCompartidosAUsuario(@PathVariable Long id){
        log.info("Solicitud ejercicios compartidos por el usuario id: " + id);
        List<Ejercicio> ejercicios = service.getEjerciciosCompartidosAUsuario(id);

        if(ejercicios.isEmpty()){
            log.info("Ejercicios no encontrados");
            return ResponseEntity.notFound().build();
        }

        log.info("Ejercicios encontrados");
        return ResponseEntity.ok(ejercicioMapper.toDTOList(ejercicios));
    }
}
