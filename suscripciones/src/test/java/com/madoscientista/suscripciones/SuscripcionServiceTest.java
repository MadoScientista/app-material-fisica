package com.madoscientista.suscripciones;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.madoscientista.suscripciones.client.HistorialClient;
import com.madoscientista.suscripciones.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.suscripciones.dto.EventoDTO.ResponseEventoDTO;
import com.madoscientista.suscripciones.model.Suscripcion;
import com.madoscientista.suscripciones.model.TipoSuscripcion;
import com.madoscientista.suscripciones.repository.SuscripcionRepository;
import com.madoscientista.suscripciones.repository.TipoSuscripcionRepository;
import com.madoscientista.suscripciones.service.SuscripcionService;


@SpringBootTest
public class SuscripcionServiceTest {

    @Autowired
    private SuscripcionService suscripcionService;

    @MockitoBean
    private SuscripcionRepository suscripcionRepo;

    @MockitoBean
    private HistorialClient hClient;

    @MockitoBean
    private TipoSuscripcionRepository tipoSuscripcionRepo;


    // Retorna una lista de suscripciones activas
    @Test
    public void getSuscripcionesActivas(){

        // Tipo de suscripcion de prueba
        TipoSuscripcion tipoSuscripcion = new TipoSuscripcion();
        tipoSuscripcion.setIdTipoSuscripcion(1L);
        tipoSuscripcion.setNombre("PREMIUM");

        // Suscripcion de prueba
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setIdSuscripcion(1L);
        suscripcion.setIdUsuario(1L);
        suscripcion.setActivo(true);
        suscripcion.setTipoSuscripcion(tipoSuscripcion);

        List<Suscripcion> listaSuscripciones = new ArrayList<>();
        listaSuscripciones.add(suscripcion);

        // Simulacion de retorno de repositorio
        when(suscripcionRepo.findByActivo(true)).thenReturn(listaSuscripciones);

        // Metodo a testear
        List<Suscripcion> suscripcionesEncontradas = suscripcionService.getSuscripcionesActivas();

        // Resultados esperados
        assertNotNull(suscripcionesEncontradas);
        assertEquals(1, suscripcionesEncontradas.size());
        assertTrue(suscripcionesEncontradas.get(0).isActivo());
    }


    // Retorna una lista con los IDs de usuarios con suscripciones activas
    @Test
    public void getUsuariosConSuscripcionesActivas(){

        // Suscripciones de prueba
        Suscripcion suscripcion1 = new Suscripcion();
        suscripcion1.setIdSuscripcion(1L);
        suscripcion1.setIdUsuario(1L);
        suscripcion1.setActivo(true);

        Suscripcion suscripcion2 = new Suscripcion();
        suscripcion2.setIdSuscripcion(2L);
        suscripcion2.setIdUsuario(2L);
        suscripcion2.setActivo(true);

        List<Suscripcion> listaSuscripciones = new ArrayList<>();
        listaSuscripciones.add(suscripcion1);
        listaSuscripciones.add(suscripcion2);

        // Simulacion de retorno de repositorio
        when(suscripcionRepo.findByActivo(true)).thenReturn(listaSuscripciones);

        // Metodo a testear
        List<Long> idsUsuarios = suscripcionService.getUsuariosConSuscripcionesActivas();

        // Resultados esperados
        assertNotNull(idsUsuarios);
        assertEquals(2, idsUsuarios.size());
        assertTrue(idsUsuarios.contains(1L));
        assertTrue(idsUsuarios.contains(2L));
    }


    // Retorna la suscripcion de un usuario por su ID
    @Test
    public void getSuscripcionByUsuarioId(){

        Long idUsuario = 1L;

        // Suscripcion de prueba
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setIdSuscripcion(1L);
        suscripcion.setIdUsuario(idUsuario);
        suscripcion.setActivo(true);

        // Simulacion de retorno de repositorio
        when(suscripcionRepo.findByIdUsuario(idUsuario)).thenReturn(Optional.of(suscripcion));

        // Metodo a testear
        Suscripcion suscripcionEncontrada = suscripcionService.getSuscripcionByUsuarioId(idUsuario);

        // Resultados esperados
        assertNotNull(suscripcionEncontrada);
        assertEquals(idUsuario, suscripcionEncontrada.getIdUsuario());
    }


