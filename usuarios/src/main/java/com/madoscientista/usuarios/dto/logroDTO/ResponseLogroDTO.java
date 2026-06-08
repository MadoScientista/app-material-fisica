package com.madoscientista.usuarios.dto.logroDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseLogroDTO {

    @Schema(example = "1")
    private Long idUsuario;

    @Schema(example = "Creador de ejercicios")
    private String nombreTipoLogro;

    @Schema(example = "Completa 10 ejercicios")
    private String descripcionTipoLogro;

    @Schema(example = "2025-04-01")
    private String fechaCompletado;

}
