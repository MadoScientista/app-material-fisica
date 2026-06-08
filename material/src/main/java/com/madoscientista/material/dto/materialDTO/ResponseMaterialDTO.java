package com.madoscientista.material.dto.materialDTO;

import java.util.List;

import com.madoscientista.material.dto.itemEjercicioDTO.ResponseItemEjercicioDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseMaterialDTO {

    @Schema(description = "ID del material", example = "36")
    private Long idMaterial;

    @Schema(description = "Id del usuario creador", example ="12")
    private Long idUsuarioCreador;

    @Schema(description = "Fecha de creación del material", example = "12-10-2026")
    private String fechaCreacion;

    @Schema(description = "Lista de IDs de ítems de ejercios que incluye el material", example = "[15,36,10]")
    private List<ResponseItemEjercicioDTO> itemsEjercicios;
}
