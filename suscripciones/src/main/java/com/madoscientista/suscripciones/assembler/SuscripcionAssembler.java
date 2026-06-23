package com.madoscientista.suscripciones.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.madoscientista.suscripciones.controller.SuscripcionControllerV2;
import com.madoscientista.suscripciones.dto.SuscripcionDTO.ResponseSuscripcionDTO;
import com.madoscientista.suscripciones.mapper.SuscripcionMapper;
import com.madoscientista.suscripciones.model.Suscripcion;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SuscripcionAssembler implements RepresentationModelAssembler<Suscripcion, EntityModel<ResponseSuscripcionDTO>> {

    private final SuscripcionMapper mapper;

    @Override
    public EntityModel<ResponseSuscripcionDTO> toModel(Suscripcion suscripcion) {
        ResponseSuscripcionDTO dto = mapper.toDTO(suscripcion);
        return EntityModel.of(dto,
            linkTo(methodOn(SuscripcionControllerV2.class).getSuscripcionByUsuarioId(suscripcion.getIdUsuario())).withSelfRel(),
            linkTo(methodOn(SuscripcionControllerV2.class).getSuscripcionesActivas()).withRel("suscripciones")
        );
    }

    @Override
    public CollectionModel<EntityModel<ResponseSuscripcionDTO>> toCollectionModel(Iterable<? extends Suscripcion> suscripciones) {
        CollectionModel<EntityModel<ResponseSuscripcionDTO>> dtoList = RepresentationModelAssembler.super.toCollectionModel(suscripciones);
        dtoList.add(linkTo(methodOn(SuscripcionControllerV2.class).getSuscripcionesActivas()).withSelfRel());
        return dtoList;
    }
}
