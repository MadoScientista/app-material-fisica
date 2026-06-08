package com.madoscientista.notificador.dto.NotificacionDTO;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestNotificacionDTO {
    
    @Schema(description = "Identificador del usuario objetivo de la notificación", example = "12")
    @NotNull
    private Long idUsuario;
    
    @Schema(description = "Identificador del tipo de notificación", example = "3")
    @NotNull
    private Long idTipoNotificacion;
    
    @Schema(description = "Datos adicionales para la plantilla de notificación", example = "{'nombreUsuario':'Juan'}")
    @NotNull
    private Map<String, String> datos;
}
