package com.madoscientista.historial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.historial.repository.EventoRepository;

@Service
public class EventoService {

    @Autowired
    private EventoRepository repo;
}
