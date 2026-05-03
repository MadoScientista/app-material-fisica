package com.madoscientista.historial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madoscientista.historial.model.TipoEvento;

public interface TipoEventoRepository extends JpaRepository<TipoEvento, Long>{

    
}
