package com.madoscientista.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "suscripciones", url = "localhost:8086" )
public interface SuscripcionesClient {

    // Recupera suscripciones de un usuario por su ID
    @GetMapping("api/v1/suscripciones/{idUsuario}")
    public ResponseEntity<?> getSuscripcionByUsuarioId(@PathVariable Long idUsuario);

    // Recupera el número máximo de ejercicios permitidos para un usuario según su suscripción
    @GetMapping("api/v1/suscripciones/max-ejercicios/{idUsuario}")
    public ResponseEntity<Long> getMaxEjerciciosByUsuarioId(@PathVariable Long idUsuario);
}
