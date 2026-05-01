package com.madoscientista.generador_ejercicios.mapper;

import org.springframework.stereotype.Component;

import com.madoscientista.generador_ejercicios.dto.plantillaEnunciadoDTO.ResponsePlantillaEnunciadoDTO;
import com.madoscientista.generador_ejercicios.model.PlantillaEnunciado;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class PlantillaEnunciadoMapper {

    public ResponsePlantillaEnunciadoDTO build(PlantillaEnunciado p){
        return(
            new ResponsePlantillaEnunciadoDTO(
                p.getIdPlantillaEnunciado(),
                p.getTema().getNombre(),
                p.getContexto().getNombre(),
                p.getIncognita().getNombre(),
                p.isResultadoPositivo()?"Positivo":"Negativo",
                p.getEnunciado())
        );
    }
}
