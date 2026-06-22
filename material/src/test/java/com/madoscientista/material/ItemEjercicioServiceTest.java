package com.madoscientista.material;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.madoscientista.material.client.HistorialClient;
import com.madoscientista.material.client.LogrosClient;
import com.madoscientista.material.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.material.dto.EventoDTO.ResponseEventoDTO;
import com.madoscientista.material.dto.recuentoDTO.ResponseRecuentoDTO;
import com.madoscientista.material.model.ItemEjercicio;
import com.madoscientista.material.repository.ItemEjercicioRepository;
import com.madoscientista.material.service.ItemEjercicioService;


@SpringBootTest
public class ItemEjercicioServiceTest {

    @Autowired
    private ItemEjercicioService itemEjercicioService;

    @MockitoBean
    private ItemEjercicioRepository ieRepo;

    @MockitoBean
    private HistorialClient hClient;

    @MockitoBean
    private LogrosClient lClient;


    // Retorna todos los items disponibles en la plataforma
    @Test
    public void getItemEjercicios(){

        // Item de prueba
        ItemEjercicio item = new ItemEjercicio();
        item.setIdItemEjercicio(1L);
        item.setIdUsuarioCreador(1L);
        item.setTextoEjercicios("Enunciado de prueba");

        // Lista de items de prueba
        List<ItemEjercicio> listaItems = new ArrayList<>();
        listaItems.add(item);

        // Simulacion de retorno de repositorio
        when(ieRepo.findAll()).thenReturn(listaItems);

        // Metodo a testear
        List<ItemEjercicio> itemsEncontrados = itemEjercicioService.getItemEjercicios();

        // Resultados esperados
        assertNotNull(itemsEncontrados);
        assertEquals(listaItems, itemsEncontrados);
    }


    // Retorna un item por su id
    @Test
    public void getItemEjercicioById(){

        // Item de prueba
        Long id = 1L;
        ItemEjercicio item = new ItemEjercicio();
        item.setIdItemEjercicio(id);
        item.setIdUsuarioCreador(1L);
        item.setTextoEjercicios("Enunciado de prueba");

        // Simulacion de retorno de repositorio
        when(ieRepo.findById(id)).thenReturn(Optional.of(item));

        // Metodo a testear
        ItemEjercicio itemEncontrado = itemEjercicioService.getItemEjercicioById(id);

        // Resultados esperados
        assertNotNull(itemEncontrado);
        assertEquals(id, itemEncontrado.getIdItemEjercicio());
    }


    // Retorna los items generados por un mismo usuario
    @Test
    public void getItemEjercicioByIdUsuarioCreador(){

        Long idUsuario = 1L;

        // Item de prueba
        ItemEjercicio item = new ItemEjercicio();
        item.setIdItemEjercicio(1L);
        item.setIdUsuarioCreador(idUsuario);
        item.setTextoEjercicios("Enunciado de prueba");

        // Lista de items de prueba
        List<ItemEjercicio> listaItems = new ArrayList<>();
        listaItems.add(item);

        // Simulacion de retorno de repositorio
        when(ieRepo.findAllByIdUsuarioCreador(idUsuario)).thenReturn(listaItems);

        // Metodo a testear
        List<ItemEjercicio> itemsEncontrados = itemEjercicioService.getItemEjercicioByIdUsuarioCreador(idUsuario);

        // Resultados esperados
        assertNotNull(itemsEncontrados);
        assertEquals(listaItems, itemsEncontrados);
    }


    // Retorna una lista de items filtrados por id
    @Test
    public void getItemEjercicioByIdIn(){

        // Items de prueba
        ItemEjercicio item1 = new ItemEjercicio();
        item1.setIdItemEjercicio(1L);
        item1.setIdUsuarioCreador(1L);

        ItemEjercicio item2 = new ItemEjercicio();
        item2.setIdItemEjercicio(2L);
        item2.setIdUsuarioCreador(1L);

        Set<Long> ids = new HashSet<>();
        ids.add(1L);
        ids.add(2L);

        List<ItemEjercicio> listaItems = new ArrayList<>();
        listaItems.add(item1);
        listaItems.add(item2);

        // Simulacion de retorno de repositorio
        when(ieRepo.findByIdItemEjercicioIn(ids)).thenReturn(listaItems);

        // Metodo a testear
        List<ItemEjercicio> itemsEncontrados = itemEjercicioService.getItemEjercicioByIdIn(ids);

        // Resultados esperados
        assertNotNull(itemsEncontrados);
        assertEquals(2, itemsEncontrados.size());
    }


    // Guarda un nuevo item
    @Test
    public void postItemEjercicio(){

        // Item de prueba
        ItemEjercicio item = new ItemEjercicio();
        item.setIdItemEjercicio(1L);
        item.setIdUsuarioCreador(1L);
        item.setTextoEjercicios("Enunciado de prueba");

        // Simulacion de retorno de repositorio
        when(ieRepo.save(item)).thenReturn(item);

        // Simulacion de respuesta de clientes
        when(hClient.postEvento(any(RequestEventoDTO.class))).thenReturn(new ResponseEventoDTO());
        when(lClient.postIncrementarItemCreado(anyLong(), anyInt())).thenReturn(ResponseEntity.ok(new ResponseRecuentoDTO()));

        // Metodo a testear
        ItemEjercicio itemCreado = itemEjercicioService.postItemEjercicio(item);

        // Resultados esperados
        assertNotNull(itemCreado);
        assertEquals(1L, itemCreado.getIdUsuarioCreador());

        // Verificacion de que se ejecutan los metodos internos
        verify(hClient).postEvento(any(RequestEventoDTO.class));
        verify(lClient).postIncrementarItemCreado(anyLong(), anyInt());
    }


