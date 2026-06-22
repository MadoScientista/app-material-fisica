package com.madoscientista.logros;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.madoscientista.logros.model.Recuento;
import com.madoscientista.logros.repository.RecuentoRepository;
import com.madoscientista.logros.service.LogroEvaluatorService;
import com.madoscientista.logros.service.RecuentoService;


@SpringBootTest
public class RecuentoServiceTest {

    @Autowired
    private RecuentoService recuentoService;

    @MockitoBean
    private RecuentoRepository recuentoRepository;

    @MockitoBean
    private LogroEvaluatorService logroEvaluator;


    // Retorna la lista de recuentos disponible en DB
    @Test
    public void getRecuentos(){

        // Recuento de prueba
        Recuento recuento = new Recuento();
        recuento.setIdRecuento(1L);
        recuento.setIdUsuario(1L);

        // Lista de recuentos de prueba
        List<Recuento> listaRecuentos = new ArrayList<>();
        listaRecuentos.add(recuento);

        // Simulación de retorno de repositorio
        when(recuentoRepository.findAll()).thenReturn(listaRecuentos);

        // Método a testear
        List<Recuento> recuentosEncontrados = recuentoService.getRecuentos();

        // Resultados esperados
        assertNotNull(recuentosEncontrados);
        assertEquals(listaRecuentos, recuentosEncontrados);
    }


    // Obtiene o crea un recuento para un usuario existente
    @Test
    public void obtenerOCrear(){

        Long idUsuario = 1L;

        // Recuento de prueba existente
        Recuento recuento = new Recuento();
        recuento.setIdRecuento(1L);
        recuento.setIdUsuario(idUsuario);
        recuento.setNEjerciciosCreados(5L);

        // Simulación de retorno de repositorio
        when(recuentoRepository.findByIdUsuario(idUsuario)).thenReturn(Optional.of(recuento));

        // Método a testear
        Recuento resultado = recuentoService.obtenerOCrear(idUsuario);

        // Resultados esperados
        assertNotNull(resultado);
        assertEquals(idUsuario, resultado.getIdUsuario());
        assertEquals(5L, resultado.getNEjerciciosCreados());
    }


    // Convierte un recuento a Map<String, String>
    @Test
    public void toMap(){

        // Recuento de prueba
        Recuento recuento = new Recuento();
        recuento.setNEjerciciosCreados(5L);
        recuento.setNEjerciciosCompartidos(3L);
        recuento.setNComunidades(2L);
        recuento.setNItemsCreados(1L);
        recuento.setNMaterialesCreados(4L);

        // Método a testear
        Map<String, String> map = recuentoService.toMap(recuento);

        // Resultados esperados
        assertNotNull(map);
        assertEquals("5", map.get("ejerciciosCreados"));
        assertEquals("3", map.get("ejerciciosCompartidos"));
        assertEquals("2", map.get("comunidades"));
        assertEquals("1", map.get("itemsCreados"));
        assertEquals("4", map.get("materialesCreados"));
    }


    // Incrementa el contador de ejercicios creados y evalúa logros
    @Test
    public void incrementarEjerciciosCreados(){

        Long idUsuario = 1L;

        // Recuento de prueba existente
        Recuento recuento = new Recuento();
        recuento.setIdRecuento(1L);
        recuento.setIdUsuario(idUsuario);
        recuento.setNEjerciciosCreados(5L);

        // Simulación de retorno de repositorio
        when(recuentoRepository.findByIdUsuario(idUsuario)).thenReturn(Optional.of(recuento));
        when(recuentoRepository.save(recuento)).thenReturn(recuento);

        // Método a testear
        Recuento resultado = recuentoService.incrementarEjerciciosCreados(idUsuario);

        // Resultados esperados
        assertNotNull(resultado);
        assertEquals(6L, resultado.getNEjerciciosCreados());

        // Verificación de métodos internos
        verify(recuentoRepository).save(recuento);
        verify(logroEvaluator).evaluar(recuento);
    }


    // Incrementa el contador de ejercicios compartidos y evalúa logros
    @Test
    public void incrementarEjerciciosCompartidos(){

        Long idUsuario = 1L;

        // Recuento de prueba existente
        Recuento recuento = new Recuento();
        recuento.setIdRecuento(1L);
        recuento.setIdUsuario(idUsuario);
        recuento.setNEjerciciosCompartidos(3L);

        // Simulación de retorno de repositorio
        when(recuentoRepository.findByIdUsuario(idUsuario)).thenReturn(Optional.of(recuento));
        when(recuentoRepository.save(recuento)).thenReturn(recuento);

        // Método a testear
        Recuento resultado = recuentoService.incrementarEjerciciosCompartidos(idUsuario, 2);

        // Resultados esperados
        assertNotNull(resultado);
        assertEquals(5L, resultado.getNEjerciciosCompartidos());

        // Verificación de métodos internos
        verify(recuentoRepository).save(recuento);
        verify(logroEvaluator).evaluar(recuento);
    }


