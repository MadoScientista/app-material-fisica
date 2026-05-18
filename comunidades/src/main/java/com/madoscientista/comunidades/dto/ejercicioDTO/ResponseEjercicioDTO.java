package com.madoscientista.comunidades.dto.ejercicioDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEjercicioDTO {
    private Long idEjercicio;
    private Long idCreador;
    private String fechaCreacion;
    private String tema;
    private String dificultad;
    private String incognita;
    private Long idPlantillaEnunciado;
    private String enunciado;
    private String respuesta;

}
