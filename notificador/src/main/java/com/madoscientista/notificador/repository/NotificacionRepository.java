package com.madoscientista.notificador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.madoscientista.notificador.model.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long>{

    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------
    List<Notificacion> findByIdUsuarioDestinoAndLeidoFalse(Long idUsuario);
    List<Notificacion> findByIdUsuarioDestinoAndLeidoTrue(Long idUsuario);
    List<Notificacion> findByIdUsuarioDestino(Long idUsuario);
}
