package com.madoscientista.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.madoscientista.usuarios.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.usuarios.dto.EventoDTO.ResponseEventoDTO;

@FeignClient(name = "historial", url="localhost:8082")
public interface EventoClient {

    @PostMapping("/api/v1/eventos")
    ResponseEventoDTO postEvento(@RequestBody RequestEventoDTO request);
}
