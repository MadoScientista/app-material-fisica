package com.madoscientista.generador_ejercicios.dto.contextoFisicoDTO;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseContextoFisicoDTO {

    private int id;
    private String nombre;
    private int vMin;
    private int vMax;
    private int xMin;
    private int xMax;
    private double mMin;
    private double mMax;

}
