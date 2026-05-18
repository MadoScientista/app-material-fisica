package com.madoscientista.material.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.madoscientista.material.dto.recuentoDTO.ResponseRecuentoDTO;

@FeignClient(name = "logros", url = "localhost:8083")
public interface LogrosClient {

    @PostMapping("api/v1/logros/recuento/item-creado/{idUsuario}")
    ResponseEntity<ResponseRecuentoDTO> postIncrementarItemCreado(
            @PathVariable Long idUsuario, @RequestBody int cantidad);

    @PostMapping("api/v1/logros/recuento/material/{idUsuario}")
    ResponseEntity<ResponseRecuentoDTO> postIncrementarMaterialCreado(
            @PathVariable Long idUsuario);
}
