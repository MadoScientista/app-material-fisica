package com.madoscientista.generador_ejercicios.dto.ejercicioDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestEjercicioDTO {

    @Schema(description = "Tema del ejercicio", example="MRU")
    @NotBlank
    private String tema;
    
    @Schema(description = "Contexto del ejercicio", example="PERSONA")
    @NotBlank
    private String contexto;
    
    @Schema(description = "Incógnita del ejercicio", example="POSICION_INICIAL")
    @NotBlank
    private String incognita;
    
    @Schema(description = "Dificultad del ejercicio", example="INTERMEDIO")
    @NotNull
    @NotBlank
    private String dificultad;
    
    @Schema(description = "Calcula los datos para un resultado positivo o negativo", example="true")
    @NotNull
    private boolean resultadoPositivo;
}
