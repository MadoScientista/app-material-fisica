package com.madoscientista.valoraciones.dto.ValoracionDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestValoracionDTO {

    @Schema(description = "ID del ejercicio valorado", example = "15")
    @NotNull
    private Long idEjercicio;

    @Schema(description = "ID del usuario que valora", example = "1")
    @NotNull
    private Long idUsuario;

    @Schema(description = "Puntuación del 1 al 5", example = "4")
    @NotNull
    @Min(1)
    @Max(5)
    private Integer puntuacion;

    @Schema(description = "Comentario opcional (máx. 500 caracteres)", example = "Muy buen ejercicio")
    @Size(max = 500)
    private String comentario;
}
