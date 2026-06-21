package com.madoscientista.notificador.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.madoscientista.notificador.dto.usuarioDTO.ResponseUsuarioDTO;

import jakarta.validation.Valid;

@FeignClient(name = "usuarios")
public interface UsuarioClient {

    @GetMapping("api/v1/usuarios/{id}")
    public ResponseEntity<ResponseUsuarioDTO> getUsuarioById(@PathVariable Long id);


    @PostMapping("api/v1/usuarios/lista")
    public ResponseEntity<List<ResponseUsuarioDTO>> listUsuariosByIds(@Valid @RequestBody List<Long> ids);
}
