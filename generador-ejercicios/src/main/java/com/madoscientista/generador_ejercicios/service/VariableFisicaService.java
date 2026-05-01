package com.madoscientista.generador_ejercicios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.generador_ejercicios.model.VariableFisica;
import com.madoscientista.generador_ejercicios.repository.VariableFisicaRepository;

@Service
public class VariableFisicaService {

    @Autowired
    private VariableFisicaRepository repo;

    public VariableFisica getBySimbolo(String simbolo){
        return repo.findBySimbolo(simbolo);
    }
}
