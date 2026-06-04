package com.madoscientista.historial.dto.EventoDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEventoDTO {

    @Schema(description = "ID del evento", example = "644")
    private long idEvento;

    @Schema(description = "ID del usuario a quién le pertenece el evento", example = "40")
    private long idUsuario;

    @Schema(description = "Fecha de la creación del evento", example = "10-04-2026")
    private String fecha;

    @Schema(description = "Descripción del evento", example = "El usuario 644 ha completado un logro.")
    private String descripcion;
}
