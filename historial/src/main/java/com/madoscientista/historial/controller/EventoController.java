package com.madoscientista.historial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.historial.service.EventoService;

@RestController
@RequestMapping("api/v1/eventos")
public class EventoController {

    @Autowired
    private EventoService service;
}
