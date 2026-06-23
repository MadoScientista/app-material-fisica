package com.madoscientista.valoraciones.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.madoscientista.valoraciones.controller.ValoracionControllerV2;
import com.madoscientista.valoraciones.dto.ValoracionDTO.ResponseValoracionDTO;
import com.madoscientista.valoraciones.mapper.ValoracionMapper;
import com.madoscientista.valoraciones.model.Valoracion;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ValoracionAssembler implements RepresentationModelAssembler<Valoracion, EntityModel<ResponseValoracionDTO>> {

    private final ValoracionMapper mapper;

    @Override
    public EntityModel<ResponseValoracionDTO> toModel(Valoracion valoracion) {
        ResponseValoracionDTO dto = mapper.toDTO(valoracion);
        return EntityModel.of(dto,
            linkTo(methodOn(ValoracionControllerV2.class).getValoracionById(valoracion.getIdValoracion())).withSelfRel(),
            linkTo(methodOn(ValoracionControllerV2.class).getValoracionesByEjercicio(valoracion.getIdEjercicio())).withRel("ejercicio"),
            linkTo(methodOn(ValoracionControllerV2.class).getValoracionesByUsuario(valoracion.getIdUsuario())).withRel("usuario")
        );
    }

    @Override
    public CollectionModel<EntityModel<ResponseValoracionDTO>> toCollectionModel(Iterable<? extends Valoracion> valoraciones) {
        return RepresentationModelAssembler.super.toCollectionModel(valoraciones);
    }
}
