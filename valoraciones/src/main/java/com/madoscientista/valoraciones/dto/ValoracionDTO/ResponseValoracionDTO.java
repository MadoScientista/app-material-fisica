package com.madoscientista.valoraciones.dto.ValoracionDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseValoracionDTO {

    private Long idValoracion;
    private Long idEjercicio;
    private Long idUsuario;
    private Integer puntuacion;
    private String comentario;
    private String fechaCreacion;
}
