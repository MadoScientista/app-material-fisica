package com.madoscientista.suscripciones.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.suscripciones.client.HistorialClient;
import com.madoscientista.suscripciones.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.suscripciones.model.Suscripcion;
import com.madoscientista.suscripciones.model.TipoSuscripcion;
import com.madoscientista.suscripciones.repository.SuscripcionRepository;

@Service
public class SuscripcionService {
    
    // Inyección repositorio de suscripciones
    @Autowired
    private SuscripcionRepository suscripcionRepo;

    // Inyección servicio de tipos de suscripción
    @Autowired
    private TipoSuscripcionService tipoSuscripcionService;

    // Inyección cliente ms de historial
    @Autowired
    private HistorialClient hClient;

    // Por ahora los ID de los eventos para comunicación con el ms de historial
    // son constantes coherentes con la base de datos del ms de historial.
    private static final Long SUSCRIPCION_NUEVA = 8L;
    private static final Long SUSCRIPCION_CANCELADA = 9L;
    private static final Long SUSCRIPCION_ACTUALIZADA = 11L;

    // ------------------------------------------------------------
    // ---------------------- Sección GET -------------------------
    // ------------------------------------------------------------

    // Retorna una lista de suscripciones activas
    public List<Suscripcion> getSuscripcionesActivas() {
        return suscripcionRepo.findByActivo(true);
    }

    // Retorna una lista con los IDs de usuarios con suscripciones activas
    public List<Long> getUsuariosConSuscripcionesActivas() {
        return suscripcionRepo.findByActivo(true).stream()
            .map(Suscripcion::getIdUsuario)
            .collect(Collectors.toList());
    }

    // Retorna la suscripción de un usuario por us DI
    public Suscripcion getSuscripcionByUsuarioId(Long idUsuario) {
        return suscripcionRepo.findByIdUsuario(idUsuario).orElse(null);
    }

    // Retorna una lista de suscripciones por una lista de IDs de usuario
    public List<Suscripcion> getSuscripcionesByUsuarioIds(List<Long> idUsuarios) {
        return suscripcionRepo.findByIdUsuarioIn(idUsuarios);
    }

    // Retorna la cantidad máxima de ejercicios permitidos para un usuario según su tipo de suscripción
    public Long getMaxEjerciciosByUsuarioId(Long idUsuario) {
        Suscripcion suscripcion = suscripcionRepo.findByIdUsuarioActivoTrue(idUsuario).orElse(null);
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
        Suscripcion suscripcionActual = suscripcionRepo.findByIdUsuarioActivoTrue(idUsuario).orElse(null);

        if(tipoSub == null || suscripcionActual != null){
            return null;
        }

        Suscripcion nueavaSuscripcion = new Suscripcion();
        nueavaSuscripcion.setIdUsuario(idUsuario);
        nueavaSuscripcion.setActivo(true);
        nueavaSuscripcion.setTipoSuscripcion(tipoSub);

        Suscripcion suscripcionCreada = suscripcionRepo.save(nueavaSuscripcion);

        // Comunica al ms historial el evento de nueva suscripción
        registrarEvento(
            idUsuario, 
            SUSCRIPCION_NUEVA, 
            "El usuario ID: " + idUsuario + "ha creado una suscripción de tipo: " + nombreSuscripcion);

        return suscripcionCreada;
    }

    // ------------------------------------------------------
    // ---------------- Sección PUT -------------------------
    // ------------------------------------------------------

    // Cancela una suscripción de un usuario por el ID del usuario
    public Suscripcion cancelarSuscripcion(Long idUsuario) {
        Suscripcion suscripcion = suscripcionRepo.findByIdUsuarioActivoTrue(idUsuario).orElse(null);

        if (suscripcion != null && suscripcion.isActivo()) {
            suscripcion.setActivo(false);
            suscripcionRepo.save(suscripcion);
            Suscripcion subGratuita = postSuscripcion(idUsuario, "GRATUITA");

            // Comunica al ms historial el evento de cancelación de suscripción
            registrarEvento(
                idUsuario, 
                SUSCRIPCION_CANCELADA, 
                "El usuario ID: " + idUsuario + "ha cancelado su suscripción.");

            return subGratuita;
        }
        return null;
    }

    // Actualiza el tipo de suscripción de un usuario por su ID
    public Suscripcion actualizarSuscripcion(Long idUsuario, String nuevoTipoSuscripcion) {
        Suscripcion subActual = suscripcionRepo.findByIdUsuarioActivoTrue(idUsuario).orElse(null);
        TipoSuscripcion tipoSub = tipoSuscripcionService.getByNombre(nuevoTipoSuscripcion);

        if (subActual != null && subActual.isActivo() && tipoSub != null) {

            // Da de baja la suscripción actual
            subActual.setActivo(false);
            suscripcionRepo.save(subActual);

            // Comunica al ms historial el evento de actualización de suscripción
            registrarEvento(
                idUsuario, 
                SUSCRIPCION_CANCELADA, 
                "El usuario ID: " + idUsuario + "ha cancelado su suscripción tipo: " + nuevoTipoSuscripcion);


            Suscripcion subActualizada = postSuscripcion(idUsuario, nuevoTipoSuscripcion);

            // Comunica al ms historial el evento de actualización de suscripción
            registrarEvento(
                idUsuario, 
                SUSCRIPCION_ACTUALIZADA, 
                "El usuario ID: " + idUsuario + "ha actualizado su suscripción al tipo: " + nuevoTipoSuscripcion);

            return subActualizada;
        }
        return null;
    }

    // --------------------------------------------------------
    // ------------------ Sección EVENTOS ---------------------
    // --------------------------------------------------------

    private void registrarEvento(Long idUsuario, Long idTipoEvento, String descripcion) {
        RequestEventoDTO eventoDTO = new RequestEventoDTO();
        eventoDTO.setIdUsuario(idUsuario);
        eventoDTO.setIdTipoEvento(idTipoEvento);
        eventoDTO.setDescripcion(descripcion);
        hClient.postEvento(eventoDTO);
    }

}
