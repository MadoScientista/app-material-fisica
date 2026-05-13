package com.madoscientista.usuarios.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.madoscientista.usuarios.dto.logroDTO.ResponseLogroDTO;
import com.madoscientista.usuarios.dto.recuentoDTO.ResponseRecuentoDTO;


@FeignClient(name = "logros", url = "localhost:8083")
public interface LogrosClient {

    @PostMapping("api/v1/logros/sincronizar/{idUsuario}")
    public ResponseEntity<List<ResponseLogroDTO>> postSincronizarLogrosUsuario(@PathVariable Long idUsuario);

    @PostMapping("api/v1/logros/recuento/ejercicio-creado/{idUsuario}")
    public ResponseEntity<ResponseRecuentoDTO> postIncrementarEjercicioCreado(@PathVariable Long idUsuario);

    @PostMapping("api/v1/logros/recuento/ejercicio-compartido/{idUsuario}")
    public ResponseEntity<ResponseRecuentoDTO> postIncrementarEjercicioCompartido(
            @PathVariable Long idUsuario, @RequestBody int cantidad);

    @PostMapping("api/v1/logros/recuento/comunidad/{idUsuario}")
    public ResponseEntity<ResponseRecuentoDTO> postIncrementarComunidad(
            @PathVariable Long idUsuario, @RequestBody int cantidad);
}
