package com.madoscientista.comunidades.client;

import java.util.List;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.madoscientista.comunidades.dto.recuentoDTO.ResponseRecuentoDTO;


@FeignClient(name = "logros", url = "localhost:8083")
public interface LogrosClient {

    @PostMapping("api/v1/logros/recuento/comunidad/{idUsuario}")
    public ResponseEntity<ResponseRecuentoDTO> postIncrementarComunidad(
            @PathVariable Long idUsuario, @RequestBody int cantidad);

    @PostMapping("api/v1/logros/recuento/comunidad")
    public ResponseEntity<List<ResponseRecuentoDTO>> postIncrementarComunidades(
            @RequestBody Set<Long> idsUsuarios);
}
