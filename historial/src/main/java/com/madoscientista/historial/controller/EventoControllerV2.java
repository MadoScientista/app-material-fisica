package com.madoscientista.historial.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.historial.assembler.EventoAssembler;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Eventos V2", description = "API de eventos con HATEOAS")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/eventos")
public class EventoControllerV2 {

    private final EventoService eService;
    private final TipoEventoService teService;
    private final EventoMapper eMapper;
    private final EventoAssembler assembler;

    // --------------------------------------------------------
    // ------------------ Seccion GET -------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Obtener todos los eventos de un usuario",
        description = "Retorna todos los eventos de un usuario cuyo ID se indica en el path")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eventos encontrados"),
        @ApiResponse(responseCode = "204", description = "No se han encontrado eventos o usuario no encontrado")
    })
    @GetMapping("usuarios/{idUsuario}")
    public ResponseEntity<CollectionModel<EntityModel<ResponseEventoDTO>>> getEventosByUsuarioId(
        @Parameter(description = "ID de usuario", example = "6")
        @PathVariable Long idUsuario){

        log.info("Se solicitaron el historial del usuario id: " + idUsuario);
        List<Evento> eventos = eService.getEventosByIdUsuarioOrigen(idUsuario);

        if(eventos.isEmpty()){
            log.debug("Usuario ID: {} no registra eventos", idUsuario);
            return ResponseEntity.noContent().build();
        }
        CollectionModel<EntityModel<ResponseEventoDTO>> dtoList = assembler.toCollectionModel(eventos);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Obtener todos los eventos disponibles",
        description = "Retorna todos los eventos disponibles en la base de datos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Se han recuperado los eventos correctamente"),
        @ApiResponse(responseCode = "404", description = "No se han encontrado eventos")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ResponseEventoDTO>>> getEventos(){
        log.info("Lista de eventos solicitada");
        List<Evento> listaEventos = eService.getEventos();

        if(listaEventos.isEmpty()){
            log.info("Lista de eventos vacia. No se encontraron eventos");
            return ResponseEntity.notFound().build();
        }

        CollectionModel<EntityModel<ResponseEventoDTO>> dtoList = assembler.toCollectionModel(listaEventos);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Obtener evento por ID",
        description = "Retorna un evento filtrado por su ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Evento encontrado",
            content = @Content(schema = @Schema(implementation = ResponseEventoDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontro evento con el ID indicado",
            content = @Content)
    })
    @GetMapping("{idEvento}")
    public ResponseEntity<EntityModel<ResponseEventoDTO>> getEventoById(
            @Parameter(description = "ID del evento", example = "10")
            @PathVariable Long idEvento){
        log.info("Solicitud de evento id: " + idEvento);
        Evento evento = eService.getEventoById(idEvento);
        if(evento == null){
            log.info("Evento no encontrado");
            return ResponseEntity.notFound().build();
        }
        EntityModel<ResponseEventoDTO> dto = assembler.toModel(evento);
        return ResponseEntity.ok(dto);
    }

    // --------------------------------------------------------
    // ------------------ Seccion POST ------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Crear eventos para un usuario origen y varios usuarios destino",
        description = "La creacion de eventos tiene un usuario origen y uno o varios de destino")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Eventos creados con exito",
            content = @Content(schema = @Schema(implementation = ResponseEventoDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Error en la solicitud",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<ResponseEventoDTO>> postEvento(
        @Valid @RequestBody RequestEventoDTO request){

        log.debug("Solicitud de creacion de eventos con los datos {} ", request);
        TipoEvento tipoEvento = teService.getById(request.getIdTipoEvento());
        Evento evento = eService.postEvento(eMapper.toEntity(request, tipoEvento), request.getIdUsuarioDestino());

        if(evento == null){
            return ResponseEntity.badRequest().build();
        }

        EntityModel<ResponseEventoDTO> response = assembler.toModel(evento);
        log.debug("Eventos creados {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
        summary = "Crear eventos para una lista de usuarios",
        description = "Crea eventos a partir de una lista de solicitudes RequestEventoDTO")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Eventos creados con exito",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseEventoDTO.class)))),
        @ApiResponse(
            responseCode = "400",
            description = "Solicitud invalida o datos incorrectos",
            content = @Content)
    })
    @PostMapping("/lista")
    public ResponseEntity<CollectionModel<EntityModel<ResponseEventoDTO>>> postEventos(@Valid @RequestBody List<RequestEventoDTO> requests) {

        log.debug("Solicitud de creacion de eventos multiples: {}", requests);
        List<Evento> eventosCreados = new ArrayList<>();
        for (RequestEventoDTO request : requests) {
            TipoEvento tipoEvento = teService.getById(request.getIdTipoEvento());
            if (tipoEvento == null) continue;
            Evento evento = eService.postEvento(
                eMapper.toEntity(request, tipoEvento),
                request.getIdUsuarioDestino()
            );
            if (evento != null) {
                eventosCreados.add(evento);
            }
        }
        CollectionModel<EntityModel<ResponseEventoDTO>> response = assembler.toCollectionModel(eventosCreados);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
