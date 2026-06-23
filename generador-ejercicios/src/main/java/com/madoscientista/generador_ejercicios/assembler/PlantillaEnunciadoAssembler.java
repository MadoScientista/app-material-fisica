package com.madoscientista.generador_ejercicios.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.madoscientista.generador_ejercicios.controller.PlantillaEnunciadoControllerV2;
import com.madoscientista.generador_ejercicios.dto.plantillaEnunciadoDTO.ResponsePlantillaEnunciadoDTO;
import com.madoscientista.generador_ejercicios.mapper.PlantillaEnunciadoMapper;
import com.madoscientista.generador_ejercicios.model.PlantillaEnunciado;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PlantillaEnunciadoAssembler implements RepresentationModelAssembler<PlantillaEnunciado, EntityModel<ResponsePlantillaEnunciadoDTO>> {

    private final PlantillaEnunciadoMapper mapper;

    @Override
    public EntityModel<ResponsePlantillaEnunciadoDTO> toModel(PlantillaEnunciado plantilla) {
        ResponsePlantillaEnunciadoDTO dto = mapper.build(plantilla);
        return EntityModel.of(dto,
            linkTo(methodOn(PlantillaEnunciadoControllerV2.class).getPlantillaById(dto.getIdPlantillaEnunciado())).withSelfRel(),
            linkTo(methodOn(PlantillaEnunciadoControllerV2.class).getPlantillas()).withRel("plantillas")
        );
    }

    @Override
    public CollectionModel<EntityModel<ResponsePlantillaEnunciadoDTO>> toCollectionModel(
            Iterable<? extends PlantillaEnunciado> plantillas) {
        CollectionModel<EntityModel<ResponsePlantillaEnunciadoDTO>> dtoList =
            RepresentationModelAssembler.super.toCollectionModel(plantillas);
        dtoList.add(linkTo(methodOn(PlantillaEnunciadoControllerV2.class).getPlantillas()).withSelfRel());
        return dtoList;
    }
}
