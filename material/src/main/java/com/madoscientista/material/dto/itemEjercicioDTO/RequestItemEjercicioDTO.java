package com.madoscientista.material.dto.itemEjercicioDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestItemEjercicioDTO {

    @Schema(description = "Identificador del usuario que crea el item", example = "14")
    @NotNull
    private Long idUsuarioCreador;
    
    @Schema(description = "Título del item", example = "Ejercicios MRU intermedio")
    private String titulo;

    @Schema(description = "Descripción del item", example = "Ejercicios de desarrollo de MRU nivel intermedio")
    private String descripcion;
    
    @Schema(description = "Texto con los ejercicios", example = "Ejercicio 1: ...")
    @NotBlank
    private String textoEjercicios;
}
