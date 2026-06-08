package com.madoscientista.usuarios.dto.ejercicioDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEjercicioDTO {

    @Schema(example = "15")
    private Long idEjercicio;

    @Schema(example = "1")
    private Long idCreador;

    @Schema(example = "2025-03-10T10:30:00")
    private String fechaCreacion;

    @Schema(example = "MRU")
    private String tema;

    @Schema(example = "Fácil")
    private String dificultad;

    @Schema(example = "velocidad final")
    private String incognita;

    @Schema(example = "3")
    private Long idPlantillaEnunciado;

    @Schema(example = "Un automóvil viaja a 20 m/s durante 10 segundos, ¿qué distancia recorre?")
    private String enunciado;

    @Schema(example = "200")
    private String respuesta;

}
