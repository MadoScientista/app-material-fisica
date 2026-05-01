package com.madoscientista.notificador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.notificador.repository.NotificacionRepository;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository repo;
    
}
