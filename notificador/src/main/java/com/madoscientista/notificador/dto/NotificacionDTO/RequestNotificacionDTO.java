package com.madoscientista.notificador.dto.NotificacionDTO;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestNotificacionDTO {
    private Long idUsuario;
    private Long idTipoNotificacion;
    private Map<String, String> datos;
}
