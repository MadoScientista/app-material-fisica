package com.madoscientista.material.dto.errorDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseErrorDTO {

    private String campo;
    private String mensaje;
    private int codigo;
}
