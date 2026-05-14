package com.madoscientista.valoraciones.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.madoscientista.valoraciones.model.Valoracion;

@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {

    List<Valoracion> findByIdEjercicio(Long idEjercicio);

    List<Valoracion> findByIdUsuario(Long idUsuario);

    Optional<Valoracion> findByIdEjercicioAndIdUsuario(Long idEjercicio, Long idUsuario);

    void deleteByIdEjercicio(Long idEjercicio);
}
