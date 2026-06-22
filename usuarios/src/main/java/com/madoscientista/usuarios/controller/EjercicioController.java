package com.madoscientista.usuarios.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.usuarios.dto.ejercicioDTO.RequestEjercicioDTO;
import com.madoscientista.usuarios.dto.ejercicioDTO.ResponseEjercicioDTO;
import com.madoscientista.usuarios.mapper.EjercicioMapper;
import com.madoscientista.usuarios.model.Ejercicio;
import com.madoscientista.usuarios.service.EjercicioService;

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
@RequiredArgsConstructor
@Tag(name = "Ejercicios", description = "API de ejercicios de usuario")
@RestController
@RequestMapping("api/v1/ejercicios")
public class EjercicioController {

    
    private final EjercicioService service;

    private final EjercicioMapper ejercicioMapper;

    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Generar un nuevo ejercicio para un usuario",
        description = "Crea un ejercicio asociado a un usuario según los parámetros proporcionados")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Ejercicio creado exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseEjercicioDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "No se pudo crear el ejercicio. Verifique los datos enviados.",
            content = @Content)
    })
    @PostMapping("usuario/{id}")
    public ResponseEntity<ResponseEjercicioDTO> postGenerarEjercicio(
            @Valid @RequestBody RequestEjercicioDTO request,
            @Parameter(description = "ID del usuario que crea el ejercicio", example = "1")
            @PathVariable Long id){
        log.info("Solicitud de creación de un ejercicio");
        Ejercicio ejercicio = service.postEjercicio(request, id);
        if(ejercicio != null){
            log.info("Ejercicio creado con éxito");
            return ResponseEntity.status(HttpStatus.CREATED).body(ejercicioMapper.toDTO(ejercicio));
        }
        log.info("No se pudo crear el ejercicio");
        return ResponseEntity.badRequest().build();
    }

    @Operation(
        summary = "Listar ejercicios de un conjunto de usuarios",
        description = "Retorna los ejercicios asociados a una lista de IDs de usuarios")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de ejercicios obtenida exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseEjercicioDTO.class))))
    })
    @PostMapping("usuarios")
    public ResponseEntity<List<ResponseEjercicioDTO>> listarEjerciciosDeUsuarios(@Valid @RequestBody Set<Long> idEjercicio){
        List<Ejercicio> listaEjercicios = service.listarEjerciciosDeUSuarios(idEjercicio);
        List<ResponseEjercicioDTO> response = ejercicioMapper.toDTOList(listaEjercicios);
        return ResponseEntity.ok(response);
    }

    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Compartir un ejercicio con otros usuarios",
        description = "Comparte un ejercicio con una lista de usuarios indicando el creador y el ID del ejercicio")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Ejercicio compartido exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Long.class)))),
        @ApiResponse(
            responseCode = "400",
            description = "No se pudo compartir el ejercicio",
            content = @Content)
    })
    @PutMapping("/compartir/{idCreador}/{idEjercicio}")
    public ResponseEntity<List<Long>> putCompartirEjercicio(
            @Parameter(description = "ID del usuario creador", example = "1")
            @PathVariable Long idCreador,
            @Parameter(description = "ID del ejercicio a compartir", example = "15")
            @PathVariable Long idEjercicio,
            @Valid @RequestBody List<Long> idsUsuarios){
        log.info("Solicitud para compartir el ejercicio id: " + idEjercicio);
        Ejercicio compartido = service.compartirEjercicio(idCreador, idEjercicio, idsUsuarios);
        if(compartido != null){
            log.info("Ejercicio compartido");
            return ResponseEntity.ok(idsUsuarios);
        }
        log.info("No se pudo compartir el ejercicio");
        return ResponseEntity.badRequest().build();
    }

    @Operation(
        summary = "Dejar de compartir un ejercicio",
        description = "Revoca el acceso a un ejercicio para una lista de usuarios")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Acceso revocado exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Long.class)))),
        @ApiResponse(
            responseCode = "400",
            description = "No se pudo revocar el acceso al ejercicio",
            content = @Content)
    })
    @PutMapping("/dejar-compartir/{idCreador}/{idEjercicio}")
    public ResponseEntity<List<Long>> putDejarCompartirEjercicio(
            @Parameter(description = "ID del usuario creador", example = "1")
            @PathVariable Long idCreador,
            @Parameter(description = "ID del ejercicio", example = "15")
            @PathVariable Long idEjercicio,
            @Valid @RequestBody List<Long> idsUsuarios){
        log.info("Solicitud para dejar de compartir el ejercicio id: " + idEjercicio);
        Ejercicio resultado = service.dejarDeCompartirEjercicio(idEjercicio, idCreador, idsUsuarios);
        if(resultado != null){
            log.info("Operación exitosa");
            return ResponseEntity.ok(idsUsuarios);
        }
        log.info("Operación fallida");
        return ResponseEntity.badRequest().build();
    }

    // --------------------------------------------------------
    // ------------------ Sección DELETE ----------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Eliminar un ejercicio",
        description = "Elimina un ejercicio de un usuario según los IDs proporcionados")
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Ejercicio eliminado exitosamente",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontró el ejercicio o el usuario indicado",
            content = @Content)
    })
    @DeleteMapping("usuario/{idUsuario}/{idEjercicio}")
    public ResponseEntity<ResponseEjercicioDTO> deleteEjercicio(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Long idUsuario,
            @Parameter(description = "ID del ejercicio a eliminar", example = "15")
            @PathVariable Long idEjercicio){
        log.info("Solicitud de eliminación del ejercicio id: " + idEjercicio);
        boolean eliminado = service.deleteEjercicio(idUsuario, idEjercicio);
        if(eliminado){
            log.info("Ejercicio eliminado con éxito");
            return ResponseEntity.noContent().build();
        }
        log.info("No se pudo eliminar el ejercicio");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Obtener ejercicio por ID",
        description = "Retorna la información de un ejercicio según su ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Ejercicio encontrado exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseEjercicioDTO.class))),
        @ApiResponse(
            responseCode = "204",
            description = "No se encontró ejercicio con el ID indicado",
            content = @Content)
    })
    @GetMapping("{idEjercicio}")
    public ResponseEntity<ResponseEjercicioDTO> getEjercicioById(
            @Parameter(description = "ID del ejercicio", example = "15")
            @PathVariable Long idEjercicio){
        log.info("Solicitud de ejercicio id: " + idEjercicio);
        Ejercicio ejercicio = service.getEjercicioById(idEjercicio);
        if(ejercicio == null){
            log.info("Ejercicio no encontrado");
            return ResponseEntity.noContent().build();
        }
        ResponseEjercicioDTO dto = ejercicioMapper.toDTO(ejercicio);
        log.debug("Ejercicio encontrado: {} ", dto);
        return ResponseEntity.ok(dto);
    }

    @Operation(
        summary = "Obtener todos los ejercicios",
        description = "Retorna una lista de todos los ejercicios disponibles en la plataforma")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de ejercicios obtenida exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseEjercicioDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron ejercicios en la base de datos",
            content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ResponseEjercicioDTO>> getEjercicios(){
        log.info("Solicitud de la lista de ejercicios disponibles en la plataforma");
        List<Ejercicio> ejercicios = service.getEjercicios();
        if(ejercicios.isEmpty()){
            log.info("No se encontraron ejercicios en BD");
            return ResponseEntity.notFound().build();
        }
        log.info("Ejercicios encontrados");
        List<ResponseEjercicioDTO> dtoList = ejercicioMapper.toDTOList(ejercicios);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Obtener ejercicios creados por un usuario",
        description = "Retorna los ejercicios que ha creado y almacenado un usuario según su ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de ejercicios obtenida exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseEjercicioDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron ejercicios para el usuario indicado",
            content = @Content)
    })
    @GetMapping("creados/{id}")
    public ResponseEntity<List<ResponseEjercicioDTO>> getEjerciciosCreadosByUsuario(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Long id){
        log.info("Solicitud de ejercicios creados y almacenados por el usuario id: " + id);
        List<Ejercicio> ejercicios = service.getEjerciciosCreadosByUsuario(id);
        if(ejercicios.isEmpty()){
            log.info("Ejercicios no encontrados");
            return ResponseEntity.notFound().build();
        }
        log.debug("Ejercicios encontrados", ejercicios);
        return ResponseEntity.ok(ejercicioMapper.toDTOList(ejercicios));
    }

    @Operation(
        summary = "Obtener ejercicios compartidos a un usuario",
        description = "Retorna los ejercicios que han sido compartidos con un usuario según su ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de ejercicios obtenida exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseEjercicioDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron ejercicios compartidos para el usuario indicado",
            content = @Content)
    })
    @GetMapping("compartidos/{id}")
    public ResponseEntity<List<ResponseEjercicioDTO>> getEjerciciosCompartidosAUsuario(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Long id){
        log.info("Solicitud ejercicios compartidos por el usuario id: " + id);
        List<Ejercicio> ejercicios = service.getEjerciciosCompartidosAUsuario(id);
        if(ejercicios.isEmpty()){
            log.info("Ejercicios no encontrados");
            return ResponseEntity.notFound().build();
        }
        log.info("Ejercicios encontrados");
        return ResponseEntity.ok(ejercicioMapper.toDTOList(ejercicios));
    }
}
