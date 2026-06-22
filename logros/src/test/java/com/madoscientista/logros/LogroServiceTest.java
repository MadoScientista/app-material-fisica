package com.madoscientista.logros;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.madoscientista.logros.model.Logro;
import com.madoscientista.logros.model.TipoLogro;
import com.madoscientista.logros.repository.LogroRepository;
import com.madoscientista.logros.repository.TipoLogroRepository;
import com.madoscientista.logros.service.LogroService;


@SpringBootTest
public class LogroServiceTest {

    @Autowired
    private LogroService logroService;

    @MockitoBean
    private LogroRepository logroRepository;

    @MockitoBean
    private TipoLogroRepository tipoLogroRepository;


    // Obtiene un logro según su ID
    @Test
    public void getLogroById(){

        // Logro de prueba
        Long id = 1L;
        Logro logro = new Logro();
        logro.setIdLogro(id);
        logro.setIdUsuario(1L);

        // Simulación de retorno de repositorio
        when(logroRepository.findById(id)).thenReturn(Optional.of(logro));

        // Método a testear
        Logro logroEncontrado = logroService.getLogroById(id);

        // Resultados esperados
        assertNotNull(logroEncontrado);
        assertEquals(id, logroEncontrado.getIdLogro());
    }


    // Obtiene todos los logros de un usuario en particular
    @Test
    public void getLogrosByIdUsuario(){

        Long idUsuario = 1L;

        // Logro de prueba
        Logro logro = new Logro();
        logro.setIdLogro(1L);
        logro.setIdUsuario(idUsuario);

        // Lista de logros de prueba
        List<Logro> listaLogros = new ArrayList<>();
        listaLogros.add(logro);

        // Simulación de retorno de repositorio
        when(logroRepository.findAllByIdUsuario(idUsuario)).thenReturn(listaLogros);

        // Método a testear
        List<Logro> logrosEncontrados = logroService.getLogrosByIdUsuario(idUsuario);

        // Resultados esperados
        assertNotNull(logrosEncontrados);
        assertEquals(listaLogros, logrosEncontrados);
    }


    // Retorna la lista de logros disponibles en BD
    @Test
    public void getLogros(){

        // Logro de prueba
        Logro logro = new Logro();
        logro.setIdLogro(1L);
        logro.setIdUsuario(1L);

        // Lista de logros de prueba
        List<Logro> listaLogros = new ArrayList<>();
        listaLogros.add(logro);

        // Simulación de retorno de repositorio
        when(logroRepository.findAll()).thenReturn(listaLogros);

        // Método a testear
        List<Logro> logrosEncontrados = logroService.getLogros();

        // Resultados esperados
        assertNotNull(logrosEncontrados);
        assertEquals(listaLogros, logrosEncontrados);
    }


    // Crea un nuevo logro
    @Test
    public void postLogro(){

        // Logro de prueba
        Logro logro = new Logro();
        logro.setIdUsuario(1L);
        logro.setCompletado(false);

        // Simulación de retorno de repositorio
        when(logroRepository.save(logro)).thenReturn(logro);

        // Método a testear
        Logro logroCreado = logroService.postLogro(logro);

        // Resultados esperados
        assertNotNull(logroCreado);
        assertEquals(1L, logroCreado.getIdUsuario());
    }


    // Crea los logros faltantes para un usuario según los tipos de logro disponibles
    @Test
    public void postSincronizarLogrosUsuario(){

        Long idUsuario = 1L;

        // Tipo de logro de prueba 1 (existente)
        TipoLogro tipoLogro1 = new TipoLogro();
        tipoLogro1.setIdTipoLogro(1L);
        tipoLogro1.setNombre("PRIMER_EJERCICIO");

        // Tipo de logro de prueba 2 (existente)
        TipoLogro tipoLogro2 = new TipoLogro();
        tipoLogro2.setIdTipoLogro(2L);
        tipoLogro2.setNombre("DIEZ_EJERCICIOS");

        // Tipo de logro de prueba 3 (nuevo)
        TipoLogro tipoLogro3 = new TipoLogro();
        tipoLogro3.setIdTipoLogro(3L);
        tipoLogro3.setNombre("CINCUENTA_EJERCICIOS");

        // Lista de todos los tipos de logro
        List<TipoLogro> todosTipos = new ArrayList<>();
        todosTipos.add(tipoLogro1);
        todosTipos.add(tipoLogro2);
        todosTipos.add(tipoLogro3);

        // Logros existentes para el usuario (tipos 1 y 2 ya están)
        Logro logroExistente1 = new Logro();
        logroExistente1.setIdLogro(1L);
        logroExistente1.setIdUsuario(idUsuario);
        logroExistente1.setTipoLogro(tipoLogro1);

        Logro logroExistente2 = new Logro();
        logroExistente2.setIdLogro(2L);
        logroExistente2.setIdUsuario(idUsuario);
        logroExistente2.setTipoLogro(tipoLogro2);

        List<Logro> logrosExistentes = new ArrayList<>();
        logrosExistentes.add(logroExistente1);
        logrosExistentes.add(logroExistente2);

        // Nuevo logro que se creará (tipo 3)
        Logro nuevoLogro = new Logro();
        nuevoLogro.setIdLogro(3L);
        nuevoLogro.setIdUsuario(idUsuario);
        nuevoLogro.setTipoLogro(tipoLogro3);
        nuevoLogro.setCompletado(false);

        List<Logro> nuevosLogros = new ArrayList<>();
        nuevosLogros.add(nuevoLogro);

        // Simulación de retorno de repositorio
        when(tipoLogroRepository.findAll()).thenReturn(todosTipos);
        when(logroRepository.findAllByIdUsuario(idUsuario)).thenReturn(logrosExistentes);
        when(logroRepository.saveAll(anyList())).thenReturn(nuevosLogros);

        // Método a testear
        List<Logro> resultado = logroService.postSincronizarLogrosUsuario(idUsuario);

        // Resultados esperados
        assertNotNull(resultado);
        assertEquals(3, resultado.size());
    }


    // Actualiza el estado de logro a completado
    @Test
    public void putLogroCompletado(){

        Long idUsuario = 1L;
        String nombreTipoLogro = "PRIMER_EJERCICIO";

        // Logro de prueba (no completado)
        Logro logro = new Logro();
        logro.setIdLogro(1L);
        logro.setIdUsuario(idUsuario);
        logro.setCompletado(false);

        // Simulación de retorno de repositorio
        when(logroRepository.findByIdUsuarioAndTipoLogroNombre(idUsuario, nombreTipoLogro)).thenReturn(logro);
        when(logroRepository.save(logro)).thenReturn(logro);

        // Método a testear
        Logro logroActualizado = logroService.putLogroCompletado(idUsuario, nombreTipoLogro);

        // Resultados esperados
        assertNotNull(logroActualizado);
        assertTrue(logroActualizado.isCompletado());
    }
}
