package com.madoscientista.generador_ejercicios.service;

import org.springframework.stereotype.Service;

import com.madoscientista.generador_ejercicios.model.VariableFisica;
import com.madoscientista.generador_ejercicios.repository.VariableFisicaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VariableFisicaService {

    private final VariableFisicaRepository repo;

    public VariableFisica getBySimbolo(String simbolo){
        return repo.findBySimbolo(simbolo);
    }
}
