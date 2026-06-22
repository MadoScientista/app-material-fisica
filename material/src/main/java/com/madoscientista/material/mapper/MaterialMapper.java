package com.madoscientista.material.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.madoscientista.material.dto.materialDTO.RequestMaterialDTO;
import com.madoscientista.material.dto.materialDTO.ResponseMaterialDTO;
import com.madoscientista.material.model.ItemEjercicio;
import com.madoscientista.material.model.Material;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MaterialMapper {

    private final ItemEjercicioMapper ieMapper;

    public ResponseMaterialDTO toDTO(Material m){
        ResponseMaterialDTO dto = new ResponseMaterialDTO();

        dto.setIdMaterial(m.getIdMaterial());
        dto.setIdUsuarioCreador(m.getIdUsuarioCreador());
        dto.setFechaCreacion(m.getFechaCreacion().toString());
        dto.setItemsEjercicios(ieMapper.toDTOList(m.getItemsEjercicios()));

        return dto;
    }

    public List<ResponseMaterialDTO> toDTOList(List<Material> mList){
        List<ResponseMaterialDTO> dtoList = new ArrayList<>();
        dtoList = mList.stream().map(this::toDTO).collect(Collectors.toList());
        return dtoList;
    }

    public Material toEntity(RequestMaterialDTO dto, List<ItemEjercicio> ejercicioList){
        Material m = new Material();
        m.setIdUsuarioCreador(dto.getIdUsuarioCreador());
        m.setItemsEjercicios(ejercicioList);
        return m;
    }
}
