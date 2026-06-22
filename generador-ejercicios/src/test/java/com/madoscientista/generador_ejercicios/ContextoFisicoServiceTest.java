package com.madoscientista.generador_ejercicios;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.madoscientista.generador_ejercicios.model.ContextoFisico;
import com.madoscientista.generador_ejercicios.repository.ContextoFisicoRepository;
import com.madoscientista.generador_ejercicios.service.ContextoFisicoService;

@SpringBootTest
public class ContextoFisicoServiceTest {

    @Autowired
    private ContextoFisicoService contextoFisicoService;

    @MockitoBean
    private ContextoFisicoRepository contextoFisicoRepository;

    // Retorna todos los contextos fisicos ordenados
    @Test
    public void getContextos() {
        ContextoFisico contexto = new ContextoFisico();
        contexto.setIdContextoFisico(1);
        contexto.setNombre("PERSONA");

        List<ContextoFisico> lista = new ArrayList<>();
        lista.add(contexto);

        when(contextoFisicoRepository.findAllByOrderByIdContextoFisicoAsc()).thenReturn(lista);

        List<ContextoFisico> resultado = contextoFisicoService.getContextos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("PERSONA", resultado.get(0).getNombre());
    }

    // Retorna un contexto fisico por su ID
    @Test
    public void getContextoFisicoById() {
        ContextoFisico contexto = new ContextoFisico();
        contexto.setIdContextoFisico(1);
        contexto.setNombre("PERSONA");
        contexto.setVMin(2);
        contexto.setVMax(7);
        contexto.setXMin(10);
        contexto.setXMax(1000);

        when(contextoFisicoRepository.findByIdContextoFisico(1)).thenReturn(contexto);

        ContextoFisico resultado = contextoFisicoService.getContextoFisicoById(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdContextoFisico());
        assertEquals("PERSONA", resultado.getNombre());
        assertEquals(2, resultado.getVMin());
        assertEquals(7, resultado.getVMax());
    }
}
