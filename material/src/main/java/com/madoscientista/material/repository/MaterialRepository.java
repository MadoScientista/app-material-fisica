package com.madoscientista.material.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.madoscientista.material.model.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long>{

}
