package com.madoscientista.comunidades.dto.ejercicioDTO;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEjercicioDTO {

    @Schema(description="ID del ejercicio", example="120")
    private Long idEjercicio;

    @Schema(description="ID del usuario dueño del ejercicio", example ="21")
    private Long idCreador;

    @Schema(description = "Fecha de la creación del ejercicio", example="10-07-2026")
    private String fechaCreacion;

    @Schema(description = "Tema del ejercicio", example="MRU")
    private String tema;

    @Schema(description = "Dificultad del ejercicio", example="INTERMEDIO")
    private String dificultad;

    @Schema(description = "Incógnita del ejercicio", example="POSICION")
    private String incognita;

    @Schema(description = "ID de la plantilla", example="15")
    private Long idPlantillaEnunciado;

    @Schema(description = "Texto plantilla del ejercicio", example="Una persona está cumpliendo su meta diaria de pasos. Al revisar su aplicación, nota que ya ha recorrido 10 m desde su punto de partida. Si decide mantener un paso constante de 2 m/s durante los próximos 5 s para enfriar los músculos, ¿cuál será su posición final total respecto al inicio?")
    private String enunciado;

    @Schema(description = "Valor de la incógnita junto a su unidad de medida", example = "20 m")
    private String respuesta;

}
