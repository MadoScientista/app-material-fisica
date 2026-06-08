package com.madoscientista.usuarios.dto.usuarioDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUsuarioDTO {

    @Schema(example = "1")
    private long idUsuario;

    @Schema(example = "Juan")
    private String nombre;

    @Schema(example = "Pérez")
    private String apellido;

    @Schema(example = "juanperez123")
    private String nombreUsuario;

    @Schema(example = "juan.perez@email.com")
    private String email;
}
