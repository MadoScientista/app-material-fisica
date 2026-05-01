package com.madoscientista.generador_ejercicios.dto.variableFisicaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseVariableFisicaDTO {

    private int idVariableFisica;
    private String nombre; 
    private String simbolo;
    private String magnitudFisica;

}
