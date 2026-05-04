package com.madoscientista.suscripciones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.suscripciones.repository.TipoSuscripcionRepository;

@Service
public class TipoSuscripcionService {

    @Autowired
    private TipoSuscripcionRepository repo;

}
