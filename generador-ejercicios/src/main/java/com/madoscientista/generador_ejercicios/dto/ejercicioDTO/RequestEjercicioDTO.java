package com.madoscientista.generador_ejercicios.dto.ejercicioDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestEjercicioDTO {
    private String tema;
    private String contexto;
    private String incognita;
    private String dificultad;
    private boolean resultadoPositivo;
}
