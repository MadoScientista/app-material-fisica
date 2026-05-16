package com.madoscientista.historial.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.madoscientista.historial.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.historial.dto.EventoDTO.ResponseEventoDTO;
import com.madoscientista.historial.model.Evento;
import com.madoscientista.historial.model.TipoEvento;

import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
public class EventoMapper {

    public Evento toEntity(RequestEventoDTO request, TipoEvento tipo){
        Evento evento = new Evento();

        String idsDestino = request.getIdUsuarioDestino().toString().replace("[", "").replace("]", "");
        String descripcion = String.format(tipo.getDescripcion(), request.getIdUsuarioOrigen(), idsDestino);

        evento.setDescripcion(descripcion);
        evento.setIdUsuarioOrigen(request.getIdUsuarioOrigen());
        evento.setTipoEvento(tipo);

        return evento;
    }

    public List<Evento> toEntities(List<RequestEventoDTO> requests, List<TipoEvento> tipos) {
        Map<Long, TipoEvento> index = new HashMap<>();
        for (TipoEvento t : tipos) {
            index.put(t.getIdTipoEvento(), t);
        }

        List<Evento> eventos = new ArrayList<>();
        for (RequestEventoDTO req : requests) {
            TipoEvento tipo = index.get(req.getIdTipoEvento());
            String idsDestino = req.getIdUsuarioDestino().toString().replace("[", "").replace("]", "");
            String descripcion = String.format(tipo.getDescripcion(), req.getIdUsuarioOrigen(), idsDestino);

            Evento evento = new Evento();
            evento.setDescripcion(descripcion);
            evento.setIdUsuarioOrigen(req.getIdUsuarioOrigen());
            evento.setTipoEvento(tipo);
            eventos.add(evento);
        }

        return eventos;
    }

    public ResponseEventoDTO toDTO(Evento evento){
        ResponseEventoDTO response = new ResponseEventoDTO();

        response.setIdEvento(evento.getIdEvento());
        response.setIdUsuario(evento.getIdUsuarioOrigen());
        response.setFecha(evento.getFecha().toString());
        response.setDescripcion(evento.getDescripcion());

        return response;
    }

    public List<ResponseEventoDTO> toDTOList(List<Evento> eventos){
        List<ResponseEventoDTO> dtos = new ArrayList<>();
        for (Evento e : eventos) {
            dtos.add(toDTO(e));
        }
        return dtos;
    }
}
