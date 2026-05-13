package com.madoscientista.logros.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.madoscientista.logros.dto.logroDTO.ResponseLogroDTO;
import com.madoscientista.logros.model.Logro;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class LogroMapper {

    public ResponseLogroDTO toDTO(Logro l){
        ResponseLogroDTO response = new ResponseLogroDTO();

        response.setDescripcionTipoLogro(l.getTipoLogro().getDescripcion());
        response.setFechaCompletado(l.isCompletado() ? l.getFechaCompletado().toString(): "No completado");
        response.setIdUsuario(l.getIdUsuario());
        response.setNombreTipoLogro(l.getTipoLogro().getNombre());

        return response;
    }

    public List<ResponseLogroDTO> toDTOs(List<Logro> listaLogros){

        List<ResponseLogroDTO> listaDTOs = new ArrayList<>();

        for(Logro l : listaLogros){
            listaDTOs.add(toDTO(l));
        }

        return listaDTOs;
    }
}
