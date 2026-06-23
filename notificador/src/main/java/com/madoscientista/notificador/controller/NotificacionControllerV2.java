package com.madoscientista.notificador.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.notificador.assembler.NotificacionAssembler;
import com.madoscientista.notificador.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.notificador.dto.NotificacionDTO.ResponseNotificacionDTO;
import com.madoscientista.notificador.mapper.NotificacionMapper;
import com.madoscientista.notificador.model.Notificacion;
import com.madoscientista.notificador.model.TipoNotificacion;
import com.madoscientista.notificador.service.NotificacionService;
import com.madoscientista.notificador.service.TipoNotificacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Notificaciones V2", description = "API para el manejo de notificaciones con HATEOAS")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/notificaciones")
public class NotificacionControllerV2 {

    private final NotificacionService nService;
    private final TipoNotificacionService tnService;
    private final NotificacionMapper nMapper;
    private final NotificacionAssembler assembler;

    // --------------------------------------------------------
    // ------------------ Seccion GET -------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Obtener todas las notificaciones",
        description = "Retorna todas las notificaciones disponibles en la plataforma")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista encontrada con exito",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseNotificacionDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron notificaciones",
            content = @Content)
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ResponseNotificacionDTO>>> getNotificaciones(){
        log.info("Lista de notificaciones disponibles en DB solicitada");

        List<Notificacion> notificaciones = nService.getNotificaciones();

        if(notificaciones.isEmpty()){
            log.info("Lista de notificaciones vacia");
            return ResponseEntity.notFound().build();
        }

        CollectionModel<EntityModel<ResponseNotificacionDTO>> dtoList = assembler.toCollectionModel(notificaciones);
        log.debug("Lista de notificaciones encontrada, {}", dtoList);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Obtener notificacion por ID",
        description = "Retorna una notificacion filtrada por el ID indicado en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Notificacion encontrada",
            content = @Content(schema = @Schema(implementation = ResponseNotificacionDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Notificacion no encontrada",
            content = @Content)
    })
    @GetMapping("{idNotificacion}")
    public ResponseEntity<EntityModel<ResponseNotificacionDTO>> getNotificacionById(@PathVariable Long idNotificacion){
        log.info("Notificacion solicitada por id {}", idNotificacion);

        Notificacion n = nService.getNotificacionById(idNotificacion);
        if (n == null) {
            log.info("Notificacion no encontrada para id {}", idNotificacion);
            return ResponseEntity.notFound().build();
        }

        EntityModel<ResponseNotificacionDTO> dto = assembler.toModel(n);
        log.debug("Notificacion encontrada {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Operation(
        summary = "Obtener notificaciones de un usuario",
        description = "Retorna una lista de todas las notificaciones de un usuario filtrado por el ID indicado en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de notificaciones encontradas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseNotificacionDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "Lista de notificaciones no encontrada o usuario no existe",
            content = @Content)
    })
    @GetMapping("usuarios/{idUsuario}")
    public ResponseEntity<CollectionModel<EntityModel<ResponseNotificacionDTO>>> getByIdUsuario(@PathVariable Long idUsuario){
        log.info("Lista de notificaciones de usuario solicitada");

        List<Notificacion> notificaciones = nService.getAllNotificacionesByUsuarioId(idUsuario);

        if(notificaciones.isEmpty()){
            log.info("Lista de notificaciones vacia");
            return ResponseEntity.notFound().build();
        }

        CollectionModel<EntityModel<ResponseNotificacionDTO>> dtoList = assembler.toCollectionModel(notificaciones);
        log.debug("Lista de notificaciones encontrada, {}", dtoList);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Obtener notificaciones leidas por un usuario",
        description = "Retorna una lista con las notificaciones leidas por un usuario filtrado por el ID indicado en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de notificaciones encontrada",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseNotificacionDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "Lista de notificaciones vacia o usuario no existe",
            content = @Content)
    })
    @GetMapping("leidas/usuarios/{idUsuario}")
    public ResponseEntity<CollectionModel<EntityModel<ResponseNotificacionDTO>>> getLeidasByIdUsuario(@PathVariable Long idUsuario){
        log.info("Lista de notificaciones leidas por un usuario solicitada");

        List<Notificacion> notificaciones = nService.getNotificacionesLeidasByUsuarioId(idUsuario);

        if(notificaciones.isEmpty()){
            log.info("Lista de notificaciones vacia");
            return ResponseEntity.notFound().build();
        }

        CollectionModel<EntityModel<ResponseNotificacionDTO>> dtoList = assembler.toCollectionModel(notificaciones);
        log.debug("Lista de notificaciones encontrada, {}", dtoList);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Obtener las notificaciones no leidas por un usuario",
        description = "Retorna una lista con las notificaciones no leidas por un usuario filtrado por el ID indicado en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de notificaciones encontrada",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseNotificacionDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "Lista de notificaciones vacia o usuario no existe",
            content = @Content)
    })
    @GetMapping("no-leidas/usuarios/{idUsuario}")
    public ResponseEntity<CollectionModel<EntityModel<ResponseNotificacionDTO>>> getNoLeidasByIdUsuario(@PathVariable Long idUsuario){
        log.info("Lista de notificaciones no leidas por un usuario solicitada");

        List<Notificacion> notificaciones = nService.getNotificacionesNoLeidasByUsuarioId(idUsuario);

        if(notificaciones.isEmpty()){
            log.info("Lista de notificaciones vacia");
            return ResponseEntity.notFound().build();
        }

        CollectionModel<EntityModel<ResponseNotificacionDTO>> dtoList = assembler.toCollectionModel(notificaciones);
        log.debug("Lista de notificaciones encontrada, {}", dtoList);
        return ResponseEntity.ok(dtoList);
    }

    // --------------------------------------------------------
    // ------------------ Seccion POST ------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Crea una notificacion",
        description = "Crea una notificacion para un usuario creador y uno o varios destinos")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Notificacion creada con exito",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseNotificacionDTO.class))))
    })
    @PostMapping
    public ResponseEntity<CollectionModel<EntityModel<ResponseNotificacionDTO>>> postNotificacion(@RequestBody @Valid RequestEventoDTO request) {
        log.debug("Solicitud de creacion de notificaciones con los siguientes datos {}", request);

        List<TipoNotificacion> tipos = tnService.getTipoNotificacionByIdTipoEvento(request.getIdTipoEvento());
        List<Notificacion> nuevasNotificaciones = nService.postNotificaciones(nMapper.toEntities(request, tipos));
        CollectionModel<EntityModel<ResponseNotificacionDTO>> response = assembler.toCollectionModel(nuevasNotificaciones);

        log.debug("Notificaciones creadas {} ", response);
        return ResponseEntity.ok(response);
    }
}
