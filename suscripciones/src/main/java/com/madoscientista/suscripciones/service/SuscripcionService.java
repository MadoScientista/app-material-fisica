package com.madoscientista.suscripciones.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.suscripciones.model.Suscripcion;
import com.madoscientista.suscripciones.model.TipoSuscripcion;
import com.madoscientista.suscripciones.repository.SuscripcionRepository;

@Service
public class SuscripcionService {
    
    @Autowired
    private SuscripcionRepository susucripcionRepo;

    @Autowired
    private TipoSuscripcionService tipoSuscripcionService;

    // ------------------------------------------------------------
    // ---------------------- Sección GET -------------------------
    // ------------------------------------------------------------

    // Retorna una lista de suscripciones activas
    public List<Suscripcion> getSuscripcionesActivas() {
        return susucripcionRepo.findByActivo(true);
    }

    // Retorna una lista con los IDs de usuarios con suscripciones activas
    public List<Long> getUsuariosConSuscripcionesActivas() {
        return susucripcionRepo.findByActivo(true).stream()
            .map(Suscripcion::getIdUsuario)
            .collect(Collectors.toList());
    }

    // Retorna la suscripción de un usuario por us DI
    public Suscripcion getSuscripcionByUsuarioId(Long idUsuario) {
        return susucripcionRepo.findByIdUsuario(idUsuario).orElse(null);
    }

    // Retorna una lista de suscripciones por una lista de IDs de usuario
    public List<Suscripcion> getSuscripcionesByUsuarioIds(List<Long> idUsuarios) {
        return susucripcionRepo.findByIdUsuarioIn(idUsuarios);
    }

    // Retorna la cantidad máxima de ejercicios permitidos para un usuario según su tipo de suscripción
    public Long getMaxEjerciciosByUsuarioId(Long idUsuario) {
        Suscripcion suscripcion = susucripcionRepo.findByIdUsuario(idUsuario).orElse(null);
        if (suscripcion != null && suscripcion.isActivo()) {
            TipoSuscripcion tipoSub = suscripcion.getTipoSuscripcion();
            return tipoSub.getNMaxEjercicios();
        }
        return 0L; // Retorna 0 si el usuario no tiene una suscripción activa
    }   

    
    // ------------------------------------------------------
    // ---------------- Sección POST ------------------------
    // ------------------------------------------------------

    // Crea una nueva suscripción para un usuario según su ID
    public Suscripcion postSuscripcion(Long idUsuario, String nombreSuscripcion){
        TipoSuscripcion tipoSub = tipoSuscripcionService.getByNombre(nombreSuscripcion);
        if(tipoSub == null){
            return null;
        }

        Suscripcion nueavaSuscripcion = new Suscripcion();
        nueavaSuscripcion.setIdUsuario(idUsuario);
        nueavaSuscripcion.setActivo(true);
        nueavaSuscripcion.setTipoSuscripcion(tipoSub);

        return susucripcionRepo.save(nueavaSuscripcion);
    }

    // ------------------------------------------------------
    // ---------------- Sección PUT -------------------------
    // ------------------------------------------------------

    // Cancela una suscripción de un usuario por el ID del usuario
    public Suscripcion cancelarSuscripcion(Long idUsuario) {
        Suscripcion suscripcion = susucripcionRepo.findByIdUsuario(idUsuario).orElse(null);
        if (suscripcion != null) {
            suscripcion.setActivo(false);
            return susucripcionRepo.save(suscripcion);
        }
        return null;
    }

}
