package com.madoscientista.suscripciones.dto.EventoDTO;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestEventoDTO {

    @Schema(description = "ID del tipo de evento", example = "2")
    @NotNull
    private Long idTipoEvento;

    @Schema(description = "ID del usuario que origina el evento", example = "3")
    @NotNull
    private Long idUsuarioOrigen;

    @Schema(description = "Lista de IDs de los usuarios destino", example = "[4, 5, 6]")
    @NotEmpty
    private List<Long> idUsuarioDestino;
}