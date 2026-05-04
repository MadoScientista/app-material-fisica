package com.madoscientista.usuarios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.madoscientista.usuarios.model.Ejercicio;

@Repository
public interface EjercicioRepository extends JpaRepository<Ejercicio, Long>{

    // Retorna la lista de ejercicios creados por un usuario
    List<Ejercicio> findAllByCreadorIdUsuario(long id);

    // Retorna una lista de ejercicios que se les ha compartido a un usuario
    List<Ejercicio> findByUsuariosCompartidosIdUsuario(long id);


}
