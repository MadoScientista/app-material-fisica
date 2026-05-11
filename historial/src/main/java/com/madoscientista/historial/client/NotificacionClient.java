package com.madoscientista.historial.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.madoscientista.historial.dto.NotificacionDTO.RequestNotificacionDTO;
import com.madoscientista.historial.dto.NotificacionDTO.ResponseNotificacionDTO;

import jakarta.validation.Valid;

@FeignClient(name = "notificador", url = "localhost:8085" )
public interface NotificacionClient {

    @PostMapping
    public ResponseEntity<ResponseNotificacionDTO> postNotificacion(@RequestBody @Valid RequestNotificacionDTO request);
}
