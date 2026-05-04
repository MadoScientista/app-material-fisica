package com.madoscientista.historial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.historial.dto.RequestEventoDTO;
import com.madoscientista.historial.model.Evento;
import com.madoscientista.historial.model.TipoEvento;
import com.madoscientista.historial.repository.EventoRepository;
import com.madoscientista.historial.repository.TipoEventoRepository;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eRepo;

    @Autowired
    private TipoEventoRepository teRepo;

    // Crea un nuevo evento a partir de un RequestDTO
    public Evento postEvento(RequestEventoDTO request){
        Evento evento = new Evento();
        TipoEvento tipoEvento = teRepo.findById(request.getIdTipoEvento()).orElse(null);

        evento.setIdUsuario(request.getIdUsuario());
        
        evento.setTipoEvento(tipoEvento);
        evento.setDescripcion(request.getDescripcion());
        return eRepo.save(evento);
    }

    // Retorna una lista de eventos asociados a un usuario
    public List<Evento> getEventosByIdUsuario(Long idUsuario){
        return eRepo.findAllByIdUsuario(idUsuario);
    }
}
