package com.madoscientista.usuarios.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.usuarios.dto.usuarioDTO.RequestUsuarioDTO;
import com.madoscientista.usuarios.dto.usuarioDTO.ResponseUsuarioDTO;
import com.madoscientista.usuarios.mapper.UsuarioMapper;
import com.madoscientista.usuarios.model.Usuario;
import com.madoscientista.usuarios.service.GeneradorUsuariosService;
import com.madoscientista.usuarios.service.UsuarioService;

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
@Tag(name = "Usuarios", description = "API de usuarios")
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    private final UsuarioMapper usuarioMapper;

    private final GeneradorUsuariosService generadorUsuariosService;

    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Obtener todos los usuarios",
        description = "Retorna una lista de los usuarios disponibles en la plataforma")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de usuarios obtenida exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseUsuarioDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron usuarios en la base de datos",
            content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ResponseUsuarioDTO>> getUsuarios(){
        log.info("Solicitud usuarios disponibles en la plataforma");
        List<Usuario> usuarios = service.getUsuarios();
        if(usuarios.isEmpty()){
            log.info("No se encontraron usuarios");
            return ResponseEntity.notFound().build();
        }
        log.info("Usuarios encontrados");
        List<ResponseUsuarioDTO> dtoList = usuarioMapper.toDTOList(usuarios);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Obtener usuario por ID",
        description = "Retorna la información de un usuario según su ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Usuario encontrado exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseUsuarioDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontró usuario con el ID indicado",
            content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUsuarioDTO> getUsuarioById(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Long id){
        log.info("Solicitud de información del usuario id: " + id);
        Usuario u = service.getUsuarioById(id);
        if(u == null){
            log.info("Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ResponseUsuarioDTO response = usuarioMapper.toDTO(u);
        log.debug("Usuario encontrado: {}", response);
        return ResponseEntity.ok(response);
    }

    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Crear un nuevo usuario",
        description = "Crea un nuevo usuario en la plataforma y retorna sus datos")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Usuario creado exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseUsuarioDTO.class)))
    })
    @PostMapping
    public ResponseEntity<ResponseUsuarioDTO> postUsuario(@Valid @RequestBody RequestUsuarioDTO dto){
        log.info("Solicitud creación de un nuevo usuario");
        Usuario usuarioCreado = service.postUSuario(usuarioMapper.toEntity(dto));
        ResponseUsuarioDTO response = usuarioMapper.toDTO(usuarioCreado);
        log.debug("Usuario creado: ", response);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(
        summary = "Obtener usuarios por lista de IDs",
        description = "Retorna una lista de usuarios según una lista de IDs proporcionada")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de usuarios obtenida exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseUsuarioDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron usuarios para los IDs proporcionados",
            content = @Content)
    })
    @PostMapping("/lista")
    public ResponseEntity<List<ResponseUsuarioDTO>> listUsuariosByIds(@Valid @RequestBody List<Long> ids){
        log.info("Solicitud de información de los usuarios: " + ids);
        List<Usuario> usuarios = service.getUsuariosByIds(ids);
        if(usuarios.isEmpty()){
            log.info("No se encontraron usuarios");
            return ResponseEntity.notFound().build();
        }
        List<ResponseUsuarioDTO> dtoList = usuarioMapper.toDTOList(usuarios);
        log.debug("Usuarios encontrados: ", dtoList);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Generar usuarios de prueba con DataFaker",
        description = "Genera una cantidad específica de usuarios con datos ficticios realistas")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Usuarios generados exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseUsuarioDTO.class)))),
        @ApiResponse(
            responseCode = "400",
            description = "Cantidad inválida (debe ser 1-1000)",
            content = @Content)
    })
    @PostMapping("/generar")
    public ResponseEntity<List<ResponseUsuarioDTO>> generarUsuarios(
            @Parameter(description = "Cantidad de usuarios a generar (1-1000)", example = "50")
            @RequestParam(defaultValue = "10") int cantidad) {

        if (cantidad < 1 || cantidad > 1000) {
            return ResponseEntity.badRequest().build();
        }

        log.info("Solicitud de generación de {} usuarios con DataFaker", cantidad);
        List<Usuario> usuarios = generadorUsuariosService.generarUsuarios(cantidad);
        List<ResponseUsuarioDTO> dtoList = usuarioMapper.toDTOList(usuarios);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoList);
    }

    // --------------------------------------------------------
    // ------------------ Sección DELETE ----------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Eliminar un usuario",
        description = "Elimina un usuario de la plataforma según su ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Usuario eliminado exitosamente",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontró usuario con el ID indicado",
            content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseUsuarioDTO> deleteUsuario(
            @Parameter(description = "ID del usuario a eliminar", example = "1")
            @PathVariable Long id){
        boolean eliminado = service.deleteUsuario(id);
        if(eliminado){
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build();
    }

    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    @Operation(
        summary = "Actualizar un usuario",
        description = "Actualiza la información de un usuario según su ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Usuario actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = ResponseUsuarioDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontró usuario con el ID indicado",
            content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseUsuarioDTO> putUsuario(
            @Parameter(description = "ID del usuario a actualizar", example = "1")
            @PathVariable long id,
            @Valid @RequestBody RequestUsuarioDTO dto){
        Usuario usuarioActualizado = service.putUsuario(id, usuarioMapper.toEntity(dto));
        if(usuarioActualizado != null){
            ResponseUsuarioDTO response = usuarioMapper.toDTO(usuarioActualizado);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }
}
