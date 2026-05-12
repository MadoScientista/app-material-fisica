package com.madoscientista.logros.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseLogroDTO {

    private Long idUsuario;
    private String nombreTipoLogro;
    private String descripcionTipoLogro;
    private String fechaCompletado;

}
