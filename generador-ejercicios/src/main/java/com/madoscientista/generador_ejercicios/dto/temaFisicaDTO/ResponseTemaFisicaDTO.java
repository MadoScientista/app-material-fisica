package com.madoscientista.generador_ejercicios.dto.temaFisicaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseTemaFisicaDTO {
    private int idTemaFisica;
    private String nombre;
    private String descripcion;
}
