package com.madoscientista.comunidades.dto.comunidadDTO;

import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseComunidadDTO {

    private Long idComunidad;
    private Long idUsuarioCreador;
    private String nombre;
    private Set<Long> idMiembros;
}
