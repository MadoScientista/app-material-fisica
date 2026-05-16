package com.madoscientista.logros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madoscientista.logros.model.Logro;

public interface LogroRepository extends JpaRepository<Logro, Long>{

    List<Logro> findAllByIdUsuario(Long idUsuario);

    List<Logro> findAllByIdUsuarioIn(List<Long> idsUsuarios);

    boolean existsByIdUsuario(Long idUsuario);

    Logro findByIdUsuarioAndTipoLogroNombre(Long idUsuario, String nombreTipoLogro);
}
