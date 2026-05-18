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

    // Retorna los eventos de un usuario filtrado por su ID
    @GetMapping("usuarios/{idUsuario}")
    public ResponseEntity<List<ResponseEventoDTO>> getEventosByUsuarioId(@PathVariable Long idUsuario){
        log.info("Se solicitaron el historial del usuario id: " + idUsuario);
        List<Evento> eventos = eService.getEventosByIdUsuarioOrigen(idUsuario);

        if(eventos.isEmpty()){
            log.debug("Usuario ID: {} no registra eventos", idUsuario);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(eMapper.toDTOList(eventos));
    }

    // Retorna la lista de eventos disponible en BD
    @GetMapping
    public ResponseEntity<List<ResponseEventoDTO>> getEventos(){
        log.info("Lista de eventos solicitada");
        List<Evento> listaEventos = eService.getEventos();

        if(listaEventos.isEmpty()){
            log.info("Lista de eventos vacía. No se encontraron eventos");
            return ResponseEntity.notFound().build();
        }

        List<ResponseEventoDTO> dtoList = eMapper.toDTOList(listaEventos);

        return ResponseEntity.ok(dtoList);
    }


    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // Crea eventos para un usuario origen y varios usuarios destino
    @PostMapping
    public ResponseEntity<ResponseEventoDTO> postEvento(@Valid @RequestBody RequestEventoDTO request){

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
