package com.madoscientista.notificador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.notificador.repository.TipoNotificacionRepository;

@Service
public class TipoNotificacionService {

    @Autowired
    private TipoNotificacionRepository tipoNotificacionRepo;
}
