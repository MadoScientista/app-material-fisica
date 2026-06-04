package com.madoscientista.generador_ejercicios.dto.plantillaEnunciadoDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class RequestPlantillaEnunciadoDTO {

    @Schema(description = "Temática del ejercicio", example="MRU")
    @NotBlank    
    private String tema;

    @Schema(description = "Contexto del ejercicio para el cálculo de valores y narrativa de la plantilla", example="PERSONA")
    @NotBlank
    private String contexto;
    
    @Schema(description = "Variable a calcular en el ejercicio", example="VELOCIDAD")
    @NotBlank
    private String incognita;
    
    @Schema(description = "Booleano para resultado positivo o negativo, impacta en los cálculos y narrativa de la plantilla", example ="true")
    @NotNull
    private boolean resultadoPositivo;
    
    @Schema(description = "Texto plantilla del ejercicio", example="Una persona está cumpliendo su meta diaria de pasos. Al revisar su aplicación, nota que ya ha recorrido {x0} desde su punto de partida. Si decide mantener un paso constante de {v} durante los próximos {t} para enfriar los músculos, ¿cuál será su posición final total respecto al inicio?")
    @NotBlank
    private String enunciado;
    
}
