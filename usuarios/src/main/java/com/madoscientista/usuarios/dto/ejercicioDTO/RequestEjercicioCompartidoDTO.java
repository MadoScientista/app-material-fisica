package com.madoscientista.usuarios.dto.ejercicioDTO;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestEjercicioCompartidoDTO {

    @Schema(description = "ID del usuario creador", example = "1")
    @NotNull
    private Long idCreador;

    @Schema(description = "ID del ejercicio", example = "3")
    @NotNull
    private Long idEjercicio;

    @Schema(description = "Set de IDs de usuarios con quienes está compartido el ejercicio", example = "[2, 3, 4]")
    @NotNull
    private List<Long> idsUsuariosCompartido;
}
