package com.madoscientista.notificador.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.madoscientista.notificador.model.TipoNotificacion;
import com.madoscientista.notificador.repository.TipoNotificacionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoNotificacionService {

    private final TipoNotificacionRepository tipoNotificacionRepo;

    public TipoNotificacion getTipoNotificacionById(Long id) {
        return tipoNotificacionRepo.findById(id).orElse(null);
    }

    public List<TipoNotificacion> getTipoNotificacionByIdTipoEvento(Long idTipoEvento){
        return tipoNotificacionRepo.findAllByIdTipoEvento(idTipoEvento);
    }
}
