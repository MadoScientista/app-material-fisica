package com.madoscientista.notificador.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.madoscientista.notificador.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.notificador.dto.NotificacionDTO.RequestNotificacionDTO;
import com.madoscientista.notificador.dto.NotificacionDTO.ResponseNotificacionDTO;
import com.madoscientista.notificador.model.Notificacion;
import com.madoscientista.notificador.model.TipoNotificacion;

import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
public class NotificacionMapper {

    public List<Notificacion> toEntities(RequestEventoDTO request, List<TipoNotificacion> tipos){
        List<Notificacion> listaNotificaciones = new ArrayList<>();

        for(TipoNotificacion tn : tipos){
            for(Long idDestino : request.getIdUsuarioDestino()){
                Notificacion n = new Notificacion();
                n.setIdUsuarioOrigen(request.getIdUsuarioOrigen());
                n.setIdUsuarioDestino(idDestino);
                n.setTipoNotificacion(tn);
                listaNotificaciones.add(n);
            }
        }

        return listaNotificaciones;
    }

    public List<Notificacion> toEntities(List<RequestEventoDTO> requests, List<TipoNotificacion> tipos) {
        Map<Long, List<TipoNotificacion>> index = new HashMap<>();
        for (TipoNotificacion tn : tipos) {
            long idTipoEvento = tn.getIdTipoEvento();
            if (!index.containsKey(idTipoEvento)) {
                index.put(idTipoEvento, new ArrayList<>());
            }
            index.get(idTipoEvento).add(tn);
        }

        List<Notificacion> notificaciones = new ArrayList<>();
        for (RequestEventoDTO req : requests) {
            List<TipoNotificacion> tiposParaEvento = index.get(req.getIdTipoEvento());
            if (tiposParaEvento == null) continue;

            for (TipoNotificacion tn : tiposParaEvento) {
                for (Long idDestino : req.getIdUsuarioDestino()) {
                    Notificacion n = new Notificacion();
                    n.setIdUsuarioOrigen(req.getIdUsuarioOrigen());
                    n.setIdUsuarioDestino(idDestino);
                    n.setTipoNotificacion(tn);
                    notificaciones.add(n);
                }
            }
        }
        return notificaciones;
    }

    // Genera una lista de DTOs
    public List<ResponseNotificacionDTO> toDTOs(List<Notificacion> notificaciones){
        List<ResponseNotificacionDTO> dtos = new ArrayList<>();

        for(Notificacion n : notificaciones){
            dtos.add(toDTO(n));
        }

        return dtos;
    }


    // A partir de un RequestNotificacionDTO y un TipoNotificacion, crea una entidad Notificacion
    public Notificacion toEntity(RequestNotificacionDTO request, TipoNotificacion tipo) {
        Notificacion notificacion = new Notificacion();
        notificacion.setIdUsuarioOrigen(request.getIdUsuario());
        notificacion.setTipoNotificacion(tipo);
        return notificacion;
    }

    // Retorna un ResponseNotificacionDTO a partir de una notificación
    public ResponseNotificacionDTO toDTO(Notificacion notificacion){
        ResponseNotificacionDTO response = new ResponseNotificacionDTO();

        response.setFecha(notificacion.getFechaCreacion().toString());
        response.setIdUsuario(notificacion.getIdUsuarioDestino());
        response.setLeido(notificacion.isLeido());
        response.setMensaje(notificacion.getMensaje());

        return response;
    }

}
