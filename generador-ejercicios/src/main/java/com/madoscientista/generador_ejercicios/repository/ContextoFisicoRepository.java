package com.madoscientista.generador_ejercicios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madoscientista.generador_ejercicios.model.ContextoFisico;

public interface ContextoFisicoRepository extends JpaRepository<ContextoFisico, Integer> {

    List<ContextoFisico> findByNombre(String nombre);
    List<ContextoFisico> findAllByOrderByIdContextoFisicoAsc();

    ContextoFisico findByIdContextoFisico(int id);
}
