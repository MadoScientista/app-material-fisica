package com.madoscientista.historial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.historial.dto.RequestEventoDTO;
import com.madoscientista.historial.dto.ResponseEventoDTO;
import com.madoscientista.historial.model.Evento;
import com.madoscientista.historial.service.EventoService;

@RestController
@RequestMapping("api/v1/eventos")
public class EventoController {

    @Autowired
    private EventoService service;


    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    // Retorna una lista de eventos asociados a un usuario según el ID del usuario
    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<Evento>> getEventosByUsuarioId(@PathVariable Long idUsuario){
        List<Evento> eventos = service.getEventosByIdUsuario(idUsuario);
        return ResponseEntity.ok(eventos);
    }

    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // Crea un nuevo evento a partir de los datos del request
    @PostMapping
    public ResponseEntity<?> postEvento(@RequestBody RequestEventoDTO request){
        Evento evento = service.postEvento(request);

        if(evento == null){
            return ResponseEntity.badRequest().build();
        }

        ResponseEventoDTO response = new ResponseEventoDTO();
        response.setIdEvento(evento.getIdEvento());
        response.setFecha(evento.getFecha());
        response.setIdUsuario(evento.getIdUsuario());
        response.setDescripcion(evento.getDescripcion());
        
        return ResponseEntity.ok(response);
    }
    
}
