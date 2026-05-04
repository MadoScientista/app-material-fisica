package com.madoscientista.suscripciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.suscripciones.dto.ResponseSuscripcionDTO;
import com.madoscientista.suscripciones.mapper.SuscripcionMapper;
import com.madoscientista.suscripciones.service.SuscripcionService;

@RestController
@RequestMapping("api/v1/suscripciones")
public class SuscripcionController {

    @Autowired
    private SuscripcionService service;

    @Autowired
    private SuscripcionMapper suscripcionMapper;

    @GetMapping("/activas")
    public List<ResponseSuscripcionDTO> getSuscripcionesActivas() {
        return suscripcionMapper.toDTOList(service.getSuscripcionesActivas());
    }

    @GetMapping("/usuarios-activos")
    public List<Long> getUsuariosConSuscripcionesActivas() {
        return service.getUsuariosConSuscripcionesActivas();
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseSuscripcionDTO getSuscripcionByUsuarioId(@PathVariable Long idUsuario) {
        return suscripcionMapper.toDTO(service.getSuscripcionByUsuarioId(idUsuario));
    }

    @GetMapping("/usuarios")
    public List<ResponseSuscripcionDTO> getSuscripcionesByUsuarioIds(@RequestBody List<Long> idUsuarios) {
        return suscripcionMapper.toDTOList(service.getSuscripcionesByUsuarioIds(idUsuarios));
    }
}
