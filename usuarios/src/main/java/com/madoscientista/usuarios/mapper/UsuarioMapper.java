package com.madoscientista.usuarios.mapper;

import org.springframework.stereotype.Component;

import com.madoscientista.usuarios.dto.usuarioDTO.ResponseUsuarioDTO;
import com.madoscientista.usuarios.model.Usuario;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class UsuarioMapper {

    public ResponseUsuarioDTO toDTO(Usuario u){
        return new ResponseUsuarioDTO(
            u.getIdUsuario(),
            u.getNombre(),
            u.getApellido(),
            u.getNombreUsuario(),
            u.getEmail()
        );
    }
}
