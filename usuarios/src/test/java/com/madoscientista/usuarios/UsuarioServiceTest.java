package com.madoscientista.usuarios;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.madoscientista.usuarios.model.Usuario;
import com.madoscientista.usuarios.repository.UsuarioRepository;
import com.madoscientista.usuarios.service.UsuarioService;

@SpringBootTest
public class UsuarioServiceTest {

    // Inyecta el servicio de Usuario
    @Autowired
    private UsuarioService usuarioService;

    // Crea un mock del repositorio de usuario
    @MockitoBean
    private UsuarioRepository usuarioRepository;

    
    // Retorna un usuario filtrado por id
    @Test
    public void getUsuarioById(){
        
        // 'Samuel', 'Cortés', 'sam_cortes', 'sam_cortes@app.com', '1234'
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(id);
        usuario.setNombreUsuario("sam_cortes");
        usuario.setPassword("1234");

        when(usuarioRepository.findByIdUsuario(id)).thenReturn(usuario);

        Usuario usuarioEncontrado = usuarioRepository.findByIdUsuario(id);

        assertNotNull(usuarioEncontrado);
        assertEquals(id, usuarioEncontrado.getIdUsuario());

    }

}
