package com.madoscientista.usuarios.dto.ejercicioDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestEjercicioDTO {

    @Schema(description = "Tema del ejercicio", example = "MRU")
    @NotBlank
    private String tema;

    @Schema(description = "Contexto físico del ejercicio", example = "Automóvil en carretera recta")
    @NotBlank
    private String contexto;

    @Schema(description = "Incógnita del ejercicio", example = "velocidad final")
    @NotBlank
    private String incognita;

    @Schema(description = "Dificultad del ejercicio", example = "Fácil")
    @NotBlank
    private String dificultad;

    @Schema(description = "Indica si el resultado debe ser positivo", example = "true")
    @NotNull
    private boolean resultadoPositivo;
}
