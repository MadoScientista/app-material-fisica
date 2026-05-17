package com.madoscientista.material.dto.itemEjercicioDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestItemEjercicioDTO {

    @NotNull
    private Long idUsuarioCreador;
    
    private String titulo;
    private String descripcion;
    
    @NotBlank
    private String textoEjercicios;
}
