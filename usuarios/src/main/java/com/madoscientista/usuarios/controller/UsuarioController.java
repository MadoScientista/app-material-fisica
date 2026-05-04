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
    public List<ResponseUsuarioDTO> getUsuarios(){
        return service.getUsuarios()
                .stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Retorna un usuario filtrado por id
    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id){
        Usuario u = service.getUsuarioById(id);

        ResponseUsuarioDTO response = usuarioMapper.toDTO(u);

        if(u != null){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró al usuario con id: " + id);
    }

    // Retorna una lista de usuarios filtrados por id
    @GetMapping("/lista")
    public List<ResponseUsuarioDTO> getUsuariosByIds(@RequestBody List<Long> ids){
        return service.getUsuariosByIds(ids)
                .stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }


    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // Crea un usuario nuevo a partir de los datos del request
    @PostMapping
    public ResponseEntity<ResponseUsuarioDTO> postUsuario(@RequestBody RequestUsuarioDTO dto){

        Usuario usuarioCreado = service.postUSuario(usuarioMapper.toEntity(dto));
        ResponseUsuarioDTO response = usuarioMapper.toDTO(usuarioCreado);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // --------------------------------------------------------
    // ------------------ Sección DELETE ----------------------
    // --------------------------------------------------------

    // Elimina un usuario filtrado por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id){

        boolean eliminado = service.deleteUsuario(id);

        if(eliminado){
            return ResponseEntity.noContent().build(); // 204
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("No se encontró al usuario con id: " + id);
    }


    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    // Actualiza un usuario filtrado por su ID
    @PutMapping("/{id}")
    public ResponseEntity<?> putUsuario(
            @PathVariable long id,
            @RequestBody RequestUsuarioDTO dto){

        Usuario usuarioActualizado = service.putUsuario(id, usuarioMapper.toEntity(dto));

        if(usuarioActualizado != null){
            ResponseUsuarioDTO response = usuarioMapper.toDTO(usuarioActualizado);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("No se encontró al usuario con id: " + id);
    }
}
