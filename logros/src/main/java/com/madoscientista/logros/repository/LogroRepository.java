package com.madoscientista.logros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madoscientista.logros.model.Logro;

public interface LogroRepository extends JpaRepository<Logro, Long>{

    // Recupera todos los logros de un usuario
    List<Logro> findAllByIdUsuario(Long idUsuario);

    // Recupera el logro de un usuario según el nombre del tipo de logro
    Logro findByIdUsuarioAndTipoLogroNombre(Long idUsuario, String nombreTipoLogro);
}
