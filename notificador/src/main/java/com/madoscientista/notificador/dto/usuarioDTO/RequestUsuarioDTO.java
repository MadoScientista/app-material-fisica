package com.madoscientista.notificador.dto.usuarioDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestUsuarioDTO {
    
    private String nombre;
    private String apellido;

    @NotBlank
    private String nombreUsuario;
    
    @Email
    private String email;
    
    @NotBlank
    private String password;
}
