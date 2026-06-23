package com.madoscientista.usuarios.controller;


import java.util.List;
import java.util.Set;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

import com.madoscientista.usuarios.assembler.EjercicioAssembler;
import com.madoscientista.usuarios.dto.ejercicioDTO.RequestEjercicioCompartidoDTO;
import com.madoscientista.usuarios.dto.ejercicioDTO.RequestEjercicioDTO;
import com.madoscientista.usuarios.dto.ejercicioDTO.ResponseEjercicioDTO;
import com.madoscientista.usuarios.model.Ejercicio;
import com.madoscientista.usuarios.service.EjercicioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "Ejercicios V2", description = "API de ejercicios de usuario")
@RestController
@RequestMapping("api/v2/ejercicios")
public class EjercicioControllerV2 {

    
    private final EjercicioService service;
    private final EjercicioAssembler assembler;

    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Generar un nuevo ejercicio para un usuario",
        description = "Crea un ejercicio asociado a un usuario según los parámetros proporcionados y retorna un modelo con links HATEOAS")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Ejercicio creado exitosamente. La respuesta se envuelve en EntityModel con enlaces HATEOAS",
            content = @Content(schema = @Schema(implementation = ResponseEjercicioDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "No se pudo crear el ejercicio. Verifique los datos enviados.",
            content = @Content)
    })
    @PostMapping("usuario/{id}")
    public ResponseEntity<EntityModel<ResponseEjercicioDTO>> postGenerarEjercicio(
            @Valid @RequestBody RequestEjercicioDTO request,
            @Parameter(description = "ID del usuario que crea el ejercicio", example = "1")
            @PathVariable Long id){

        log.info("Solicitud de creación de un ejercicio");
        Ejercicio ejercicio = service.postEjercicio(request, id);
        if(ejercicio != null){
            log.info("Ejercicio creado con éxito");
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(ejercicio));
        }
        log.info("No se pudo crear el ejercicio");
        return ResponseEntity.badRequest().build();
    }

    // Obtener los ejercicios creados y almacenados por un conjunto de usuarios
    @Operation(
        summary = "Listar ejercicios de un conjunto de usuarios",
        description = "Retorna los ejercicios asociados a una lista de IDs de usuarios")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de ejercicios obtenida exitosamente. La respuesta se envuelve en CollectionModel con enlaces HATEOAS",
            content = @Content(
                array = @ArraySchema(schema = @Schema(implementation = ResponseEjercicioDTO.class)))),
        @ApiResponse(
            responseCode = "204",
            description = "Usuarios no encontrados",
            content = @Content
        )
    })
    @PostMapping("usuarios")
    public ResponseEntity<CollectionModel<EntityModel<ResponseEjercicioDTO>>> listarEjerciciosDeUsuarios(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "IDs usuarios a buscar",
                required = true,
                content = @Content(examples = @ExampleObject(value = "[1, 2, 3]")))
        @Valid 
        @RequestBody Set<Long> idsUsuarios){

        List<Ejercicio> listaEjercicios = service.listarEjerciciosDeUSuarios(idsUsuarios);

        if(listaEjercicios.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        CollectionModel<EntityModel<ResponseEjercicioDTO>> dtoList = assembler.toCollectionModel(listaEjercicios);
        return ResponseEntity.ok(dtoList);
    }

    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Actualizar lista de compartidos",
        description = "Actualiza la lista de IDs de usuarios con quienes se está compartiendo un ejercicio en particular")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de IDs de usuarios compartidos actualizada exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseEjercicioDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "No se pudo actualizar la lista de IDs de usuarios compartidos",
            content = @Content)
    })
    @PutMapping("/compartir")
    public ResponseEntity<EntityModel<ResponseEjercicioDTO>> putCompartirEjercicio(
            @Valid @RequestBody 
            RequestEjercicioCompartidoDTO request){

        log.info("Solicitud para compartir el ejercicio id: " + request.getIdEjercicio());
        
        Ejercicio compartido = service.compartirEjercicio(
            request.getIdCreador(), request.getIdEjercicio(), request.getIdsUsuariosCompartido());
        
        if(compartido != null){
            log.info("Ejercicio compartido");

            EntityModel<ResponseEjercicioDTO> dto = assembler.toModel(compartido);

            return ResponseEntity.ok(dto);
        }
        log.info("No se pudo compartir el ejercicio");
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
            @Parameter(description = "ID del ejercicio a eliminar", example = "3")
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

    // Retorna la información de un ejercicio según su ID
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
    public ResponseEntity<EntityModel<ResponseEjercicioDTO>> getEjercicioById(
            @Parameter(description = "ID del ejercicio", example = "3")
            @PathVariable Long idEjercicio){

        log.info("Solicitud de ejercicio id: " + idEjercicio);
        Ejercicio ejercicio = service.getEjercicioById(idEjercicio);

        if(ejercicio == null){
            log.info("Ejercicio no encontrado");
            return ResponseEntity.noContent().build();
        }

        EntityModel<ResponseEjercicioDTO> dto = assembler.toModel(ejercicio);
        log.debug("Ejercicio encontrado: {} ", dto);

        return ResponseEntity.ok(dto);
    }

    // Obtener todos los ejercicios
    @Operation(
        summary = "Obtener todos los ejercicios",
        description = "Retorna una lista de todos los ejercicios disponibles en la plataforma")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de ejercicios obtenida exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseEjercicioDTO.class)))),
        @ApiResponse(
            responseCode = "204",
            description = "No se encontraron ejercicios en la base de datos",
            content = @Content)
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ResponseEjercicioDTO>>> getEjercicios(){
        log.info("Solicitud de la lista de ejercicios disponibles en la plataforma");
        List<Ejercicio> ejercicios = service.getEjercicios();
        if(ejercicios.isEmpty()){
            log.info("No se encontraron ejercicios en BD");
            return ResponseEntity.noContent().build();
        }
        log.info("Ejercicios encontrados");
        CollectionModel<EntityModel<ResponseEjercicioDTO>> dtoList = assembler.toCollectionModel(ejercicios);
    
        return ResponseEntity.ok(dtoList);
    }


    // Obtener los ejercicios creados y almacenados por un usuario
    @Operation(
        summary = "Obtener ejercicios creados y almacenados por un usuario",
        description = "Retorna los ejercicios que ha creado y almacenado un usuario según su ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de ejercicios obtenida exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseEjercicioDTO.class)))),
        @ApiResponse(
            responseCode = "204",
            description = "No se encontraron ejercicios para el usuario indicado",
            content = @Content)
    })
    @GetMapping("creados/{id}")
    public ResponseEntity<CollectionModel<EntityModel<ResponseEjercicioDTO>>> getEjerciciosCreadosByUsuario(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Long id){
        log.info("Solicitud de ejercicios creados y almacenados por el usuario id: " + id);
        List<Ejercicio> ejercicios = service.getEjerciciosCreadosByUsuario(id);
        if(ejercicios.isEmpty()){
            log.info("Ejercicios no encontrados");
            return ResponseEntity.noContent().build();
        }
        log.debug("Ejercicios encontrados", ejercicios);
        CollectionModel<EntityModel<ResponseEjercicioDTO>> dtoList = assembler.toCollectionModel(ejercicios);

        return ResponseEntity.ok(dtoList);
    }

    // Obtener los ejercicios compartidos por un usuario
    @Operation(
        summary = "Obtener ejercicios compartidos a un usuario",
        description = "Retorna los ejercicios que han sido compartidos con un usuario según su ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de ejercicios obtenida exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseEjercicioDTO.class)))),
        @ApiResponse(
            responseCode = "204",
            description = "No se encontraron ejercicios compartidos para el usuario indicado",
            content = @Content)
    })
    @GetMapping("compartidos/{id}")
    public ResponseEntity<CollectionModel<EntityModel<ResponseEjercicioDTO>>>getEjerciciosCompartidosAUsuario(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Long id){
        log.info("Solicitud ejercicios compartidos por el usuario id: " + id);
        List<Ejercicio> ejercicios = service.getEjerciciosCompartidosAUsuario(id);
        if(ejercicios.isEmpty()){
            log.info("Ejercicios no encontrados");
            return ResponseEntity.noContent().build();
        }
        log.info("Ejercicios encontrados");

        CollectionModel<EntityModel<ResponseEjercicioDTO>> dtoList = assembler.toCollectionModel(ejercicios);
        return ResponseEntity.ok(dtoList);
    }
}
