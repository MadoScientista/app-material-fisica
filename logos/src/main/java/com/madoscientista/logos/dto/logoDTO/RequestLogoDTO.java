package com.madoscientista.logos.dto.logoDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestLogoDTO {

    @Schema(description = "ID del usuario creador", example = "1")
    @NotNull
    private Long idUsuarioCreador;

    @Schema(description = "Nombre del logo", example = "Liceo C34")
    @NotBlank
    private String nombre;

    @Schema(description = "Descripción del logo", example = "Logo institucional del Liceo C34")
    @NotBlank
    private String descripcion;

    @Schema(description = "Por ahora solo será texto", example = "Texto del logo")
    @NotBlank
    private String imagen;
}
