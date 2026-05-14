package com.madoscientista.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.madoscientista.usuarios.dto.valoracionDTO.PromedioValoracionDTO;

@FeignClient(name = "valoraciones", url = "localhost:8088")
public interface ValoracionesClient {

    @GetMapping("api/v1/valoraciones/promedio/{idEjercicio}")
    ResponseEntity<PromedioValoracionDTO> getPromedioByEjercicio(@PathVariable Long idEjercicio);
}
