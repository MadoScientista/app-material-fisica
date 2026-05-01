package com.madoscientista.notificador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.notificador.service.NotificacionService;

@RestController
@RequestMapping("api/v1/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService service;
}
