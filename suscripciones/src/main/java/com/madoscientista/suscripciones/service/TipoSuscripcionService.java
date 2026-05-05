package com.madoscientista.suscripciones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.suscripciones.model.TipoSuscripcion;
import com.madoscientista.suscripciones.repository.TipoSuscripcionRepository;

@Service
public class TipoSuscripcionService {

    @Autowired
    private TipoSuscripcionRepository repo;

    public TipoSuscripcion getById(Long id){
        return repo.findById(id).orElse(null);
    }

    public TipoSuscripcion getByNombre(String nombre){
        return repo.findByNombre(nombre).orElse(null);
    }

}
