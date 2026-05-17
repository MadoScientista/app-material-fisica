package com.madoscientista.material.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.madoscientista.material.model.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long>{

    List<Material> findByIdUsuarioCreador(Long idUsuarioCreador);
}
