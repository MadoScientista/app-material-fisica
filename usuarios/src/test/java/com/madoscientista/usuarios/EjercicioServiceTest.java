package com.madoscientista.usuarios;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.madoscientista.usuarios.client.HistorialClient;
import com.madoscientista.usuarios.client.GeneradorEjerciciosClient;
import com.madoscientista.usuarios.client.LogrosClient;
import com.madoscientista.usuarios.client.SuscripcionesClient;
import com.madoscientista.usuarios.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.usuarios.dto.ejercicioDTO.RequestEjercicioDTO;
import com.madoscientista.usuarios.dto.ejercicioDTO.ResponseEjercicioDTO;
import com.madoscientista.usuarios.mapper.EjercicioMapper;
import com.madoscientista.usuarios.model.Ejercicio;
import com.madoscientista.usuarios.model.Usuario;
import com.madoscientista.usuarios.repository.EjercicioRepository;
import com.madoscientista.usuarios.service.EjercicioService;
import com.madoscientista.usuarios.service.UsuarioService;


@SpringBootTest
public class EjercicioServiceTest {

    @Autowired
    private EjercicioService ejercicioService;

    @MockitoBean
    private EjercicioRepository ejercicioRepository;

    @MockitoBean
    private GeneradorEjerciciosClient geClient;

    @MockitoBean
    private HistorialClient hClient;

    @MockitoBean
    private SuscripcionesClient sClient;

    @MockitoBean
    private LogrosClient lClient;

    @MockitoBean
    private UsuarioService uService;

    @MockitoBean
    private EjercicioMapper mapper;


    // getEjercicios retorna una lista de todos los ejercicios en DB
    @Test
    public void getEjercicios(){

        // Ejercicio de prueba 1
        Long id1 = 1L;
        String dificultad1 = "INTERMEDIO";
        String tema1 = "MRU";
        Ejercicio ejercicio1 = new Ejercicio();
        ejercicio1.setIdEjercicio(id1);
        ejercicio1.setDificultad(dificultad1);
        ejercicio1.setTema(tema1);

        // Ejercicio de prueba 2
        Long id2 = 2L;
        String dificultad2 = "AVANZADO";
        String tema2 = "MRU"; 
        Ejercicio ejercicio2 = new Ejercicio();
        ejercicio2.setIdEjercicio(id2);
        ejercicio2.setDificultad(dificultad2);
        ejercicio2.setTema(tema2);

        // Lista de ejercicios de prueba
        List<Ejercicio> listaEjercicios = new ArrayList<>();
        listaEjercicios.add(ejercicio1);
        listaEjercicios.add(ejercicio2);

        // Retorno simulado del repositorio
        when(ejercicioRepository.findAll()).thenReturn(listaEjercicios);

        // Método a testear
        List<Ejercicio> ejerciciosEncontrados = ejercicioService.getEjercicios();

        // Resultados esperados
        assertNotNull(ejerciciosEncontrados);
        assertEquals(listaEjercicios, ejerciciosEncontrados);
        assertEquals(2, ejerciciosEncontrados.size());
    }


    @Test
    public void getEjercicioById(){

        // Ejercicio de prueba
        Long id1 = 1L;
        String dificultad1 = "INTERMEDIO";
        String tema1 = "MRU";
        Ejercicio ejercicio1 = new Ejercicio();
        ejercicio1.setIdEjercicio(id1);
        ejercicio1.setDificultad(dificultad1);
        ejercicio1.setTema(tema1);

        // Retorno simulado del repositorio
        when(ejercicioRepository.findById(id1)).thenReturn(Optional.of(ejercicio1));

        // Método a testear
        Ejercicio ejercicioEncontrado = ejercicioService.getEjercicioById(id1);

        // Resultados esperados
        assertNotNull(ejercicioEncontrado);
        assertEquals(ejercicio1, ejercicioEncontrado);
        assertEquals(dificultad1, ejercicioEncontrado.getDificultad());
    }


