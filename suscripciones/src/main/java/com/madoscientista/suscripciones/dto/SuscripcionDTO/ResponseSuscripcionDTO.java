package com.madoscientista.suscripciones.dto.SuscripcionDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSuscripcionDTO {

    @Schema(example = "1")
    private Long idUsuario;

    @Schema(example = "5")
    private long idSuscripcion;

    @Schema(example = "Premium")
    private String tipoSuscripcion;

    @Schema(example = "true")
    private boolean activo;

    @Schema(example = "2025-01-15")
    private String fechaInicio;
}
