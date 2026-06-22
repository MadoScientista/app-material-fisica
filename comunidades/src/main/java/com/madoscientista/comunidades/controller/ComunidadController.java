package com.madoscientista.comunidades.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.comunidades.client.EjercicioClient;
import com.madoscientista.comunidades.dto.comunidadDTO.RequestComunidadDTO;
import com.madoscientista.comunidades.dto.comunidadDTO.ResponseComunidadDTO;
import com.madoscientista.comunidades.dto.ejercicioDTO.ResponseEjercicioDTO;
import com.madoscientista.comunidades.mapper.ComunidadMapper;
import com.madoscientista.comunidades.model.Comunidad;
import com.madoscientista.comunidades.service.ComunidadService;

import feign.FeignException;
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

@Tag(name = "Comunidades", description="API de comunidades")
@RestController
@RequestMapping("api/v1/comunidades")
@RequiredArgsConstructor
public class ComunidadController {

    // Inyección cliente service
    private final ComunidadService cService;

    // Inyección cliente ejercicios
    private final EjercicioClient eClient;

    private final ComunidadMapper cMapper;

    // -----------------------------------------------------------------------------------------------------
    // ----------------------------------------- Sección GET -----------------------------------------------
    // -----------------------------------------------------------------------------------------------------


    // -------------------------------------- Obtener comunidades ------------------------------------------

    @Operation(
        summary = "Obtener comunidades",
        description = "Retorna una lista de las comunidades disponibles en la base de datos")
    @ApiResponses({
        @ApiResponse(
            responseCode="200", 
            description="Lista de comunidades obtenidas exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseComunidadDTO.class)))),
        @ApiResponse(
            responseCode="404", 
            description="No se han encontrado comunidades o no se han logrado recuperar comunidades desde DB",
            content = @Content)
    })

    @GetMapping
    public ResponseEntity<List<ResponseComunidadDTO>> getComunidades(){
        List<Comunidad> comunidades = cService.getComunidades();

        if(comunidades.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        List<ResponseComunidadDTO> comunidadesDTO = cMapper.toListDTO(comunidades);
        return ResponseEntity.ok(comunidadesDTO);
        
    }


    // ---------------------------------- Filtrar comunidades por ID ------------------------------------------

    @Operation(
        summary = "Filtrar comunidades por ID",
        description = "Retorna una comunidad indicando el ID de la misma en el path")
    @ApiResponses({
        @ApiResponse(
            responseCode="200", 
            description="Retorna una comunidad filtrada por su ID",
            content = @Content(schema = @Schema(implementation = ResponseComunidadDTO.class))),
        @ApiResponse(
            responseCode="404", 
            description="No se encontró comunidad con el ID indicado",
            content = @Content)
    })

    @GetMapping("{idComunidad}")
    public ResponseEntity<ResponseComunidadDTO> getComunidadById(
        @Parameter(description="ID de la comunidad a buscar", example="4")
        @PathVariable Long idComunidad){
        
        Comunidad comunidad = cService.getComunidadById(idComunidad);

        if(comunidad == null){
            return ResponseEntity.notFound().build();
        }
        ResponseComunidadDTO comunidadDTO = cMapper.toDTO(comunidad);
        return ResponseEntity.ok(comunidadDTO);
    }

    //------------------------- Obtener los miembros de una comunidad filtrada por ID --------------------------

    @Operation(
        summary = "Obtener los miembros de una comunidad",
        description = "Retorna una lista de los ID de los miembros de una comunidad cuyo ID es indicada en el path")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Retorna los miembros de la comunidad filtrada por ID",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Long.class)))),
        @ApiResponse(
            responseCode = "404", 
            description="La comunidad no tiene miembros o la comunidad no existe",
            content = @Content)
    })

