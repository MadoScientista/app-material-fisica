package com.madoscientista.usuarios;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.madoscientista.usuarios.client.LogrosClient;
import com.madoscientista.usuarios.model.Usuario;
import com.madoscientista.usuarios.repository.UsuarioRepository;
import com.madoscientista.usuarios.service.UsuarioService;


@SpringBootTest
public class UsuarioServiceTest {

    // Inyecta el servicio de Usuario
    @Autowired
    private UsuarioService usuarioService;

    // Crea un mock del cliente de logros
    @MockitoBean
    private LogrosClient logrosClient;

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

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Usuario usuarioEncontrado = usuarioService.getUsuarioById(id);

        assertNotNull(usuarioEncontrado);
        assertEquals(id, usuarioEncontrado.getIdUsuario());

    }
    

    // Test getUsuarios()
    // Retorna todos los usuarios disponibles en el repositorio
    @Test
    public void getUsuarios(){

        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(id);
        usuario.setNombreUsuario("sam_cortes");
        usuario.setPassword("1234");

        List<Usuario> listaUsuarios = new ArrayList<>();

        listaUsuarios.add(usuario);

        when(usuarioRepository.findAll()).thenReturn(listaUsuarios);

        List<Usuario> usuariosEncontrados = usuarioService.getUsuarios();

        assertNotNull(usuariosEncontrados);
        assertEquals(listaUsuarios, usuariosEncontrados);
    }


    // Retorna una lista de usuarios filtrados por id
    @Test
    public void getUsuariosByIds(){
        
        // Preparar una lista de usuarios
        Long id1 = 1L;
        Long id2 = 2L;

        List<Long> listaIds = new ArrayList<>();
        listaIds.add(id1);
        listaIds.add(id2);

        Usuario usuario1 = new Usuario();
        usuario1.setIdUsuario(id1);
        usuario1.setNombreUsuario("sam_cortes1");
        usuario1.setPassword("12341");

        Usuario usuario2 = new Usuario();
        usuario2.setIdUsuario(id2);
        usuario2.setNombreUsuario("sam_cortes2");
        usuario2.setPassword("12342");

        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(usuario1);
        listaUsuarios.add(usuario2);

        when(usuarioRepository.findById(id1)).thenReturn(Optional.of(usuario1));
        when(usuarioRepository.findById(id2)).thenReturn(Optional.of(usuario2));

        List<Usuario> usuarios = usuarioService.getUsuariosByIds(listaIds);

        assertNotNull(usuarios);
        assertEquals(listaUsuarios, usuarios);
    }

    // Elimina un usuario por id
    @Test
    public void deleteUsuario(){

        // 'Samuel', 'Cortés', 'sam_cortes', 'sam_cortes@app.com', '1234'
        Long id = 1L;

        when(usuarioRepository.existsById(id)).thenReturn(true);

        boolean resultado = usuarioService.deleteUsuario(id);

        assertTrue(resultado);

        // Verificar que los métodos void se están ejecutando
        verify(usuarioRepository).deleteById(id);
    }


    // Crea un usuario y sincroniza sus logros
    @Test
    public void postUSuario(){

        // Usuario de prueba
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(id);
        usuario.setNombreUsuario("sam_cortes1");
        usuario.setPassword("12341");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario usuarioCreado = usuarioService.postUSuario(usuario);

        assertNotNull(usuarioCreado);
        assertEquals("sam_cortes1", usuarioCreado.getNombreUsuario());
        assertEquals(id, usuarioCreado.getIdUsuario());

        // Verificación de que se están ejecutando métodos internos
        verify(logrosClient).postSincronizarLogrosUsuario(id);
    }


    // Actualiza un usuario
    @Test
    public void putUsuario(){

        // Usuario de prueba
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(id);
        usuario.setNombreUsuario("sam_cortes1");
        usuario.setPassword("12341");

        String nombreUsuarioActualizado = "nombre_de_prueba";
        String passwordActualizado = "password_de_prueba";

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setIdUsuario(id);
        usuarioActualizado.setNombreUsuario(nombreUsuarioActualizado);
        usuarioActualizado.setPassword(passwordActualizado);

        when(usuarioRepository.existsById(id)).thenReturn(true);
        when(usuarioRepository.findByIdUsuario(id)).thenReturn(usuario);
        when(usuarioRepository.save(usuarioActualizado)).thenReturn(usuarioActualizado);

        Usuario usuarioTest = usuarioService.putUsuario(id, usuarioActualizado);

        assertNotNull(usuarioTest);
        assertEquals(nombreUsuarioActualizado, usuarioTest.getNombreUsuario());
    }
}