    // Guarda una lista de items
    @Test
    public void postListaItemEjercicio(){

        // Items de prueba
        ItemEjercicio item1 = new ItemEjercicio();
        item1.setIdItemEjercicio(1L);
        item1.setIdUsuarioCreador(1L);
        item1.setTextoEjercicios("Enunciado 1");

        ItemEjercicio item2 = new ItemEjercicio();
        item2.setIdItemEjercicio(2L);
        item2.setIdUsuarioCreador(1L);
        item2.setTextoEjercicios("Enunciado 2");

        List<ItemEjercicio> listaItems = new ArrayList<>();
        listaItems.add(item1);
        listaItems.add(item2);

        // Simulacion de retorno de repositorio
        when(ieRepo.saveAll(listaItems)).thenReturn(listaItems);

        // Simulacion de respuesta de clientes
        when(hClient.postEvento(any(RequestEventoDTO.class))).thenReturn(new ResponseEventoDTO());
        when(lClient.postIncrementarItemCreado(anyLong(), anyInt())).thenReturn(ResponseEntity.ok(new ResponseRecuentoDTO()));

        // Metodo a testear
        List<ItemEjercicio> itemsCreados = itemEjercicioService.postListaItemEjercicio(listaItems);

        // Resultados esperados
        assertNotNull(itemsCreados);
        assertEquals(2, itemsCreados.size());

        // Verificacion de que se ejecutan los metodos internos
        verify(hClient).postEvento(any(RequestEventoDTO.class));
        verify(lClient).postIncrementarItemCreado(anyLong(), anyInt());
    }


    // Elimina un item por su id
    @Test
    public void deleteItemEjercicioById(){

        // Item de prueba
        Long id = 1L;
        ItemEjercicio item = new ItemEjercicio();
        item.setIdItemEjercicio(id);
        item.setIdUsuarioCreador(1L);
        item.setTextoEjercicios("Enunciado de prueba");

        // Simulacion de retorno de repositorio
        when(ieRepo.findById(id)).thenReturn(Optional.of(item));
        doNothing().when(ieRepo).delete(item);

        // Simulacion de respuesta de cliente
        when(hClient.postEvento(any(RequestEventoDTO.class))).thenReturn(new ResponseEventoDTO());

        // Metodo a testear
        ItemEjercicio itemEliminado = itemEjercicioService.deleteItemEjercicioById(id);

        // Resultados esperados
        assertNotNull(itemEliminado);
        assertEquals(id, itemEliminado.getIdItemEjercicio());

        // Verificacion de que se ejecutan los metodos internos
        verify(ieRepo).delete(item);
        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }


    // Actualiza los datos de un item
    @Test
    public void putItemEjercicio(){

        // Item actual de prueba
        Long id = 1L;
        ItemEjercicio itemActual = new ItemEjercicio();
        itemActual.setIdItemEjercicio(id);
        itemActual.setIdUsuarioCreador(1L);
        itemActual.setTitulo("Titulo original");
        itemActual.setDescripcion("Descripcion original");
        itemActual.setTextoEjercicios("Enunciado original");

        // Item con datos actualizados
        ItemEjercicio itemActualizado = new ItemEjercicio();
        itemActualizado.setTitulo("Titulo actualizado");
        itemActualizado.setDescripcion("Descripcion actualizada");
        itemActualizado.setTextoEjercicios("Enunciado actualizado");

        ItemEjercicio itemGuardado = new ItemEjercicio();
        itemGuardado.setIdItemEjercicio(id);
        itemGuardado.setIdUsuarioCreador(1L);
        itemGuardado.setTitulo("Titulo actualizado");
        itemGuardado.setDescripcion("Descripcion actualizada");
        itemGuardado.setTextoEjercicios("Enunciado actualizado");

        // Simulacion de retorno de repositorio
        when(ieRepo.findById(id)).thenReturn(Optional.of(itemActual));
        when(ieRepo.save(any(ItemEjercicio.class))).thenReturn(itemGuardado);

        // Simulacion de respuesta de cliente
        when(hClient.postEvento(any(RequestEventoDTO.class))).thenReturn(new ResponseEventoDTO());

        // Metodo a testear
        ItemEjercicio itemResultado = itemEjercicioService.putItemEjercicio(id, itemActualizado);

        // Resultados esperados
        assertNotNull(itemResultado);
        assertEquals("Titulo actualizado", itemResultado.getTitulo());
        assertEquals("Descripcion actualizada", itemResultado.getDescripcion());
        assertEquals("Enunciado actualizado", itemResultado.getTextoEjercicios());

        // Verificacion de que se ejecutan los metodos internos
        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }
}
