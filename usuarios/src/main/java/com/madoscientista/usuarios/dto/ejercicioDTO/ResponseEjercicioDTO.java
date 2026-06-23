package com.madoscientista.usuarios.dto.ejercicioDTO;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEjercicioDTO {

    @Schema(example = "3")
    private Long idEjercicio;

    @Schema(example = "3")
    private Long idCreador;

    @Schema(example = "2025-03-10T10:30:00")
    private String fechaCreacion;

    @Schema(example = "MRU")
    private String tema;

    @Schema(example = "ELEMENTAL")
    private String dificultad;

    @Schema(example = "POSICION")
    private String incognita;

    @Schema(example = "3")
    private Long idPlantillaEnunciado;

    @Schema(example = "Una persona se mueve a 2m/s durante 10 segundos, ¿A qué distancia se encontrará del punto de partida luego de los 10 segundos?")
    private String enunciado;

    @Schema(example = "20")
    private String respuesta;

    @Schema(example = "[1,2]")
    private List<Long> idUsuariosCompartido;

}
