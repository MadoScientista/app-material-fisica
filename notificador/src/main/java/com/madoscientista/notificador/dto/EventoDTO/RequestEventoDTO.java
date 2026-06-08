package com.madoscientista.notificador.dto.EventoDTO;

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
    
    @Schema(description = "ID del usuario origen", example = "35")
    @NotNull
    private Long idUsuarioOrigen;
    
    @Schema(description = "Lista de ID de los usuarios destino", example = "[40,41,42,60]")
    @NotEmpty
    private List<Long> idUsuarioDestino;
}
