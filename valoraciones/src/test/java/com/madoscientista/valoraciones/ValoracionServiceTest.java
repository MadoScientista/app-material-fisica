package com.madoscientista.valoraciones;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.madoscientista.valoraciones.client.HistorialClient;
import com.madoscientista.valoraciones.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.valoraciones.dto.EventoDTO.ResponseEventoDTO;
import com.madoscientista.valoraciones.dto.ValoracionDTO.PromedioValoracionDTO;
import com.madoscientista.valoraciones.dto.ValoracionDTO.RequestValoracionDTO;
import com.madoscientista.valoraciones.model.Valoracion;
import com.madoscientista.valoraciones.repository.ValoracionRepository;
import com.madoscientista.valoraciones.service.ValoracionService;


@SpringBootTest
public class ValoracionServiceTest {

    @Autowired
    private ValoracionService valoracionService;

    @MockitoBean
    private ValoracionRepository valoracionRepo;

    @MockitoBean
    private HistorialClient hClient;


    // Retorna una valoracion filtrada por su id
    @Test
    public void getValoracionById(){

        // Valoracion de prueba
        Long id = 1L;
        Valoracion valoracion = new Valoracion();
        valoracion.setIdValoracion(id);
        valoracion.setIdEjercicio(10L);
        valoracion.setIdUsuario(5L);
        valoracion.setPuntuacion(4);
        valoracion.setComentario("Buen ejercicio");

        // Simulacion de retorno de repositorio
        when(valoracionRepo.findById(id)).thenReturn(Optional.of(valoracion));

        // Metodo a testear
        Valoracion valoracionEncontrada = valoracionService.getValoracionById(id);

        // Resultados esperados
        assertNotNull(valoracionEncontrada);
        assertEquals(id, valoracionEncontrada.getIdValoracion());
    }


    // Retorna una lista de valoraciones filtradas por id de ejercicio
    @Test
    public void getValoracionesByEjercicio(){

        Long idEjercicio = 10L;

        // Valoraciones de prueba
        Valoracion valoracion1 = new Valoracion();
        valoracion1.setIdValoracion(1L);
        valoracion1.setIdEjercicio(idEjercicio);
        valoracion1.setIdUsuario(5L);
        valoracion1.setPuntuacion(4);

        Valoracion valoracion2 = new Valoracion();
        valoracion2.setIdValoracion(2L);
        valoracion2.setIdEjercicio(idEjercicio);
        valoracion2.setIdUsuario(6L);
        valoracion2.setPuntuacion(5);

        List<Valoracion> listaValoraciones = new ArrayList<>();
        listaValoraciones.add(valoracion1);
        listaValoraciones.add(valoracion2);

        // Simulacion de retorno de repositorio
        when(valoracionRepo.findByIdEjercicio(idEjercicio)).thenReturn(listaValoraciones);

        // Metodo a testear
        List<Valoracion> valoracionesEncontradas = valoracionService.getValoracionesByEjercicio(idEjercicio);

        // Resultados esperados
        assertNotNull(valoracionesEncontradas);
        assertEquals(2, valoracionesEncontradas.size());
        assertTrue(valoracionesEncontradas.contains(valoracion1));
        assertTrue(valoracionesEncontradas.contains(valoracion2));

    }


    // Retorna una lista de valoraciones filtradas por id de usuario
    @Test
    public void getValoracionesByUsuario(){

        Long idUsuario = 5L;

        // Valoraciones de prueba
        Valoracion valoracion1 = new Valoracion();
        valoracion1.setIdValoracion(1L);
        valoracion1.setIdEjercicio(10L);
        valoracion1.setIdUsuario(idUsuario);
        valoracion1.setPuntuacion(4);

        Valoracion valoracion2 = new Valoracion();
        valoracion2.setIdValoracion(2L);
        valoracion2.setIdEjercicio(11L);
        valoracion2.setIdUsuario(idUsuario);
        valoracion2.setPuntuacion(5);

        List<Valoracion> listaValoraciones = new ArrayList<>();
        listaValoraciones.add(valoracion1);
        listaValoraciones.add(valoracion2);

        // Simulacion de retorno de repositorio
        when(valoracionRepo.findByIdUsuario(idUsuario)).thenReturn(listaValoraciones);

        // Metodo a testear
        List<Valoracion> valoracionesEncontradas = valoracionService.getValoracionesByUsuario(idUsuario);

        // Resultados esperados
        assertNotNull(valoracionesEncontradas);
        assertEquals(2, valoracionesEncontradas.size());
    }


