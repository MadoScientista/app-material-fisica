package com.madoscientista.generador_ejercicios;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.madoscientista.generador_ejercicios.model.MagnitudFisica;
import com.madoscientista.generador_ejercicios.model.UnidadDeMedida;
import com.madoscientista.generador_ejercicios.repository.UnidadDeMedidaRepository;
import com.madoscientista.generador_ejercicios.service.UnidadDeMedidaService;

@SpringBootTest
public class UnidadDeMedidaServiceTest {

    @Autowired
    private UnidadDeMedidaService unidadDeMedidaService;

    @MockitoBean
    private UnidadDeMedidaRepository unidadDeMedidaRepository;

    // Retorna una unidad de medida por su simbolo
    @Test
    public void getBySimbolo() {
        UnidadDeMedida unidad = new UnidadDeMedida();
        unidad.setIdUnidadDeMedida(1);
        unidad.setSimbolo("m");
        unidad.setNombre("metro");
        unidad.setEsSI(true);
        unidad.setEsBaseSI(true);

        when(unidadDeMedidaRepository.findBySimbolo("m")).thenReturn(unidad);

        UnidadDeMedida resultado = unidadDeMedidaService.getBySimbolo("m");

        assertNotNull(resultado);
        assertEquals("m", resultado.getSimbolo());
        assertEquals("metro", resultado.getNombre());
    }

    // Retorna la unidad base SI de una magnitud fisica
    @Test
    public void getUnidadBaseSI_MagnitudFisica() {
        MagnitudFisica magnitud = new MagnitudFisica();
        magnitud.setIdMagnitudFisica(1);
        magnitud.setNombre("Longitud");
        magnitud.setSimbolo("L");

        UnidadDeMedida unidad = new UnidadDeMedida();
        unidad.setIdUnidadDeMedida(1);
        unidad.setSimbolo("m");
        unidad.setEsBaseSI(true);

        when(unidadDeMedidaRepository.findByMagnitudFisicaAndEsBaseSITrue(magnitud)).thenReturn(unidad);

        UnidadDeMedida resultado = unidadDeMedidaService.getUnidadBaseSI(magnitud);

        assertNotNull(resultado);
        assertEquals("m", resultado.getSimbolo());
    }

    // Retorna la unidad base SI por nombre de magnitud fisica
    @Test
    public void getUnidadBaseSI_NombreMagnitud() {
        UnidadDeMedida unidad = new UnidadDeMedida();
        unidad.setIdUnidadDeMedida(1);
        unidad.setSimbolo("m");
        unidad.setEsBaseSI(true);

        when(unidadDeMedidaRepository.findByMagnitudFisicaNombreAndEsBaseSITrue("Longitud")).thenReturn(unidad);

        UnidadDeMedida resultado = unidadDeMedidaService.getUnidadBaseSI("Longitud");

        assertNotNull(resultado);
        assertEquals("m", resultado.getSimbolo());
    }
}
