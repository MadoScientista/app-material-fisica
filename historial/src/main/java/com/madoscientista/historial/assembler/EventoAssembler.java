package com.madoscientista.historial.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.madoscientista.historial.controller.EventoControllerV2;
import com.madoscientista.historial.dto.EventoDTO.ResponseEventoDTO;
import com.madoscientista.historial.mapper.EventoMapper;
import com.madoscientista.historial.model.Evento;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventoAssembler implements RepresentationModelAssembler<Evento, EntityModel<ResponseEventoDTO>> {

    private final EventoMapper mapper;

    @Override
    public EntityModel<ResponseEventoDTO> toModel(Evento evento) {
        ResponseEventoDTO dto = mapper.toDTO(evento);
        return EntityModel.of(dto,
            linkTo(methodOn(EventoControllerV2.class).getEventoById(evento.getIdEvento())).withSelfRel(),
            linkTo(methodOn(EventoControllerV2.class).getEventos()).withRel("eventos"),
            linkTo(methodOn(EventoControllerV2.class).getEventosByUsuarioId(evento.getIdUsuarioOrigen())).withRel("usuario")
        );
    }

    @Override
    public CollectionModel<EntityModel<ResponseEventoDTO>> toCollectionModel(Iterable<? extends Evento> eventos) {
        CollectionModel<EntityModel<ResponseEventoDTO>> dtoList = RepresentationModelAssembler.super.toCollectionModel(eventos);
        dtoList.add(linkTo(methodOn(EventoControllerV2.class).getEventos()).withSelfRel());
        return dtoList;
    }
}
