package com.madoscientista.usuarios.dto.recuentoDTO;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseRecuentoDTO {

    @Schema(example = "1")
    private Long idUsuario;

    @Schema(example = "{\"ejerciciosCreados\": \"5\", \"ejerciciosCompartidos\": \"3\"}")
    private Map<String, String> recuento;
}
