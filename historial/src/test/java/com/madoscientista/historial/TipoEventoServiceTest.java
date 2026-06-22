package com.madoscientista.historial;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.madoscientista.historial.model.TipoEvento;
import com.madoscientista.historial.repository.TipoEventoRepository;
import com.madoscientista.historial.service.TipoEventoService;


@SpringBootTest
public class TipoEventoServiceTest {

    @Autowired
    private TipoEventoService tipoEventoService;

    @MockitoBean
    private TipoEventoRepository tipoEventoRepository;


    // Retorna un TipoEvento filtrado por su id
    @Test
    public void getById(){

        // Tipo de evento de prueba
        Long id = 1L;
        TipoEvento tipoEvento = new TipoEvento();
        tipoEvento.setIdTipoEvento(id);
        tipoEvento.setNombre("COMUNIDAD_CREADA");

        // Simulación de retorno de repositorio
        when(tipoEventoRepository.findById(id)).thenReturn(Optional.of(tipoEvento));

        // Método a testear
        TipoEvento tipoEventoEncontrado = tipoEventoService.getById(id);

        // Resultados esperados
        assertNotNull(tipoEventoEncontrado);
        assertEquals(id, tipoEventoEncontrado.getIdTipoEvento());
    }
}
