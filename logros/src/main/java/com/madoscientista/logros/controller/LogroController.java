package com.madoscientista.logros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.logros.dto.logroDTO.RequestLogroDTO;
import com.madoscientista.logros.model.Logro;
import com.madoscientista.logros.service.LogroService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/logros")
public class LogroController {

    @Autowired
    private LogroService service;


    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    // Actualiza un logro a completado
    @PostMapping
    public ResponseEntity<?> putLogroCompletado(@Valid @RequestBody RequestLogroDTO request){
        Long idUsuario = request.getIdUsuario();
        String nombreTipoUsuario = request.getNombreTipoLogro();

        Logro logroActualizado = service.putLogroCompletado(idUsuario, nombreTipoUsuario);

        if(logroActualizado != null){
            return ResponseEntity.ok(logroActualizado);
        }

        return ResponseEntity.badRequest().body("Error al actualizar el logro");

    }
}
