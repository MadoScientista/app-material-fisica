package com.madoscientista.usuarios.controller;

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

import com.madoscientista.usuarios.dto.ejercicioDTO.RequestEjercicioDTO;
import com.madoscientista.usuarios.dto.valoracionDTO.PromedioValoracionDTO;
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
    @PostMapping("/{id}")
    public ResponseEntity<?> postGenerarEjercicio(@Valid @RequestBody RequestEjercicioDTO request, @PathVariable Long id){
        log.info("Solicitud de creación de un ejercicio");
        Ejercicio ejercicio = service.postEjercicio(request, id);

        if(ejercicio != null){
            log.info("Ejercicio creado con éxito");
            return ResponseEntity.status(HttpStatus.CREATED).body(ejercicioMapper.toDTO(ejercicio));
        }

        log.info("No se pudo crear el ejercicio");
        return ResponseEntity.badRequest().body("No se ha podido generar el ejercicio");
    }

    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    // Comparte un ejercicio con una lista de usuarios
    @PutMapping("/compartir/{idCreador}/{idEjercicio}")
    public ResponseEntity<?> putCompartirEjercicio(@PathVariable Long idCreador, @PathVariable Long idEjercicio, @Valid @RequestBody List<Long> idsUsuarios){
        log.info("Solicitud para compartir el ejercicio id: " + idEjercicio);
        Ejercicio compartido = service.compartirEjercicio(idCreador, idEjercicio, idsUsuarios);

        if(compartido != null){
            log.info("Ejercicio compartido");
            return ResponseEntity.ok("Ejercicio compartido");
        }

        log.info("No se pudo compartir el ejercicio");
        return ResponseEntity.badRequest().body("No se pudo compartir el ejercicio");
    }

    // Deja de compartir un ejercicio con una lista de usuarios
    @PutMapping("/dejar-compartir/{idCreador}/{idEjercicio}")
    public ResponseEntity<?> putDejarCompartirEjercicio(@PathVariable Long idCreador, @PathVariable Long idEjercicio,@Valid @RequestBody List<Long> idsUsuarios){
        log.info("Solicitud para dejar de compartir el ejercicio id: " + idEjercicio);
        Ejercicio resultado = service.dejarDeCompartirEjercicio(idEjercicio, idCreador, idsUsuarios);

        if(resultado != null){
            log.info("Operación exitosa");
            return ResponseEntity.ok("Ejercicio dejado de compartir");
        }

        log.info("Operación fallida");
        return ResponseEntity.badRequest().body("No se pudo dejar de compartir el ejercicio");
    }


    // --------------------------------------------------------
    // ------------------ Sección DELETE ----------------------
    // --------------------------------------------------------

    // Elimina un ejercicio creado por un usuario según el ID del ejercicio
    @DeleteMapping("/{idUsuario}/{idEjercicio}")
    public ResponseEntity<?> deleteEjercicio(@PathVariable Long idUsuario, @PathVariable Long idEjercicio){
        log.info("Solicitud de eliminación del ejercicio id: " + idEjercicio);
        boolean eliminado = service.deleteEjercicio(idUsuario, idEjercicio);

        if(eliminado){
            log.info("Ejercicio eliminado con éxito");
            return ResponseEntity.noContent().build();
        }

        log.info("No se pudo eliminar el ejercicio");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el ejercicio con id: " + idEjercicio);
    }


    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    // Retorna un ejercicio por su id
    @GetMapping("{id}")
    public ResponseEntity<Ejercicio> getEjercicioById(@PathVariable Long idEjercicio){
        Ejercicio ejercicio = service.getEjercicioById(idEjercicio);

        if(ejercicio != null){
            return ResponseEntity.ok(ejercicio);
        }

        return ResponseEntity.noContent().build();

    }
    // Retorna una lista de todos los ejercicios disponibles en la plataforma
    @GetMapping
    public ResponseEntity<?> getEjercicios(){
        log.info("Solicitud de la lista de ejercicios disponibles en la plataforma");
        List<Ejercicio> ejercicios = service.getEjercicios();

        return ResponseEntity.ok(ejercicioMapper.toDTOList(ejercicios));
    }

    // Retorna una lista de ejercicios creados por un usuario según el ID del usuario
    @GetMapping("creados/{id}")
    public ResponseEntity<?> getEjerciciosCreadosByUsuario(@PathVariable Long id){
        log.info("Solicitud de ejercicios creados y almacenados por el usuario id: " + id);
        List<Ejercicio> ejercicios = service.getEjerciciosCreadosByUsuario(id);

        log.debug("Ejercicios encontrados", ejercicios);;
        return ResponseEntity.ok(ejercicioMapper.toDTOList(ejercicios));
    }

    // Retorna el promedio de valoración de un ejercicio consultando al ms valoraciones
    @GetMapping("/promedio-valoracion/{idEjercicio}")
    public ResponseEntity<?> getPromedioValoracion(@PathVariable Long idEjercicio) {
        log.info("Solicitud de promedio de valoración del ejercicio id: " + idEjercicio);
        PromedioValoracionDTO promedio = service.getPromedioValoracionByEjercicio(idEjercicio);
        return ResponseEntity.ok(promedio);
    }

    // Retorna una lista con los ejercicios compartidos con el usuario según el ID del usuario
    @GetMapping("compartidos/{id}")
    public ResponseEntity<?> getEjerciciosCompartidosAUsuario(@PathVariable Long id){
        log.info("Solicitud ejercicios compartidos por el usuario id: " + id);
        List<Ejercicio> ejercicios = service.getEjerciciosCompartidosAUsuario(id);

        log.info("Ejercicios encontrados");
        return ResponseEntity.ok(ejercicioMapper.toDTOList(ejercicios));
    }
}
