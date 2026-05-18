package com.madoscientista.material.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.material.client.HistorialClient;
import com.madoscientista.material.client.LogrosClient;
import com.madoscientista.material.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.material.model.ItemEjercicio;
import com.madoscientista.material.repository.ItemEjercicioRepository;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ItemEjercicioService {

    private static final Long ITEM_EJERCICIO_CREADO = 19L;
    private static final Long ITEM_EJERCICIO_ACTUALIZADO = 20L;
    private static final Long ITEM_EJERCICIO_ELIMINADO = 21L;

    @Autowired
    private ItemEjercicioRepository ieRepo;

    @Autowired
    private HistorialClient hClient;

    @Autowired
    private LogrosClient lClient;

    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    // Retorna todos los items disponibles en la plataforma
    public List<ItemEjercicio> getItemEjercicios(){
        return ieRepo.findAll();
    }
    
    // Retorna un item por su id
    public ItemEjercicio getItemEjercicioById(Long idItemEjercicio){
        return ieRepo.findById(idItemEjercicio).orElse(null);
    }

    // Retorna los items generados por un mismo usuario
    public List<ItemEjercicio> getItemEjercicioByIdUsuarioCreador(Long idUsuarioCreador){
        return ieRepo.findAllByIdUsuarioCreador(idUsuarioCreador);
    }

    // Retorna una lista de item de ejercicios filtrados por id
    public List<ItemEjercicio> getItemEjercicioByIdIn(Set<Long> idItemEjercicios){
        return ieRepo.findByIdItemEjercicioIn(idItemEjercicios);
    }

    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // Guarda un nuevo item
    public ItemEjercicio postItemEjercicio(ItemEjercicio itemEjercicio){
        ItemEjercicio ieCreado = ieRepo.save(itemEjercicio);

        try{
            registrarEvento(ieCreado.getIdUsuarioCreador(), ITEM_EJERCICIO_CREADO);
        }catch(FeignException e){
            log.debug("No se pudo comunicar el evento - crear item -", e);
        }

        try{
            lClient.postIncrementarItemCreado(ieCreado.getIdUsuarioCreador(), 1);
        }catch(FeignException e){
            log.debug("No se pudo solicitar el aumento del recuento de items", e);
        }

        return ieCreado;
    }

    // Guarda una lista de items
    public List<ItemEjercicio> postListaItemEjercicio(List<ItemEjercicio> listaItemEjercicio){
        List<ItemEjercicio> ieListCreada = ieRepo.saveAll(listaItemEjercicio);

        if(!ieListCreada.isEmpty()){
            Long idUsuario = ieListCreada.get(0).getIdUsuarioCreador();
            int cantidad = ieListCreada.size();

            try{
                registrarEvento(idUsuario, ITEM_EJERCICIO_CREADO);
            }catch(FeignException e){
                log.debug("No se pudo comunicar el evento - crear items -", e);
            }

            try{
                lClient.postIncrementarItemCreado(idUsuario, cantidad);
            }catch(FeignException e){
                log.debug("No se pudo solicitar el aumento del recuento de items", e);
            }
        }

        return ieListCreada;
    }

    // --------------------------------------------------------
    // ------------------ Sección DELETE ----------------------
    // --------------------------------------------------------

    // Elimina un item por su id
    public ItemEjercicio deleteItemEjercicioById(Long idItemEjercicio){
        
        ItemEjercicio itemEncontrado = ieRepo.findById(idItemEjercicio).orElse(null);
        if(itemEncontrado != null){
            ieRepo.delete(itemEncontrado);

            try{
                registrarEvento(itemEncontrado.getIdUsuarioCreador(), ITEM_EJERCICIO_ELIMINADO);
            }catch(FeignException e){
                log.debug("No se pudo comunicar el evento - eliminar item -", e);
            }

            return itemEncontrado;
        }

        return null;
    }


    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    // Actualiza los datos de un item
    public ItemEjercicio putItemEjercicio(Long idItemEjercicio, ItemEjercicio itemActualizado){
        ItemEjercicio itemEjercicioActual = getItemEjercicioById(idItemEjercicio);

        if(itemEjercicioActual == null){
            return null;
        }

        itemEjercicioActual.setDescripcion(itemActualizado.getDescripcion());
        itemEjercicioActual.setTitulo(itemActualizado.getTitulo());
        itemEjercicioActual.setTextoEjercicios(itemActualizado.getTextoEjercicios());

        ItemEjercicio ieActualizado = ieRepo.save(itemEjercicioActual);

        try{
            registrarEvento(ieActualizado.getIdUsuarioCreador(), ITEM_EJERCICIO_ACTUALIZADO);
        }catch(FeignException e){
            log.debug("No se pudo comunicar el evento - actualizar item -", e);
        }

        return ieActualizado;
    }

    // --------------------------------------------------------
    // ------------------ Sección EVENTOS ---------------------
    // --------------------------------------------------------

    private void registrarEvento(Long idUsuarioOrigen, Long idTipoEvento) {
        RequestEventoDTO eventoDTO = new RequestEventoDTO();
        eventoDTO.setIdTipoEvento(idTipoEvento);
        eventoDTO.setIdUsuarioOrigen(idUsuarioOrigen);
        List<Long> destinos = new ArrayList<>();
        destinos.add(idUsuarioOrigen);
        eventoDTO.setIdUsuarioDestino(destinos);
        try{
            hClient.postEvento(eventoDTO);
        }catch(FeignException e){
            log.warn("Error de comunicación con microservicio historial. Evento no registrado");
        }
    }

}
