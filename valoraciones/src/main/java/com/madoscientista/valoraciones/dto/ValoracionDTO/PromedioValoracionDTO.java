package com.madoscientista.valoraciones.dto.ValoracionDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromedioValoracionDTO {

    @Schema(example = "15")
    private Long idEjercicio;

    @Schema(example = "4.5")
    private Double promedio;

    @Schema(example = "12")
    private Long totalValoraciones;
}
