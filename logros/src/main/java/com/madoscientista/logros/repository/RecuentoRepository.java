package com.madoscientista.logros.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madoscientista.logros.model.Recuento;

public interface RecuentoRepository extends JpaRepository<Recuento, Long>{

    Optional<Recuento> findByIdUsuario(Long idUsuario);
}
