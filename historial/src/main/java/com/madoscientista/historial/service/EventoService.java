package com.madoscientista.historial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.historial.client.NotificacionClient;
import com.madoscientista.historial.model.Evento;
import com.madoscientista.historial.repository.EventoRepository;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eRepo;

    @Autowired
    private NotificacionClient nClient;


    // Crea un nuevo evento a partir de un RequestDTO
    public Evento postEvento(Evento evento){

        return eRepo.save(evento);
    }

    // Retorna una lista de eventos asociados a un usuario
    public List<Evento> getEventosByIdUsuario(Long idUsuario){
        return eRepo.findAllByIdUsuario(idUsuario);
    }
}