    // Retorna una lista de suscripciones por una lista de IDs de usuario
    @Test
    public void getSuscripcionesByUsuarioIds(){

        // Suscripciones de prueba
        Suscripcion suscripcion1 = new Suscripcion();
        suscripcion1.setIdSuscripcion(1L);
        suscripcion1.setIdUsuario(1L);

        Suscripcion suscripcion2 = new Suscripcion();
        suscripcion2.setIdSuscripcion(2L);
        suscripcion2.setIdUsuario(2L);

        List<Long> idsUsuarios = new ArrayList<>();
        idsUsuarios.add(1L);
        idsUsuarios.add(2L);

        List<Suscripcion> listaSuscripciones = new ArrayList<>();
        listaSuscripciones.add(suscripcion1);
        listaSuscripciones.add(suscripcion2);

        // Simulacion de retorno de repositorio
        when(suscripcionRepo.findByIdUsuarioIn(idsUsuarios)).thenReturn(listaSuscripciones);

        // Metodo a testear
        List<Suscripcion> suscripcionesEncontradas = suscripcionService.getSuscripcionesByUsuarioIds(idsUsuarios);

        // Resultados esperados
        assertNotNull(suscripcionesEncontradas);
        assertEquals(2, suscripcionesEncontradas.size());
    }


    // Retorna la cantidad maxima de ejercicios permitidos para un usuario segun su tipo de suscripcion
    @Test
    public void getMaxEjerciciosByUsuarioId(){

        Long idUsuario = 1L;

        // Tipo de suscripcion de prueba
        TipoSuscripcion tipoSuscripcion = new TipoSuscripcion();
        tipoSuscripcion.setIdTipoSuscripcion(1L);
        tipoSuscripcion.setNombre("PREMIUM");
        tipoSuscripcion.setNMaxEjercicios(100L);

        // Suscripcion de prueba
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setIdSuscripcion(1L);
        suscripcion.setIdUsuario(idUsuario);
        suscripcion.setActivo(true);
        suscripcion.setTipoSuscripcion(tipoSuscripcion);

        // Simulacion de retorno de repositorio
        when(suscripcionRepo.findByIdUsuarioAndActivoTrue(idUsuario)).thenReturn(Optional.of(suscripcion));

        // Metodo a testear
        Long maxEjercicios = suscripcionService.getMaxEjerciciosByUsuarioId(idUsuario);

        // Resultados esperados
        assertNotNull(maxEjercicios);
        assertEquals(100L, maxEjercicios);
    }


    // Crea una nueva suscripcion para un usuario segun su ID
    @Test
    public void postSuscripcion(){

        Long idUsuario = 1L;
        String nombreSuscripcion = "PREMIUM";

        // Tipo de suscripcion de prueba
        TipoSuscripcion tipoSuscripcion = new TipoSuscripcion();
        tipoSuscripcion.setIdTipoSuscripcion(1L);
        tipoSuscripcion.setNombre(nombreSuscripcion);
        tipoSuscripcion.setNMaxEjercicios(100L);

        // Suscripcion creada de prueba
        Suscripcion suscripcionCreada = new Suscripcion();
        suscripcionCreada.setIdSuscripcion(1L);
        suscripcionCreada.setIdUsuario(idUsuario);
        suscripcionCreada.setActivo(true);
        suscripcionCreada.setTipoSuscripcion(tipoSuscripcion);

        // Simulacion de retorno de repositorio
        when(tipoSuscripcionRepo.findByNombre(nombreSuscripcion)).thenReturn(Optional.of(tipoSuscripcion));
        when(suscripcionRepo.findByIdUsuarioAndActivoTrue(idUsuario)).thenReturn(Optional.empty());
        when(suscripcionRepo.save(any(Suscripcion.class))).thenReturn(suscripcionCreada);

        // Simulacion de respuesta de cliente
        when(hClient.postEvento(any(RequestEventoDTO.class))).thenReturn(new ResponseEventoDTO());

        // Metodo a testear
        Suscripcion suscripcionResultado = suscripcionService.postSuscripcion(idUsuario, nombreSuscripcion);

        // Resultados esperados
        assertNotNull(suscripcionResultado);
        assertEquals(idUsuario, suscripcionResultado.getIdUsuario());
        assertTrue(suscripcionResultado.isActivo());

        // Verificacion de que se ejecutan los metodos internos
        verify(suscripcionRepo).save(any(Suscripcion.class));
        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }


