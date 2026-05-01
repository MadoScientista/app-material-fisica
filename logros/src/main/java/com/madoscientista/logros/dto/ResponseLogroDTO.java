package com.madoscientista.logros.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseLogroDTO {

    private long idLogro;
    private long idUsuario;
    private String descripción;
}
