package com.madoscientista.valoraciones.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.madoscientista.valoraciones.dto.ValoracionDTO.ResponseValoracionDTO;
import com.madoscientista.valoraciones.model.Valoracion;

@Component
public class ValoracionMapper {

    public ResponseValoracionDTO toDTO(Valoracion valoracion) {
        if (valoracion == null) return null;
        return new ResponseValoracionDTO(
            valoracion.getIdValoracion(),
            valoracion.getIdEjercicio(),
            valoracion.getIdUsuario(),
            valoracion.getPuntuacion(),
            valoracion.getComentario(),
            valoracion.getFechaCreacion().toString()
        );
    }

    public List<ResponseValoracionDTO> toDTOList(List<Valoracion> valoraciones) {
        return valoraciones.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}
