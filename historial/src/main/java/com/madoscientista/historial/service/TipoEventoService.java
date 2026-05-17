package com.madoscientista.historial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.historial.model.TipoEvento;
import com.madoscientista.historial.repository.TipoEventoRepository;

@Service
public class TipoEventoService {

    @Autowired
    private TipoEventoRepository repo;
    
    public TipoEvento getById(Long id){
        return repo.findById(id).orElse(null);
    }
}
