package com.madoscientista.logros.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.logros.dto.logroDTO.RequestLogroDTO;
import com.madoscientista.logros.dto.logroDTO.ResponseLogroDTO;
import com.madoscientista.logros.mapper.LogroMapper;
import com.madoscientista.logros.mapper.RecuentoMapper;
import com.madoscientista.logros.model.Logro;
import com.madoscientista.logros.model.Recuento;
import com.madoscientista.logros.dto.recuentoDTO.ResponseRecuentoDTO;
import com.madoscientista.logros.service.LogroService;
import com.madoscientista.logros.service.RecuentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("api/v1/logros")
public class LogroController {

    @Autowired
    private LogroService lService;

    @Autowired
    private RecuentoService rService;

    @Autowired
    private LogroMapper logroMapper;

    @Autowired
    private RecuentoMapper recuentoMapper;


    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    
    // Retorna todos los logros de un usuario

    @Operation(
        summary = "Obtener todos los logros de un usuario",
        description = "Retorna todos los logros de un usuario identificado con su id en la ruta"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de logros del usuario obtenida exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseLogroDTO.class))))
    })
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ResponseLogroDTO>> getLogrosByIdUsuario(@PathVariable Long idUsuario) {
        List<ResponseLogroDTO> response = logroMapper.toDTOs(lService.getLogrosByIdUsuario(idUsuario));
        return ResponseEntity.ok(response);
    }

    // Retorna la lista de logros disponible en BD

    @Operation(
        summary = "Obtener todos los logros",
        description = "Retorna todos los logros disponibles en la base de datos"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de logros obtenida exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseLogroDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron logros",
            content = @Content
        )
    })
    @GetMapping
    public ResponseEntity<List<ResponseLogroDTO>> getLogros() {
        log.info("Lista de logros en DB solicitada");
        List<Logro> logroList = lService.getLogros();

        if(logroList.isEmpty()){
            log.info("No se encontraron logros en DB");
            return ResponseEntity.notFound().build();
        }

        log.info("Lista de logros encontrada");
        List<ResponseLogroDTO> dtoList = logroMapper.toDTOs(logroList);

        return ResponseEntity.ok(dtoList);
    }

    // Retorna el recuento de un usuario filtrado por su id

    @Operation(
        summary = "Obtener el recuento de eventos de un usuario",
        description = "Retorna el recuento de los eventos de un usuario identificado por su ID en la ruta"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Recuentos encontrados exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseRecuentoDTO.class))
        )
    })
    @GetMapping("recuentos/usuario/{idUsuario}")
    public ResponseEntity<ResponseRecuentoDTO> getRecuentoByIdUsuario(@PathVariable Long idUsuario){
        log.info("Solciitud del recuento del usuario ID: " + idUsuario);

        Recuento recuento = rService.obtenerOCrear(idUsuario);

        if(recuento == null){
            log.info("Error al obtener el recuento");
            return ResponseEntity.notFound().build();
        }

        ResponseRecuentoDTO dto = recuentoMapper.toDTO(recuento);
        log.debug("Recuento encotnrado: {}", dto);
        return ResponseEntity.ok(dto);
        
    }

    // Retorna todos los recuentos disponible en DB
    @Operation(
        summary = "Obtener los recuentos de todos los usuarios",
        description = "Retorna una lista con todos los recuentos de los usuarios registrados en la plataforma"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode ="200", 
            description = "Lista de recuentos encontrada",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseRecuentoDTO.class))))
    })
    @GetMapping("recuentos")
    public ResponseEntity<List<ResponseRecuentoDTO>> getRecuentos(){
        log.info("Lista de recuentos en DB solicitada");
        List<Recuento> recuentos = rService.getRecuentos();

        if(recuentos.isEmpty()){
            log.info("No se encontraron recuentos en DB");
            return ResponseEntity.notFound().build();
        }

        List<ResponseRecuentoDTO> dtoList = recuentoMapper.toDTOList(recuentos);
        log.debug("Lista de recuentos encontrada: {}", dtoList);
        return ResponseEntity.ok(dtoList);
    }



    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------
    
    // Sincroniza los logros del usuario con los tipos de logro disponibles

    @Operation(
        summary = "Sincroniza los logros del usuario con los tipos de logro disponibles",
        description = "Actualiza la lista de logros disponible para un usuario, considerando nuevos logros que fueron creados luego que el usuario se registrara"
    )
    @ApiResponses(
        @ApiResponse(
            responseCode="200",
            description="Lista de logros actualizada correctamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseLogroDTO.class)))
        )
    )
    @PostMapping("/sincronizar/{idUsuario}")
    public ResponseEntity<List<ResponseLogroDTO>> postSincronizarLogrosUsuario(
        @Parameter(description = "ID del usuario")
        @PathVariable Long idUsuario){

        List<ResponseLogroDTO> response = logroMapper.toDTOs(lService.postSincronizarLogrosUsuario(idUsuario));
        return ResponseEntity.ok(response);
    }

    // --------------------------------------------------------
    // ------------------ Sección RECUENTO --------------------
    // --------------------------------------------------------

    // Incrementa en 1 el contador de ejercicios creados para un usuario
    @Operation(
        summary = "Incrementa en 1 el contador de ejercicio creado",
        description = "Aumenta en 1 el contador de ejercicio creado para un usuario identificado por su ID en la ruta"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Contador de ejercicio creado aumentado en 1",
            content = @Content(schema = @Schema(implementation = ResponseRecuentoDTO.class))
        )
    })
    @PostMapping("/recuento/ejercicio-creado/{idUsuario}")
    public ResponseEntity<ResponseRecuentoDTO> postIncrementarEjercicioCreado(@PathVariable Long idUsuario) {
        log.info("Incrementando ejercicios creados para el usuario {}", idUsuario);
        Recuento recuento = rService.incrementarEjerciciosCreados(idUsuario);
        ResponseRecuentoDTO response = new ResponseRecuentoDTO(idUsuario, rService.toMap(recuento));
        return ResponseEntity.ok(response);
    }

    // Incrementa el contador de ejercicios compartidos para un usuario
    @Operation(
        summary = "Incrementa el contador de ejercicios compartidos",
        description = "Incrementa en 1 el contador de ejercicios compartidos para un usuario identificado por su ID en la ruta"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Contador de ejercicios compartidos aumentado en 1",
            content = @Content(schema = @Schema(implementation = ResponseRecuentoDTO.class)))
    })
    @PostMapping("/recuento/ejercicio-compartido/{idUsuario}")
    public ResponseEntity<ResponseRecuentoDTO> postIncrementarEjercicioCompartido(
            @PathVariable Long idUsuario, @RequestBody int cantidad) {
        log.info("Incrementando ejercicios compartidos para el usuario {} en {}", idUsuario, cantidad);
        Recuento recuento = rService.incrementarEjerciciosCompartidos(idUsuario, cantidad);
        ResponseRecuentoDTO response = new ResponseRecuentoDTO(idUsuario, rService.toMap(recuento));
        return ResponseEntity.ok(response);
    }

    // Incrementa el contador de comunidades para un usuario
    @Operation(
        summary = "Incrementa el contador de comunidad",
        description = "Incrementa el contador de comunidad para un usuario identificado por su ID en la ruta"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode="200",
            description = "Contador de comunidad aumentado en 1",
            content = @Content(schema = @Schema(implementation = ResponseRecuentoDTO.class))
        )
    })
    @PostMapping("/recuento/comunidad/{idUsuario}")
    public ResponseEntity<ResponseRecuentoDTO> postIncrementarComunidad(
            @PathVariable Long idUsuario, @RequestBody int cantidad) {
        log.info("Incrementando comunidades para el usuario {} en {}", idUsuario, cantidad);
        Recuento recuento = rService.incrementarComunidad(idUsuario, cantidad);
        ResponseRecuentoDTO response = new ResponseRecuentoDTO(idUsuario, rService.toMap(recuento));
        return ResponseEntity.ok(response);
    }

    
    // Incrementa el contador de items creados para un usuario
    @Operation(
        summary = "Incrementar contador de items creados", 
        description = "Incrementa el contador de items creados para un usuario identificado por su ID en la ruta"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Contador de items aumentado en 1",
            content = @Content(schema = @Schema(implementation = ResponseRecuentoDTO.class))
        )
    })
    @PostMapping("/recuento/item-creado/{idUsuario}")
    public ResponseEntity<ResponseRecuentoDTO> postIncrementarItemCreado(
            @PathVariable Long idUsuario, @RequestBody int cantidad) {
        log.info("Incrementando items creados para el usuario {} en {}", idUsuario, cantidad);
        Recuento recuento = rService.incrementarItemsCreados(idUsuario, cantidad);
        ResponseRecuentoDTO response = new ResponseRecuentoDTO(idUsuario, rService.toMap(recuento));
        return ResponseEntity.ok(response);
    }

    // Incrementa el contador de materiales creados para un usuario
    @PostMapping("/recuento/material/{idUsuario}")
    public ResponseEntity<ResponseRecuentoDTO> postIncrementarMaterialCreado(
            @PathVariable Long idUsuario) {
        log.info("Incrementando materiales creados para el usuario {}", idUsuario);
        Recuento recuento = rService.incrementarMaterialCreado(idUsuario);
        ResponseRecuentoDTO response = new ResponseRecuentoDTO(idUsuario, rService.toMap(recuento));
        return ResponseEntity.ok(response);
    }

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
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    // Actualiza un logro a completado
    @Operation(
        summary = "Actualiza un logro a completado",
        description = "Actualiza un logro a completado y agrega la fecha de completación"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Logro actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseLogroDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Logro no encontrado o datos inválidos",
            content = @Content)
    })
    @PutMapping
    public ResponseEntity<ResponseLogroDTO> putLogroCompletado(@Valid @RequestBody RequestLogroDTO request){
        Long idUsuario = request.getIdUsuario();
        String nombreTipoUsuario = request.getNombreTipoLogro();

        Logro logroActualizado = lService.putLogroCompletado(idUsuario, nombreTipoUsuario);

        if(logroActualizado != null){
            ResponseLogroDTO dto = logroMapper.toDTO(logroActualizado);
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.badRequest().build();

    }
}
