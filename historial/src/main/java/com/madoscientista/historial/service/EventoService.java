package com.madoscientista.historial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.historial.client.NotificacionClient;
import com.madoscientista.historial.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.historial.model.Evento;
import com.madoscientista.historial.repository.EventoRepository;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eRepo;

    @Autowired
    private NotificacionClient nClient;


    // Crea un nuevo evento a partir de un RequestDTO
    public Evento postEvento(Evento evento, List<Long> idDestino){

        Evento eventoCreado = eRepo.save(evento);

        if(eventoCreado.getTipoEvento().isNotificacionActiva()){

            // Por ahora se crea nuevamente el request del evento original
            // esto es solo para mantener la lógica dentro del service
            // pero después la idea es cambiarlo. El problema está en realmente
            // en el mapper, que quita la lista de ids de destino
            RequestEventoDTO request = new RequestEventoDTO();
            request.setIdTipoEvento(eventoCreado.getTipoEvento().getIdTipoEvento());
            request.setIdUsuarioOrigen(eventoCreado.getIdUsuarioOrigen());
            request.setIdUsuarioDestino(idDestino);

            nClient.postNotificacion(request);
        }

        return eventoCreado;
    }

    // Retorna una lista de eventos asociados a un usuario
    public List<Evento> getEventosByIdUsuarioOrigen(Long idUsuario){
        return eRepo.findAllByIdUsuarioOrigen(idUsuario);
    }
}
