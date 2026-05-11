package com.madoscientista.historial.mapper;

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

        evento.setDescripcion(request.getDescripcion());
        evento.setIdUsuario(request.getIdUsuario());
        evento.setTipoEvento(tipo);

        return evento;
    }

    public ResponseEventoDTO toDTO(Evento evento){
        ResponseEventoDTO response = new ResponseEventoDTO();

        response.setIdEvento(evento.getIdEvento());
        response.setIdUsuario(evento.getIdUsuario());
        response.setFecha(evento.getFecha().toString());
        response.setDescripcion(evento.getDescripcion());

        return response;
    }
}
