package com.madoscientista.historial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.historial.client.NotificacionClient;
import com.madoscientista.historial.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.historial.model.Evento;
import com.madoscientista.historial.repository.EventoRepository;

import feign.FeignException.FeignClientException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EventoService {

    @Autowired
    private EventoRepository eRepo;

    @Autowired
    private NotificacionClient nClient;

    public Evento postEvento(Evento evento, List<Long> idDestino){
        Evento eventoCreado = eRepo.save(evento);

        if(eventoCreado.getTipoEvento().isNotificacionActiva()){
            RequestEventoDTO request = new RequestEventoDTO();
            request.setIdTipoEvento(eventoCreado.getTipoEvento().getIdTipoEvento());
            request.setIdUsuarioOrigen(eventoCreado.getIdUsuarioOrigen());
            request.setIdUsuarioDestino(idDestino);

            try{
                nClient.postNotificacion(request);
            }catch(FeignClientException e){
                log.error("Error al notificar el evento ID: {}, Causa: {}", 
                eventoCreado.getIdEvento(), 
                e.getMessage());
            }
        }

        return eventoCreado;
    }

    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    // Retorna una lista de eventos filtrados por el ID de un usuario
    public List<Evento> getEventosByIdUsuarioOrigen(Long idUsuario){
        return eRepo.findAllByIdUsuarioOrigen(idUsuario);
    }

    // Retorna la lista de eventos disponibles en BD
    public List<Evento> getEventos(){
        return eRepo.findAll();
    }
}
