package com.madoscientista.logos.dto.logoDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseLogoDTO {

    @Schema(description = "ID del logo", example = "26")
    private Long idLogo;

    @Schema(description = "ID del usuario creador", example = "17")
    private Long idUsuarioCreador;

    @Schema(description = "Fecha de creación", example = "10-04-2026")
    private String fechaCreacion;

    @Schema(description = "Nombre del logo", example = "Logo Liceo C34")
    private String nombre;

    @Schema(description = "Descripción del logo", example = "Logo institucional del Liceo C34")
    private String descripcion;

    @Schema(description = "Por ahora es solo texto", example = "C34")
    private String imagen;

    @Schema(description = "URL del logo almacenado en el sistema", example = "www.gef.com/logo-26-17-10-04-2026-logo-liceo-c34.jpg")
    private String url;
}
