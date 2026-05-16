package com.madoscientista.comunidades.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.madoscientista.comunidades.dto.comunidadDTO.RequestComunidadDTO;
import com.madoscientista.comunidades.dto.comunidadDTO.ResponseComunidadDTO;
import com.madoscientista.comunidades.model.Comunidad;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ComunidadMapper {


    public ResponseComunidadDTO toDTO(Comunidad comunidad){
        ResponseComunidadDTO dto = new ResponseComunidadDTO();

        dto.setIdComunidad(comunidad.getIdComunidad());
        dto.setIdMiembros(comunidad.getIdMiembros());
        dto.setIdUsuarioCreador(comunidad.getIdUsuarioCreador());
        dto.setNombre(comunidad.getNombre());

        return dto;
    }


    public List<ResponseComunidadDTO> toListDTO(List<Comunidad> listaComunidades){
        return listaComunidades.stream().map(this::toDTO).collect(Collectors.toList());    
    }


    public Comunidad toEntity(RequestComunidadDTO dto){
        Comunidad comunidad = new Comunidad();

        comunidad.setIdUsuarioCreador(dto.getIdUsuarioCreador());
        comunidad.setNombre(dto.getNombre());
        comunidad.setIdMiembros(new HashSet<Long>());

        return comunidad;
    }
}
