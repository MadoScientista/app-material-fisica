package com.madoscientista.logos.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.madoscientista.logos.model.Logo;

@Repository
public interface LogoRepository extends JpaRepository<Logo, Long>{

    List<Logo> findAllByIdUsuarioCreador(Long idUsuarioCreador);

    List<Logo> findByIdLogoIn(Set<Long> ids);

    List<Logo> findAllByIdUsuarioCreadorIn(Set<Long> ids);
}
