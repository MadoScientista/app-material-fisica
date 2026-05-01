package com.madoscientista.generador_ejercicios.dto.plantillaEnunciadoDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class RequestPlantillaEnunciadoDTO {

    @NotBlank    
    private String tema;

    @NotBlank
    private String contexto;
    
    @NotBlank
    private String incognita;
    
    @NotBlank
    private boolean resultadoPositivo;
    
    @NotBlank
    private String enunciado;
    
}
