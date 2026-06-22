package com.madoscientista.material;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.madoscientista.material.model.Material;
import com.madoscientista.material.repository.MaterialRepository;
import com.madoscientista.material.service.MaterialService;


@SpringBootTest
public class MaterialServiceTest {

    @Autowired
    private MaterialService materialService;

    @MockitoBean
    private MaterialRepository mRepo;

    @MockitoBean
    private HistorialClient hClient;

    @MockitoBean
    private LogrosClient lClient;


    // Retorna todos los materiales de la plataforma
    @Test
    public void getMateriales(){

        // Material de prueba
        Material material = new Material();
        material.setIdMaterial(1L);
        material.setIdUsuarioCreador(1L);

        // Lista de materiales de prueba
        List<Material> listaMateriales = new ArrayList<>();
        listaMateriales.add(material);

        // Simulacion de retorno de repositorio
        when(mRepo.findAll()).thenReturn(listaMateriales);

        // Metodo a testear
        List<Material> materialesEncontrados = materialService.getMateriales();

        // Resultados esperados
        assertNotNull(materialesEncontrados);
        assertEquals(listaMateriales, materialesEncontrados);
        assertEquals(1L, materialesEncontrados.size());
    }


    // Retorna un material filtrado por ID
    @Test
    public void getMaterialById(){

        // Material de prueba
        Long id = 1L;
        Long idUsuarioCreador = 2L;
        Material material = new Material();
        material.setIdMaterial(id);
        material.setIdUsuarioCreador(idUsuarioCreador);

        // Simulacion de retorno de repositorio
        when(mRepo.findById(id)).thenReturn(Optional.of(material));

        // Metodo a testear
        Material materialEncontrado = materialService.getMaterialById(id);

        // Resultados esperados
        assertNotNull(materialEncontrado);
        assertEquals(id, materialEncontrado.getIdMaterial());
        assertEquals(idUsuarioCreador, materialEncontrado.getIdUsuarioCreador());
    }


    // Retorna todos los materiales creados por un mismo usuario
    @Test
    public void getMaterialByUsuarioCreador(){

        Long idUsuario = 1L;

        // Material de prueba
        Material material = new Material();
        material.setIdMaterial(1L);
        material.setIdUsuarioCreador(idUsuario);

        // Lista de materiales de prueba
        List<Material> listaMateriales = new ArrayList<>();
        listaMateriales.add(material);

        // Simulacion de retorno de repositorio
        when(mRepo.findByIdUsuarioCreador(idUsuario)).thenReturn(listaMateriales);

        // Metodo a testear
        List<Material> materialesEncontrados = materialService.getMaterialByUsuarioCreador(idUsuario);

        // Resultados esperados
        assertNotNull(materialesEncontrados);
        assertEquals(listaMateriales, materialesEncontrados);
    }


    // Crea un nuevo material
    @Test
    public void postMaterial(){

        // Material de prueba
        Material material = new Material();
        material.setIdMaterial(1L);
        material.setIdUsuarioCreador(1L);

        // Simulacion de retorno de repositorio
        when(mRepo.save(material)).thenReturn(material);

        // Simulacion de respuesta de clientes
        when(hClient.postEvento(any(RequestEventoDTO.class))).thenReturn(new ResponseEventoDTO());
        when(lClient.postIncrementarMaterialCreado(anyLong())).thenReturn(ResponseEntity.ok(new ResponseRecuentoDTO()));

        // Metodo a testear
        Material materialCreado = materialService.postMaterial(material);

        // Resultados esperados
        assertNotNull(materialCreado);
        assertEquals(1L, materialCreado.getIdUsuarioCreador());

        // Verificacion de que se ejecutan los metodos internos
        verify(hClient).postEvento(any(RequestEventoDTO.class));
        verify(lClient).postIncrementarMaterialCreado(anyLong());
    }


    // Crea una lista de materiales
    @Test
    public void postListaMateriales(){

        // Materiales de prueba
        Material material1 = new Material();
        material1.setIdMaterial(1L);
        material1.setIdUsuarioCreador(1L);

        Material material2 = new Material();
        material2.setIdMaterial(2L);
        material2.setIdUsuarioCreador(1L);

        List<Material> listaMateriales = new ArrayList<>();
        listaMateriales.add(material1);
        listaMateriales.add(material2);

        // Simulacion de retorno de repositorio
        when(mRepo.saveAll(listaMateriales)).thenReturn(listaMateriales);

        // Metodo a testear
        List<Material> materialesCreados = materialService.postMaterial(listaMateriales);

        // Resultados esperados
        assertNotNull(materialesCreados);
        assertEquals(2, materialesCreados.size());
        assertEquals(listaMateriales, materialesCreados);
    }


    // Actualiza un material por su id
    @Test
    public void actualizarEjercicios(){

        // Material actual de prueba
        Long id = 1L;
        Material materialActual = new Material();
        materialActual.setIdMaterial(id);
        materialActual.setIdUsuarioCreador(1L);
        materialActual.setItemsEjercicios(new ArrayList<>());

        // Material con datos actualizados
        List<ItemEjercicio> nuevosItems = new ArrayList<>();
        ItemEjercicio item = new ItemEjercicio();
        item.setIdItemEjercicio(1L);
        nuevosItems.add(item);

        Material materialActualizado = new Material();
        materialActualizado.setItemsEjercicios(nuevosItems);

        Material materialGuardado = new Material();
        materialGuardado.setIdMaterial(id);
        materialGuardado.setIdUsuarioCreador(1L);
        materialGuardado.setItemsEjercicios(nuevosItems);

        // Simulacion de retorno de repositorio
        when(mRepo.findById(id)).thenReturn(Optional.of(materialActual));
        when(mRepo.save(any(Material.class))).thenReturn(materialGuardado);

        // Simulacion de respuesta de cliente
        when(hClient.postEvento(any(RequestEventoDTO.class))).thenReturn(new ResponseEventoDTO());

        // Metodo a testear
        Material materialResultado = materialService.actualizarEjercicios(id, materialActualizado);

        // Resultados esperados
        assertNotNull(materialResultado);
        assertEquals(1, materialResultado.getItemsEjercicios().size());

        // Verificacion de que se ejecutan los metodos internos
        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }


    // Elimina un material por su id
    @Test
    public void deleteMaterial(){

        // Material de prueba
        Long id = 1L;
        Material material = new Material();
        material.setIdMaterial(id);
        material.setIdUsuarioCreador(1L);

        // Simulacion de retorno de repositorio
        when(mRepo.findById(id)).thenReturn(Optional.of(material));
        doNothing().when(mRepo).delete(material);

        // Simulacion de respuesta de cliente
        when(hClient.postEvento(any(RequestEventoDTO.class))).thenReturn(new ResponseEventoDTO());

        // Metodo a testear
        Material materialEliminado = materialService.deleteMaterial(id);

        // Resultados esperados
        assertNotNull(materialEliminado);
        assertEquals(id, materialEliminado.getIdMaterial());

        // Verificacion de que se ejecutan los metodos internos
        verify(mRepo).delete(material);
        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }
}
