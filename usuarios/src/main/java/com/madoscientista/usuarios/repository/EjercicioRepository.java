package com.madoscientista.usuarios.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;


import com.madoscientista.usuarios.model.Ejercicio;


public interface EjercicioRepository extends JpaRepository<Ejercicio, Long>{

    // Retorna la lista de ejercicios creados por un usuario
    List<Ejercicio> findAllByCreadorIdUsuario(long id);

    // Retorna una lista de ejercicios creados por un Set de usuarios
    List<Ejercicio> findAllByCreadorIdUsuarioIn(Set<Long> idUsuarios);

    // Retorna una lista de ejercicios que se les ha compartido a un usuario
    List<Ejercicio> findByUsuariosCompartidosIdUsuario(long id);

    Long countByCreadorIdUsuario(Long id);

    Optional<Ejercicio> findByIdEjercicio(Long id);
}
