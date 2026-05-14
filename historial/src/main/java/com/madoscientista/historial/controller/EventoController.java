package com.madoscientista.historial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.historial.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.historial.dto.EventoDTO.ResponseEventoDTO;
import com.madoscientista.historial.mapper.EventoMapper;
import com.madoscientista.historial.model.Evento;
import com.madoscientista.historial.model.TipoEvento;
import com.madoscientista.historial.service.EventoService;
import com.madoscientista.historial.service.TipoEventoService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/eventos")
public class EventoController {

    @Autowired
    private EventoService eService;

    @Autowired
    private EventoMapper eMapper;

    @Autowired
    private TipoEventoService teService;


    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    // Retorna una lista de eventos asociados a un usuario según el ID del usuario
    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<ResponseEventoDTO>> getEventosByUsuarioId(@PathVariable Long idUsuario){
        log.info("Se solicitaron el historial del usuario id: " + idUsuario);
        List<Evento> eventos = eService.getEventosByIdUsuarioOrigen(idUsuario);

        if(eventos.isEmpty()){
            log.debug("Usuario ID: {} no registra eventos", idUsuario);
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(eMapper.toDTOList(eventos));
    }

    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // Crea un nuevo evento a partir de los datos del request
    @PostMapping
    public ResponseEntity<?> postEvento(@Valid @RequestBody RequestEventoDTO request){

        log.debug("Solicitud de creación de eventos con los datos {} ", request);
        TipoEvento tipoEvento = teService.getById(request.getIdTipoEvento());
        Evento evento = eService.postEvento(eMapper.toEntity(request, tipoEvento), request.getIdUsuarioDestino());

        if(evento == null){
            return ResponseEntity.badRequest().build();
        }

        ResponseEventoDTO response = eMapper.toDTO(evento);
        
        log.debug("Eventos creados {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
}
