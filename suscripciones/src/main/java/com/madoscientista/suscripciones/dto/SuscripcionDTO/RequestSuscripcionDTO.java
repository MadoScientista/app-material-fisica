package com.madoscientista.suscripciones.dto.SuscripcionDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestSuscripcionDTO {
    
    @NotNull
    private Long idUsuario;
    
    @NotBlank
    private String nombreTipoSuscripcion;
}
