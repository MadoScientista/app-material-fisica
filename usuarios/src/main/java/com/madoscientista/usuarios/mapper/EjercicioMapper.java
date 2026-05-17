package com.madoscientista.usuarios.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.madoscientista.usuarios.dto.ejercicioDTO.ResponseEjercicioDTO;
import com.madoscientista.usuarios.model.Ejercicio;
import com.madoscientista.usuarios.model.Usuario;

@Component
public class EjercicioMapper {

    public Ejercicio toEntity(ResponseEjercicioDTO response, Usuario u){
        Ejercicio ejercicio = new Ejercicio();

        ejercicio.setCreador(u);
        ejercicio.setDificultad(response.getDificultad());
        ejercicio.setEnunciado(response.getEnunciado());
        ejercicio.setIncognita(response.getIncognita());
        ejercicio.setRespuesta(response.getRespuesta());
        ejercicio.setIdPlantillaEnunciado(response.getIdPlantillaEnunciado());
        ejercicio.setTema(response.getTema());

        return ejercicio;
    }

    public ResponseEjercicioDTO toDTO(Ejercicio ejercicio){
        ResponseEjercicioDTO response = new ResponseEjercicioDTO();

        response.setIdEjercicio(ejercicio.getIdEjercicio());
        response.setIdCreador(ejercicio.getCreador().getIdUsuario());
        response.setFechaCreacion(ejercicio.getFechaCreacion().toString());
        response.setTema(ejercicio.getTema());
        response.setDificultad(ejercicio.getDificultad());
        response.setIncognita(ejercicio.getIncognita());
        response.setIdPlantillaEnunciado(ejercicio.getIdPlantillaEnunciado());
        response.setEnunciado(ejercicio.getEnunciado());
        response.setRespuesta(ejercicio.getRespuesta());

        return response;
    }

    public List<ResponseEjercicioDTO> toDTOList(List<Ejercicio> ejercicios){
        List<ResponseEjercicioDTO> response = new ArrayList<>();

        for(Ejercicio e : ejercicios){
            response.add(toDTO(e));
        }

        return response;
    }
}
