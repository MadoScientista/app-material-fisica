package com.madoscientista.logros.dto.logroDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseLogroDTO {

    @Schema(description = "ID de usuario", example = "74")
    private Long idUsuario;

    @Schema(description = "Nombre del tipo de logro", example = "A estudiar")
    private String nombreTipoLogro;

    @Schema(description = "Descripción del tipo de logo", example = "Genera tu primer ejercicio")
    private String descripcionTipoLogro;

    @Schema(description = "Fecha en la que se completó el logro", example = "25-10-2026")
    private String fechaCompletado;

}
