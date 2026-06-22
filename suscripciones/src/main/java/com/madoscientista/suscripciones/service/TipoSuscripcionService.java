package com.madoscientista.suscripciones.service;

import org.springframework.stereotype.Service;

import com.madoscientista.suscripciones.model.TipoSuscripcion;
import com.madoscientista.suscripciones.repository.TipoSuscripcionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoSuscripcionService {

    private final TipoSuscripcionRepository repo;

    public TipoSuscripcion getById(Long id){
        return repo.findById(id).orElse(null);
    }

    public TipoSuscripcion getByNombre(String nombre){
        return repo.findByNombre(nombre).orElse(null);
    }

}
