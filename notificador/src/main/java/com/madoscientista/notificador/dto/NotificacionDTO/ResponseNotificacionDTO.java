package com.madoscientista.notificador.dto.NotificacionDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseNotificacionDTO {

    private Long idUsuario;
    private String fecha;
    private String mensaje;
    private boolean leido;
    
}
