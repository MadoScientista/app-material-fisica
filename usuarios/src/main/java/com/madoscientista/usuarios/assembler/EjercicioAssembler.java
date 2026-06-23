package com.madoscientista.usuarios.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.madoscientista.usuarios.controller.EjercicioControllerV2;
import com.madoscientista.usuarios.controller.UsuarioController;
import com.madoscientista.usuarios.dto.ejercicioDTO.ResponseEjercicioDTO;
import com.madoscientista.usuarios.mapper.EjercicioMapper;
import com.madoscientista.usuarios.model.Ejercicio;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EjercicioAssembler implements RepresentationModelAssembler<Ejercicio, EntityModel<ResponseEjercicioDTO>>{

    private final EjercicioMapper mapper;

    @Override
    public EntityModel<ResponseEjercicioDTO> toModel(Ejercicio ejercicio){
        ResponseEjercicioDTO dto = mapper.toDTO(ejercicio);
        
        return EntityModel.of(dto,
            linkTo(methodOn(EjercicioControllerV2.class).getEjercicioById(dto.getIdEjercicio())).withSelfRel(),
            linkTo(methodOn(EjercicioControllerV2.class).getEjercicios()).withRel("ejercicios"),
            linkTo(methodOn(UsuarioController.class).getUsuarioById(dto.getIdCreador())).withRel("creador"),
            linkTo(methodOn(EjercicioControllerV2.class).deleteEjercicio(dto.getIdCreador(), dto.getIdEjercicio())).withRel("eliminar"),
            linkTo(EjercicioControllerV2.class).slash("compartir").withRel("compartir")
        );
    }

    @Override
    public CollectionModel<EntityModel<ResponseEjercicioDTO>> toCollectionModel(
        Iterable<? extends Ejercicio> ejercicios){

            CollectionModel<EntityModel<ResponseEjercicioDTO>> dtoList = RepresentationModelAssembler.super.toCollectionModel(ejercicios);
            dtoList.add(linkTo(methodOn(EjercicioControllerV2.class).getEjercicios()).withSelfRel());
            
            return dtoList;
        } 
}
