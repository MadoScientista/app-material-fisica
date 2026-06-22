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
import com.madoscientista.generador_ejercicios.service.EjercicioFisicaService;
import com.madoscientista.generador_ejercicios.service.PlantillaEnunciadoService;
import com.madoscientista.generador_ejercicios.service.UnidadDeMedidaService;
import com.madoscientista.generador_ejercicios.service.VariableFisicaService;

@SpringBootTest
public class EjercicioFisicaServiceTest {

    @Autowired
    private EjercicioFisicaService ejercicioFisicaService;

    @MockitoBean
    private PlantillaEnunciadoService peService;

    @MockitoBean
    private UnidadDeMedidaService umService;

    @MockitoBean
    private VariableFisicaService vfService;

    // Retorna lista de plantillas delegando a PlantillaEnunciadoService
    @Test
    public void getPlantillas() {
        PlantillaEnunciado plantilla = new PlantillaEnunciado();
        plantilla.setIdPlantillaEnunciado(1L);

        List<PlantillaEnunciado> lista = new ArrayList<>();
        lista.add(plantilla);

        when(peService.getPlantillas()).thenReturn(lista);

        List<PlantillaEnunciado> resultado = ejercicioFisicaService.getPlantillas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

}
