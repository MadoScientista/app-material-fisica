package com.madoscientista.generador_ejercicios.dto.unidadDeMedidaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseUnidadDeMedidaDTO {
    private int idUnidadDeMedida;
    private String nombre;
    private String simbolo;
    private String esSI;
    private String esBaseSI;
    private double factorConversionSI;
    private String magnitudFisica;
}
