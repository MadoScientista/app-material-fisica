package com.madoscientista.logros.dto.logroDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestLogroDTO {

    @Schema(description = "Identificador del usuario", example = "74")
    @NotNull
    private Long idUsuario;
    
    @Schema(description = "Nombre del tipo de logro", example = "A estudiar")
    @NotBlank
    private String nombreTipoLogro;
}
