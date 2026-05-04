package com.madoscientista.historial.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEventoDTO {

    private long idEvento;
    private long idUsuario;
    private LocalDateTime fecha;
    private String descripcion;
}
