package com.madoscientista.generador_ejercicios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madoscientista.generador_ejercicios.model.PlantillaEnunciado;

public interface PlantillaEnunciadoRepository extends JpaRepository<PlantillaEnunciado, Long> {

    List<PlantillaEnunciado> findByTema_nombreAndContexto_nombreAndIncognita_nombreAndResultadoPositivo(
        String nombreTema,
        String nombreContexto,
        String nombreIncognita,
        boolean resultadoPositivo
    );

    List<PlantillaEnunciado> findAllByOrderByIdPlantillaEnunciadoAsc();

    List<PlantillaEnunciado> findAllByTema_nombre(String tema);

    <Optional> PlantillaEnunciado findByIdPlantillaEnunciado(long id);
}
