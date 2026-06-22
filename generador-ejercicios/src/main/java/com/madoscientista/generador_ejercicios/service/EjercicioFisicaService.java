package com.madoscientista.generador_ejercicios.service;


import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.madoscientista.generador_ejercicios.model.DatosEjercicio;
import com.madoscientista.generador_ejercicios.model.EjercicioFisica;
import com.madoscientista.generador_ejercicios.model.EnunciadoFisica;
import com.madoscientista.generador_ejercicios.model.PlantillaEnunciado;
import com.madoscientista.generador_ejercicios.generadores.GeneradorValoresMRU;
import com.madoscientista.generador_ejercicios.model.ContextoFisico;
import com.madoscientista.generador_ejercicios.model.Dificultad;
import com.madoscientista.generador_ejercicios.model.TemaFisica;
import com.madoscientista.generador_ejercicios.model.VariableFisica;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EjercicioFisicaService {

    private final PlantillaEnunciadoService peService;
    private final UnidadDeMedidaService umService;
    private final VariableFisicaService vfService;

    private static Random random = new Random();



    //-------------------------------------------------------------
    //-------------- SECCIÓN GET EJERCICIOS -----------------------
    //-------------------------------------------------------------
    
    public List<PlantillaEnunciado> getPlantillas(){
        return peService.getPlantillas();
    }



    // Retorna un ejercicio aleatorio
    public EjercicioFisica getEjercicio(String nombreTema, String nombreContexto, String nombreIncognita, String nombreDificultad, boolean resultadoPositivo){

        List<PlantillaEnunciado> plantillasEnunciado = peService.getPlantillaEnunciado(nombreTema, nombreContexto, nombreIncognita, resultadoPositivo);
        

        if(plantillasEnunciado.size() == 0){
            return null;
        }

        PlantillaEnunciado plantillaEnunciado = plantillasEnunciado.get(random.nextInt(plantillasEnunciado.size()));

        TemaFisica tema = plantillaEnunciado.getTema();
        VariableFisica incognita = plantillaEnunciado.getIncognita();
        Dificultad dificultad = new Dificultad();
        dificultad.setNombre(nombreDificultad);
        ContextoFisico contexto = plantillaEnunciado.getContexto();

        DatosEjercicio datosEjercicio = switch (nombreTema) {

                case "MRU" ->{
                    GeneradorValoresMRU generadorValoresMRU = new GeneradorValoresMRU(umService, vfService);
                    yield generadorValoresMRU.generarValores(nombreIncognita, nombreDificultad, contexto, resultadoPositivo);

                }
            
                default ->null;
            };

        EnunciadoFisica enunciado = new EnunciadoFisica(plantillaEnunciado, datosEjercicio);
        EjercicioFisica ejercicio = new EjercicioFisica(enunciado, dificultad, contexto, datosEjercicio, tema, incognita);
        return ejercicio;
    }
}
