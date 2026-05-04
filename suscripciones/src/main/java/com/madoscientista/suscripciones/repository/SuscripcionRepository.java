package com.madoscientista.suscripciones.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.madoscientista.suscripciones.model.Suscripcion;

@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long>{

    List<Suscripcion> findByActivo(boolean activo);
    List<Suscripcion> findByIdUsuario(Long idUsuario);
    List<Suscripcion> findByIdUsuarioIn(List<Long> idUsuarios);
}

