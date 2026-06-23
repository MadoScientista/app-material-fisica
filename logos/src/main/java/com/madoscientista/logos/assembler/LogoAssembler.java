package com.madoscientista.logos.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.madoscientista.logos.controller.LogoControllerV2;
import com.madoscientista.logos.dto.logoDTO.ResponseLogoDTO;
import com.madoscientista.logos.mapper.LogoMapper;
import com.madoscientista.logos.model.Logo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LogoAssembler implements RepresentationModelAssembler<Logo, EntityModel<ResponseLogoDTO>>{

    private final LogoMapper mapper;

    @Override
    public EntityModel<ResponseLogoDTO> toModel(Logo logo){
        
        ResponseLogoDTO dto = mapper.toDTO(logo);

        return EntityModel.of(dto,
            linkTo(methodOn(LogoControllerV2.class).getLogoById(dto.getIdLogo())).withSelfRel(),
            linkTo(methodOn(LogoControllerV2.class).getLogos()).withRel("logos"),
            linkTo(methodOn(LogoControllerV2.class).deleteLogoById(dto.getIdLogo())).withRel("eliminar")
        );
    }

    @Override
    public CollectionModel<EntityModel<ResponseLogoDTO>> toCollectionModel(
        Iterable<? extends Logo> logos
    ){
        CollectionModel<EntityModel<ResponseLogoDTO>> dtoList = RepresentationModelAssembler.super.toCollectionModel(logos);
        dtoList.add(linkTo(methodOn(LogoControllerV2.class).getLogos()).withSelfRel());
        return dtoList;
    }
}
