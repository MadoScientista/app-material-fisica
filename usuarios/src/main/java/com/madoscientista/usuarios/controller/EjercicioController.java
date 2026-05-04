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
import com.madoscientista.usuarios.mapper.EjercicioMapper;
import com.madoscientista.usuarios.model.Ejercicio;
import com.madoscientista.usuarios.service.EjercicioService;

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
    public ResponseEntity<?> postGenerarEjercicio(@RequestBody RequestEjercicioDTO request, @PathVariable Long id){
        Ejercicio ejercicio = service.postEjercicio(request, id);

        if(ejercicio != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(ejercicioMapper.toDTO(ejercicio));
        }

        return ResponseEntity.badRequest().body("No se ha podido generar el ejercicio");
    }

    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    // Comparte un ejercicio con una lista de usuarios
    @PutMapping("/compartir/{idCreador}/{idEjercicio}")
    public ResponseEntity<?> putCompartirEjercicio(@PathVariable Long idCreador, @PathVariable Long idEjercicio, @RequestBody List<Long> idsUsuarios){
        Ejercicio compartido = service.compartirEjercicio(idCreador, idEjercicio, idsUsuarios);

        if(compartido != null){
            return ResponseEntity.ok("Ejercicio compartido");
        }

        return ResponseEntity.badRequest().body("No se pudo compartir el ejercicio");
    }

    // Deja de compartir un ejercicio con una lista de usuarios
    @PutMapping("/dejar-compartir/{idCreador}/{idEjercicio}")
    public ResponseEntity<?> putDejarCompartirEjercicio(@PathVariable Long idCreador, @PathVariable Long idEjercicio, @RequestBody List<Long> idsUsuarios){
        Ejercicio resultado = service.dejarDeCompartirEjercicio(idEjercicio, idCreador, idsUsuarios);

        if(resultado != null){
            return ResponseEntity.ok("Ejercicio dejado de compartir");
        }

        return ResponseEntity.badRequest().body("No se pudo dejar de compartir el ejercicio");
    }


    // --------------------------------------------------------
    // ------------------ Sección DELETE ----------------------
    // --------------------------------------------------------

    // Elimina un ejercicio creado por un usuario según el ID del ejercicio
    @DeleteMapping("/{idUsuario}/{idEjercicio}")
    public ResponseEntity<?> deleteEjercicio(@PathVariable Long idUsuario, @PathVariable Long idEjercicio){
        boolean eliminado = service.deleteEjercicio(idUsuario, idEjercicio);

        if(eliminado){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el ejercicio con id: " + idEjercicio);
    }


    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    // Retorna una lista de todos los ejercicios disponibles en la plataforma
    @GetMapping
    public ResponseEntity<?> getEjercicios(){
        List<Ejercicio> ejercicios = service.getEjercicios();

        return ResponseEntity.ok(ejercicioMapper.toDTOList(ejercicios));
    }

    // Retorna una lista de ejercicios creados por un usuario según el ID del usuario
    @GetMapping("creados/{id}")
    public ResponseEntity<?> getEjerciciosCreadosByUsuario(@PathVariable Long id){
        List<Ejercicio> ejercicios = service.getEjerciciosCreadosByUsuario(id);

        return ResponseEntity.ok(ejercicioMapper.toDTOList(ejercicios));
    }

    // Retorna una lista con los ejercicios compartidos con el usuario según el ID del usuario
    @GetMapping("compartidos/{id}")
    public ResponseEntity<?> getEjerciciosCompartidosAUsuario(@PathVariable Long id){
        List<Ejercicio> ejercicios = service.getEjerciciosCompartidosAUsuario(id);

        return ResponseEntity.ok(ejercicioMapper.toDTOList(ejercicios));
    }
}
