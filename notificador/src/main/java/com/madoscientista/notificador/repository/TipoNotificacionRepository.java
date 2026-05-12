package com.madoscientista.notificador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.madoscientista.notificador.model.TipoNotificacion;

@Repository
public interface TipoNotificacionRepository extends JpaRepository<TipoNotificacion, Long>{

    List<TipoNotificacion> findAllByIdTipoEvento(Long idTipoEvento);
}
