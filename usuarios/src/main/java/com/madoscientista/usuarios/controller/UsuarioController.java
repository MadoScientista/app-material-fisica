package com.madoscientista.usuarios.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.madoscientista.usuarios.dto.usuarioDTO.RequestUsuarioDTO;
import com.madoscientista.usuarios.dto.usuarioDTO.ResponseUsuarioDTO;
import com.madoscientista.usuarios.mapper.UsuarioMapper;
import com.madoscientista.usuarios.model.Usuario;
import com.madoscientista.usuarios.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private UsuarioMapper usuarioMapper;


    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    // Retorna la lista de usuarios disponibles
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

    // Retorna un usuario filtrado por id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUsuarioDTO> getUsuarioById(@PathVariable Long id){
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

    // Crea un usuario nuevo a partir de los datos del request
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

    // Retorna una lista de usuarios filtrados por id
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

    // --------------------------------------------------------
    // ------------------ Sección DELETE ----------------------
    // --------------------------------------------------------

    // Elimina un usuario filtrado por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseUsuarioDTO> deleteUsuario(@PathVariable Long id){

        boolean eliminado = service.deleteUsuario(id);

        if(eliminado){
            return ResponseEntity.noContent().build(); // 204
        }

        return ResponseEntity.notFound().build();
    }


    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    // Actualiza un usuario filtrado por su ID
    @PutMapping("/{id}")
    public ResponseEntity<ResponseUsuarioDTO> putUsuario(
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
