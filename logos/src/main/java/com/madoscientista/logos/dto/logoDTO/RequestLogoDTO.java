package com.madoscientista.logos.dto.logoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestLogoDTO {

    @NotNull
    private Long idUsuarioCreador;

    private String nombre;

    private String descripcion;

    @NotBlank
    private String imagen;
}
