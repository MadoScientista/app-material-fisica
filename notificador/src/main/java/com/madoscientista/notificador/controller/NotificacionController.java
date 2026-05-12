package com.madoscientista.notificador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.notificador.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.notificador.dto.NotificacionDTO.ResponseNotificacionDTO;
import com.madoscientista.notificador.mapper.NotificacionMapper;
import com.madoscientista.notificador.model.Notificacion;
import com.madoscientista.notificador.model.TipoNotificacion;
import com.madoscientista.notificador.service.NotificacionService;
import com.madoscientista.notificador.service.TipoNotificacionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService nService;

    @Autowired
    private TipoNotificacionService tnService;

    @Autowired
    private NotificacionMapper nMapper;



    @PostMapping
    public ResponseEntity<List<ResponseNotificacionDTO>> postNotificacion(@RequestBody @Valid RequestEventoDTO request) {

        // Obtiene el tipo de notificación para obtener la plantilla del mensaje
        TipoNotificacion tipo = tnService.getTipoNotificacionById(request.getIdTipoEvento());
        
        // Crea la notificación
        List<Notificacion> nuevasNotificaciones = nService.postNotificaciones(nMapper.toEntities(request, tipo));

        // Retorna DTO
        List<ResponseNotificacionDTO> response = nMapper.toDTOs(nuevasNotificaciones);
        
        return ResponseEntity.ok(response);
    }
}
