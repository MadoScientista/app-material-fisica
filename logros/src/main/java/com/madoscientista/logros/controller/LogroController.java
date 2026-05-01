package com.madoscientista.logros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.logros.service.LogroService;

@RestController
@RequestMapping("api/v1/logros")
public class LogroController {

    @Autowired
    private LogroService service;
}
