package com.madoscientista.comunidades.dto.comunidadDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestComunidadDTO {

    @Schema(description = "ID del usuario creador", example="12")
    @NotNull
    private Long idUsuarioCreador;

    @Schema(description = "Nombre de la comunidad", example="La buena comunidad")
    @NotNull
    private String nombre;
}
