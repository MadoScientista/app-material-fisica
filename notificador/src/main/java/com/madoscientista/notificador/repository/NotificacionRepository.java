package com.madoscientista.notificador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madoscientista.notificador.model.Notificacion;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long>{

}
