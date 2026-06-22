package com.madoscientista.generador_ejercicios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madoscientista.generador_ejercicios.model.MagnitudFisica;

public interface MagnitudFisicaRepository extends JpaRepository<MagnitudFisica, Integer> {

    MagnitudFisica findByIdMagnitudFisica(int id);

    List<MagnitudFisica> findAllByOrderByIdMagnitudFisicaAsc();
}
