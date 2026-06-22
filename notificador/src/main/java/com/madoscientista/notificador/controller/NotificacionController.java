package com.madoscientista.notificador.controller;

import java.util.List;

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

@Tag(name = "Notificación", description = "API para el manejo de notificaciones")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/notificaciones")
public class NotificacionController {

    private final NotificacionService nService;
    private final TipoNotificacionService tnService;
    private final NotificacionMapper nMapper;


    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // Crea notificaciones para un usuario creador hacia uno o varios usuarios destino

    @Operation(
        summary = "Crea una notificación",
        description = "Crea una notificación para un usuario creador y uno o varios destinos"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Notificación creada con éxito",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseNotificacionDTO.class)))
        )
    })
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

    @Operation(
        summary = "Obtener todas las notificaciones",
        description = "Retorna todas las notificaciones disponibles en la plataforma"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista encontrada con éxito",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseNotificacionDTO.class)))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron notificaciones",
            content = @Content
        )
    })
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

    // Retorna una notificación filtrada por id
    @Operation(
        summary = "Obtener notificación por ID",
        description = "Retorna una notificación filtrada por el ID indicado en la ruta"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Notificación encontrada",
            content = @Content(schema = @Schema(implementation = ResponseNotificacionDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Notificación no encontrada",
            content = @Content
        )
    })
    @GetMapping("{idNotificacion}")
    public ResponseEntity<ResponseNotificacionDTO> getNotificacionById(@PathVariable Long idNotificacion){
        log.info("Notificación solicitada por id {}", idNotificacion);

        Notificacion n = nService.getNotificacionById(idNotificacion);
        if (n == null) {
            log.info("Notificación no encontrada para id {}", idNotificacion);
            return ResponseEntity.notFound().build();
        }

        ResponseNotificacionDTO dto = nMapper.toDTO(n);
        log.debug("Notificación encontrada {}", dto);
        return ResponseEntity.ok(dto);
    }

    // Retorna las notificaciones filtradas por ID de usuario

    @Operation(
        summary = "Obtener notificaciones de un usuario",
        description = "Retorna una lista de todas las notificaciones de un usuario filtrado por el ID indicado en la ruta"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de notificaciones encontradas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseNotificacionDTO.class)))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Lista de notificaciones no encontrada o usuario no existe",
            content = @Content
        )
    })
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
    @Operation(
        summary = "Obtener notificaciones leídas por un usuario",
        description = "Retorna una lista con las notificaciones leídas por un usuario filtrado por el ID indicado en la ruta"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de notificaciones encontrada",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseNotificacionDTO.class)))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Lista de notificaciones vacía o usuario no existe",
            content = @Content
        )
    })
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
    @Operation(
        summary = "Obtener las notificaciones no leídas por un usuario",
        description = "Retorna una lista con las notificaciones no leídas por un usuario filtrado por el ID indicado en la ruta"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de notificaciones encontrada",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseNotificacionDTO.class)))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Lista de notificaciones vacía o usuario no existe",
            content = @Content
        )
    })
    @GetMapping("no-leidas/usuarios/{idUsuario}")
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
