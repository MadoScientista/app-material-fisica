package com.madoscientista.usuarios.dto.suscripcionDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSuscripcionDTO {

    private Long idUsuario;
    private long idSuscripcion;
    private String tipoSuscripcion;
    private boolean activo;
    private String fechaInicio;
}