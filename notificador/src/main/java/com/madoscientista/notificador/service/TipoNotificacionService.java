package com.madoscientista.notificador.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.notificador.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.notificador.model.TipoNotificacion;
import com.madoscientista.notificador.repository.TipoNotificacionRepository;

@Service
public class TipoNotificacionService {

    @Autowired
    private TipoNotificacionRepository tipoNotificacionRepo;

    public TipoNotificacion getTipoNotificacionById(Long id) {
        return tipoNotificacionRepo.findById(id).orElse(null);
    }

    public List<TipoNotificacion> getTipoNotificacionByIdTipoEvento(Long idTipoEvento){
        return tipoNotificacionRepo.findAllByIdTipoEvento(idTipoEvento);
    }

    public List<TipoNotificacion> getTipoNotificacionByIdsEvento(List<RequestEventoDTO> requests) {
        Set<Long> idsTipos = new HashSet<>();
        for (RequestEventoDTO req : requests) {
            idsTipos.add(req.getIdTipoEvento());
        }
        return tipoNotificacionRepo.findByIdTipoEventoIn(idsTipos);
    }
}
