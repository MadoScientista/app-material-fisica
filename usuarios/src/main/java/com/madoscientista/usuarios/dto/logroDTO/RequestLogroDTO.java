package com.madoscientista.usuarios.dto.logroDTO;

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

    @Schema(description = "ID del usuario", example = "1")
    @NotNull
    private Long idUsuario;

    @Schema(description = "Nombre del tipo de logro", example = "Creador de ejercicios")
    @NotBlank
    private String nombreTipoLogro;
}
