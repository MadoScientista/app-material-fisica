package com.madoscientista.suscripciones.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.madoscientista.suscripciones.model.TipoSuscripcion;

@Repository
public interface TipoSuscripcionRepository extends JpaRepository<TipoSuscripcion, Long>{

    Optional<TipoSuscripcion> findByNombre(String nombre);
}
