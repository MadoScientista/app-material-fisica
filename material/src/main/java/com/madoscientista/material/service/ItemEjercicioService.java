package com.madoscientista.material.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.material.model.ItemEjercicio;
import com.madoscientista.material.repository.ItemEjercicioRepository;

@Service
public class ItemEjercicioService {

    @Autowired
    private ItemEjercicioRepository ieRepo;

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
        return ieRepo.save(itemEjercicio);
    }

    // Guarda una lista de items
    public List<ItemEjercicio>postListaItemEjercicio(List<ItemEjercicio> listaItemEjercicio){
        return ieRepo.saveAll(listaItemEjercicio);
    }

    // --------------------------------------------------------
    // ------------------ Sección DELETE ----------------------
    // --------------------------------------------------------

    // Elimina un item por su id
    public ItemEjercicio deleteItemEjercicioById(Long idItemEjercicio){
        
        ItemEjercicio itemEncontrado = ieRepo.findById(idItemEjercicio).orElse(null);
        if(itemEncontrado != null){
            ieRepo.delete(itemEncontrado);
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

        return ieRepo.save(itemEjercicioActual);
    }

}
