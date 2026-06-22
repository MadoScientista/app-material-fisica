package com.madoscientista.logos;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.madoscientista.logos.client.HistorialClient;
import com.madoscientista.logos.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.logos.model.Logo;
import com.madoscientista.logos.repository.LogoRepository;
import com.madoscientista.logos.service.LogoService;


@SpringBootTest
public class LogoServiceTest {

    @Autowired
    private LogoService logoService;

    @MockitoBean
    private LogoRepository logoRepository;

    @MockitoBean
    private HistorialClient hClient;


    // Retorna la lista de logos disponibles en BD
    @Test
    public void getLogos(){

        // Logo de prueba
        Logo logo = new Logo();
        logo.setIdLogo(1L);
        logo.setIdUsuarioCreador(1L);
        logo.setNombre("Logo de prueba");

        // Lista de logos de prueba
        List<Logo> listaLogos = new ArrayList<>();
        listaLogos.add(logo);

        // Simulación de retorno de repositorio
        when(logoRepository.findAll()).thenReturn(listaLogos);

        // Método a testear
        List<Logo> logosEncontrados = logoService.getLogos();

        // Resultados esperados
        assertNotNull(logosEncontrados);
        assertEquals(listaLogos, logosEncontrados);
    }


    // Retorna un logo filtrado por su id
    @Test
    public void getLogoById(){

        // Logo de prueba
        Long id = 1L;
        Logo logo = new Logo();
        logo.setIdLogo(id);
        logo.setIdUsuarioCreador(1L);
        logo.setNombre("Logo de prueba");

        // Simulación de retorno de repositorio
        when(logoRepository.findById(id)).thenReturn(Optional.of(logo));

        // Método a testear
        Logo logoEncontrado = logoService.getLogoById(id);

        // Resultados esperados
        assertNotNull(logoEncontrado);
        assertEquals(id, logoEncontrado.getIdLogo());
        assertEquals("Logo de prueba", logoEncontrado.getNombre());
    }


    // Retorna los logos filtrados por el id del usuario creador
    @Test
    public void getLogoByIdUsuarioCreador(){

        Long idUsuario = 1L;

        // Logo de prueba
        Logo logo = new Logo();
        logo.setIdLogo(1L);
        logo.setIdUsuarioCreador(idUsuario);

        // Lista de logos de prueba
        List<Logo> listaLogos = new ArrayList<>();
        listaLogos.add(logo);

        // Simulación de retorno de repositorio
        when(logoRepository.findAllByIdUsuarioCreador(idUsuario)).thenReturn(listaLogos);

        // Método a testear
        List<Logo> logosEncontrados = logoService.getLogoByIdUsuarioCreador(idUsuario);

        // Resultados esperados
        assertNotNull(logosEncontrados);
        assertEquals(listaLogos, logosEncontrados);
        assertEquals(1, logosEncontrados.size());
    }


    // Retorna una lista de logos filtrados por un conjunto de ids
    @Test
    public void getLogosByListId(){

        Set<Long> ids = Set.of(1L, 2L);

        // Logo de prueba 1
        Logo logo1 = new Logo();
        logo1.setIdLogo(1L);
        logo1.setIdUsuarioCreador(1L);

        // Logo de prueba 2
        Logo logo2 = new Logo();
        logo2.setIdLogo(2L);
        logo2.setIdUsuarioCreador(1L);

        // Lista de logos de prueba
        List<Logo> listaLogos = new ArrayList<>();
        listaLogos.add(logo1);
        listaLogos.add(logo2);

        // Simulación de retorno de repositorio
        when(logoRepository.findByIdLogoIn(ids)).thenReturn(listaLogos);

        // Método a testear
        List<Logo> logosEncontrados = logoService.getLogosByListId(ids);

        // Resultados esperados
        assertNotNull(logosEncontrados);
        assertEquals(listaLogos, logosEncontrados);
        assertEquals(2, logosEncontrados.size());
    }


    // Retorna una lista de logos filtrados por un conjunto de ids de usuario creador
    @Test
    public void getLogosByListIdUsuarioCreador(){

        Set<Long> idsUsuarios = Set.of(1L, 2L);

        // Logo de prueba 1
        Logo logo1 = new Logo();
        logo1.setIdLogo(1L);
        logo1.setIdUsuarioCreador(1L);

        // Logo de prueba 2
        Logo logo2 = new Logo();
        logo2.setIdLogo(2L);
        logo2.setIdUsuarioCreador(2L);

        // Lista de logos de prueba
        List<Logo> listaLogos = new ArrayList<>();
        listaLogos.add(logo1);
        listaLogos.add(logo2);

        // Simulación de retorno de repositorio
        when(logoRepository.findAllByIdUsuarioCreadorIn(idsUsuarios)).thenReturn(listaLogos);

        // Método a testear
        List<Logo> logosEncontrados = logoService.getLogosByListIdUsuarioCreador(idsUsuarios);

        // Resultados esperados
        assertNotNull(logosEncontrados);
        assertEquals(listaLogos, logosEncontrados);
        assertEquals(2, logosEncontrados.size());
    }


