package com.madoscientista.material.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.material.model.Material;
import com.madoscientista.material.repository.MaterialRepository;

@Service
public class MaterialService {

    @Autowired
    MaterialRepository mRepo;

    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    // Retorna todos los materiales de la plataforma
    public List<Material> getMateriales(){
        return mRepo.findAll();
    }

    // Retorna un material filtrado por ID
    public Material getMaterialById(Long idMaterial){
        return mRepo.findById(idMaterial).orElse(null);
    }

    // Retorna todos los materiales creados por un mismo usuario
    public List<Material> getMaterialByUsuarioCreador(Long idUsuarioCreador){
        return mRepo.findByIdUsuarioCreador(idUsuarioCreador);
    }

    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // Crea un nuevo material
    public Material postMaterial(Material m){
        return mRepo.save(m);
    }

    // Crea una lista de materiales
    public List<Material> postMaterial(List<Material> materiales){
        return mRepo.saveAll(materiales);
    }

    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    // Actualiza un material por su id
    public Material actualizarEjercicios(Long idMaterial, Material materialActualizado){
        Material materialActual = getMaterialById(idMaterial);

        if(materialActualizado != null){
            materialActual.setItemsEjercicios(materialActualizado.getItemsEjercicios());
            materialActual = mRepo.save(materialActual);
        }

        return materialActual;
    }

    // --------------------------------------------------------
    // ------------------ Sección DELETE ----------------------
    // --------------------------------------------------------

    // Elimina un material por su id
    public Material deleteMaterial(Long idMaterial){
        Material material = getMaterialById(idMaterial);
        if(material != null){
            mRepo.delete(material);
        }
        return material;
    }
}
