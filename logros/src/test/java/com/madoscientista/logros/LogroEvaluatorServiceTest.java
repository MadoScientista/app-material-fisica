package com.madoscientista.logros;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.madoscientista.logros.client.HistorialClient;
import com.madoscientista.logros.model.Logro;
import com.madoscientista.logros.model.Recuento;
import com.madoscientista.logros.model.TipoLogro;
import com.madoscientista.logros.repository.LogroRepository;
import com.madoscientista.logros.repository.TipoLogroRepository;
import com.madoscientista.logros.service.LogroEvaluatorService;


@SpringBootTest
public class LogroEvaluatorServiceTest {

    @Autowired
    private LogroEvaluatorService logroEvaluatorService;

    @MockitoBean
    private TipoLogroRepository tipoLogroRepository;

    @MockitoBean
    private LogroRepository logroRepository;

    @MockitoBean
    private HistorialClient hClient;


    // Evalúa si un recuento cumple las condiciones para completar un logro
    @Test
    public void evaluar(){

        Long idUsuario = 1L;

        // Tipo de logro de prueba con condición nEjerciciosCreados >= 5
        TipoLogro tipoLogro = new TipoLogro();
        tipoLogro.setIdTipoLogro(1L);
        tipoLogro.setNombre("CINCO_EJERCICIOS");
        tipoLogro.setCriterio("nEjerciciosCreados");
        tipoLogro.setOperador(">=");
        tipoLogro.setUmbral(5L);

        // Lista de tipos de logro
        List<TipoLogro> tipos = new ArrayList<>();
        tipos.add(tipoLogro);

        // Recuento de prueba con 10 ejercicios creados (cumple >= 5)
        Recuento recuento = new Recuento();
        recuento.setIdUsuario(idUsuario);
        recuento.setNEjerciciosCreados(10L);

        // Logro existente no completado
        Logro logro = new Logro();
        logro.setIdLogro(1L);
        logro.setIdUsuario(idUsuario);
        logro.setTipoLogro(tipoLogro);
        logro.setCompletado(false);

        // Simulación de retorno de repositorio
        when(tipoLogroRepository.findAll()).thenReturn(tipos);
        when(logroRepository.findByIdUsuarioAndTipoLogroNombre(idUsuario, tipoLogro.getNombre())).thenReturn(logro);
        when(logroRepository.save(logro)).thenReturn(logro);

        // Método a testear
        logroEvaluatorService.evaluar(recuento);

        // Resultados esperados
        assertTrue(logro.isCompletado());
        verify(logroRepository).save(logro);
        verify(hClient).postEvento(any());
    }


    // Evalúa si varios recuentos cumplen las condiciones para completar logros
    @Test
    public void evaluarVariosUsuarios(){

        Long idUsuario1 = 1L;
        Long idUsuario2 = 2L;

        // Tipo de logro de prueba con condición nEjerciciosCreados >= 5
        TipoLogro tipoLogro = new TipoLogro();
        tipoLogro.setIdTipoLogro(1L);
        tipoLogro.setNombre("CINCO_EJERCICIOS");
        tipoLogro.setCriterio("nEjerciciosCreados");
        tipoLogro.setOperador(">=");
        tipoLogro.setUmbral(5L);

        // Lista de tipos de logro
        List<TipoLogro> tipos = new ArrayList<>();
        tipos.add(tipoLogro);

        // Recuento de prueba para usuario 1
        Recuento recuento1 = new Recuento();
        recuento1.setIdUsuario(idUsuario1);
        recuento1.setNEjerciciosCreados(10L);

        // Recuento de prueba para usuario 2
        Recuento recuento2 = new Recuento();
        recuento2.setIdUsuario(idUsuario2);
        recuento2.setNEjerciciosCreados(7L);

        // Lista de recuentos
        List<Recuento> recuentos = new ArrayList<>();
        recuentos.add(recuento1);
        recuentos.add(recuento2);

        // Logro existente no completado para usuario 1
        Logro logro1 = new Logro();
        logro1.setIdLogro(1L);
        logro1.setIdUsuario(idUsuario1);
        logro1.setTipoLogro(tipoLogro);
        logro1.setCompletado(false);

        // Logro existente no completado para usuario 2
        Logro logro2 = new Logro();
        logro2.setIdLogro(2L);
        logro2.setIdUsuario(idUsuario2);
        logro2.setTipoLogro(tipoLogro);
        logro2.setCompletado(false);

        // Lista de logros existentes
        List<Logro> logrosExistentes = new ArrayList<>();
        logrosExistentes.add(logro1);
        logrosExistentes.add(logro2);

        // Simulación de retorno de repositorio
        when(tipoLogroRepository.findAll()).thenReturn(tipos);
        when(logroRepository.findAllByIdUsuarioIn(anyList())).thenReturn(logrosExistentes);
        when(logroRepository.saveAll(anyList())).thenReturn(logrosExistentes);

        // Método a testear
        logroEvaluatorService.evaluarVariosUsuarios(recuentos);

        // Resultados esperados
        assertTrue(logro1.isCompletado());
        assertTrue(logro2.isCompletado());
        verify(logroRepository).saveAll(anyList());
        verify(hClient).postEventos(anyList());
    }
}
