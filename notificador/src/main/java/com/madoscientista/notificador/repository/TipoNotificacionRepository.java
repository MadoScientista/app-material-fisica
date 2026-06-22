package com.madoscientista.notificador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madoscientista.notificador.model.TipoNotificacion;

public interface TipoNotificacionRepository extends JpaRepository<TipoNotificacion, Long>{

    List<TipoNotificacion> findAllByIdTipoEvento(Long idTipoEvento);
}
