package com.madoscientista.suscripciones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.suscripciones.repository.SuscripcionRepository;

@Service
public class SuscripcionService {
    
    @Autowired
    private SuscripcionRepository repo;

    
}
