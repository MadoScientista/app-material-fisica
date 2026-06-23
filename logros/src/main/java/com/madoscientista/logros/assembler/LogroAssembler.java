package com.madoscientista.logros.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.madoscientista.logros.controller.LogroControllerV2;
import com.madoscientista.logros.dto.logroDTO.ResponseLogroDTO;
import com.madoscientista.logros.mapper.LogroMapper;
import com.madoscientista.logros.model.Logro;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LogroAssembler implements RepresentationModelAssembler<Logro, EntityModel<ResponseLogroDTO>>{

    private final LogroMapper mapper;

    @Override
    public EntityModel<ResponseLogroDTO> toModel(Logro logro){
        
        ResponseLogroDTO dto = mapper.toDTO(logro);

        return EntityModel.of(dto,
            linkTo(methodOn(LogroControllerV2.class).getLogroById(logro.getIdLogro())).withSelfRel(),
            linkTo(methodOn(LogroControllerV2.class).getLogros()).withRel("logros")
        );
    }

    @Override
    public CollectionModel<EntityModel<ResponseLogroDTO>> toCollectionModel(
        Iterable<? extends Logro> logros
    ){
        CollectionModel<EntityModel<ResponseLogroDTO>> dtoList = RepresentationModelAssembler.super.toCollectionModel(logros);
        dtoList.add(linkTo(methodOn(LogroControllerV2.class).getLogros()).withSelfRel());
        return dtoList;
    }
}
