package com.madoscientista.generador_ejercicios.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnunciadoFisica {
    private PlantillaEnunciado plantilla;
    private DatosEjercicio datosEjercicio;

}
