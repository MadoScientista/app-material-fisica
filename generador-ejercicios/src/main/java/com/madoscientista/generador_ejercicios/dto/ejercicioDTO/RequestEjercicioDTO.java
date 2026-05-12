package com.madoscientista.generador_ejercicios.dto.ejercicioDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestEjercicioDTO {

    @NotBlank
    private String tema;
    
    @NotBlank
    private String contexto;
    
    @NotBlank
    private String incognita;
    
    @NotBlank
    private String dificultad;
    
    @NotNull
    private boolean resultadoPositivo;
}
