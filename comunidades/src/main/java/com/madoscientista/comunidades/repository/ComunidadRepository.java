package com.madoscientista.comunidades.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.madoscientista.comunidades.model.Comunidad;

@Repository
public interface ComunidadRepository extends JpaRepository<Comunidad, Long>{

    // Retorna todas las comunidades a las que pertenece un usuario
    List<Comunidad> findAllByIdMiembrosContains(Long idMiembro);

}

