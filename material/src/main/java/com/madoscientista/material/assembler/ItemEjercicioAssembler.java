package com.madoscientista.material.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.madoscientista.material.controller.ItemEjercicioControllerV2;
import com.madoscientista.material.dto.itemEjercicioDTO.ResponseItemEjercicioDTO;
import com.madoscientista.material.mapper.ItemEjercicioMapper;
import com.madoscientista.material.model.ItemEjercicio;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemEjercicioAssembler implements RepresentationModelAssembler<ItemEjercicio, EntityModel<ResponseItemEjercicioDTO>> {

    private final ItemEjercicioMapper mapper;

    @Override
    public EntityModel<ResponseItemEjercicioDTO> toModel(ItemEjercicio itemEjercicio) {
        ResponseItemEjercicioDTO dto = mapper.toDTO(itemEjercicio);
        return EntityModel.of(dto,
            linkTo(methodOn(ItemEjercicioControllerV2.class).getItemEjercicioById(itemEjercicio.getIdItemEjercicio())).withSelfRel(),
            linkTo(methodOn(ItemEjercicioControllerV2.class).getItemEjercicios()).withRel("items"),
            linkTo(methodOn(ItemEjercicioControllerV2.class).deleteItemEjercicio(itemEjercicio.getIdItemEjercicio())).withRel("eliminar")
        );
    }

    @Override
    public CollectionModel<EntityModel<ResponseItemEjercicioDTO>> toCollectionModel(Iterable<? extends ItemEjercicio> items) {
        CollectionModel<EntityModel<ResponseItemEjercicioDTO>> dtoList = RepresentationModelAssembler.super.toCollectionModel(items);
        dtoList.add(linkTo(methodOn(ItemEjercicioControllerV2.class).getItemEjercicios()).withSelfRel());
        return dtoList;
    }
}
