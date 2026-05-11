package com.madoscientista.notificador.mapper;

import java.util.Map;

import org.springframework.stereotype.Component;

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


    // A partir de un RequestNotificacionDTO y un TipoNotificacion, crea una entidad Notificacion
    public Notificacion toEntity(RequestNotificacionDTO request, TipoNotificacion tipo) {
        Notificacion notificacion = new Notificacion();
        notificacion.setIdUsuario(request.getIdUsuario());
        notificacion.setTipoNotificacion(tipo);
        notificacion.setMensaje(reemplazarPlaceholders(tipo.getPlantillaMensaje(), request.getDatos()));
        notificacion.setLeido(false);
        return notificacion;
    }

    // Retorna un ResponseNotificacionDTO a partir de una notificación
    public ResponseNotificacionDTO toDTO(Notificacion notificacion){
        ResponseNotificacionDTO response = new ResponseNotificacionDTO();

        response.setFecha(notificacion.getFechaCreacion().toString());
        response.setIdUsuario(notificacion.getIdUsuario());
        response.setLeido(notificacion.isLeido());
        response.setMensaje(notificacion.getMensaje());

        return response;
    }

    // Reemplaza los placehollders de la plantilla con los datos proporcionados
    private String reemplazarPlaceholders(String plantilla, Map<String, String> datos) {
        if (datos == null) return plantilla;
        for (Map.Entry<String, String> entry : datos.entrySet()) {
            plantilla = plantilla.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return plantilla;
    }


}
