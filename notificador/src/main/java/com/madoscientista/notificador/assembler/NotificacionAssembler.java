package com.madoscientista.notificador.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.madoscientista.notificador.controller.NotificacionControllerV2;
import com.madoscientista.notificador.dto.NotificacionDTO.ResponseNotificacionDTO;
import com.madoscientista.notificador.mapper.NotificacionMapper;
import com.madoscientista.notificador.model.Notificacion;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificacionAssembler implements RepresentationModelAssembler<Notificacion, EntityModel<ResponseNotificacionDTO>> {

    private final NotificacionMapper mapper;

    @Override
    public EntityModel<ResponseNotificacionDTO> toModel(Notificacion notificacion) {
        ResponseNotificacionDTO dto = mapper.toDTO(notificacion);
        return EntityModel.of(dto,
            linkTo(methodOn(NotificacionControllerV2.class).getNotificacionById(notificacion.getIdNotificacion())).withSelfRel(),
            linkTo(methodOn(NotificacionControllerV2.class).getNotificaciones()).withRel("notificaciones")
        );
    }

    @Override
    public CollectionModel<EntityModel<ResponseNotificacionDTO>> toCollectionModel(Iterable<? extends Notificacion> notificaciones) {
        CollectionModel<EntityModel<ResponseNotificacionDTO>> dtoList = RepresentationModelAssembler.super.toCollectionModel(notificaciones);
        dtoList.add(linkTo(methodOn(NotificacionControllerV2.class).getNotificaciones()).withSelfRel());
        return dtoList;
    }
}
