package com.madoscientista.historial.mapper;

import java.util.List;
import java.util.stream.Collectors;

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

    public ResponseEventoDTO toDTO(Evento evento){
        ResponseEventoDTO response = new ResponseEventoDTO();

        response.setIdEvento(evento.getIdEvento());
        response.setIdUsuario(evento.getIdUsuarioOrigen());
        response.setFecha(evento.getFecha().toString());
        response.setDescripcion(evento.getDescripcion());

        return response;
    }

    public List<ResponseEventoDTO> toDTOList(List<Evento> eventos){
        return eventos.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
