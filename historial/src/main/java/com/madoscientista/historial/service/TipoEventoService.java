package com.madoscientista.historial.service;

import org.springframework.stereotype.Service;

import com.madoscientista.historial.model.TipoEvento;
import com.madoscientista.historial.repository.TipoEventoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoEventoService {

    private final TipoEventoRepository repo;
    
    public TipoEvento getById(Long id){
        return repo.findById(id).orElse(null);
    }
}
