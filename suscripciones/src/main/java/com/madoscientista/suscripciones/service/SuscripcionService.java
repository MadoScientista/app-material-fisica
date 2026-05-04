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

    public List<Suscripcion> getSuscripcionesActivas() {
        return repo.findByActivo(true);
    }

    public List<Long> getUsuariosConSuscripcionesActivas() {
        return repo.findByActivo(true).stream()
            .map(Suscripcion::getIdUsuario)
            .collect(Collectors.toList());
    }

    public Suscripcion getSuscripcionByUsuarioId(Long idUsuario) {
        return repo.findByIdUsuario(idUsuario).stream()
            .findFirst()
            .orElse(null);
    }

    public List<Suscripcion> getSuscripcionesByUsuarioIds(List<Long> idUsuarios) {
        return repo.findByIdUsuarioIn(idUsuarios);
    }
}
