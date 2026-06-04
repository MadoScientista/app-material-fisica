package com.madoscientista.comunidades.dto.comunidadDTO;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseComunidadDTO {

    @Schema(example = "4")
    private Long idComunidad;

    @Schema(example = "12")
    private Long idUsuarioCreador;

    @Schema(example = "La buena comunidad")
    private String nombre;

    @Schema(example = "[1,2,3,4]")
    private Set<Long> idMiembros;
}