    // Retorna una lista de ejercicios filtrados por el idCreador
    @Test
    public void getEjerciciosCreadosUsuario(){

        // Ejercicio de prueba
        Long idCreador = 1L;
        Long id = 1L;
        String dificultad = "INTERMEDIO";
        String tema = "MRU";
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setIdEjercicio(id);
        ejercicio.setDificultad(dificultad);
        ejercicio.setTema(tema);

        List<Ejercicio> listaEjercicios = new ArrayList<>();
        listaEjercicios.add(ejercicio);

        when(ejercicioRepository.findAllByCreadorIdUsuario(idCreador)).thenReturn(listaEjercicios);

        List<Ejercicio> resultado = ejercicioService.getEjerciciosCreadosUsuario(idCreador);

        assertNotNull(resultado);
        assertEquals(listaEjercicios, resultado);
        assertEquals(1, resultado.size());
    }


    // Retorna una lista de ejercicios que se les ha compartido a un usuario
    @Test
    public void getEjerciciosCompartidosAUsuario(){

        // Ejercicio de prueba
        Long idCreador = 1L;
        Long id = 1L;
        String dificultad = "INTERMEDIO";
        String tema = "MRU";
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setIdEjercicio(id);
        ejercicio.setDificultad(dificultad);
        ejercicio.setTema(tema);

        List<Ejercicio> listaEjercicios = new ArrayList<>();
        listaEjercicios.add(ejercicio);

        when(ejercicioRepository.findByUsuariosCompartidosIdUsuario(idCreador)).thenReturn(listaEjercicios);

        List<Ejercicio> resultado = ejercicioService.getEjerciciosCompartidosAUsuario(id);

        assertNotNull(resultado);
        assertEquals(listaEjercicios, resultado);
        assertEquals(1, resultado.size());
    }


    // Retorna una lista de ejercicios creados por un usuario
    @Test
    public void getEjerciciosCreadosByUsuario(){

        // Ejercicio de prueba
         // Ejercicio de prueba
        Long idCreador = 1L;
        Long id = 1L;
        String dificultad = "INTERMEDIO";
        String tema = "MRU";
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setIdEjercicio(id);
        ejercicio.setDificultad(dificultad);
        ejercicio.setTema(tema);

        List<Ejercicio> listaEjercicios = new ArrayList<>();
        listaEjercicios.add(ejercicio);

        when(ejercicioRepository.findAllByCreadorIdUsuario(idCreador)).thenReturn(listaEjercicios);

        List<Ejercicio> resultado = ejercicioService.getEjerciciosCreadosByUsuario(id);

        assertNotNull(resultado);
        assertEquals(listaEjercicios, resultado);
        assertEquals(1, resultado.size());
    }


    // Retorna la cantidad de ejercicios almacenados por un usuario
    @Test
    public void contarEjerciciosByIUsuario(){

        // Valores de búsqueda y retorno
        Long id = 1L;
        Long totalEjercicios = 5L;

        // Retorno simulado
        when(ejercicioRepository.countByCreadorIdUsuario(id)).thenReturn(totalEjercicios);

        // Método a testear
        Long resultado = ejercicioService.contarEjerciciosByIUsuario(id);

        // Resultado esperado
        assertEquals(totalEjercicios, resultado);
    }


    // Solicita al microservicio generador de ejercicios la creación de un nuevo ejercicio
    @Test
    public void postEjercicio(){

        // IDs necesarios para los objetos de prueba
        Long idUsuario = 1L;
        Long idEjercicio = 1L;

        // DTO de solicitud de prueba
        RequestEjercicioDTO request = new RequestEjercicioDTO();
        request.setTema("MRU");
        request.setContexto("Automóvil en carretera recta");
        request.setIncognita("velocidad final");
        request.setDificultad("Fácil");
        request.setResultadoPositivo(true);

        // DTO de respuesta de prueba
        ResponseEjercicioDTO ejercicioDTO = new ResponseEjercicioDTO();

        // Usuario de prueba
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);

        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setIdEjercicio(idEjercicio);

        // Retornos de prueba
        when(ejercicioRepository.countByCreadorIdUsuario(idUsuario)).thenReturn(0L);
        when(sClient.getMaxEjerciciosByUsuarioId(idUsuario)).thenReturn(ResponseEntity.ok(10L));
        when(geClient.getEjercicioMRU(request)).thenReturn(ejercicioDTO);
        when(uService.getUsuarioById(idUsuario)).thenReturn(usuario);
        when(mapper.toEntity(ejercicioDTO, usuario)).thenReturn(ejercicio);
        when(ejercicioRepository.save(ejercicio)).thenReturn(ejercicio);

        Ejercicio resultado = ejercicioService.postEjercicio(request, idUsuario);

