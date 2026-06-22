package com.madoscientista.suscripciones;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.madoscientista.suscripciones.model.TipoSuscripcion;
import com.madoscientista.suscripciones.repository.TipoSuscripcionRepository;
import com.madoscientista.suscripciones.service.TipoSuscripcionService;


@SpringBootTest
public class TipoSuscripcionServiceTest {

    @Autowired
    private TipoSuscripcionService tipoSuscripcionService;

    @MockitoBean
    private TipoSuscripcionRepository repo;


    // Retorna un TipoSuscripcion filtrado por su id
    @Test
    public void getById(){

        // Tipo de suscripcion de prueba
        Long id = 1L;
        TipoSuscripcion tipoSuscripcion = new TipoSuscripcion();
        tipoSuscripcion.setIdTipoSuscripcion(id);
        tipoSuscripcion.setNombre("PREMIUM");
        tipoSuscripcion.setNMaxEjercicios(100L);
        tipoSuscripcion.setPrecioPorMes(10L);

        // Simulacion de retorno de repositorio
        when(repo.findById(id)).thenReturn(Optional.of(tipoSuscripcion));

        // Metodo a testear
        TipoSuscripcion tipoSuscripcionEncontrado = tipoSuscripcionService.getById(id);

        // Resultados esperados
        assertNotNull(tipoSuscripcionEncontrado);
        assertEquals(id, tipoSuscripcionEncontrado.getIdTipoSuscripcion());
    }


    // Retorna un TipoSuscripcion filtrado por su nombre
    @Test
    public void getByNombre(){

        // Tipo de suscripcion de prueba
        String nombre = "PREMIUM";
        TipoSuscripcion tipoSuscripcion = new TipoSuscripcion();
        tipoSuscripcion.setIdTipoSuscripcion(1L);
        tipoSuscripcion.setNombre(nombre);
        tipoSuscripcion.setNMaxEjercicios(100L);
        tipoSuscripcion.setPrecioPorMes(10L);

        // Simulacion de retorno de repositorio
        when(repo.findByNombre(nombre)).thenReturn(Optional.of(tipoSuscripcion));

        // Metodo a testear
        TipoSuscripcion tipoSuscripcionEncontrado = tipoSuscripcionService.getByNombre(nombre);

        // Resultados esperados
        assertNotNull(tipoSuscripcionEncontrado);
        assertEquals(nombre, tipoSuscripcionEncontrado.getNombre());
    }
}
