package com.madoscientista.notificador.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestNotificacionDTO {
    private Long idUsuario;
    private Long idTipoNotificacion;
    private String tipoNotificacion;
}
