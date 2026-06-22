package com.madoscientista.notificador;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.madoscientista.notificador.client.UsuarioClient;
import com.madoscientista.notificador.dto.usuarioDTO.ResponseUsuarioDTO;
import com.madoscientista.notificador.model.Notificacion;
import com.madoscientista.notificador.model.TipoNotificacion;
import com.madoscientista.notificador.repository.NotificacionRepository;
import com.madoscientista.notificador.service.NotificacionService;


@SpringBootTest
public class NotificacionServiceTest {

    @Autowired
    private NotificacionService notificacionService;

    @MockitoBean
    private NotificacionRepository notificacionRepo;

    @MockitoBean
    private UsuarioClient uClient;


    // Retorna una lista de notificaciones no leidas por un usuario
    @Test
    public void getNotificacionesNoLeidasByUsuarioId(){

        Long idUsuario = 1L;

        // Tipo de notificacion de prueba
        TipoNotificacion tipoNotificacion = new TipoNotificacion();
        tipoNotificacion.setIdTipoNotificacion(1L);
        tipoNotificacion.setPlantillaMensaje("Plantilla de prueba");

        // Notificacion de prueba
        Notificacion notificacion = new Notificacion();
        notificacion.setIdNotificacion(1L);
        notificacion.setIdUsuarioDestino(idUsuario);
        notificacion.setLeido(false);
        notificacion.setTipoNotificacion(tipoNotificacion);

        List<Notificacion> listaNotificaciones = new ArrayList<>();
        listaNotificaciones.add(notificacion);

        // Simulacion de retorno de repositorio
        when(notificacionRepo.findByIdUsuarioDestinoAndLeidoFalse(idUsuario)).thenReturn(listaNotificaciones);

        // Metodo a testear
        List<Notificacion> notificacionesEncontradas = notificacionService.getNotificacionesNoLeidasByUsuarioId(idUsuario);

        // Resultados esperados
        assertNotNull(notificacionesEncontradas);
        assertEquals(1, notificacionesEncontradas.size());
        assertFalse(notificacionesEncontradas.get(0).isLeido());
    }


    // Retorna una lista de notificaciones leidas por un usuario
    @Test
    public void getNotificacionesLeidasByUsuarioId(){

        Long idUsuario = 1L;

        // Tipo de notificacion de prueba
        TipoNotificacion tipoNotificacion = new TipoNotificacion();
        tipoNotificacion.setIdTipoNotificacion(1L);
        tipoNotificacion.setPlantillaMensaje("Plantilla de prueba");

        // Notificacion de prueba
        Notificacion notificacion = new Notificacion();
        notificacion.setIdNotificacion(1L);
        notificacion.setIdUsuarioDestino(idUsuario);
        notificacion.setLeido(true);
        notificacion.setTipoNotificacion(tipoNotificacion);

        List<Notificacion> listaNotificaciones = new ArrayList<>();
        listaNotificaciones.add(notificacion);

        // Simulacion de retorno de repositorio
        when(notificacionRepo.findByIdUsuarioDestinoAndLeidoTrue(idUsuario)).thenReturn(listaNotificaciones);

        // Metodo a testear
        List<Notificacion> notificacionesEncontradas = notificacionService.getNotificacionesLeidasByUsuarioId(idUsuario);

        // Resultados esperados
        assertNotNull(notificacionesEncontradas);
        assertEquals(1, notificacionesEncontradas.size());
        assertTrue(notificacionesEncontradas.get(0).isLeido());
    }


    // Retorna todas las notificaciones de un usuario
    @Test
    public void getAllNotificacionesByUsuarioId(){

        Long idUsuario = 1L;

        // Tipo de notificacion de prueba
        TipoNotificacion tipoNotificacion = new TipoNotificacion();
        tipoNotificacion.setIdTipoNotificacion(1L);
        tipoNotificacion.setPlantillaMensaje("Plantilla de prueba");

        // Notificacion de prueba
        Notificacion notificacion = new Notificacion();
        notificacion.setIdNotificacion(1L);
        notificacion.setIdUsuarioDestino(idUsuario);
        notificacion.setTipoNotificacion(tipoNotificacion);

        List<Notificacion> listaNotificaciones = new ArrayList<>();
        listaNotificaciones.add(notificacion);

        // Simulacion de retorno de repositorio
        when(notificacionRepo.findByIdUsuarioDestino(idUsuario)).thenReturn(listaNotificaciones);

        // Metodo a testear
        List<Notificacion> notificacionesEncontradas = notificacionService.getAllNotificacionesByUsuarioId(idUsuario);

        // Resultados esperados
        assertNotNull(notificacionesEncontradas);
        assertEquals(1, notificacionesEncontradas.size());
    }


