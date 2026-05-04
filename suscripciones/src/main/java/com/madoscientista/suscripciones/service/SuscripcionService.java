package com.madoscientista.suscripciones.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.suscripciones.model.Suscripcion;
import com.madoscientista.suscripciones.repository.SuscripcionRepository;

@Service
public class SuscripcionService {
    
    @Autowired
    private SuscripcionRepository repo;

    // ------------------------------------------------------
    // ---------------- Sección GET -------------------------
    // ------------------------------------------------------

    // Retorna una lista de suscripciones activas
    public List<Suscripcion> getSuscripcionesActivas() {
        return repo.findByActivo(true);
    }

    // Retorna una lista con los IDs de usuarios con suscripciones activas
    public List<Long> getUsuariosConSuscripcionesActivas() {
        return repo.findByActivo(true).stream()
            .map(Suscripcion::getIdUsuario)
            .collect(Collectors.toList());
    }

    // Retorna la suscripción de un usuario por us DI
    public Suscripcion getSuscripcionByUsuarioId(Long idUsuario) {
        return repo.findByIdUsuario(idUsuario).orElse(null);
    }

    // Retorna una lista de suscripciones por una lista de IDs de usuario
    public List<Suscripcion> getSuscripcionesByUsuarioIds(List<Long> idUsuarios) {
        return repo.findByIdUsuarioIn(idUsuarios);
    }
}
