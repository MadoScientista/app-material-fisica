package com.madoscientista.material.dto.itemEjercicioDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseItemEjercicioDTO {

    private Long idItemEjercicio;
    private Long idUsuarioCreador;
    private String fechaCreacion;
    private String titulo;
    private String descripcion;
    private String textoEjercicios;
}
