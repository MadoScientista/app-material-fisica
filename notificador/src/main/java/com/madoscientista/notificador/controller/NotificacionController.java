package com.madoscientista.notificador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService nService;

    @Autowired
    private TipoNotificacionService tnService;

    @Autowired
    private NotificacionMapper nMapper;


    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // Crea notificaciones para un usuario creador hacia uno o varios usuarios destino
    @PostMapping
    public ResponseEntity<List<ResponseNotificacionDTO>> postNotificacion(@RequestBody @Valid RequestEventoDTO request) {
        log.debug("Solicitud de creación de notificaciones con los siguientes datos {}", request);

        List<TipoNotificacion> tipos = tnService.getTipoNotificacionByIdTipoEvento(request.getIdTipoEvento());
        List<Notificacion> nuevasNotificaciones = nService.postNotificaciones(nMapper.toEntities(request, tipos));
        List<ResponseNotificacionDTO> response = nMapper.toDTOs(nuevasNotificaciones);
        
        log.debug("Norificaciones creadas {} ", response);
        return ResponseEntity.ok(response);
    }

    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    // Retorna todas las notiicaciones disponibles en BD
    @GetMapping
    public ResponseEntity<List<ResponseNotificacionDTO>> getNotificaciones(){
        log.info("Lista de notificaciones disponibles en DB solicitada");

        List<Notificacion> notificaciones = nService.getNotificaciones();

        if(notificaciones.isEmpty()){
            log.info("Lista de notificaciones vacía");
            return ResponseEntity.notFound().build();
        }

        List<ResponseNotificacionDTO> dtoList = nMapper.toDTOs(notificaciones);
        log.debug("Lista de notificaciones encontrada, {}", dtoList);
        return ResponseEntity.ok(dtoList);
    }

    // Retorna las notificaciones filtradas por ID de usuario
    @GetMapping("usuarios/{idUsuario}")
    public ResponseEntity<List<ResponseNotificacionDTO>> getByIdUsuario(@PathVariable Long idUsuario){
        log.info("Lista de notificaciones de usuario solicitada");

        List<Notificacion> notificaciones = nService.getAllNotificacionesByUsuarioId(idUsuario);

        if(notificaciones.isEmpty()){
            log.info("Lista de notificaciones vacía");
            return ResponseEntity.notFound().build();
        }

        List<ResponseNotificacionDTO> dtoList = nMapper.toDTOs(notificaciones);
        log.debug("Lista de notificaciones encontrada, {}", dtoList);
        return ResponseEntity.ok(dtoList);
    }

    // Retorna las notificaciones leídas por un usuario
    @GetMapping("leidas/usuarios/{idUsuario}")
    public ResponseEntity<List<ResponseNotificacionDTO>> getLeidasByIdUsuario(@PathVariable Long idUsuario){
        log.info("Lista de notificaciones leídas por un usuario solicitada");

        List<Notificacion> notificaciones = nService.getNotificacionesLeidasByUsuarioId(idUsuario);

        if(notificaciones.isEmpty()){
            log.info("Lista de notificaciones vacía");
            return ResponseEntity.notFound().build();
        }

        List<ResponseNotificacionDTO> dtoList = nMapper.toDTOs(notificaciones);
        log.debug("Lista de notificaciones encontrada, {}", dtoList);
        return ResponseEntity.ok(dtoList);
    }


    // Retorna las notificaciones no leídas por un usuario
    @GetMapping("leidas/usuarios/{idUsuario}")
    public ResponseEntity<List<ResponseNotificacionDTO>> getNoLeidasByIdUsuario(@PathVariable Long idUsuario){
        log.info("Lista de notificaciones no leídas por un usuario solicitada");

        List<Notificacion> notificaciones = nService.getNotificacionesNoLeidasByUsuarioId(idUsuario);

        if(notificaciones.isEmpty()){
            log.info("Lista de notificaciones vacía");
            return ResponseEntity.notFound().build();
        }

        List<ResponseNotificacionDTO> dtoList = nMapper.toDTOs(notificaciones);
        log.debug("Lista de notificaciones encontrada, {}", dtoList);
        return ResponseEntity.ok(dtoList);
    }
}
