package com.madoscientista.suscripciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.suscripciones.service.SuscripcionService;

@RestController
@RequestMapping("api/v1/suscripciones")
public class SuscripcionController {

    @Autowired
    private SuscripcionService service;

    
}
