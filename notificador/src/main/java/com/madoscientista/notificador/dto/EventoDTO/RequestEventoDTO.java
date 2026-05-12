package com.madoscientista.notificador.dto.EventoDTO;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestEventoDTO {

    private Long idTipoEvento;
    private Long idUsuarioOrigen;
    private List<Long> idUsuarioDestino;
}