    @GetMapping("{idComunidad}/usuarios")
    public ResponseEntity<Set<Long>> getMiembrosDeComunidad(
        @Parameter(description="ID de la comunidad a filtrar", example="8")
        @PathVariable Long idComunidad){

        Set<Long> idMiembros = cService.getMiembrosDeComunidad(idComunidad);

        if(idMiembros == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(idMiembros);

    }

    
    // -----------------------------------------------------------------------------------------------------
    // ----------------------------------------- Sección POST ----------------------------------------------
    // -----------------------------------------------------------------------------------------------------


    //------------------------------------- Crear una nueva comunidad --------------------------------------

    @Operation(
        summary = "Crear una nueva comunidad",
        description = "Crea una comunidad y retorna información que incluye ID y fecha de creación")
    @ApiResponses({
        @ApiResponse(
            responseCode="201", 
            description = "Retorna la comunidad creada con éxito",
            content = @Content(schema = @Schema(implementation = ResponseComunidadDTO.class)))
    })

    @PostMapping
    public ResponseEntity<ResponseComunidadDTO> postComunidad(@Valid @RequestBody RequestComunidadDTO request){
        Comunidad comunidad = cMapper.toEntity(request);

        ResponseComunidadDTO response = cMapper.toDTO(cService.postComunidad(comunidad));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // -----------------------------------------------------------------------------------------------------
    // ----------------------------------------- Sección PUT -----------------------------------------------
    // -----------------------------------------------------------------------------------------------------


    // ------------------------------- Agregar miembros a una comunidad ------------------------------------

    @Operation(
        summary = "Agregar miembros a una comunidad", 
        description = "Permite agregar miembros a una comunidad indicando en el path el ID de la comunidad")
    @ApiResponses({
        @ApiResponse(
            responseCode="200", 
            description="Se ha actualizado exitosamente la comunidad", 
            content = @Content(schema = @Schema(implementation = ResponseComunidadDTO.class))),
        @ApiResponse(
            responseCode = "40X",
            description = "Error en el cuerpo de la solicitud o no se encontró la comunidad",
            content = @Content
        )
    })

    @PutMapping("/agregar-miembros/{idComunidad}")
    public ResponseEntity<ResponseComunidadDTO> agregarMiembrosAComunidad(
        @Parameter(description = "ID de la comunidad a la que se agregarán los miembros", example = "4")
        @PathVariable Long idComunidad, 
        @RequestBody Set<Long> idMiembros){
        
        Comunidad comunidad = cService.agregarMiembrosAComunidad(idComunidad, idMiembros);

        if(comunidad != null){
            ResponseComunidadDTO response = cMapper.toDTO(comunidad);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
    }


    // ---------------------------------- Eliminar miembros de una comunidad ---------------------------------------

    @Operation(
        summary = "Eliminar miembros de una comunidad",
        description = "Permite agregar miembros a una comunidad indicando en el path el ID de la comunidad, y en el cuerpo de la petición una lista de los ID de los miembros")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Se han eliminado los miembros de la comunidad exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseComunidadDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Error en el cuerpo de la solicitud o no se encontró la comunidad",
            content = @Content
        )
    })

    @PutMapping("/eliminar-miembros/{idComunidad}")
    public ResponseEntity<ResponseComunidadDTO> eliminarMiembrosDeComunidad(@PathVariable Long idComunidad, @RequestBody Set<Long> idMiembros){
        Comunidad comunidad = cService.eliminarMiembrosDeComunidad(idComunidad, idMiembros);

        if(comunidad != null){
            ResponseComunidadDTO response = cMapper.toDTO(comunidad);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
    }

    // -----------------------------------------------------------------------------------------------------
    // ----------------------------------------- Sección Ejercicios ----------------------------------------
    // -----------------------------------------------------------------------------------------------------

    // ----------------------------- Obtener lista de ejercicios de la comunidad -------------------------

    @Operation(
        summary = "Obtener lista de ejercicios de la comunidad",
        description = "Lista los ejercicios almacenados por los miembros de la comunidad filtrada por ID")
    @ApiResponses({
        @ApiResponse(
            responseCode="200", 
            description="Lista de los ejercicios almacenados por los miembros de la comunidad",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseEjercicioDTO.class)))),
        @ApiResponse(
            responseCode="404", 
            description="No se ecnontraron ejercicios para la comunidad",
            content = @Content)
    })

    @GetMapping("ejercicios/{idComunidad}")
    public ResponseEntity<List<ResponseEjercicioDTO>> listarEjerciciosDeComunidad(@PathVariable Long idComunidad){
        Set<Long> idUsuarios = cService.getMiembrosDeComunidad(idComunidad);

        if(idUsuarios == null){
            return ResponseEntity.notFound().build();
        }

        try{
            List<ResponseEjercicioDTO> listaEjercicios = eClient.listarEjerciciosDeUsuarios(idUsuarios).getBody();
            return ResponseEntity.ok(listaEjercicios);
        }catch(FeignException e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
    }
}
