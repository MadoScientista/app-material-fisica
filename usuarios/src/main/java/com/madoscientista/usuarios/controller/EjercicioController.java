package com.madoscientista.usuarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.usuarios.dto.ejercicioDTO.RequestEjercicioDTO;
import com.madoscientista.usuarios.model.Ejercicio;
import com.madoscientista.usuarios.service.EjercicioService;

@RestController
@RequestMapping("api/v1/ejercicios")
public class EjercicioController {

    @Autowired
    private EjercicioService service;


    // Genera un ejercicio nuevo a partir de los datos del request y el id del usuario que lo crea
    @PostMapping("/{id}")
    public ResponseEntity<?> postGenerarEjercicio(@RequestBody RequestEjercicioDTO request, @PathVariable Long id){
        Ejercicio ejercicio = service.postEjercicio(request, id);

        if(ejercicio != null){
            return ResponseEntity.status(HttpStatus.CREATED).body("Creado");
        }

        return ResponseEntity.badRequest().body("turururu");
    }

    // Comparte un ejercicio con una lista de usuarios
    @PostMapping("/compartir/{idCreador}/{idEjercicio}")
    public ResponseEntity<?> postCompartirEjercicio(@PathVariable Long idCreador, @PathVariable Long idEjercicio, @RequestBody List<Long> idsUsuarios){
        Ejercicio compartido = service.compartirEjercicio(idCreador, idEjercicio, idsUsuarios);

        if(compartido != null){
            return ResponseEntity.ok("Ejercicio compartido");
        }

        return ResponseEntity.badRequest().body("No se pudo compartir el ejercicio");
    }

    @GetMapping("creados/{id}")
    public ResponseEntity<?> getEjerciciosCrea(@PathVariable Long id){
        List<Ejercicio> ejercicios = service.getEjerciciosCreadosByUsuario(id);
        return ResponseEntity.ok(ejercicios);
    }
}
