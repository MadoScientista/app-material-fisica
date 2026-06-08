package com.madoscientista.logos.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
import lombok.extern.slf4j.Slf4j;

@Tag(name="Logos", description = "API Logos")
@Slf4j
@RestController
@RequestMapping("api/v1/logos")
public class LogoController {

    // Inyección de servicios
    @Autowired
    private LogoService logoService;

    // Inyección de mappers
    @Autowired
    private LogoMapper logoMapper;

    // ----------------------------------------------------------------------------------------------------------
    // -------------------------------------- Sección GET -------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------


    // -------------------------------- Obtener todos los logros ------------------------------------------------
    @Operation(
        summary = "Obtener todos los logos",
        description = "Retorna todos los losgos registrados en la base de datos"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Logos recuperados con éxito",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseLogoDTO.class)))),
        @ApiResponse(
            responseCode = "400",
            description = "Solicitud inválida o no se encontraron logos",
            content = @Content)

    })
    @GetMapping
    public ResponseEntity<List<ResponseLogoDTO>> getLogos() {
        log.info("Lista de logos solicitada");
        List<Logo> logos = logoService.getLogos();

        if (logos.isEmpty()) {
            log.info("No se encontraron logos");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<ResponseLogoDTO> dtoList = logoMapper.toDTOList(logos);
        return ResponseEntity.ok(dtoList);
    }

    // -------------------------------- Filtrar logos por ID ------------------------------------------------
    @Operation(
        summary = "Filtrar logo por ID",
        description = "Retorna un logo por su ID especificado en la ruta"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Logo recuperado con éxito",
            content = @Content(schema = @Schema(implementation = ResponseLogoDTO.class))
        )
    })
    @GetMapping("{idLogo}")
    public ResponseEntity<ResponseLogoDTO> getLogoById(@PathVariable Long idLogo) {
        log.info("Logo con id: {} solicitado", idLogo);
        Logo logo = logoService.getLogoById(idLogo);

        if (logo == null) {
            log.info("Logo no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ResponseLogoDTO dto = logoMapper.toDTO(logo);
        return ResponseEntity.ok(dto);
    }

    // -------------------------------- Obtener logos por ID de usuario ------------------------------------------------

    @Operation(
        summary = "Obtener logos por ID de usuario",
        description = "Retorna una lista de logos filtradas por el ID de usuario indicado en la ruta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de logros encontrada",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseLogoDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron logros o usuario no existe",
            content = @Content
        )
    })
    @GetMapping("usuario/{idUsuarioCreador}")
    public ResponseEntity<List<ResponseLogoDTO>> getLogoByIdUsuarioCreador(
            @Parameter(description = "ID del usuario creador")
            @PathVariable Long idUsuarioCreador) {

        log.info("Logos del usuario id: {} solicitados", idUsuarioCreador);
        List<Logo> logos = logoService.getLogoByIdUsuarioCreador(idUsuarioCreador);

        if (logos.isEmpty()) {
            log.info("Logos no encontrados para el usuario");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        log.info("Logos encontrados");
        List<ResponseLogoDTO> dtoList = logoMapper.toDTOList(logos);
        return ResponseEntity.ok(dtoList);
    }


    // -------------------------------- Obtener una lista de logos ------------------------------------------------

    @Operation(
        summary = "Obtener una lista de logos",
        description = "Retorna una lista de logos filtrados por una lista de IDs"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de logos encontrada",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseLogoDTO.class)))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron logos para los IDs proporcionados",
            content = @Content
        )
    })
    @PostMapping("lista-ids")
    public ResponseEntity<List<ResponseLogoDTO>> getLogosByListId(
            @Valid @RequestBody Set<Long> ids) {
        log.info("Logos por lista de ids solicitado");
        List<Logo> logos = logoService.getLogosByListId(ids);

        if (logos.isEmpty()) {
            log.info("Logos no encontrados para los ids proporcionados");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Mapeo a DTOs y retorno la respuesta HTTP
        List<ResponseLogoDTO> dtoList = logoMapper.toDTOList(logos);
        return ResponseEntity.ok(dtoList);
    }


    // -------------------------------- Obtener una lista de logos de varios usuarios ---------------------------------------

    @Operation(
        summary = "Obtener una lista de logos de varios usuarios",
        description = "Obtiene una lista de logos a partir de una lista de IDs de usuarios"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de logos encontrada",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseLogoDTO.class)))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron logos o lista con ID de usuarios inexistentes",
            content = @Content
        )
    })
    @PostMapping("usuarios-ids")
    public ResponseEntity<List<ResponseLogoDTO>> getLogosByListIdUsuarioCreador(
            @Valid @RequestBody Set<Long> ids) {

        log.info("Logos por lista de ids de usuarios solicitado");
        List<Logo> logos = logoService.getLogosByListIdUsuarioCreador(ids);

        if (logos.isEmpty()) {
            log.info("Logos no encontrados para los ids de usuarios proporcionados");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Mapeo a DTOs y retorno la respuesta
        List<ResponseLogoDTO> dtoList = logoMapper.toDTOList(logos);
        return ResponseEntity.ok(dtoList);
    }

    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // -------------------------------- Crea un logo ---------------------------------------

    @Operation(
        summary = "Crea un nuevo logo",
        description = "Crea un nuevo logo"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Logo creado exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseLogoDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Error al crear el logo",
            content = @Content
        )
    })
    @PostMapping
    public ResponseEntity<ResponseLogoDTO> postLogo(
            @Valid @RequestBody RequestLogoDTO request) {

        log.debug("Solicitud de creación de logo: {}", request);
        Logo logo = logoMapper.toEntity(request);
        Logo logoCreado = logoService.postLogo(logo);

        if (logoCreado == null) {
            return ResponseEntity.notFound().build();
        }

        // Mapeo a DTO y retorno la respuesta
        ResponseLogoDTO dto = logoMapper.toDTO(logoCreado);
        log.debug("Logo creado con éxito: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    @PutMapping("{idLogo}")
    public ResponseEntity<ResponseLogoDTO> putLogo(
            @PathVariable Long idLogo,
            @Valid @RequestBody RequestLogoDTO request) {
        log.debug("Solicitud de actualización de logo: {}", request);
        Logo logo = logoMapper.toEntity(request);
        Logo logoActualizado = logoService.putLogo(idLogo, logo);

        if (logoActualizado == null) {
            log.info("Logo no encontrado");
            return ResponseEntity.notFound().build();
        }

        // Mapeo a DTO y retorno la respuesta
        ResponseLogoDTO dto = logoMapper.toDTO(logoActualizado);
        log.debug("Logo actualizado con éxito: {}", dto);
        return ResponseEntity.ok(dto);
    }

    // --------------------------------------------------------
    // ------------------ Sección DELETE ----------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Eliminar logo por id",
        description = "Eliminar un logo por su id identificado en la ruta"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Logo eliminado exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseLogoDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "No se ha podido eliminar el logo o el logo no existe",
            content = @Content
        )
    })
    @DeleteMapping("{idLogo}")
    public ResponseEntity<ResponseLogoDTO> deleteLogoById(
        @Parameter(description = "Id del logo a eliminar")
        @PathVariable Long idLogo) {

        log.info("Solicitud de eliminación de logo id: {}", idLogo);
        Logo logo = logoService.deleteLogoById(idLogo);

        if (logo == null) {
            log.info("Logo no encontrado");
            return ResponseEntity.notFound().build();
        }

        // Mapeo a DTO y retorno la respuesta
        log.info("Logo eliminado correctamente");
        ResponseLogoDTO dto = logoMapper.toDTO(logo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(dto);
    }

    // ------------------ Elimina logos por lista de ids -------------------------

    @Operation(
        summary = "Eliminar varios logos por lista de ids",
        description = "Elimina los logos cuyos ids se encuentran en la lista proporcionada"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Eliminación exitosa de logos",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseLogoDTO.class)))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Ocurrió un error al eliminar los logos",
            content = @Content
        )
    })
    @PostMapping("eliminar-lista")
    public ResponseEntity<List<ResponseLogoDTO>> deleteLogoByListId(
            @Valid @RequestBody Set<Long> ids) {

        log.info("Solicitud de eliminación de logos por lista de ids");
        List<Logo> logos = logoService.deleteLogoByListId(ids);

        if (logos.isEmpty()) {
            log.info("Logos no encontrados");
            return ResponseEntity.notFound().build();
        }

        // Mapeo a DTO y retorno la respuesta
        log.info("Logos eliminados exitosamente");
        List<ResponseLogoDTO> dtoList = logoMapper.toDTOList(logos);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(dtoList);
    }
}
