package com.madoscientista.material.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.madoscientista.material.dto.itemEjercicioDTO.RequestItemEjercicioDTO;
import com.madoscientista.material.dto.itemEjercicioDTO.ResponseItemEjercicioDTO;
import com.madoscientista.material.model.ItemEjercicio;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ItemEjercicioMapper {

    public ResponseItemEjercicioDTO toDTO(ItemEjercicio ie){
        ResponseItemEjercicioDTO dto = new ResponseItemEjercicioDTO();

        dto.setIdItemEjercicio(ie.getIdItemEjercicio());
        dto.setIdUsuarioCreador(ie.getIdUsuarioCreador());
        dto.setFechaCreacion(ie.getFechaCreacion().toString());
        dto.setTitulo(ie.getTitulo());
        dto.setDescripcion(ie.getDescripcion());
        dto.setTextoEjercicios(ie.getTextoEjercicios());

        return dto;
    }

    public List<ResponseItemEjercicioDTO> toDTOList(List<ItemEjercicio> ieList){
        List<ResponseItemEjercicioDTO> dtoList = new ArrayList<>();

        dtoList = ieList.stream().map(this::toDTO).collect(Collectors.toList());
        return dtoList;
    }

    public ItemEjercicio toEntity(RequestItemEjercicioDTO dto){
        ItemEjercicio ie = new ItemEjercicio();

        ie.setIdUsuarioCreador(dto.getIdUsuarioCreador());
        ie.setTitulo(dto.getTitulo());
        ie.setDescripcion(dto.getDescripcion());
        ie.setTextoEjercicios(dto.getTextoEjercicios());

        return ie;
    }

    public List<ItemEjercicio> toEntities(List<RequestItemEjercicioDTO> dtoList){
        List<ItemEjercicio> ieList = new ArrayList<>();

        ieList = dtoList.stream().map(this::toEntity).collect(Collectors.toList());
        return ieList; 
    }
}
