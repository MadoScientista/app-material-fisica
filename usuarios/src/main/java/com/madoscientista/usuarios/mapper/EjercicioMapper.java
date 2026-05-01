package com.madoscientista.usuarios.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.madoscientista.usuarios.dto.ejercicioDTO.ResponseEjercicioDTO;
import com.madoscientista.usuarios.model.Ejercicio;
import com.madoscientista.usuarios.model.Usuario;

@Component
public class EjercicioMapper {

    public Ejercicio toNewEjercicio(ResponseEjercicioDTO response, Usuario u){
        Ejercicio ejercicio = new Ejercicio();

        ejercicio.setCreador(u);
        ejercicio.setDificultad(response.getDificultad());
        ejercicio.setEnunciado(response.getEnunciado());
        ejercicio.setFecha(LocalDate.now());
        ejercicio.setIncognita(response.getIncognita());
        ejercicio.setRespuesta("Trabajando en ello jajaj");
        ejercicio.setTema(response.getTema());

        return ejercicio;
    }
}
