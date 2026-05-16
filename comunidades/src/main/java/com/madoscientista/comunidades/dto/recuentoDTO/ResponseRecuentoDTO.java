package com.madoscientista.comunidades.dto.recuentoDTO;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseRecuentoDTO {

    private Long idUsuario;
    private Map<String, String> recuento;
}
