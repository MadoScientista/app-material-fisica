package com.madoscientista.logos.dto.logoDTO;

import lombok.Data;

@Data
public class ResponseLogoDTO {

    private Long idLogo;
    private Long idUsuarioCreador;
    private String fechaCreacion;
    private String nombre;
    private String descripcion;
    private String imagen;
    private String url;
}
