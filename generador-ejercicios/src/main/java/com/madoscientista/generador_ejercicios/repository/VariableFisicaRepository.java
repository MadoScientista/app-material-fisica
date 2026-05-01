package com.madoscientista.generador_ejercicios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madoscientista.generador_ejercicios.model.VariableFisica;


public interface VariableFisicaRepository  extends JpaRepository<VariableFisica, Integer>{

    VariableFisica findBySimbolo(String simbolo);

    List<VariableFisica> findAllByOrderByIdVariableFisicaAsc();
}