    // Retorna una notificacion por su ID y la marca como leida
    @Test
    public void getNotificacionById(){

        // Notificacion de prueba
        Long id = 1L;
        Notificacion notificacion = new Notificacion();
        notificacion.setIdNotificacion(id);
        notificacion.setIdUsuarioDestino(1L);
        notificacion.setLeido(false);
        notificacion.setMensaje("Mensaje de prueba");

        Notificacion notificacionActualizada = new Notificacion();
        notificacionActualizada.setIdNotificacion(id);
        notificacionActualizada.setLeido(true);

        // Simulacion de retorno de repositorio
        when(notificacionRepo.findById(id)).thenReturn(Optional.of(notificacion));
        when(notificacionRepo.save(any(Notificacion.class))).thenReturn(notificacionActualizada);

        // Metodo a testear
        Notificacion notificacionEncontrada = notificacionService.getNotificacionById(id);

        // Resultados esperados
        assertNotNull(notificacionEncontrada);
        assertTrue(notificacionEncontrada.isLeido());
    }


    // Retorna todas las notificaciones disponibles en DB
    @Test
    public void getNotificaciones(){

        // Notificaciones de prueba
        Notificacion notificacion1 = new Notificacion();
        notificacion1.setIdNotificacion(1L);
        notificacion1.setIdUsuarioDestino(1L);

        Notificacion notificacion2 = new Notificacion();
        notificacion2.setIdNotificacion(2L);
        notificacion2.setIdUsuarioDestino(2L);

        List<Notificacion> listaNotificaciones = new ArrayList<>();
        listaNotificaciones.add(notificacion1);
        listaNotificaciones.add(notificacion2);

        // Simulacion de retorno de repositorio
        when(notificacionRepo.findAll()).thenReturn(listaNotificaciones);

        // Metodo a testear
        List<Notificacion> notificacionesEncontradas = notificacionService.getNotificaciones();

        // Resultados esperados
        assertNotNull(notificacionesEncontradas);
        assertEquals(2, notificacionesEncontradas.size());
    }


    // Crea un conjunto de notificaciones procesando las plantillas con nombres de usuario
    @Test
    public void postNotificaciones(){

        // Tipo de notificacion de prueba
        TipoNotificacion tipoNotificacion = new TipoNotificacion();
        tipoNotificacion.setIdTipoNotificacion(1L);
        tipoNotificacion.setPlantillaMensaje("{usuarioOrigen} ha realizado una accion para {usuarioDestino}");

        // Notificacion de prueba
        Notificacion notificacion = new Notificacion();
        notificacion.setIdUsuarioOrigen(1L);
        notificacion.setIdUsuarioDestino(2L);
        notificacion.setTipoNotificacion(tipoNotificacion);

        List<Notificacion> listaNotificaciones = new ArrayList<>();
        listaNotificaciones.add(notificacion);

        // Simulacion de retorno de cliente de usuarios
        ResponseUsuarioDTO usuarioOrigen = new ResponseUsuarioDTO(1L, "Juan", "Perez", "juanperez", "juan@email.com");
        ResponseUsuarioDTO usuarioDestino = new ResponseUsuarioDTO(2L, "Maria", "Lopez", "marialopez", "maria@email.com");
        List<ResponseUsuarioDTO> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(usuarioOrigen);
        listaUsuarios.add(usuarioDestino);

        when(uClient.listUsuariosByIds(anyList())).thenReturn(ResponseEntity.ok(listaUsuarios));

        // Simulacion de retorno de repositorio
        when(notificacionRepo.saveAll(anyList())).thenReturn(listaNotificaciones);

        // Metodo a testear
        List<Notificacion> notificacionesCreadas = notificacionService.postNotificaciones(listaNotificaciones);

        // Resultados esperados
        assertNotNull(notificacionesCreadas);
        assertEquals(1, notificacionesCreadas.size());
        assertEquals("juanperez ha realizado una accion para marialopez", notificacionesCreadas.get(0).getMensaje());
        assertFalse(notificacionesCreadas.get(0).isLeido());

        // Verificacion de que se ejecutan los metodos internos
        verify(uClient).listUsuariosByIds(anyList());
        verify(notificacionRepo).saveAll(anyList());
    }
}
