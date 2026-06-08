package com.madoscientista.usuarios.dto.EventoDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEventoDTO {

    @Schema(example = "10")
    private long idEvento;

    @Schema(example = "3")
    private long idUsuario;

    @Schema(example = "2025-02-10T14:30:00")
    private String fecha;

    @Schema(example = "Ejercicio creado")
    private String descripcion;
}
