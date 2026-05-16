package com.madoscientista.comunidades.dto.comunidadDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestComunidadDTO {

    @NotNull
    private Long idUsuarioCreador;

    @NotNull
    private String nombre;
}
