package com.madoscientista.material.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.madoscientista.material.model.ItemEjercicio;

@Repository
public interface ItemEjercicioRepository extends JpaRepository<ItemEjercicio, Long>{

    // Retorna una lista de items creados por un mismo usuario
    public List<ItemEjercicio> findAllByIdUsuarioCreador(Long idUsuarioCreador);

    
}
