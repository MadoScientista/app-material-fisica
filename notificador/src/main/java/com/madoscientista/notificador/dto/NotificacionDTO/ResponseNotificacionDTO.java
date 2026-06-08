package com.madoscientista.notificador.dto.NotificacionDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseNotificacionDTO {

    @Schema(description = "ID de la notificación", example = "145")
    private Long idNotificacion;
    
    @Schema(description = "ID del usuario destino de la notificación", example = "741")
    private Long idUsuario;

    @Schema(description = "Fecha de creación de la notificacion", example = "10-02-2026")
    private String fecha;

    @Schema(description = "Mensaje de la notificación", example = "¡Felicidades! Has completado un logro")
    private String mensaje;

    @Schema(description = "Booleano que indica si la notificación ha sido leída", example = "true")
    private boolean leido;
    
}
