package com.madoscientista.suscripciones.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.madoscientista.suscripciones.dto.SuscripcionDTO.ResponseSuscripcionDTO;
import com.madoscientista.suscripciones.model.Suscripcion;

@Component
public class SuscripcionMapper {

    public ResponseSuscripcionDTO toDTO(Suscripcion suscripcion) {
        if (suscripcion == null) return null;
        return new ResponseSuscripcionDTO(
            suscripcion.getIdUsuario(),
            suscripcion.getIdSuscripcion(),
            suscripcion.getTipoSuscripcion() != null ? suscripcion.getTipoSuscripcion().getNombre() : "",
            suscripcion.isActivo(),
            suscripcion.getFechaInicio().toString()
        );
    }

    public List<ResponseSuscripcionDTO> toDTOList(List<Suscripcion> suscripciones) {
        return suscripciones.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}
