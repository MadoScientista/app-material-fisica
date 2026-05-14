package com.madoscientista.valoraciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.valoraciones.dto.ValoracionDTO.PromedioValoracionDTO;
import com.madoscientista.valoraciones.dto.ValoracionDTO.RequestValoracionDTO;
import com.madoscientista.valoraciones.dto.ValoracionDTO.ResponseValoracionDTO;
import com.madoscientista.valoraciones.mapper.ValoracionMapper;
import com.madoscientista.valoraciones.model.Valoracion;
import com.madoscientista.valoraciones.service.ValoracionService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/valoraciones")
public class ValoracionController {

    @Autowired
    private ValoracionService service;

    @Autowired
    private ValoracionMapper valoracionMapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> getValoracionById(@PathVariable Long id) {
        log.info("Solicitud de valoraci\u00f3n id: " + id);
        ResponseValoracionDTO response = valoracionMapper.toDTO(service.getValoracionById(id));
        if (response == null) {
            log.info("Valoraci\u00f3n no encontrada");
            return ResponseEntity.notFound().build();
        }
        log.info("Valoraci\u00f3n encontrada");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ejercicio/{idEjercicio}")
    public List<ResponseValoracionDTO> getValoracionesByEjercicio(@PathVariable Long idEjercicio) {
        log.info("Solicitud de valoraciones del ejercicio id: " + idEjercicio);
        return valoracionMapper.toDTOList(service.getValoracionesByEjercicio(idEjercicio));
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<ResponseValoracionDTO> getValoracionesByUsuario(@PathVariable Long idUsuario) {
        log.info("Solicitud de valoraciones del usuario id: " + idUsuario);
        return valoracionMapper.toDTOList(service.getValoracionesByUsuario(idUsuario));
    }

    @GetMapping("/promedio/{idEjercicio}")
    public ResponseEntity<PromedioValoracionDTO> getPromedioByEjercicio(@PathVariable Long idEjercicio) {
        log.info("Solicitud de promedio del ejercicio id: " + idEjercicio);
        PromedioValoracionDTO promedio = service.getPromedioByEjercicio(idEjercicio);
        return ResponseEntity.ok(promedio);
    }

    @PostMapping
    public ResponseEntity<?> postValoracion(@Valid @RequestBody RequestValoracionDTO request) {
        log.info("Solicitud creaci\u00f3n de una nueva valoraci\u00f3n");
        Valoracion nuevaValoracion = service.postValoracion(request);
        if (nuevaValoracion == null) {
            log.info("No se pudo crear la valoraci\u00f3n: el usuario ya valor\u00f3 este ejercicio");
            return ResponseEntity.badRequest()
                .body("El usuario ya ha valorado este ejercicio. Use PUT para actualizar.");
        }
        ResponseValoracionDTO response = valoracionMapper.toDTO(nuevaValoracion);
        log.debug("Valoraci\u00f3n creada", response);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putValoracion(@PathVariable Long id,
                                           @Valid @RequestBody RequestValoracionDTO request) {
        log.info("Solicitud actualizaci\u00f3n de valoraci\u00f3n id: " + id);
        Valoracion actualizada = service.putValoracion(id, request);
        if (actualizada == null) {
            log.info("Valoraci\u00f3n no encontrada");
            return ResponseEntity.notFound().build();
        }
        ResponseValoracionDTO response = valoracionMapper.toDTO(actualizada);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteValoracion(@PathVariable Long id) {
        log.info("Solicitud eliminaci\u00f3n de valoraci\u00f3n id: " + id);
        boolean eliminada = service.deleteValoracion(id);
        if (!eliminada) {
            log.info("Valoraci\u00f3n no encontrada");
            return ResponseEntity.notFound().build();
        }
        log.info("Valoraci\u00f3n eliminada");
        return ResponseEntity.ok("Valoraci\u00f3n eliminada correctamente");
    }
}
