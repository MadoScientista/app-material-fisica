package com.madoscientista.generador_ejercicios.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dato {
    private VariableFisica variable;
    private double valor;
    private UnidadDeMedida unidadDeMedida;
}
