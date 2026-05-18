package com.madoscientista.material.dto.EventoDTO;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestEventoDTO {

    @NotNull
    private Long idTipoEvento;

    @NotNull
    private Long idUsuarioOrigen;

    @NotEmpty
    private List<Long> idUsuarioDestino;
}
