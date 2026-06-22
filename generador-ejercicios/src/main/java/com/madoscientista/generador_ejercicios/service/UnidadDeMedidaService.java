package com.madoscientista.generador_ejercicios.service;

import org.springframework.stereotype.Service;

import com.madoscientista.generador_ejercicios.model.MagnitudFisica;
import com.madoscientista.generador_ejercicios.model.UnidadDeMedida;
import com.madoscientista.generador_ejercicios.repository.UnidadDeMedidaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnidadDeMedidaService {

    private final UnidadDeMedidaRepository repo;
    
    public UnidadDeMedida getBySimbolo(String simbolo){
        return repo.findBySimbolo(simbolo);
    }

    public UnidadDeMedida getUnidadBaseSI(MagnitudFisica magnitudFisica){
        return repo.findByMagnitudFisicaAndEsBaseSITrue(magnitudFisica);
    }

    public UnidadDeMedida getUnidadBaseSI(String nombreMagnitudFisica){
        return repo.findByMagnitudFisicaNombreAndEsBaseSITrue(nombreMagnitudFisica);
    }
}
