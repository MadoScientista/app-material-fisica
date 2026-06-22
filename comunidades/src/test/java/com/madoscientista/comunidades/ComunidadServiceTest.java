package com.madoscientista.comunidades;

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
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.madoscientista.comunidades.client.HistorialClient;
import com.madoscientista.comunidades.client.LogrosClient;
import com.madoscientista.comunidades.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.comunidades.model.Comunidad;
import com.madoscientista.comunidades.repository.ComunidadRepository;
import com.madoscientista.comunidades.service.ComunidadService;


@SpringBootTest
public class ComunidadServiceTest {

    @Autowired
    private ComunidadService comunidadService;

    @MockitoBean
    private ComunidadRepository comunidadRepository;

    @MockitoBean
    private HistorialClient hClient;

    @MockitoBean
    private LogrosClient lClient;


    // Retorna las comunidades disponibles
    @Test
    public void getComunidades(){

        // Comunidad de prueba
        Long id = 1L;
        Comunidad comunidad = new Comunidad();
        comunidad.setIdComunidad(id);
        comunidad.setNombre("Comunidad de prueba");
        comunidad.setIdUsuarioCreador(1L);

        // Set de ids de miembros
        Set<Long> idMiembros = new HashSet<>();
        idMiembros.add(1L);
        comunidad.setIdMiembros(idMiembros);

        // Lista de comunidades de prueba
        List<Comunidad> listaComunidades = new ArrayList<>();
        listaComunidades.add(comunidad);

        // Simulación de retorno de repositorio
        when(comunidadRepository.findAll()).thenReturn(listaComunidades);

        // Método a testear
        List<Comunidad> comunidadesEncontradas = comunidadService.getComunidades();

        // Resultados esperados
        assertNotNull(comunidadesEncontradas);                  // Retorno no nulo
        assertEquals(listaComunidades, comunidadesEncontradas); // Retorno esperado
        assertTrue(comunidadesEncontradas.contains(comunidad)); // Contiene a la comunidad simualda dentro de la lista
    }


    // Retorna una comunidad filtrada por su id
    @Test
    public void getComunidadById(){

        // Comunidad simulada
        Long id = 1L;
        Comunidad comunidad = new Comunidad();
        comunidad.setIdComunidad(id);
        comunidad.setNombre("Comunidad de prueba");

        // Retorno simulado del repositorio
        when(comunidadRepository.findById(id)).thenReturn(Optional.of(comunidad));

        // Método a testear
        Comunidad comunidadEncontrada = comunidadService.getComunidadById(id);

        // Resultados esperados
        assertNotNull(comunidadEncontrada);
        assertEquals(id, comunidadEncontrada.getIdComunidad());
        assertEquals("Comunidad de prueba", comunidadEncontrada.getNombre());
    }


    // Retorna la lista de ids de usuarios que pertenecen a una comunidad
    @Test
    public void getMiembrosDeComunidad(){

        Long idComunidad = 1L;

        Comunidad comunidad = new Comunidad();
        comunidad.setIdComunidad(idComunidad);

        Set<Long> idMiembros = new HashSet<>();
        idMiembros.add(1L);
        idMiembros.add(2L);
        comunidad.setIdMiembros(idMiembros);

        when(comunidadRepository.findById(idComunidad)).thenReturn(Optional.of(comunidad));

        Set<Long> miembros = comunidadService.getMiembrosDeComunidad(idComunidad);

        assertNotNull(miembros);
        assertEquals(2, miembros.size());
        assertTrue(miembros.contains(1L));
        assertTrue(miembros.contains(2L));
    }


    // Crea una comunidad nueva
    @Test
    public void postComunidad(){

        Long id = 1L;
        Comunidad comunidad = new Comunidad();
        comunidad.setIdUsuarioCreador(id);
        comunidad.setNombre("Comunidad de prueba");
        comunidad.setIdMiembros(new HashSet<>());

        when(comunidadRepository.save(comunidad)).thenReturn(comunidad);

        Comunidad comunidadCreada = comunidadService.postComunidad(comunidad);

        assertNotNull(comunidadCreada);
        assertTrue(comunidadCreada.getIdMiembros().contains(id));

        verify(lClient).postIncrementarComunidad(1L, 1);
        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }


