package com.madoscientista.suscripciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSuscripcionDTO {

    private long idUsuario;
    private long idSuscripcion;
    private String tipoSuscripcion;
    private boolean activo;
    private String fechaInicio;
}
