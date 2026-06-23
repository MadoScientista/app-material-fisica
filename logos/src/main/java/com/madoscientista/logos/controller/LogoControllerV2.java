package com.madoscientista.logos.controller;

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

import com.madoscientista.logos.assembler.LogoAssembler;
import com.madoscientista.logos.dto.logoDTO.RequestLogoDTO;
import com.madoscientista.logos.dto.logoDTO.ResponseLogoDTO;
import com.madoscientista.logos.mapper.LogoMapper;
import com.madoscientista.logos.model.Logo;
import com.madoscientista.logos.service.LogoService;

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

@Tag(name = "Logos V2", description = "API de logos")
@Slf4j
@RestController
@RequestMapping("api/v2/logos")
@RequiredArgsConstructor
public class LogoControllerV2 {

    private final LogoService logoService;
    private final LogoAssembler assembler;
    private final LogoMapper logoMapper;

    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Obtener todos los logos",
        description = "Retorna todos los logos registrados en la base de datos")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Logos recuperados con éxito",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseLogoDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron logos",
            content = @Content)
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ResponseLogoDTO>>> getLogos() {
        log.info("Solicitud de lista de logos disponibles en la plataforma");
        List<Logo> logos = logoService.getLogos();

        if (logos.isEmpty()) {
            log.info("No se encontraron logos");
            return ResponseEntity.notFound().build();
        }

        log.info("Logos encontrados");
        CollectionModel<EntityModel<ResponseLogoDTO>> dtoList = assembler.toCollectionModel(logos);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Obtener logo por ID",
        description = "Retorna un logo por su ID especificado en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Logo recuperado con éxito",
            content = @Content(schema = @Schema(implementation = ResponseLogoDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontró logo con el ID indicado",
            content = @Content)
    })
    @GetMapping("{idLogo}")
    public ResponseEntity<EntityModel<ResponseLogoDTO>> getLogoById(
            @Parameter(description = "ID del logo", example = "26")
            @PathVariable Long idLogo) {
        log.info("Solicitud de logo id: {}", idLogo);
        Logo logo = logoService.getLogoById(idLogo);

        if (logo == null) {
            log.info("Logo no encontrado");
            return ResponseEntity.notFound().build();
        }

        EntityModel<ResponseLogoDTO> dto = assembler.toModel(logo);
        log.debug("Logo encontrado: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Operation(
        summary = "Obtener logos por ID de usuario",
        description = "Retorna una lista de logos filtrados por el ID de usuario indicado en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de logos encontrada",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseLogoDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron logos para el usuario indicado",
            content = @Content)
    })
    @GetMapping("usuario/{idUsuarioCreador}")
    public ResponseEntity<CollectionModel<EntityModel<ResponseLogoDTO>>> getLogoByIdUsuarioCreador(
            @Parameter(description = "ID del usuario creador", example = "17")
            @PathVariable Long idUsuarioCreador) {

        log.info("Solicitud de logos del usuario id: {}", idUsuarioCreador);
        List<Logo> logos = logoService.getLogoByIdUsuarioCreador(idUsuarioCreador);

        if (logos.isEmpty()) {
            log.info("Logos no encontrados para el usuario");
            return ResponseEntity.notFound().build();
        }

        log.info("Logos encontrados");
        CollectionModel<EntityModel<ResponseLogoDTO>> dtoList = assembler.toCollectionModel(logos);
        return ResponseEntity.ok(dtoList);
    }

    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Obtener una lista de logos",
        description = "Retorna una lista de logos filtrados por una lista de IDs")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de logos encontrada",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseLogoDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron logos para los IDs proporcionados",
            content = @Content)
    })
    @PostMapping("lista-ids")
    public ResponseEntity<CollectionModel<EntityModel<ResponseLogoDTO>>> getLogosByListId(
            @Valid @RequestBody Set<Long> ids) {
        log.info("Solicitud de logos por lista de ids");
        List<Logo> logos = logoService.getLogosByListId(ids);

        if (logos.isEmpty()) {
            log.info("Logos no encontrados para los ids proporcionados");
            return ResponseEntity.notFound().build();
        }

        CollectionModel<EntityModel<ResponseLogoDTO>> dtoList = assembler.toCollectionModel(logos);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Obtener una lista de logos de varios usuarios",
        description = "Obtiene una lista de logos a partir de una lista de IDs de usuarios")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de logos encontrada",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseLogoDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron logos para los IDs de usuarios proporcionados",
            content = @Content)
    })
    @PostMapping("usuarios-ids")
    public ResponseEntity<CollectionModel<EntityModel<ResponseLogoDTO>>> getLogosByListIdUsuarioCreador(
            @Valid @RequestBody Set<Long> ids) {

        log.info("Solicitud de logos por lista de ids de usuarios");
        List<Logo> logos = logoService.getLogosByListIdUsuarioCreador(ids);

        if (logos.isEmpty()) {
            log.info("Logos no encontrados para los ids de usuarios proporcionados");
            return ResponseEntity.notFound().build();
        }

        CollectionModel<EntityModel<ResponseLogoDTO>> dtoList = assembler.toCollectionModel(logos);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Crear un nuevo logo",
        description = "Crea un nuevo logo")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Logo creado exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseLogoDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Error al crear el logo",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<ResponseLogoDTO>> postLogo(
            @Valid @RequestBody RequestLogoDTO request) {

        log.debug("Solicitud de creación de logo: {}", request);
        Logo logo = logoMapper.toEntity(request);
        Logo logoCreado = logoService.postLogo(logo);

        if (logoCreado == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<ResponseLogoDTO> dto = assembler.toModel(logoCreado);
        log.debug("Logo creado con éxito: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Actualizar un logo",
        description = "Actualiza la información de un logo según su ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Logo actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseLogoDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontró logo con el ID indicado",
            content = @Content)
    })
    @PutMapping("{idLogo}")
    public ResponseEntity<EntityModel<ResponseLogoDTO>> putLogo(
            @Parameter(description = "ID del logo a actualizar", example = "26")
            @PathVariable Long idLogo,
            @Valid @RequestBody RequestLogoDTO request) {

        log.debug("Solicitud de actualización de logo: {}", request);
        Logo logo = logoMapper.toEntity(request);
        Logo logoActualizado = logoService.putLogo(idLogo, logo);

        if (logoActualizado == null) {
            log.info("Logo no encontrado");
            return ResponseEntity.notFound().build();
        }

        EntityModel<ResponseLogoDTO> dto = assembler.toModel(logoActualizado);
        log.debug("Logo actualizado con éxito: {}", dto);
        return ResponseEntity.ok(dto);
    }

    // --------------------------------------------------------
    // ------------------ Sección DELETE ----------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Eliminar logo por ID",
        description = "Elimina un logo por su ID especificado en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Logo eliminado exitosamente",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontró logo con el ID indicado",
            content = @Content)
    })
    @DeleteMapping("{idLogo}")
    public ResponseEntity<ResponseLogoDTO> deleteLogoById(
            @Parameter(description = "ID del logo a eliminar", example = "26")
            @PathVariable Long idLogo) {

        log.info("Solicitud de eliminación de logo id: {}", idLogo);
        Logo logo = logoService.deleteLogoById(idLogo);

        if (logo == null) {
            log.info("Logo no encontrado");
            return ResponseEntity.notFound().build();
        }

        log.info("Logo eliminado correctamente");
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Eliminar varios logos por lista de IDs",
        description = "Elimina los logos cuyos IDs se encuentran en la lista proporcionada")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Logos eliminados exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseLogoDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron logos para los IDs proporcionados",
            content = @Content)
    })
    @PostMapping("eliminar-lista")
    public ResponseEntity<CollectionModel<EntityModel<ResponseLogoDTO>>> deleteLogoByListId(
            @Valid @RequestBody Set<Long> ids) {

        log.info("Solicitud de eliminación de logos por lista de ids");
        List<Logo> logos = logoService.deleteLogoByListId(ids);

        if (logos.isEmpty()) {
            log.info("Logos no encontrados");
            return ResponseEntity.notFound().build();
        }

        log.info("Logos eliminados exitosamente");
        CollectionModel<EntityModel<ResponseLogoDTO>> dtoList = assembler.toCollectionModel(logos);
        return ResponseEntity.ok(dtoList);
    }
}
