package com.madoscientista.logros.dto.recuentoDTO;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseRecuentoDTO {

    @Schema(description = "ID de usuario", example = "74")
    private Long idUsuario;

    @Schema(description = "Recuento de logros por tipo de logro", example = "{'nEjerciciosCreados': 3, 'nComunidades': 2}")
    private Map<String, String> recuento;
}