    // Cancela una suscripcion de un usuario y crea una gratuita
    @Test
    public void cancelarSuscripcion(){

        Long idUsuario = 1L;

        // Tipo de suscripcion de prueba
        TipoSuscripcion tipoPremium = new TipoSuscripcion();
        tipoPremium.setIdTipoSuscripcion(1L);
        tipoPremium.setNombre("PREMIUM");

        TipoSuscripcion tipoGratuita = new TipoSuscripcion();
        tipoGratuita.setIdTipoSuscripcion(2L);
        tipoGratuita.setNombre("GRATUITA");
        tipoGratuita.setNMaxEjercicios(5L);

        // Suscripcion activa de prueba
        Suscripcion suscripcionActiva = new Suscripcion();
        suscripcionActiva.setIdSuscripcion(1L);
        suscripcionActiva.setIdUsuario(idUsuario);
        suscripcionActiva.setActivo(true);
        suscripcionActiva.setTipoSuscripcion(tipoPremium);

        // Suscripcion desactivada de prueba
        Suscripcion suscripcionDesactivada = new Suscripcion();
        suscripcionDesactivada.setIdSuscripcion(1L);
        suscripcionDesactivada.setIdUsuario(idUsuario);
        suscripcionDesactivada.setActivo(false);

        // Suscripcion gratuita de prueba
        Suscripcion suscripcionGratuita = new Suscripcion();
        suscripcionGratuita.setIdSuscripcion(2L);
        suscripcionGratuita.setIdUsuario(idUsuario);
        suscripcionGratuita.setActivo(true);
        suscripcionGratuita.setTipoSuscripcion(tipoGratuita);

        // Simulacion de retorno de repositorio
        when(suscripcionRepo.findByIdUsuarioAndActivoTrue(idUsuario))
            .thenReturn(Optional.of(suscripcionActiva))
            .thenReturn(Optional.empty());
        when(tipoSuscripcionRepo.findByNombre("GRATUITA")).thenReturn(Optional.of(tipoGratuita));
        when(suscripcionRepo.save(any(Suscripcion.class)))
            .thenReturn(suscripcionDesactivada)
            .thenReturn(suscripcionGratuita);

        // Simulacion de respuesta de cliente
        when(hClient.postEvento(any(RequestEventoDTO.class))).thenReturn(new ResponseEventoDTO());

        // Metodo a testear
        Suscripcion suscripcionResultado = suscripcionService.cancelarSuscripcion(idUsuario);

        // Resultados esperados
        assertNotNull(suscripcionResultado);
        assertTrue(suscripcionResultado.isActivo());
        assertEquals("GRATUITA", suscripcionResultado.getTipoSuscripcion().getNombre());

        // Verificacion de que se ejecutan los metodos internos
        verify(suscripcionRepo, times(2)).save(any(Suscripcion.class));
        verify(hClient, times(2)).postEvento(any(RequestEventoDTO.class));
    }


    // Actualiza el tipo de suscripcion de un usuario por su ID
    @Test
    public void actualizarSuscripcion(){

        Long idUsuario = 1L;
        String nuevoTipo = "PREMIUM";

        // Tipo de suscripcion de prueba
        TipoSuscripcion tipoGratuita = new TipoSuscripcion();
        tipoGratuita.setIdTipoSuscripcion(1L);
        tipoGratuita.setNombre("GRATUITA");

        TipoSuscripcion tipoPremium = new TipoSuscripcion();
        tipoPremium.setIdTipoSuscripcion(2L);
        tipoPremium.setNombre("PREMIUM");
        tipoPremium.setNMaxEjercicios(100L);

        // Suscripcion actual activa
        Suscripcion suscripcionActual = new Suscripcion();
        suscripcionActual.setIdSuscripcion(1L);
        suscripcionActual.setIdUsuario(idUsuario);
        suscripcionActual.setActivo(true);
        suscripcionActual.setTipoSuscripcion(tipoGratuita);

        // Suscripcion desactivada
        Suscripcion suscripcionDesactivada = new Suscripcion();
        suscripcionDesactivada.setIdSuscripcion(1L);
        suscripcionDesactivada.setIdUsuario(idUsuario);
        suscripcionDesactivada.setActivo(false);

        // Nueva suscripcion premium
        Suscripcion nuevaSuscripcion = new Suscripcion();
        nuevaSuscripcion.setIdSuscripcion(2L);
        nuevaSuscripcion.setIdUsuario(idUsuario);
        nuevaSuscripcion.setActivo(true);
        nuevaSuscripcion.setTipoSuscripcion(tipoPremium);

        // Simulacion de retorno de repositorio
        when(suscripcionRepo.findByIdUsuarioAndActivoTrue(idUsuario))
            .thenReturn(Optional.of(suscripcionActual))
            .thenReturn(Optional.empty());
        when(tipoSuscripcionRepo.findByNombre(nuevoTipo))
            .thenReturn(Optional.of(tipoPremium));
        when(suscripcionRepo.save(any(Suscripcion.class)))
            .thenReturn(suscripcionDesactivada)
            .thenReturn(nuevaSuscripcion);

        // Simulacion de respuesta de cliente
        when(hClient.postEvento(any(RequestEventoDTO.class))).thenReturn(new ResponseEventoDTO());

        // Metodo a testear
        Suscripcion suscripcionResultado = suscripcionService.actualizarSuscripcion(idUsuario, nuevoTipo);

        // Resultados esperados
        assertNotNull(suscripcionResultado);
        assertTrue(suscripcionResultado.isActivo());
        assertEquals(nuevoTipo, suscripcionResultado.getTipoSuscripcion().getNombre());

        // Verificacion de que se ejecutan los metodos internos
        verify(suscripcionRepo, times(2)).save(any(Suscripcion.class));
        verify(hClient, times(3)).postEvento(any(RequestEventoDTO.class));
    }
}
