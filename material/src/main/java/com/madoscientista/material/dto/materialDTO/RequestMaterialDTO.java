package com.madoscientista.material.dto.materialDTO;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestMaterialDTO {

    @Schema(description = "Nombre del material", example = "Guía Ejercicios MRU")
    @NotNull
    private Long idUsuarioCreador;
    
    @Schema(description = "Lista de IDs de ítems de ejercicios que incluye el material", example = "[2,45,12]")
    @NotEmpty
    private List<Long> idItemsEjercicios;
}
