package com.madoscientista.notificador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madoscientista.notificador.model.Notificacion;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long>{

    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------
    List<Notificacion> findByIdUsuarioDestinoAndLeidoFalse(Long idUsuario);
    List<Notificacion> findByIdUsuarioDestinoAndLeidoTrue(Long idUsuario);
    List<Notificacion> findByIdUsuarioDestino(Long idUsuario);
}
