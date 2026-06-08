package com.madoscientista.usuarios.dto.usuarioDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestUsuarioDTO {

    @Schema(description = "Nombre del usuario", example = "Juan")
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String apellido;

    @Schema(description = "Nombre de usuario único", example = "juanperez123")
    @NotBlank
    private String nombreUsuario;

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@email.com")
    @Email
    private String email;

    @Schema(description = "Contraseña del usuario", example = "miContraseñaSegura")
    @NotBlank
    private String password;
}
