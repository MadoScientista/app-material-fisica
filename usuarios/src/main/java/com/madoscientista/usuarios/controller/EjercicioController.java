package com.madoscientista.usuarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("/{id}")
    public ResponseEntity<?> postGenerarEjercicio(@RequestBody RequestEjercicioDTO request, @PathVariable Long id){
        Ejercicio ejercicio = service.postEjercicio(request, id);

        if(ejercicio != null){
            return ResponseEntity.status(HttpStatus.CREATED).body("Creado");
        }

        return ResponseEntity.badRequest().body("turururu");
    }
}
