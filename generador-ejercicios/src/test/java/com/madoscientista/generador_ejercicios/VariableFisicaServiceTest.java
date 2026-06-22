package com.madoscientista.generador_ejercicios;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.madoscientista.generador_ejercicios.model.VariableFisica;
import com.madoscientista.generador_ejercicios.repository.VariableFisicaRepository;
import com.madoscientista.generador_ejercicios.service.VariableFisicaService;

@SpringBootTest
public class VariableFisicaServiceTest {

    @Autowired
    private VariableFisicaService variableFisicaService;

    @MockitoBean
    private VariableFisicaRepository variableFisicaRepository;

    // Retorna una variable fisica por su simbolo
    @Test
    public void getBySimbolo() {
        VariableFisica variable = new VariableFisica();
        variable.setIdVariableFisica(1);
        variable.setSimbolo("v");
        variable.setNombre("Velocidad");

        when(variableFisicaRepository.findBySimbolo("v")).thenReturn(variable);

        VariableFisica resultado = variableFisicaService.getBySimbolo("v");

        assertNotNull(resultado);
        assertEquals("v", resultado.getSimbolo());
        assertEquals("Velocidad", resultado.getNombre());
    }
}
