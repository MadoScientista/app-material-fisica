package com.madoscientista.logos.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.madoscientista.logos.dto.logoDTO.RequestLogoDTO;
import com.madoscientista.logos.dto.logoDTO.ResponseLogoDTO;
import com.madoscientista.logos.model.Logo;

@Component
public class LogoMapper {

    public Logo toEntity(RequestLogoDTO dto) {
        Logo logo = new Logo();
        logo.setIdUsuarioCreador(dto.getIdUsuarioCreador());
        logo.setNombre(dto.getNombre());
        logo.setDescripcion(dto.getDescripcion());
        logo.setImagen(dto.getImagen());
        return logo;
    }

    public ResponseLogoDTO toDTO(Logo logo) {
        ResponseLogoDTO dto = new ResponseLogoDTO();
        dto.setIdLogo(logo.getIdLogo());
        dto.setIdUsuarioCreador(logo.getIdUsuarioCreador());
        dto.setFechaCreacion(logo.getFechaCreacion() != null ? logo.getFechaCreacion().toString() : null);
        dto.setNombre(logo.getNombre());
        dto.setDescripcion(logo.getDescripcion());
        dto.setImagen(logo.getImagen());
        dto.setUrl(logo.getUrl());
        return dto;
    }

    public List<ResponseLogoDTO> toDTOList(List<Logo> logos) {
        List<ResponseLogoDTO> dtoList = new ArrayList<>();
        dtoList = logos.stream().map(this::toDTO).collect(Collectors.toList());
        return dtoList;
    }
}
