package com.madoscientista.valoraciones.dto.ValoracionDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseValoracionDTO {

    @Schema(example = "8")
    private Long idValoracion;

    @Schema(example = "15")
    private Long idEjercicio;

    @Schema(example = "1")
    private Long idUsuario;

    @Schema(example = "4")
    private Integer puntuacion;

    @Schema(example = "Muy buen ejercicio")
    private String comentario;

    @Schema(example = "2025-03-15T12:00:00")
    private String fechaCreacion;
}
