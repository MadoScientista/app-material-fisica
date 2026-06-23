package com.madoscientista.usuarios.dto.errorDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseErrorDTO {

    @Schema(example = "email", description = "Nombre del campo que causó el error")
    private String campo;

    @Schema(example = "El correo electrónico ya está registrado", description = "Mensaje descriptivo del error")
    private String mensaje;

    @Schema(example = "400", description = "Código de estado HTTP del error")
    private int codigo;
}
