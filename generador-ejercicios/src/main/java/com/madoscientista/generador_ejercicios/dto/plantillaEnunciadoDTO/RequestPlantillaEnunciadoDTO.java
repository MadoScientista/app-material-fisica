package com.madoscientista.generador_ejercicios.dto.plantillaEnunciadoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class RequestPlantillaEnunciadoDTO {

    @NotBlank    
    private String tema;

    @NotBlank
    private String contexto;
    
    @NotBlank
    private String incognita;
    
    @NotNull
    private boolean resultadoPositivo;
    
    @NotBlank
    private String enunciado;
    
}
