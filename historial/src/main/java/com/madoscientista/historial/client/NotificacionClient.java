package com.madoscientista.historial.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.madoscientista.historial.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.historial.dto.NotificacionDTO.ResponseNotificacionDTO;

import jakarta.validation.Valid;

@FeignClient(name = "notificador", url = "localhost:8085" )
public interface NotificacionClient {

    @PostMapping("api/v1/notificaciones")
    public ResponseEntity<List<ResponseNotificacionDTO>> postNotificacion(@RequestBody @Valid RequestEventoDTO request);

    @PostMapping("api/v1/notificaciones/varias")
    public ResponseEntity<List<ResponseNotificacionDTO>> postVariasNotificaciones(@RequestBody @Valid List<RequestEventoDTO> requests);
}
