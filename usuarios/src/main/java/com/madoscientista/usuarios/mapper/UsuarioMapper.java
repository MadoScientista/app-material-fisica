package com.madoscientista.usuarios.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.madoscientista.usuarios.dto.usuarioDTO.RequestUsuarioDTO;
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

    public List<ResponseUsuarioDTO> toDTOList(List<Usuario> usuarios){
        return usuarios.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Usuario toEntity(RequestUsuarioDTO dto) {
        Usuario u = new Usuario();
        u.setNombre(dto.getNombre());
        u.setApellido(dto.getApellido());
        u.setNombreUsuario(dto.getNombreUsuario());
        u.setEmail(dto.getEmail());
        u.setPassword(dto.getPassword());
        return u;
    }

    public List<Usuario> toEntities(List<RequestUsuarioDTO> dtoList){
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
