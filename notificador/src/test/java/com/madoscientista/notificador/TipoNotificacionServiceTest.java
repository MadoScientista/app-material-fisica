package com.madoscientista.notificador;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.madoscientista.notificador.model.TipoNotificacion;
import com.madoscientista.notificador.repository.TipoNotificacionRepository;
import com.madoscientista.notificador.service.TipoNotificacionService;


@SpringBootTest
public class TipoNotificacionServiceTest {

    @Autowired
    private TipoNotificacionService tipoNotificacionService;

    @MockitoBean
    private TipoNotificacionRepository tipoNotificacionRepo;


    // Retorna un TipoNotificacion filtrado por su id
    @Test
    public void getTipoNotificacionById(){

        // Tipo de notificacion de prueba
        Long id = 1L;
        TipoNotificacion tipoNotificacion = new TipoNotificacion();
        tipoNotificacion.setIdTipoNotificacion(id);
        tipoNotificacion.setIdTipoEvento(3L);
        tipoNotificacion.setDescripcion("Notificacion de ejercicio");
        tipoNotificacion.setPlantillaMensaje("{usuarioOrigen} ha realizado un ejercicio para {usuarioDestino}");
        tipoNotificacion.setCanal("push");

        // Simulacion de retorno de repositorio
        when(tipoNotificacionRepo.findById(id)).thenReturn(Optional.of(tipoNotificacion));

        // Metodo a testear
        TipoNotificacion tipoNotificacionEncontrado = tipoNotificacionService.getTipoNotificacionById(id);

        // Resultados esperados
        assertNotNull(tipoNotificacionEncontrado);
        assertEquals(id, tipoNotificacionEncontrado.getIdTipoNotificacion());
    }


    // Retorna una lista de tipos de notificacion filtrados por id del tipo de evento
    @Test
    public void getTipoNotificacionByIdTipoEvento(){

        Long idTipoEvento = 3L;

        // Tipo de notificacion de prueba
        TipoNotificacion tipoNotificacion = new TipoNotificacion();
        tipoNotificacion.setIdTipoNotificacion(1L);
        tipoNotificacion.setIdTipoEvento(idTipoEvento);
        tipoNotificacion.setPlantillaMensaje("Plantilla de prueba");

        List<TipoNotificacion> listaTipoNotificaciones = new ArrayList<>();
        listaTipoNotificaciones.add(tipoNotificacion);

        // Simulacion de retorno de repositorio
        when(tipoNotificacionRepo.findAllByIdTipoEvento(idTipoEvento)).thenReturn(listaTipoNotificaciones);

        // Metodo a testear
        List<TipoNotificacion> tiposEncontrados = tipoNotificacionService.getTipoNotificacionByIdTipoEvento(idTipoEvento);

        // Resultados esperados
        assertNotNull(tiposEncontrados);
        assertEquals(1, tiposEncontrados.size());
        assertEquals(listaTipoNotificaciones, tiposEncontrados);
    }
}
