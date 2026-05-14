package com.madoscientista.usuarios.dto.valoracionDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromedioValoracionDTO {

    private Long idEjercicio;
    private Double promedio;
    private Long totalValoraciones;
}
