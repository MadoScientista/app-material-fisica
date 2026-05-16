package com.madoscientista.historial.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.historial.client.NotificacionClient;
import com.madoscientista.historial.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.historial.mapper.EventoMapper;
import com.madoscientista.historial.model.Evento;
import com.madoscientista.historial.model.TipoEvento;
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

    @Autowired
    private EventoMapper eMapper;

    @Autowired
    private TipoEventoService teService;

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

    public List<Evento> postVariosEventos(List<RequestEventoDTO> requests) {
        if (requests.isEmpty()) return List.of();

        Set<Long> idsTipos = new HashSet<>();
        for (RequestEventoDTO req : requests) {
            idsTipos.add(req.getIdTipoEvento());
        }

        List<TipoEvento> tipos = teService.findAllById(idsTipos);
        List<Evento> eventos = eRepo.saveAll(eMapper.toEntities(requests, tipos));

        List<RequestEventoDTO> notificables = new ArrayList<>();
        for (int i = 0; i < eventos.size(); i++) {
            if (eventos.get(i).getTipoEvento().isNotificacionActiva()) {
                notificables.add(requests.get(i));
            }
        }
        if (!notificables.isEmpty()) {
            try {
                nClient.postVariasNotificaciones(notificables);
            } catch (FeignClientException e) {
                log.error("Error al notificar lote de eventos", e);
            }
        }

        return eventos;
    }

    public List<Evento> getEventosByIdUsuarioOrigen(Long idUsuario){
        return eRepo.findAllByIdUsuarioOrigen(idUsuario);
    }
}
