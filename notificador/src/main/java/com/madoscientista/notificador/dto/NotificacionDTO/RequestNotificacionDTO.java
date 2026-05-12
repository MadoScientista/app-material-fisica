package com.madoscientista.notificador.dto.NotificacionDTO;

import java.util.Map;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestNotificacionDTO {
    
    @NotNull
    private Long idUsuario;
    
    @NotNull
    private Long idTipoNotificacion;
    
    @NotNull
    private Map<String, String> datos;
}
