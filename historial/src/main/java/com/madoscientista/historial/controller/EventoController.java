package com.madoscientista.historial.controller;

import java.util.ArrayList;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Eventos")
@Slf4j
@RestController
@RequestMapping("api/v1/eventos")
public class EventoController {

    // Inyección de servicios
    @Autowired
    private EventoService eService;

    @Autowired
    private TipoEventoService teService;

    // Inyección de mappers
    @Autowired
    private EventoMapper eMapper;


    // -----------------------------------------------------------------------------------------------------
    // ----------------------------------------- Sección GET -----------------------------------------------
    // -----------------------------------------------------------------------------------------------------

    // --------------------------------- Obtener eventos de un usuario -------------------------------------

    @Operation(
        summary = "Obtener todos los eventos de un usuario",
        description = "Retorna todos los eventos de un usuario cuyo ID se indica en el path")
    @ApiResponses({
        @ApiResponse(responseCode="200", description="Eventos encontrados"),
        @ApiResponse(responseCode="404", description="No se han encontrado eventos o usuario no encontrado")
    })

    @GetMapping("usuarios/{idUsuario}")
    public ResponseEntity<List<ResponseEventoDTO>> getEventosByUsuarioId(
        @Parameter(description = "ID de usuario", example="6")
        @PathVariable Long idUsuario){
        
        log.info("Se solicitaron el historial del usuario id: " + idUsuario);
        List<Evento> eventos = eService.getEventosByIdUsuarioOrigen(idUsuario);

        if(eventos.isEmpty()){
            log.debug("Usuario ID: {} no registra eventos", idUsuario);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(eMapper.toDTOList(eventos));
    }

    // --------------------------- Obtener todos los eventos disponibles -------------------------------

    @Operation(
        summary = "Obtener todos los eventos disponibles",
        description = "Retorna todos los eventos disponibles en la base de datos")
    @ApiResponses({
        @ApiResponse(responseCode="200", description="Se han recuperado los eventos correctamente"),
        @ApiResponse(responseCode="404", description="No se han encontrado eventos")
    })

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


    // -----------------------------------------------------------------------------------------------------
    // ----------------------------------------- Sección POST ----------------------------------------------
    // -----------------------------------------------------------------------------------------------------

    // ----------------- Crear eventos para un usuario origen y varios usuarios destino --------------------

    @Operation(
        summary = "Crear eventos para un usuario origen y varios usuarios destino",
        description = "La creación de eventos tiene un usuario origen y uno o varios de destino")
    @ApiResponses({
        @ApiResponse(
            responseCode="201", 
            description="Eventos creados con éxito",
            content = @Content(schema = @Schema(implementation = ResponseEventoDTO.class))),
        @ApiResponse(
            responseCode="400", 
            description="Error en la solicitud",
            content = @Content),
        @ApiResponse(
            responseCode="500", 
            description="Error interno",
            content = @Content)
    })

    @PostMapping
    public ResponseEntity<ResponseEventoDTO> postEvento(
        @Valid @RequestBody RequestEventoDTO request){

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

    //-------------------------- Crear eventos para una lista de usuarios --------------------------------------

    @Operation(
        summary = "Crear eventos para una lista de usuarios",
        description = "Crea eventos a partir de una lista de solicitudes RequestEventoDTO")
    @ApiResponses({
        @ApiResponse(
            responseCode="201", 
            description = "Eventos creados con éxito",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseEventoDTO.class)))),
        @ApiResponse(
            responseCode="400",
            description = "Solicitud inválida o datos incorrectos",
            content = @Content)
    })

    @PostMapping("/lista")
    public ResponseEntity<List<ResponseEventoDTO>> postEventos(@Valid @RequestBody List<RequestEventoDTO> requests) {

        log.debug("Solicitud de creación de eventos múltiples: {}", requests);
        List<ResponseEventoDTO> responses = new ArrayList<>();
        for (RequestEventoDTO request : requests) {
            TipoEvento tipoEvento = teService.getById(request.getIdTipoEvento());
            if (tipoEvento == null) continue;
            Evento evento = eService.postEvento(
                eMapper.toEntity(request, tipoEvento), 
                request.getIdUsuarioDestino()
            );
            if (evento != null) {
                responses.add(eMapper.toDTO(evento));
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }
    
}
