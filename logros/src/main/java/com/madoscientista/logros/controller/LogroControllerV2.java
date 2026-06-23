package com.madoscientista.logros.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.logros.assembler.LogroAssembler;
import com.madoscientista.logros.dto.logroDTO.RequestLogroDTO;
import com.madoscientista.logros.dto.logroDTO.ResponseLogroDTO;
import com.madoscientista.logros.dto.recuentoDTO.ResponseRecuentoDTO;
import com.madoscientista.logros.mapper.RecuentoMapper;
import com.madoscientista.logros.model.Logro;
import com.madoscientista.logros.model.Recuento;
import com.madoscientista.logros.service.LogroService;
import com.madoscientista.logros.service.RecuentoService;

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

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Logros V2", description = "API de logros")
@RequestMapping("api/v2/logros")
public class LogroControllerV2 {

    private final LogroService lService;
    private final RecuentoService rService;
    private final RecuentoMapper recuentoMapper;
    private final LogroAssembler assembler;

    // --------------------------------------------------------
    // ------------------ Seccion GET -------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Obtener todos los logros de un usuario",
        description = "Retorna todos los logros de un usuario identificado con su ID en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de logros del usuario obtenida exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseLogroDTO.class))))
    })
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<CollectionModel<EntityModel<ResponseLogroDTO>>> getLogrosByIdUsuario(
            @Parameter(description = "ID del usuario", example = "74")
            @PathVariable Long idUsuario) {
        List<Logro> logros = lService.getLogrosByIdUsuario(idUsuario);
        CollectionModel<EntityModel<ResponseLogroDTO>> dtoList = assembler.toCollectionModel(logros);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Obtener todos los logros",
        description = "Retorna todos los logros disponibles en la base de datos")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de logros obtenida exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseLogroDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron logros",
            content = @Content)
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ResponseLogroDTO>>> getLogros() {
        log.info("Lista de logros en DB solicitada");
        List<Logro> logroList = lService.getLogros();

        if (logroList.isEmpty()) {
            log.info("No se encontraron logros en DB");
            return ResponseEntity.notFound().build();
        }

        log.info("Lista de logros encontrada");
        CollectionModel<EntityModel<ResponseLogroDTO>> dtoList = assembler.toCollectionModel(logroList);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Obtener logro por ID",
        description = "Retorna un logro por su ID especificado en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Logro encontrado exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseLogroDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontró logro con el ID indicado",
            content = @Content)
    })
    @GetMapping("{idLogro}")
    public ResponseEntity<EntityModel<ResponseLogroDTO>> getLogroById(
            @Parameter(description = "ID del logro", example = "1")
            @PathVariable Long idLogro) {
        log.info("Solicitud de logro id: {}", idLogro);
        Logro logro = lService.getLogroById(idLogro);

        if (logro == null) {
            log.info("Logro no encontrado");
            return ResponseEntity.notFound().build();
        }

        EntityModel<ResponseLogroDTO> dto = assembler.toModel(logro);
        log.debug("Logro encontrado: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Operation(
        summary = "Obtener el recuento de eventos de un usuario",
        description = "Retorna el recuento de los eventos de un usuario identificado por su ID en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Recuentos encontrados exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseRecuentoDTO.class)))
    })
    @GetMapping("recuentos/usuario/{idUsuario}")
    public ResponseEntity<ResponseRecuentoDTO> getRecuentoByIdUsuario(
            @Parameter(description = "ID del usuario", example = "74")
            @PathVariable Long idUsuario) {
        log.info("Solicitud del recuento del usuario ID: " + idUsuario);

        Recuento recuento = rService.obtenerOCrear(idUsuario);

        if (recuento == null) {
            log.info("Error al obtener el recuento");
            return ResponseEntity.notFound().build();
        }

        ResponseRecuentoDTO dto = recuentoMapper.toDTO(recuento);
        log.debug("Recuento encontrado: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Operation(
        summary = "Obtener los recuentos de todos los usuarios",
        description = "Retorna una lista con todos los recuentos de los usuarios registrados en la plataforma")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de recuentos encontrada",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseRecuentoDTO.class))))
    })
    @GetMapping("recuentos")
    public ResponseEntity<List<ResponseRecuentoDTO>> getRecuentos() {
        log.info("Lista de recuentos en DB solicitada");
        List<Recuento> recuentos = rService.getRecuentos();

        if (recuentos.isEmpty()) {
            log.info("No se encontraron recuentos en DB");
            return ResponseEntity.notFound().build();
        }

        List<ResponseRecuentoDTO> dtoList = recuentoMapper.toDTOList(recuentos);
        log.debug("Lista de recuentos encontrada: {}", dtoList);
        return ResponseEntity.ok(dtoList);
    }

    // --------------------------------------------------------
    // ------------------ Seccion POST ------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Sincroniza los logros del usuario con los tipos de logro disponibles",
        description = "Actualiza la lista de logros disponible para un usuario, considerando nuevos logros que fueron creados luego que el usuario se registrara")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de logros actualizada correctamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseLogroDTO.class))))
    })
    @PostMapping("/sincronizar/{idUsuario}")
    public ResponseEntity<CollectionModel<EntityModel<ResponseLogroDTO>>> postSincronizarLogrosUsuario(
            @Parameter(description = "ID del usuario", example = "74")
            @PathVariable Long idUsuario) {

        List<Logro> logros = lService.postSincronizarLogrosUsuario(idUsuario);
        CollectionModel<EntityModel<ResponseLogroDTO>> dtoList = assembler.toCollectionModel(logros);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Incrementa en 1 el contador de ejercicio creado",
        description = "Aumenta en 1 el contador de ejercicio creado para un usuario identificado por su ID en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Contador de ejercicio creado aumentado en 1",
            content = @Content(schema = @Schema(implementation = ResponseRecuentoDTO.class)))
    })
    @PostMapping("/recuento/ejercicio-creado/{idUsuario}")
    public ResponseEntity<ResponseRecuentoDTO> postIncrementarEjercicioCreado(
            @Parameter(description = "ID del usuario", example = "74")
            @PathVariable Long idUsuario) {
        log.info("Incrementando ejercicios creados para el usuario {}", idUsuario);
        Recuento recuento = rService.incrementarEjerciciosCreados(idUsuario);
        ResponseRecuentoDTO response = new ResponseRecuentoDTO(idUsuario, rService.toMap(recuento));
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Incrementa el contador de ejercicios compartidos",
        description = "Incrementa en 1 el contador de ejercicios compartidos para un usuario identificado por su ID en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Contador de ejercicios compartidos aumentado en 1",
            content = @Content(schema = @Schema(implementation = ResponseRecuentoDTO.class)))
    })
    @PostMapping("/recuento/ejercicio-compartido/{idUsuario}")
    public ResponseEntity<ResponseRecuentoDTO> postIncrementarEjercicioCompartido(
            @Parameter(description = "ID del usuario", example = "74")
            @PathVariable Long idUsuario,
            @RequestBody int cantidad) {
        log.info("Incrementando ejercicios compartidos para el usuario {} en {}", idUsuario, cantidad);
        Recuento recuento = rService.incrementarEjerciciosCompartidos(idUsuario, cantidad);
        ResponseRecuentoDTO response = new ResponseRecuentoDTO(idUsuario, rService.toMap(recuento));
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Incrementa el contador de comunidad",
        description = "Incrementa el contador de comunidad para un usuario identificado por su ID en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Contador de comunidad aumentado en 1",
            content = @Content(schema = @Schema(implementation = ResponseRecuentoDTO.class)))
    })
    @PostMapping("/recuento/comunidad/{idUsuario}")
    public ResponseEntity<ResponseRecuentoDTO> postIncrementarComunidad(
            @Parameter(description = "ID del usuario", example = "74")
            @PathVariable Long idUsuario,
            @RequestBody int cantidad) {
        log.info("Incrementando comunidades para el usuario {} en {}", idUsuario, cantidad);
        Recuento recuento = rService.incrementarComunidad(idUsuario, cantidad);
        ResponseRecuentoDTO response = new ResponseRecuentoDTO(idUsuario, rService.toMap(recuento));
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Incrementar contador de items creados",
        description = "Incrementa el contador de items creados para un usuario identificado por su ID en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Contador de items aumentado en 1",
            content = @Content(schema = @Schema(implementation = ResponseRecuentoDTO.class)))
    })
    @PostMapping("/recuento/item-creado/{idUsuario}")
    public ResponseEntity<ResponseRecuentoDTO> postIncrementarItemCreado(
            @Parameter(description = "ID del usuario", example = "74")
            @PathVariable Long idUsuario,
            @RequestBody int cantidad) {
        log.info("Incrementando items creados para el usuario {} en {}", idUsuario, cantidad);
        Recuento recuento = rService.incrementarItemsCreados(idUsuario, cantidad);
        ResponseRecuentoDTO response = new ResponseRecuentoDTO(idUsuario, rService.toMap(recuento));
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Incrementa el contador de material creado",
        description = "Incrementa el contador de material creado para un usuario identificado por su ID en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Contador de material aumentado en 1",
            content = @Content(schema = @Schema(implementation = ResponseRecuentoDTO.class)))
    })
    @PostMapping("/recuento/material/{idUsuario}")
    public ResponseEntity<ResponseRecuentoDTO> postIncrementarMaterialCreado(
            @Parameter(description = "ID del usuario", example = "74")
            @PathVariable Long idUsuario) {
        log.info("Incrementando materiales creados para el usuario {}", idUsuario);
        Recuento recuento = rService.incrementarMaterialCreado(idUsuario);
        ResponseRecuentoDTO response = new ResponseRecuentoDTO(idUsuario, rService.toMap(recuento));
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Incrementar comunidad de múltiples usuarios",
        description = "Incrementa el contador de comunidad para una lista de usuarios")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Contadores de comunidad aumentados exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseRecuentoDTO.class))))
    })
    @PostMapping("/recuento/comunidad")
    public ResponseEntity<List<ResponseRecuentoDTO>> postIncrementarComunidad(
            @RequestBody Set<Long> idsUsuarios) {
        log.info("Incrementando comunidades para {} usuarios", idsUsuarios.size());
        List<Recuento> recuentos = rService.incrementarComunidadParaUsuarios(idsUsuarios, 1);
        List<ResponseRecuentoDTO> response = new ArrayList<>();
        for (Recuento r : recuentos) {
            response.add(new ResponseRecuentoDTO(r.getIdUsuario(), rService.toMap(r)));
        }
        return ResponseEntity.ok(response);
    }

    // --------------------------------------------------------
    // ------------------ Seccion PUT -------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Actualiza un logro a completado",
        description = "Actualiza un logro a completado y agrega la fecha de completacion")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Logro actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseLogroDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Logro no encontrado o datos invalidos",
            content = @Content)
    })
    @PutMapping
    public ResponseEntity<EntityModel<ResponseLogroDTO>> putLogroCompletado(
            @Valid @RequestBody RequestLogroDTO request) {

        Long idUsuario = request.getIdUsuario();
        String nombreTipoUsuario = request.getNombreTipoLogro();

        Logro logroActualizado = lService.putLogroCompletado(idUsuario, nombreTipoUsuario);

        if (logroActualizado != null) {
            EntityModel<ResponseLogroDTO> dto = assembler.toModel(logroActualizado);
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.badRequest().build();
    }
}
