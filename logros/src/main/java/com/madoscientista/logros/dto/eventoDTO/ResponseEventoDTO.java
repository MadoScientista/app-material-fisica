package com.madoscientista.logros.dto.eventoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEventoDTO {

    private Long idEvento;
    private Long idUsuario;
    private String fecha;
    private String descripcion;
}
