package com.madoscientista.valoraciones.dto.ValoracionDTO;

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

    @NotNull
    private Long idEjercicio;

    @NotNull
    private Long idUsuario;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer puntuacion;

    @Size(max = 500)
    private String comentario;
}
