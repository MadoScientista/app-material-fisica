package com.madoscientista.notificador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseNotificacionDTO {

    private long idUsuario;
    private String descripcion;
    private String fecha;
}
