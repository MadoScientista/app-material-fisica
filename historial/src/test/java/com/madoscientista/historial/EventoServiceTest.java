package com.madoscientista.historial;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.madoscientista.historial.client.NotificacionClient;
import com.madoscientista.historial.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.historial.model.Evento;
import com.madoscientista.historial.model.TipoEvento;
import com.madoscientista.historial.repository.EventoRepository;
import com.madoscientista.historial.service.EventoService;


@SpringBootTest
public class EventoServiceTest {

    @Autowired
    private EventoService eventoService;

    @MockitoBean
    private EventoRepository eventoRepository;

    @MockitoBean
    private NotificacionClient nClient;


    // Retorna la lista de eventos disponibles en BD
    @Test
    public void getEventos(){

        // Tipo de evento de prueba
        TipoEvento tipoEvento = new TipoEvento();
        tipoEvento.setIdTipoEvento(1L);
        tipoEvento.setNombre("COMUNIDAD_CREADA");

        // Evento de prueba
        Long id = 1L;
        Evento evento = new Evento();
        evento.setIdEvento(id);
        evento.setIdUsuarioOrigen(1L);
        evento.setTipoEvento(tipoEvento);

        // Lista de eventos de prueba
        List<Evento> listaEventos = new ArrayList<>();
        listaEventos.add(evento);

        // Simulación de retorno de repositorio
        when(eventoRepository.findAll()).thenReturn(listaEventos);

        // Método a testear
        List<Evento> eventosEncontrados = eventoService.getEventos();

        // Resultados esperados
        assertNotNull(eventosEncontrados);
        assertEquals(listaEventos, eventosEncontrados);
    }


    // Retorna una lista de eventos filtrados por el ID de un usuario
    @Test
    public void getEventosByIdUsuarioOrigen(){

        Long idUsuario = 1L;

        // Lista de eventos de prueba
        List<Evento> listaEventos = new ArrayList<>();

        // Simulación de retorno de repositorio
        when(eventoRepository.findAllByIdUsuarioOrigen(idUsuario)).thenReturn(listaEventos);

        // Método a testear
        List<Evento> eventosEncontrados = eventoService.getEventosByIdUsuarioOrigen(idUsuario);

        // Resultados esperados
        assertNotNull(eventosEncontrados);
        assertEquals(listaEventos, eventosEncontrados);
    }


    // Crea un nuevo evento y envía notificación si el tipo de evento lo requiere
    @Test
    public void postEvento(){

        // Tipo de evento de prueba con notificación activa
        TipoEvento tipoEvento = new TipoEvento();
        tipoEvento.setIdTipoEvento(1L);
        tipoEvento.setNombre("COMUNIDAD_CREADA");
        tipoEvento.setNotificacionActiva(true);

        // Lista de IDs de destino
        List<Long> idDestino = new ArrayList<>();
        idDestino.add(2L);
        idDestino.add(3L);

        // Evento de prueba
        Evento evento = new Evento();
        evento.setIdUsuarioOrigen(1L);
        evento.setTipoEvento(tipoEvento);

        // Simulación de retorno de repositorio
        when(eventoRepository.save(evento)).thenReturn(evento);

        // Método a testear
        Evento eventoCreado = eventoService.postEvento(evento, idDestino);

        // Resultados esperados
        assertNotNull(eventoCreado);
        assertEquals(1L, eventoCreado.getIdUsuarioOrigen());

        // Verificación de que se ejecutan los métodos internos
        verify(nClient).postNotificacion(any(RequestEventoDTO.class));
    }
}
