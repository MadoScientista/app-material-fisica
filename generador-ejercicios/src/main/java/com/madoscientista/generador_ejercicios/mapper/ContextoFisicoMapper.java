package com.madoscientista.generador_ejercicios.mapper;

import org.springframework.stereotype.Component;

import com.madoscientista.generador_ejercicios.dto.contextoFisicoDTO.ResponseContextoFisicoDTO;
import com.madoscientista.generador_ejercicios.model.ContextoFisico;

import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
public class ContextoFisicoMapper {

    public ResponseContextoFisicoDTO build(ContextoFisico contexto){
        return new ResponseContextoFisicoDTO(
            contexto.getIdContextoFisico(),
            contexto.getNombre(),
            contexto.getVMin(),
            contexto.getVMax(),
            contexto.getXMin(),
            contexto.getXMax(),
            contexto.getMMin(),
            contexto.getMMax());
    }
}
