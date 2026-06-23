package com.madoscientista.comunidades.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.madoscientista.comunidades.controller.ComunidadControllerV2;
import com.madoscientista.comunidades.dto.comunidadDTO.ResponseComunidadDTO;
import com.madoscientista.comunidades.mapper.ComunidadMapper;
import com.madoscientista.comunidades.model.Comunidad;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ComunidadAssembler implements RepresentationModelAssembler<Comunidad, EntityModel<ResponseComunidadDTO>> {

    private final ComunidadMapper mapper;

    @Override
    public EntityModel<ResponseComunidadDTO> toModel(Comunidad comunidad) {
        ResponseComunidadDTO dto = mapper.toDTO(comunidad);
        return EntityModel.of(dto,
            linkTo(methodOn(ComunidadControllerV2.class).getComunidadById(comunidad.getIdComunidad())).withSelfRel(),
            linkTo(methodOn(ComunidadControllerV2.class).getComunidades()).withRel("comunidades"),
            linkTo(methodOn(ComunidadControllerV2.class).getMiembrosDeComunidad(comunidad.getIdComunidad())).withRel("miembros")
        );
    }

    @Override
    public CollectionModel<EntityModel<ResponseComunidadDTO>> toCollectionModel(Iterable<? extends Comunidad> comunidades) {
        CollectionModel<EntityModel<ResponseComunidadDTO>> dtoList = RepresentationModelAssembler.super.toCollectionModel(comunidades);
        dtoList.add(linkTo(methodOn(ComunidadControllerV2.class).getComunidades()).withSelfRel());
        return dtoList;
    }
}