    // Retorna el promedio de puntuaciones de un ejercicio
    @Test
    public void getPromedioByEjercicio(){

        Long idEjercicio = 10L;

        // Valoraciones de prueba
        Valoracion valoracion1 = new Valoracion();
        valoracion1.setIdValoracion(1L);
        valoracion1.setIdEjercicio(idEjercicio);
        valoracion1.setPuntuacion(4);

        Valoracion valoracion2 = new Valoracion();
        valoracion2.setIdValoracion(2L);
        valoracion2.setIdEjercicio(idEjercicio);
        valoracion2.setPuntuacion(5);

        List<Valoracion> listaValoraciones = new ArrayList<>();
        listaValoraciones.add(valoracion1);
        listaValoraciones.add(valoracion2);

        // Simulacion de retorno de repositorio
        when(valoracionRepo.findByIdEjercicio(idEjercicio)).thenReturn(listaValoraciones);

        // Metodo a testear
        PromedioValoracionDTO promedio = valoracionService.getPromedioByEjercicio(idEjercicio);

        // Resultados esperados
        assertNotNull(promedio);
        assertEquals(idEjercicio, promedio.getIdEjercicio());
        assertEquals(4.5, promedio.getPromedio(), 0.001);
        assertEquals(2L, promedio.getTotalValoraciones());
    }


    // Crea una nueva valoracion
    @Test
    public void postValoracion(){

        // Request de prueba
        RequestValoracionDTO request = new RequestValoracionDTO();
        request.setIdEjercicio(10L);
        request.setIdUsuario(5L);
        request.setPuntuacion(4);
        request.setComentario("Buen ejercicio");

        // Valoracion creada de prueba
        Valoracion valoracionCreada = new Valoracion();
        valoracionCreada.setIdValoracion(1L);
        valoracionCreada.setIdEjercicio(10L);
        valoracionCreada.setIdUsuario(5L);
        valoracionCreada.setPuntuacion(4);
        valoracionCreada.setComentario("Buen ejercicio");

        // Simulacion de retorno de repositorio
        when(valoracionRepo.findByIdEjercicioAndIdUsuario(request.getIdEjercicio(), request.getIdUsuario()))
            .thenReturn(Optional.empty());
        when(valoracionRepo.save(any(Valoracion.class))).thenReturn(valoracionCreada);

        // Simulacion de respuesta de cliente
        when(hClient.postEvento(any(RequestEventoDTO.class))).thenReturn(new ResponseEventoDTO());

        // Metodo a testear
        Valoracion resultado = valoracionService.postValoracion(request);

        // Resultados esperados
        assertNotNull(resultado);
        assertEquals(request.getIdEjercicio(), resultado.getIdEjercicio());
        assertEquals(request.getIdUsuario(), resultado.getIdUsuario());
        assertEquals(request.getPuntuacion(), resultado.getPuntuacion());

        // Verificacion de que se ejecutan los metodos internos
        verify(valoracionRepo).save(any(Valoracion.class));
        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }


    // Actualiza una valoracion existente
    @Test
    public void putValoracion(){

        Long id = 1L;

        // Valoracion existente de prueba
        Valoracion valoracionExistente = new Valoracion();
        valoracionExistente.setIdValoracion(id);
        valoracionExistente.setIdEjercicio(10L);
        valoracionExistente.setIdUsuario(5L);
        valoracionExistente.setPuntuacion(3);
        valoracionExistente.setComentario("Regular");

        // Request con datos actualizados
        RequestValoracionDTO request = new RequestValoracionDTO();
        request.setIdEjercicio(10L);
        request.setIdUsuario(5L);
        request.setPuntuacion(5);
        request.setComentario("Excelente");

        // Valoracion actualizada de prueba
        Valoracion valoracionActualizada = new Valoracion();
        valoracionActualizada.setIdValoracion(id);
        valoracionActualizada.setIdEjercicio(10L);
        valoracionActualizada.setIdUsuario(5L);
        valoracionActualizada.setPuntuacion(5);
        valoracionActualizada.setComentario("Excelente");

        // Simulacion de retorno de repositorio
        when(valoracionRepo.findById(id)).thenReturn(Optional.of(valoracionExistente));
        when(valoracionRepo.save(any(Valoracion.class))).thenReturn(valoracionActualizada);

        // Simulacion de respuesta de cliente
        when(hClient.postEvento(any(RequestEventoDTO.class))).thenReturn(new ResponseEventoDTO());

        // Metodo a testear
        Valoracion resultado = valoracionService.putValoracion(id, request);

        // Resultados esperados
        assertNotNull(resultado);
        assertEquals(5, resultado.getPuntuacion());
        assertEquals("Excelente", resultado.getComentario());

        // Verificacion de que se ejecutan los metodos internos
        verify(valoracionRepo).save(any(Valoracion.class));
        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }


    // Elimina una valoracion por su id
    @Test
    public void deleteValoracion(){

        Long id = 1L;

        // Valoracion de prueba
        Valoracion valoracion = new Valoracion();
        valoracion.setIdValoracion(id);
        valoracion.setIdEjercicio(10L);
        valoracion.setIdUsuario(5L);
        valoracion.setPuntuacion(4);

        // Simulacion de retorno de repositorio
        when(valoracionRepo.findById(id)).thenReturn(Optional.of(valoracion));
        doNothing().when(valoracionRepo).deleteById(id);

        // Simulacion de respuesta de cliente
        when(hClient.postEvento(any(RequestEventoDTO.class))).thenReturn(new ResponseEventoDTO());

        // Metodo a testear
        boolean resultado = valoracionService.deleteValoracion(id);

        // Resultados esperados
        assertTrue(resultado);

        // Verificacion de que se ejecutan los metodos internos
        verify(valoracionRepo).deleteById(id);
        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }
}
