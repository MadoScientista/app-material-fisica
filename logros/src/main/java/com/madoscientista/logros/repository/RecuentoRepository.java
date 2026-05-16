package com.madoscientista.logros.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madoscientista.logros.model.Recuento;

public interface RecuentoRepository extends JpaRepository<Recuento, Long>{

    Optional<Recuento> findByIdUsuario(Long idUsuario);

    List<Recuento> findAllByIdUsuarioIn(Set<Long> idUsuarios);
}
