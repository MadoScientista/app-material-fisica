package com.madoscientista.material.dto.itemEjercicioDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseItemEjercicioDTO {

    @Schema(description = "Identificador del ítem de ejercicio", example = "12")
    private Long idItemEjercicio;

    @Schema(description = "Identificador del usuario que creó el ítem", example = "14")
    private Long idUsuarioCreador;

    @Schema(description = "Fecha de creación del ítem")
    private String fechaCreacion;

    @Schema(description = "Título del ítem", example = "Ejercicios MRU intermedio")
    private String titulo;

    @Schema(description = "Descripción del ítem", example = "Ejercicios de desarrollo de MRU nivel intermedio")
    private String descripcion;

    @Schema(description = "Texto con los ejercicios", example = "Ejercicio 1: ...")
    private String textoEjercicios;
}
