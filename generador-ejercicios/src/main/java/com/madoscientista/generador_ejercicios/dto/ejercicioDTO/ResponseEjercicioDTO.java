package com.madoscientista.generador_ejercicios.dto.ejercicioDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ResponseEjercicioDTO {
    private String tema;
    private String contexto;
    private String incognita;
    private String dificultad;
    private long idPlantillaEnunciado;
    private String enunciado;
    private List<Map<String, Object>> datos;

    public ResponseEjercicioDTO(){
        datos = new ArrayList<>();
    }
}
