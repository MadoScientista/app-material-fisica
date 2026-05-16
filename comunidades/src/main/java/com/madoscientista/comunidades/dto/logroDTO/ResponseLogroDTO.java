package com.madoscientista.comunidades.dto.logroDTO;

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
