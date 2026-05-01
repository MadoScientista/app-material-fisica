package com.madoscientista.historial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEventoDTO {

    private long idEvento;
    private long idUsuario;
    private String fecha;
    private String descripcion;
}
