package com.madoscientista.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.madoscientista.usuarios.dto.ejercicioDTO.RequestEjercicioDTO;
import com.madoscientista.usuarios.dto.ejercicioDTO.ResponseEjercicioDTO;

@FeignClient(name = "generador-ejercicios", url="localhost:8081")
public interface GeneradorEjerciciosClient {

    // Generar un ejercicio
    @PostMapping("/api/v1/generar-ejercicio")
    ResponseEjercicioDTO getEjercicioMRU(@RequestBody RequestEjercicioDTO request);

}
