package com.madoscientista.generador_ejercicios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.generador_ejercicios.model.PlantillaEnunciado;
import com.madoscientista.generador_ejercicios.repository.PlantillaEnunciadoRepository;

@Service
public class PlantillaEnunciadoService {

    @Autowired
    private PlantillaEnunciadoRepository repo;


    //-------------------------------------------------------------
    //------------------------- SECCIÓN GET -----------------------
    //-------------------------------------------------------------

    public List<PlantillaEnunciado> getPlantillas(){
        return repo.findAllByOrderByIdPlantillaEnunciadoAsc();
    }


    public List<PlantillaEnunciado> getPlantillaEnunciado(String  nombreTema, String nombreContexto, String nombreIncognita, boolean resultadoPositivo){
        return repo.findByTema_nombreAndContexto_nombreAndIncognita_nombreAndResultadoPositivo(
            nombreTema, 
            nombreContexto, 
            nombreIncognita,
            resultadoPositivo);
    }

    public PlantillaEnunciado getPlantillaById(long id){
        return repo.findByIdPlantillaEnunciado(id);
    }

    public List<PlantillaEnunciado> getPlantillasByTema(String nombreTema){
        return repo.findAllByTema_nombre(nombreTema);
    }
    

}
