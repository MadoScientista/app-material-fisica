package com.madoscientista.suscripciones.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madoscientista.suscripciones.model.Suscripcion;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long>{

    List<Suscripcion> findByActivo(boolean activo);
    Optional<Suscripcion> findByIdUsuario(Long idUsuario);
    List<Suscripcion> findByIdUsuarioIn(List<Long> idUsuarios);
    Optional<Suscripcion> findByIdUsuarioAndActivoTrue(Long idUsuario);
}

