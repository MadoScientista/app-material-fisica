package com.madoscientista.suscripciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.suscripciones.dto.SuscripcionDTO.RequestSuscripcionDTO;
import com.madoscientista.suscripciones.dto.SuscripcionDTO.ResponseSuscripcionDTO;
import com.madoscientista.suscripciones.mapper.SuscripcionMapper;
import com.madoscientista.suscripciones.model.Suscripcion;
import com.madoscientista.suscripciones.service.SuscripcionService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("api/v1/suscripciones")
public class SuscripcionController {

    @Autowired
    private SuscripcionService service;

    @Autowired
    private SuscripcionMapper suscripcionMapper;

    // ------------------------------------------------------
    // ---------------- Sección GET -------------------------
    // ------------------------------------------------------

    // Retorna la lista de suscriociones activas
    @GetMapping("/activas")
    public List<ResponseSuscripcionDTO> getSuscripcionesActivas() {
        return suscripcionMapper.toDTOList(service.getSuscripcionesActivas());
    }

    // Retorna una lista con los IDs de usuarios con suscripciones activas
    @GetMapping("/usuarios-activos")
    public ResponseEntity<?> getUsuariosConSuscripcionesActivas() {

        List<Long> usuariosActivos = service.getUsuariosConSuscripcionesActivas();
        return ResponseEntity.ok(usuariosActivos);
    }

    // Retorna la suscripción de un usuario por su ID
    @GetMapping("/{idUsuario}")
    public ResponseEntity<?> getSuscripcionByUsuarioId(@PathVariable Long idUsuario) {

        ResponseSuscripcionDTO response = suscripcionMapper.toDTO(service.getSuscripcionByUsuarioId(idUsuario));
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    // Retorna el número máximo de ejercicios permitidos para un usuario según su suscripción
    @GetMapping("/max-ejercicios/{idUsuario}")
    public ResponseEntity<Long> getMaxEjerciciosByUsuarioId(@PathVariable Long idUsuario) {
        Long maxEjercicios = service.getMaxEjerciciosByUsuarioId(idUsuario);
        return ResponseEntity.ok(maxEjercicios);
    }


    // Retorna una lista de suscripciones por una lista de IDs de usuario
    @GetMapping("/usuarios")
    public List<ResponseSuscripcionDTO> getSuscripcionesByUsuarioIds(@RequestBody List<Long> idUsuarios) {
        return suscripcionMapper.toDTOList(service.getSuscripcionesByUsuarioIds(idUsuarios));
    }


    // ------------------------------------------------------
    // ---------------- Sección POST ------------------------
    // ------------------------------------------------------

    // Crea una nueva suscripción para un usuario según su ID
    @PostMapping
    public ResponseEntity<?> postSuscripcion(@Valid @RequestBody RequestSuscripcionDTO request){

        Suscripcion nuevaSuscripcion = service.postSuscripcion(request.idUsuario, request.nombreTipoSuscripcion);
        ResponseSuscripcionDTO response = suscripcionMapper.toDTO(nuevaSuscripcion);

        if(response == null){
            return ResponseEntity.badRequest().body("No se pudo crear la suscripción. Verifique que el tipo de suscripción sea válido.");
        }
        
        return ResponseEntity.ok(response);
    }


    // --------------------------------------------------------
    // ---------------- Sección PUT ---------------------------
    // --------------------------------------------------------

    // Cancela una suscripción activa
    @PutMapping("/{idUsuario}")
    public ResponseEntity<?> cancelarSuscripcion(@PathVariable Long idUsuario){
        Suscripcion suscripcionCancelada = service.cancelarSuscripcion(idUsuario);

        if(suscripcionCancelada == null){
            return ResponseEntity.badRequest().body("No se pudo cancelar la suscripción. Verifique que el usuario tenga una suscripción activa.");
        }

        ResponseSuscripcionDTO response = suscripcionMapper.toDTO(suscripcionCancelada);

        return ResponseEntity.ok(response);
    }

    // Actualiza el tipo de suscripción de un usuario
    @PutMapping("/actualizar/{idUsuario}")
    public ResponseEntity<?> actualizarSuscripcion(@PathVariable Long idUsuario, @Valid @RequestBody RequestSuscripcionDTO request){
        Suscripcion suscripcionActualizada = service.actualizarSuscripcion(idUsuario, request.nombreTipoSuscripcion);

        if(suscripcionActualizada == null){
            return ResponseEntity.badRequest().body("No se pudo actualizar la suscripción. Verifique que el usuario tenga una suscripción activa y que el nuevo tipo de suscripción sea válido.");
        }

        ResponseSuscripcionDTO response = suscripcionMapper.toDTO(suscripcionActualizada);

        return ResponseEntity.ok(response);
    }

}