    // Agregar un miembro a una comunidad
    @Test
    public void agregarMiembroAComunidad(){

        Long idComunidad = 1L;
        Long idUsuario = 2L;

        Comunidad comunidad = new Comunidad();
        comunidad.setIdComunidad(idComunidad);
        comunidad.setIdUsuarioCreador(1L);

        Set<Long> idMiembros = new HashSet<>();
        idMiembros.add(1L);
        comunidad.setIdMiembros(idMiembros);

        when(comunidadRepository.findById(idComunidad)).thenReturn(Optional.of(comunidad));
        when(comunidadRepository.save(comunidad)).thenReturn(comunidad);

        Comunidad resultado = comunidadService.agregarMiembroAComunidad(idComunidad, idUsuario);

        assertNotNull(resultado);
        assertTrue(resultado.getIdMiembros().contains(idUsuario));

        verify(lClient).postIncrementarComunidad(idUsuario, 1);
        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }


    // Elimina a un miembro de una comunidad
    @Test
    public void eliminarUsuarioDeComunidad(){

        Long idComunidad = 1L;
        Long idUsuario = 2L;

        Comunidad comunidad = new Comunidad();
        comunidad.setIdComunidad(idComunidad);

        Set<Long> idMiembros = new HashSet<>();
        idMiembros.add(1L);
        idMiembros.add(idUsuario);
        comunidad.setIdMiembros(idMiembros);

        when(comunidadRepository.findById(idComunidad)).thenReturn(Optional.of(comunidad));
        when(comunidadRepository.save(comunidad)).thenReturn(comunidad);

        Comunidad resultado = comunidadService.eliminarUsuarioDeComunidad(idComunidad, idUsuario);

        assertNotNull(resultado);
        assertFalse(resultado.getIdMiembros().contains(idUsuario));

        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }


    // Agrega una lista de miembros a una comunidad
    @Test
    public void agregarMiembrosAComunidad(){

        Long idComunidad = 1L;

        Set<Long> idMiembros = new HashSet<>();
        idMiembros.add(2L);
        idMiembros.add(3L);

        Comunidad comunidad = new Comunidad();
        comunidad.setIdComunidad(idComunidad);
        comunidad.setIdUsuarioCreador(1L);

        Set<Long> miembrosIniciales = new HashSet<>();
        miembrosIniciales.add(1L);
        comunidad.setIdMiembros(miembrosIniciales);

        when(comunidadRepository.findById(idComunidad)).thenReturn(Optional.of(comunidad));
        when(comunidadRepository.save(comunidad)).thenReturn(comunidad);

        Comunidad resultado = comunidadService.agregarMiembrosAComunidad(idComunidad, idMiembros);

        assertNotNull(resultado);
        assertTrue(resultado.getIdMiembros().contains(2L));
        assertTrue(resultado.getIdMiembros().contains(3L));
        assertEquals(3, resultado.getIdMiembros().size());

        verify(lClient).postIncrementarComunidades(idMiembros);
        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }


    // Elimina una lista de miembros de una comunidad
    @Test
    public void eliminarMiembrosDeComunidad(){

        Long idComunidad = 1L;

        Set<Long> idMiembros = new HashSet<>();
        idMiembros.add(2L);
        idMiembros.add(3L);

        Comunidad comunidad = new Comunidad();
        comunidad.setIdComunidad(idComunidad);
        comunidad.setIdUsuarioCreador(1L);

        Set<Long> miembrosIniciales = new HashSet<>();
        miembrosIniciales.add(1L);
        miembrosIniciales.add(2L);
        miembrosIniciales.add(3L);
        comunidad.setIdMiembros(miembrosIniciales);

        when(comunidadRepository.findById(idComunidad)).thenReturn(Optional.of(comunidad));
        when(comunidadRepository.save(comunidad)).thenReturn(comunidad);

        Comunidad resultado = comunidadService.eliminarMiembrosDeComunidad(idComunidad, idMiembros);

        assertNotNull(resultado);
        assertTrue(resultado.getIdMiembros().contains(1L));
        assertFalse(resultado.getIdMiembros().contains(2L));
        assertFalse(resultado.getIdMiembros().contains(3L));
        assertEquals(1, resultado.getIdMiembros().size());

        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }


    // Elimina una comunidad por su id
    @Test
    public void deleteComunidadById(){

        Long id = 1L;

        Comunidad comunidad = new Comunidad();
        comunidad.setIdComunidad(id);
        comunidad.setNombre("Comunidad de prueba");

        when(comunidadRepository.findById(id)).thenReturn(Optional.of(comunidad));

        Comunidad resultado = comunidadService.deleteComunidadById(id);

        assertNotNull(resultado);
        verify(comunidadRepository).delete(comunidad);
    }
}
