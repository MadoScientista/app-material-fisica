package com.madoscientista.generador_ejercicios.dto.ejercicioDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ResponseEjercicioDTO {

    @Schema(description = "Tema del ejercicio", example="MRU")
    private String tema;

    @Schema(description = "Contexto del ejercicio", example="PERSONA")
    private String contexto;

    @Schema(description = "Incógnita del ejercicio", example="POSICION")
    private String incognita;

    @Schema(description = "Dificultad del ejercicio", example="INTERMEDIO")
    private String dificultad;

    @Schema(description = "ID de la plantilla", example="15")
    private long idPlantillaEnunciado;

    @Schema(description = "Texto plantilla del ejercicio", example="Una persona está cumpliendo su meta diaria de pasos. Al revisar su aplicación, nota que ya ha recorrido {x0} desde su punto de partida. Si decide mantener un paso constante de {v} durante los próximos {t} para enfriar los músculos, ¿cuál será su posición final total respecto al inicio?")
    private String enunciado;

    @Schema(description="Lista de resultados variable:valor-unidad", example = "{x0:10 m, v:2 m/s, t:5 s, x:20 m}")
    private List<Map<String, Object>> datos;

    @Schema(description="Valor calculado de la incógnita junto a su unidad de medida")
    private String respuesta;

    public ResponseEjercicioDTO(){
        datos = new ArrayList<>();
    }
}
