package com.madoscientista.generador_ejercicios;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.madoscientista.generador_ejercicios.model.PlantillaEnunciado;
import com.madoscientista.generador_ejercicios.repository.PlantillaEnunciadoRepository;
import com.madoscientista.generador_ejercicios.service.PlantillaEnunciadoService;

@SpringBootTest
public class PlantillaEnunciadoServiceTest {

    @Autowired
    private PlantillaEnunciadoService plantillaEnunciadoService;

    @MockitoBean
    private PlantillaEnunciadoRepository plantillaEnunciadoRepository;

    // Retorna todas las plantillas de enunciado ordenadas
    @Test
    public void getPlantillas() {
        PlantillaEnunciado plantilla = new PlantillaEnunciado();
        plantilla.setIdPlantillaEnunciado(1L);
        plantilla.setEnunciado("Un auto viaja a {v} m/s durante {t} s.");
        plantilla.setResultadoPositivo(true);

        List<PlantillaEnunciado> lista = new ArrayList<>();
        lista.add(plantilla);

        when(plantillaEnunciadoRepository.findAllByOrderByIdPlantillaEnunciadoAsc()).thenReturn(lista);

        List<PlantillaEnunciado> resultado = plantillaEnunciadoService.getPlantillas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Un auto viaja a {v} m/s durante {t} s.", resultado.get(0).getEnunciado());
    }

    // Retorna plantillas filtradas por tema, contexto, incognita y resultado positivo
    @Test
    public void getPlantillaEnunciado() {
        PlantillaEnunciado plantilla = new PlantillaEnunciado();
        plantilla.setIdPlantillaEnunciado(1L);
        plantilla.setResultadoPositivo(true);

        List<PlantillaEnunciado> lista = new ArrayList<>();
        lista.add(plantilla);

        when(plantillaEnunciadoRepository
            .findByTema_nombreAndContexto_nombreAndIncognita_nombreAndResultadoPositivo(
                "MRU", "CARRETERA", "POSICION", true))
            .thenReturn(lista);

        List<PlantillaEnunciado> resultado = plantillaEnunciadoService
            .getPlantillaEnunciado("MRU", "CARRETERA", "POSICION", true);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    // Retorna una plantilla por su ID
    @Test
    public void getPlantillaById() {
        PlantillaEnunciado plantilla = new PlantillaEnunciado();
        plantilla.setIdPlantillaEnunciado(1L);
        plantilla.setEnunciado("Un auto viaja a {v} m/s.");
        plantilla.setResultadoPositivo(true);

        when(plantillaEnunciadoRepository.findByIdPlantillaEnunciado(1L)).thenReturn(plantilla);

        PlantillaEnunciado resultado = plantillaEnunciadoService.getPlantillaById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdPlantillaEnunciado());
        assertTrue(resultado.isResultadoPositivo());
    }

    // Retorna plantillas filtradas por nombre de tema
    @Test
    public void getPlantillasByTema() {
        PlantillaEnunciado plantilla = new PlantillaEnunciado();
        plantilla.setIdPlantillaEnunciado(1L);

        List<PlantillaEnunciado> lista = new ArrayList<>();
        lista.add(plantilla);

        when(plantillaEnunciadoRepository.findAllByTema_nombre("MRU")).thenReturn(lista);

        List<PlantillaEnunciado> resultado = plantillaEnunciadoService.getPlantillasByTema("MRU");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }
}
