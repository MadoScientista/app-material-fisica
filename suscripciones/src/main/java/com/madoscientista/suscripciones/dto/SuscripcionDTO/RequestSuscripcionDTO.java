package com.madoscientista.suscripciones.dto.SuscripcionDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestSuscripcionDTO {
    
    @Schema(description = "ID del usuario que se suscribe", example = "1")
    @NotNull
    private Long idUsuario;
    
    @Schema(description = "Nombre del tipo de suscripción", example = "Premium")
    @NotBlank
    private String nombreTipoSuscripcion;
}
