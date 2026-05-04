package com.madoscientista.generador_ejercicios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.madoscientista.generador_ejercicios.model.MagnitudFisica;

@Repository
public interface MagnitudFisicaRepository extends JpaRepository<MagnitudFisica, Integer> {

    MagnitudFisica findByIdMagnitudFisica(int id);

    List<MagnitudFisica> findAllByOrderByIdMagnitudFisicaAsc();
}
