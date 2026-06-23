package com.madoscientista.material.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.madoscientista.material.controller.MaterialControllerV2;
import com.madoscientista.material.dto.materialDTO.ResponseMaterialDTO;
import com.madoscientista.material.mapper.MaterialMapper;
import com.madoscientista.material.model.Material;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MaterialAssembler implements RepresentationModelAssembler<Material, EntityModel<ResponseMaterialDTO>> {

    private final MaterialMapper mapper;

    @Override
    public EntityModel<ResponseMaterialDTO> toModel(Material material) {
        ResponseMaterialDTO dto = mapper.toDTO(material);
        return EntityModel.of(dto,
            linkTo(methodOn(MaterialControllerV2.class).getMaterialById(material.getIdMaterial())).withSelfRel(),
            linkTo(methodOn(MaterialControllerV2.class).getMateriales()).withRel("materiales")
        );
    }

    @Override
    public CollectionModel<EntityModel<ResponseMaterialDTO>> toCollectionModel(Iterable<? extends Material> materiales) {
        CollectionModel<EntityModel<ResponseMaterialDTO>> dtoList = RepresentationModelAssembler.super.toCollectionModel(materiales);
        dtoList.add(linkTo(methodOn(MaterialControllerV2.class).getMateriales()).withSelfRel());
        return dtoList;
    }
}
