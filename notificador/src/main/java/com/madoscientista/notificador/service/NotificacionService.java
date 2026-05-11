package com.madoscientista.notificador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.notificador.model.Notificacion;
import com.madoscientista.notificador.repository.NotificacionRepository;

@Service
public class NotificacionService {

    // Inyección del repositorio de notificaciones
    @Autowired
    private NotificacionRepository notificacionRepo;


    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------
    
    // Retorna una lista de notificaciones no leídas por un usuario según el ID del usuario
    public List<Notificacion> getNotificacionesNoLeidasByUsuarioId(Long idUsuario){
        return notificacionRepo.findByIdUsuarioAndLeidoFalse(idUsuario);
    }

     // Retorna una lista de notificaciones leídas por un usuario según el ID del usuario
     public List<Notificacion> getNotificacionesLeidasByUsuarioId(Long idUsuario){
        return notificacionRepo.findByIdUsuarioAndLeidoTrue(idUsuario);
    }

     // Retorna una lista de todas las notificaciones por un usuario según el ID del usuario
     public List<Notificacion> getAllNotificacionesByUsuarioId(Long idUsuario){
        return notificacionRepo.findByIdUsuario(idUsuario);
    }

     // Retorna una notificación específica por su ID
     public Notificacion getNotificacionById(Long idNotificacion){
        return notificacionRepo.findById(idNotificacion).orElse(null);
    }

    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // Crea una nueva notificacion
    public Notificacion postNotificacion(Notificacion notificacion){

        if(notificacion == null) {
            throw new RuntimeException("Se requieren datos de notificación");
        }

        return notificacionRepo.save(notificacion);
    }

}
