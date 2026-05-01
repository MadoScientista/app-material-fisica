package com.madoscientista.usuarios.dto.usuarioDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUsuarioDTO {

    private long idUsuario;
    private String nombre;
    private String apellido;
    
    private String nombreUsuario;
    private String email;
}
