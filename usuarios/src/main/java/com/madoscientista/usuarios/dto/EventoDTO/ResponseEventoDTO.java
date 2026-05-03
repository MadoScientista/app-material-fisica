package com.madoscientista.usuarios.dto.EventoDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEventoDTO {

    private long idEvento;
    private long idUsuario;
    private LocalDate fecha;
    private String descripcion;
}
