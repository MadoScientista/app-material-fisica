package com.madoscientista.comunidades.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.madoscientista.comunidades.model.Comunidad;


public interface ComunidadRepository extends JpaRepository<Comunidad, Long>{

    // Retorna todas las comunidades a las que pertenece un usuario
    List<Comunidad> findAllByIdMiembrosContains(Long idMiembro);

}

