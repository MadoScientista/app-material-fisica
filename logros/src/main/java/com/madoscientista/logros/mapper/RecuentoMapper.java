package com.madoscientista.logros.mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.madoscientista.logros.dto.recuentoDTO.ResponseRecuentoDTO;
import com.madoscientista.logros.model.Recuento;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class RecuentoMapper {

    public ResponseRecuentoDTO toDTO(Recuento recuento){
        ResponseRecuentoDTO dto = new ResponseRecuentoDTO();

        dto.setIdUsuario(recuento.getIdUsuario());
        Map<String, String> mapaRecuento = new LinkedHashMap<>();

        mapaRecuento.put("Ejercicios creados", recuento.getNEjerciciosCreados().toString());
        mapaRecuento.put("Ejercicios compartidos", recuento.getNEjerciciosCompartidos().toString());
        mapaRecuento.put("Comunidades", recuento.getNComunidades().toString());
        mapaRecuento.put("Items creados", recuento.getNItemsCreados().toString());
        mapaRecuento.put("Materiales creados", recuento.getNMaterialesCreados().toString());

        dto.setRecuento(mapaRecuento);

        return dto;
    }

    public List<ResponseRecuentoDTO> toDTOList(List<Recuento> recuentos){
        return recuentos.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