    // Crea un nuevo logo, genera su URL y registra el evento correspondiente
    @Test
    public void postLogo(){

        Long idUsuario = 1L;

        // Logo de prueba (sin URL)
        Logo logo = new Logo();
        logo.setIdUsuarioCreador(idUsuario);
        logo.setNombre("Mi Logo");
        logo.setImagen("imagen.png");
        logo.setDescripcion("Descripción del logo");

        // Simulación de retorno de repositorio
        when(logoRepository.save(any(Logo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Método a testear
        Logo logoCreado = logoService.postLogo(logo);

        // Resultados esperados
        assertNotNull(logoCreado);
        assertEquals(idUsuario, logoCreado.getIdUsuarioCreador());
        assertNotNull(logoCreado.getUrl());
        assertTrue(logoCreado.getUrl().startsWith("www."));
        assertTrue(logoCreado.getUrl().contains("mi-logo"));
        assertTrue(logoCreado.getUrl().endsWith(".appef"));

        // Verificación de métodos internos
        verify(logoRepository).save(logo);
        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }


    // Retorna null cuando el logo a actualizar no existe
    @Test
    public void putLogo_notFound(){

        Long idLogo = 1L;

        // Logo actualizado de prueba
        Logo logoActualizado = new Logo();
        logoActualizado.setNombre("Nuevo nombre");

        // Simulación de retorno de repositorio
        when(logoRepository.findById(idLogo)).thenReturn(Optional.empty());

        // Método a testear
        Logo resultado = logoService.putLogo(idLogo, logoActualizado);

        // Resultados esperados
        assertNull(resultado);
    }


    // Actualiza un logo existente y registra el evento
    @Test
    public void putLogo(){

        Long idLogo = 1L;
        Long idUsuario = 1L;

        // Logo existente de prueba
        Logo logoExistente = new Logo();
        logoExistente.setIdLogo(idLogo);
        logoExistente.setIdUsuarioCreador(idUsuario);
        logoExistente.setNombre("Nombre original");
        logoExistente.setDescripcion("Descripción original");
        logoExistente.setImagen("original.png");
        logoExistente.setUrl("www.url-original.appef");

        // Logo actualizado de prueba
        Logo logoActualizado = new Logo();
        logoActualizado.setNombre("Nombre actualizado");
        logoActualizado.setDescripcion("Descripción actualizada");
        logoActualizado.setImagen("actualizado.png");

        // Simulación de retorno de repositorio
        when(logoRepository.findById(idLogo)).thenReturn(Optional.of(logoExistente));
        when(logoRepository.save(any(Logo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Método a testear
        Logo resultado = logoService.putLogo(idLogo, logoActualizado);

        // Resultados esperados
        assertNotNull(resultado);
        assertEquals("Nombre actualizado", resultado.getNombre());
        assertEquals("Descripción actualizada", resultado.getDescripcion());
        assertEquals("actualizado.png", resultado.getImagen());
        assertNotNull(resultado.getUrl());
        assertTrue(resultado.getUrl().contains("nombre-actualizado"));

        // Verificación de métodos internos
        verify(logoRepository).save(logoExistente);
        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }


    // Retorna null cuando el logo a eliminar no existe
    @Test
    public void deleteLogoById_notFound(){

        Long idLogo = 1L;

        // Simulación de retorno de repositorio
        when(logoRepository.findById(idLogo)).thenReturn(Optional.empty());

        // Método a testear
        Logo resultado = logoService.deleteLogoById(idLogo);

        // Resultados esperados
        assertNull(resultado);
    }


    // Elimina un logo por su id y registra el evento
    @Test
    public void deleteLogoById(){

        Long idLogo = 1L;
        Long idUsuario = 1L;

        // Logo de prueba
        Logo logo = new Logo();
        logo.setIdLogo(idLogo);
        logo.setIdUsuarioCreador(idUsuario);
        logo.setNombre("Logo a eliminar");

        // Simulación de retorno de repositorio
        when(logoRepository.findById(idLogo)).thenReturn(Optional.of(logo));

        // Método a testear
        Logo resultado = logoService.deleteLogoById(idLogo);

        // Resultados esperados
        assertNotNull(resultado);
        assertEquals(idLogo, resultado.getIdLogo());

        // Verificación de métodos internos
        verify(logoRepository).delete(logo);
        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }


    // Elimina una lista de logos por sus ids y registra el evento
    @Test
    public void deleteLogoByListId(){

        Set<Long> ids = Set.of(1L, 2L);
        Long idUsuario = 1L;

        // Logo de prueba 1
        Logo logo1 = new Logo();
        logo1.setIdLogo(1L);
        logo1.setIdUsuarioCreador(idUsuario);

        // Logo de prueba 2
        Logo logo2 = new Logo();
        logo2.setIdLogo(2L);
        logo2.setIdUsuarioCreador(idUsuario);

        List<Logo> logosAEliminar = new ArrayList<>();
        logosAEliminar.add(logo1);
        logosAEliminar.add(logo2);

        // Simulación de retorno de repositorio
        when(logoRepository.findByIdLogoIn(ids)).thenReturn(logosAEliminar);

        // Método a testear
        List<Logo> resultado = logoService.deleteLogoByListId(ids);

        // Resultados esperados
        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        // Verificación de métodos internos
        verify(logoRepository).deleteAll(logosAEliminar);
        verify(hClient).postEvento(any(RequestEventoDTO.class));
    }
}
