package com.madoscientista.generador_ejercicios.dto.plantillaEnunciadoDTO;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponsePlantillaEnunciadoDTO {
    private long idPlantillaEnunciado;
    private String tema;
    private String contexto;
    private String incognita;
    private String signoResultado;
    private String enunciado;

}
