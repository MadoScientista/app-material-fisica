package com.madoscientista.material.dto.materialDTO;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestMaterialDTO {

    @NotNull
    private Long idUsuarioCreador;
    
    @NotEmpty
    private List<Long> idItemsEjercicios;
}
