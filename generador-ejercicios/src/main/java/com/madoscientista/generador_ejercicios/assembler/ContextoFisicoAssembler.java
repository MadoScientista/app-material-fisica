package com.madoscientista.generador_ejercicios.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.madoscientista.generador_ejercicios.controller.ContextoFisicoControllerV2;
import com.madoscientista.generador_ejercicios.dto.contextoFisicoDTO.ResponseContextoFisicoDTO;
import com.madoscientista.generador_ejercicios.mapper.ContextoFisicoMapper;
import com.madoscientista.generador_ejercicios.model.ContextoFisico;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContextoFisicoAssembler implements RepresentationModelAssembler<ContextoFisico, EntityModel<ResponseContextoFisicoDTO>> {

    private final ContextoFisicoMapper mapper;

    @Override
    public EntityModel<ResponseContextoFisicoDTO> toModel(ContextoFisico contexto) {
        ResponseContextoFisicoDTO dto = mapper.build(contexto);
        return EntityModel.of(dto,
            linkTo(methodOn(ContextoFisicoControllerV2.class).getContextoById(dto.getId())).withSelfRel(),
            linkTo(methodOn(ContextoFisicoControllerV2.class).getContextos()).withRel("contextos")
        );
    }

    @Override
    public CollectionModel<EntityModel<ResponseContextoFisicoDTO>> toCollectionModel(
            Iterable<? extends ContextoFisico> contextos) {
        CollectionModel<EntityModel<ResponseContextoFisicoDTO>> dtoList =
            RepresentationModelAssembler.super.toCollectionModel(contextos);
        dtoList.add(linkTo(methodOn(ContextoFisicoControllerV2.class).getContextos()).withSelfRel());
        return dtoList;
    }
}
