package com.madoscientista.suscripciones.dto.SuscripcionDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RequestSuscripcionDTO {
    
    @NotNull
    public Long idUsuario;
    
    @NotBlank
    public String nombreTipoSuscripcion;
}
