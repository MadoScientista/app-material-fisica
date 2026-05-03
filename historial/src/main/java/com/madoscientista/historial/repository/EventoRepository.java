package com.madoscientista.historial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madoscientista.historial.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long>{

    public List<Evento> findAllByIdUsuario(Long idUsuario);
}
