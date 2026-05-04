package com.madoscientista.historial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.madoscientista.historial.model.TipoEvento;

@Repository
public interface TipoEventoRepository extends JpaRepository<TipoEvento, Long>{

    
}
