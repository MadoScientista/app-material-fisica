package com.madoscientista.usuarios.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.madoscientista.usuarios.controller.UsuarioControllerV2;
import com.madoscientista.usuarios.dto.usuarioDTO.ResponseUsuarioDTO;
import com.madoscientista.usuarios.mapper.UsuarioMapper;
import com.madoscientista.usuarios.model.Usuario;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioAssembler implements RepresentationModelAssembler<Usuario, EntityModel<ResponseUsuarioDTO>>{

    private final UsuarioMapper mapper;

    @Override
    public EntityModel<ResponseUsuarioDTO> toModel(Usuario usuario){
        
        ResponseUsuarioDTO dto = mapper.toDTO(usuario);

        return(
            EntityModel.of(dto,
                linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(dto.getIdUsuario())).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).getUsuarios()).withRel("usuarios"),
                linkTo(methodOn(UsuarioControllerV2.class).deleteUsuario(dto.getIdUsuario())).withRel("eliminar")
            )
        );
    }

    @Override
    public CollectionModel<EntityModel<ResponseUsuarioDTO>> toCollectionModel(
        Iterable<? extends Usuario> usuarios
    ){
        CollectionModel<EntityModel<ResponseUsuarioDTO>> dtoList = RepresentationModelAssembler.super.toCollectionModel(usuarios);
        dtoList.add(linkTo(methodOn(UsuarioControllerV2.class).getUsuarios()).withRel("usuarios"));
        return dtoList;
    }
}
