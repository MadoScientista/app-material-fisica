package com.madoscientista.suscripciones.dto.EventoDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestEventoDTO {

    private Long idUsuario;
    private Long idTipoEvento;
    private String descripcion;
}
