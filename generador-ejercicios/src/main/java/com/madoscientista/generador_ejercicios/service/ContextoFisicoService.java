package com.madoscientista.generador_ejercicios.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.madoscientista.generador_ejercicios.model.ContextoFisico;
import com.madoscientista.generador_ejercicios.repository.ContextoFisicoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContextoFisicoService {

    private final ContextoFisicoRepository repo;



    //-------------------------------------------------------------
    //------------------------- SECCIÓN GET -----------------------
    //-------------------------------------------------------------
    
    public List<ContextoFisico> getContextos(){
        return repo.findAllByOrderByIdContextoFisicoAsc();
    }

    public ContextoFisico getContextoFisicoById(int id){
        return repo.findByIdContextoFisico(id);
    }
}
