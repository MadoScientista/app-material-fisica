package com.madoscientista.logos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.madoscientista.logos.model.Logo;

@Repository
public interface LogoRepository extends JpaRepository<Logo, Long>{

}
