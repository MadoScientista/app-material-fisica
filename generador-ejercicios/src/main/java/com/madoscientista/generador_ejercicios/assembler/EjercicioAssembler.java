package com.madoscientista.generador_ejercicios.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import com.madoscientista.generador_ejercicios.controller.GeneradorEjercicioControllerV2;
import com.madoscientista.generador_ejercicios.controller.PlantillaEnunciadoControllerV2;
import com.madoscientista.generador_ejercicios.dto.ejercicioDTO.ResponseEjercicioDTO;
import com.madoscientista.generador_ejercicios.dto.ejercicioDTO.RequestEjercicioDTO;

@Component
public class EjercicioAssembler {

    public EntityModel<ResponseEjercicioDTO> toModel(ResponseEjercicioDTO dto, RequestEjercicioDTO request) {
        return EntityModel.of(dto,
            linkTo(methodOn(GeneradorEjercicioControllerV2.class).getEjercicioMRU(request)).withSelfRel(),
            linkTo(methodOn(PlantillaEnunciadoControllerV2.class).getPlantillas()).withRel("plantillas"),
            linkTo(methodOn(GeneradorEjercicioControllerV2.class).getEjercicioMRU(request)).withRel("generar")
        );
    }
}
