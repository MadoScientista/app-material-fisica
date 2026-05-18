package com.madoscientista.comunidades.client;

import java.util.List;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.madoscientista.comunidades.dto.ejercicioDTO.ResponseEjercicioDTO;

import jakarta.validation.Valid;

@FeignClient(name="usuarios", url="localhost:8087")
public interface EjercicioClient {

    @PostMapping("api/v1/ejercicios/usuarios")
    public ResponseEntity<List<ResponseEjercicioDTO>> listarEjerciciosDeUsuarios(
        @Valid @RequestBody Set<Long> idEjercicio);

}