    // Incrementa el contador de comunidades y evalúa logros
    @Test
    public void incrementarComunidad(){

        Long idUsuario = 1L;

        // Recuento de prueba existente
        Recuento recuento = new Recuento();
        recuento.setIdRecuento(1L);
        recuento.setIdUsuario(idUsuario);
        recuento.setNComunidades(2L);

        // Simulación de retorno de repositorio
        when(recuentoRepository.findByIdUsuario(idUsuario)).thenReturn(Optional.of(recuento));
        when(recuentoRepository.save(recuento)).thenReturn(recuento);

        // Método a testear
        Recuento resultado = recuentoService.incrementarComunidad(idUsuario, 1);

        // Resultados esperados
        assertNotNull(resultado);
        assertEquals(3L, resultado.getNComunidades());

        // Verificación de métodos internos
        verify(recuentoRepository).save(recuento);
        verify(logroEvaluator).evaluar(recuento);
    }


    // Aumenta el contador de items creados y evalúa logros
    @Test
    public void incrementarItemsCreados(){

        Long idUsuario = 1L;

        // Recuento de prueba existente
        Recuento recuento = new Recuento();
        recuento.setIdRecuento(1L);
        recuento.setIdUsuario(idUsuario);
        recuento.setNItemsCreados(1L);

        // Simulación de retorno de repositorio
        when(recuentoRepository.findByIdUsuario(idUsuario)).thenReturn(Optional.of(recuento));
        when(recuentoRepository.save(recuento)).thenReturn(recuento);

        // Método a testear
        Recuento resultado = recuentoService.incrementarItemsCreados(idUsuario, 5);

        // Resultados esperados
        assertNotNull(resultado);
        assertEquals(6L, resultado.getNItemsCreados());

        // Verificación de métodos internos
        verify(recuentoRepository).save(recuento);
        verify(logroEvaluator).evaluar(recuento);
    }


    // Incrementa el contador de material creado y evalúa logros
    @Test
    public void incrementarMaterialCreado(){

        Long idUsuario = 1L;

        // Recuento de prueba existente
        Recuento recuento = new Recuento();
        recuento.setIdRecuento(1L);
        recuento.setIdUsuario(idUsuario);
        recuento.setNMaterialesCreados(4L);

        // Simulación de retorno de repositorio
        when(recuentoRepository.findByIdUsuario(idUsuario)).thenReturn(Optional.of(recuento));
        when(recuentoRepository.save(recuento)).thenReturn(recuento);

        // Método a testear
        Recuento resultado = recuentoService.incrementarMaterialCreado(idUsuario);

        // Resultados esperados
        assertNotNull(resultado);
        assertEquals(5L, resultado.getNMaterialesCreados());

        // Verificación de métodos internos
        verify(recuentoRepository).save(recuento);
        verify(logroEvaluator).evaluar(recuento);
    }


    // Incrementa el contador de comunidades para un conjunto de usuarios
    @Test
    public void incrementarComunidadParaUsuarios(){

        Set<Long> idUsuarios = Set.of(1L, 2L);

        // Recuento existente para usuario 1
        Recuento recuento1 = new Recuento();
        recuento1.setIdRecuento(1L);
        recuento1.setIdUsuario(1L);
        recuento1.setNComunidades(2L);

        // Recuento existente para usuario 2
        Recuento recuento2 = new Recuento();
        recuento2.setIdRecuento(2L);
        recuento2.setIdUsuario(2L);
        recuento2.setNComunidades(0L);

        List<Recuento> recuentosExistentes = new ArrayList<>();
        recuentosExistentes.add(recuento1);
        recuentosExistentes.add(recuento2);

        List<Recuento> recuentosActualizados = new ArrayList<>();
        recuentosActualizados.add(recuento1);
        recuentosActualizados.add(recuento2);

        // Simulación de retorno de repositorio
        when(recuentoRepository.findAllByIdUsuarioIn(idUsuarios)).thenReturn(recuentosExistentes);
        when(recuentoRepository.saveAll(anyList())).thenReturn(recuentosActualizados);

        // Método a testear
        List<Recuento> resultado = recuentoService.incrementarComunidadParaUsuarios(idUsuarios, 1);

        // Resultados esperados
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(3L, resultado.get(0).getNComunidades());
        assertEquals(1L, resultado.get(1).getNComunidades());

        // Verificación de métodos internos
        verify(recuentoRepository).saveAll(anyList());
        verify(logroEvaluator).evaluarVariosUsuarios(anyList());
    }
}
