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

import com.madoscientista.historial.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.historial.mapper.EventoMapper;
import com.madoscientista.historial.model.Evento;
import com.madoscientista.historial.model.TipoEvento;
import com.madoscientista.historial.service.EventoService;
import com.madoscientista.historial.service.TipoEventoService;

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
    public ResponseEntity<List<Evento>> getEventosByUsuarioId(@PathVariable Long idUsuario){
        List<Evento> eventos = eService.getEventosByIdUsuario(idUsuario);
        return ResponseEntity.ok(eventos);
    }

    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // Crea un nuevo evento a partir de los datos del request
    @PostMapping
    public ResponseEntity<?> postEvento(@RequestBody RequestEventoDTO request){

        TipoEvento tipoEvento = teService.getById(request.getIdTipoEvento());
        Evento evento = eService.postEvento(eMapper.toEntity(request, tipoEvento));

        if(evento == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(eMapper.toDTO(evento));
    }
    
}
