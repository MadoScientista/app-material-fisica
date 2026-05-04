package com.madoscientista.generador_ejercicios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.madoscientista.generador_ejercicios.model.VariableFisica;

@Repository
public interface VariableFisicaRepository  extends JpaRepository<VariableFisica, Integer>{

    VariableFisica findBySimbolo(String simbolo);

    List<VariableFisica> findAllByOrderByIdVariableFisicaAsc();
}
