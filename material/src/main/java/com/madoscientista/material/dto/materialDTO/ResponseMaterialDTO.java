package com.madoscientista.material.dto.materialDTO;

import java.util.List;

import com.madoscientista.material.dto.itemEjercicioDTO.ResponseItemEjercicioDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseMaterialDTO {

    private Long idMaterial;
    private Long idUsuarioCreador;
    private String fechaCreacion;
    private List<ResponseItemEjercicioDTO> itemsEjercicios;
}
