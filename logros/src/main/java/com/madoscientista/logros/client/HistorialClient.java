package com.madoscientista.logros.client;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.madoscientista.logros.dto.eventoDTO.RequestEventoDTO;
import com.madoscientista.logros.dto.eventoDTO.ResponseEventoDTO;

import jakarta.validation.Valid;

@FeignClient(name = "historial")
public interface HistorialClient {

    @PostMapping("api/v1/eventos")
    public ResponseEntity<ResponseEventoDTO> postEvento(@Valid @RequestBody RequestEventoDTO request);

    @PostMapping("api/v1/eventos/lista")
    public ResponseEntity<List<ResponseEventoDTO>> postEventos(@Valid @RequestBody List<RequestEventoDTO> requests);
}
