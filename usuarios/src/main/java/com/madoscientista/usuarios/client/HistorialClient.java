package com.madoscientista.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.madoscientista.usuarios.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.usuarios.dto.EventoDTO.ResponseEventoDTO;

import jakarta.validation.Valid;

@FeignClient(name = "historial")
public interface HistorialClient {

    @PostMapping("/api/v1/eventos")
    ResponseEventoDTO postEvento(@Valid @RequestBody RequestEventoDTO request);
}
