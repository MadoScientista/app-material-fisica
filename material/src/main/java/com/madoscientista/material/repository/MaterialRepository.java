package com.madoscientista.material.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madoscientista.material.model.Material;

public interface MaterialRepository extends JpaRepository<Material, Long>{

    List<Material> findByIdUsuarioCreador(Long idUsuarioCreador);
}
