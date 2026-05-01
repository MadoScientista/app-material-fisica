package com.madoscientista.historial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madoscientista.historial.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long>{

}