        assertNotNull(resultado);
        assertEquals(ejercicio, resultado);
        verify(lClient).postIncrementarEjercicioCreado(idUsuario);
        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }


    // Crea un registro en la tabla ejercicios compartidos con otros usuarios
    @Test
    public void compartirEjercicio(){

        long idCreador = 1L;
        long idEjercicio = 1L;
        List<Long> idsUsuariosCompartir = new ArrayList<>();
        idsUsuariosCompartir.add(2L);

        Usuario creador = new Usuario();
        creador.setIdUsuario(idCreador);

        Usuario usuarioCompartido = new Usuario();
        usuarioCompartido.setIdUsuario(2L);

        List<Usuario> usuariosCompartir = new ArrayList<>();
        usuariosCompartir.add(usuarioCompartido);

        Set<Usuario> usuariosCompartidos = new HashSet<>();

        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setIdEjercicio(idEjercicio);
        ejercicio.setCreador(creador);
        ejercicio.setUsuariosCompartidos(usuariosCompartidos);

        when(ejercicioRepository.findByIdEjercicio(idEjercicio)).thenReturn(Optional.of(ejercicio));
        when(uService.getUsuariosByIds(idsUsuariosCompartir)).thenReturn(usuariosCompartir);
        when(ejercicioRepository.save(ejercicio)).thenReturn(ejercicio);

        Ejercicio resultado = ejercicioService.compartirEjercicio(idCreador, idEjercicio, idsUsuariosCompartir);

        assertNotNull(resultado);
        assertTrue(resultado.getUsuariosCompartidos().contains(usuarioCompartido));
        verify(lClient).postIncrementarEjercicioCompartido(idCreador, idsUsuariosCompartir.size());
        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }


    // Deja de compartir un ejercicio con una lista de usuarios
    @Test
    public void dejarDeCompartirEjercicio(){

        long idEjercicio = 1L;
        long idCreador = 1L;
        List<Long> idsUsuariosRemover = new ArrayList<>();
        idsUsuariosRemover.add(2L);

        Usuario creador = new Usuario();
        creador.setIdUsuario(idCreador);

        Usuario usuarioRemover = new Usuario();
        usuarioRemover.setIdUsuario(2L);

        Set<Usuario> usuariosCompartidos = new HashSet<>();
        usuariosCompartidos.add(usuarioRemover);

        List<Usuario> usuariosRemover = new ArrayList<>();
        usuariosRemover.add(usuarioRemover);

        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setIdEjercicio(idEjercicio);
        ejercicio.setCreador(creador);
        ejercicio.setUsuariosCompartidos(usuariosCompartidos);

        when(ejercicioRepository.findById(idEjercicio)).thenReturn(Optional.of(ejercicio));
        when(uService.getUsuariosByIds(idsUsuariosRemover)).thenReturn(usuariosRemover);
        when(ejercicioRepository.save(ejercicio)).thenReturn(ejercicio);

        Ejercicio resultado = ejercicioService.dejarDeCompartirEjercicio(idEjercicio, idCreador, idsUsuariosRemover);

        assertNotNull(resultado);
        assertTrue(resultado.getUsuariosCompartidos().isEmpty());
        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }


    // Retorna una lista de ejercicios creados por un Set de usuarios
    @Test
    public void listarEjerciciosDeUSuarios(){

        Set<Long> idUsuarios = Set.of(1L, 2L);

        List<Ejercicio> listaEjercicios = new ArrayList<>();

        when(ejercicioRepository.findAllByCreadorIdUsuarioIn(idUsuarios)).thenReturn(listaEjercicios);

        List<Ejercicio> resultado = ejercicioService.listarEjerciciosDeUSuarios(idUsuarios);

        assertNotNull(resultado);
        assertEquals(listaEjercicios, resultado);
    }


    // Elimina un ejercicio creado por un usuario según el ID del ejercicio
    @Test
    public void deleteEjercicio(){

        Long idUsuario = 1L;
        Long idEjercicio = 1L;

        Usuario creador = new Usuario();
        creador.setIdUsuario(idUsuario);

        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setIdEjercicio(idEjercicio);
        ejercicio.setCreador(creador);

        when(ejercicioRepository.findById(idEjercicio)).thenReturn(Optional.of(ejercicio));

        boolean resultado = ejercicioService.deleteEjercicio(idUsuario, idEjercicio);

        assertTrue(resultado);
        verify(ejercicioRepository).delete(ejercicio);
        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }
}
