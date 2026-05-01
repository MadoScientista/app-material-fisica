package com.madoscientista.generador_ejercicios.dto.magnitudFisicaDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MagnitudFisicaDTO {
    private int idMagnitudFisica;
    private String nombre;
    private String simbolo;
    private List<String> unidadesDeMedida;
}
