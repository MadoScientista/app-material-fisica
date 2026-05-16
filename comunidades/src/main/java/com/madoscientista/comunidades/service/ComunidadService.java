package com.madoscientista.comunidades.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.comunidades.client.HistorialClient;
import com.madoscientista.comunidades.client.LogrosClient;
import com.madoscientista.comunidades.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.comunidades.model.Comunidad;
import com.madoscientista.comunidades.repository.ComunidadRepository;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ComunidadService {

    @Autowired
    private ComunidadRepository cRepo;

    @Autowired
    private HistorialClient hClient;

    @Autowired
    private LogrosClient lClient;

    private static final Long EVENTO_COMUNIDAD_CREADA = 16L;
    private static final Long EVENTO_MIEMBRO_AGREGADO = 17L;
    private static final Long EVENTO_MIEMBRO_ELIMINADO = 18L;

    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    // Retorna las comunidades disponibles
    public List<Comunidad> getComunidades(){
        return cRepo.findAll();
    }

    // Retorna una comunidad filtrada por su id
    public Comunidad getComunidadById(Long idComunidad){
        return cRepo.findById(idComunidad).orElse(null);
    }

    // Retorna la lista de usuarios que pertenecen a una comunidad
    public Set<Long> getMiembrosDeComunidad(Long idComunidad){
        Comunidad comunidad = getComunidadById(idComunidad);

        if(comunidad != null){
            return comunidad.getIdMiembros();
        }

        return null;
    }

    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // Crea una comunidad nueva
    public Comunidad postComunidad(Comunidad comunidad){
        if(comunidad != null){

            // Añade al creador como miembro de la comunidad
            comunidad.getIdMiembros().add(comunidad.getIdUsuarioCreador());

            Comunidad nuevaComunidad = cRepo.save(comunidad);

            // Intenta registrar el evento en el ms historial
            try{
                registrarEvento(
                    comunidad.getIdUsuarioCreador(),
                    new ArrayList<>(comunidad.getIdMiembros()),
                    EVENTO_COMUNIDAD_CREADA
                );
            }catch(FeignException e){
                log.debug("No se pudo comunicar el evento - crear comunidad -", e);
            }

            // Intenta actualizar el recuento para el ms logro
            try{
                lClient.postIncrementarComunidad(comunidad.getIdUsuarioCreador(), 1);
            }catch(FeignException e){
                log.debug("No se pudo solicitar el aumento del recuento", e);
            }

            return nuevaComunidad;
        }

        return null;
    }

    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    // Agregar un miembro a una comunidad
    public Comunidad agregarMiembroAComunidad(Long idComunidad, Long idUsuario){
        Comunidad comunidad = getComunidadById(idComunidad);

        if(comunidad != null && !comunidad.getIdMiembros().contains(idUsuario)){
            comunidad.getIdMiembros().add(idUsuario);
            cRepo.save(comunidad);

            ArrayList<Long> soloUsuario = new ArrayList<>();
            soloUsuario.add(idUsuario);

            // Intenta comunicar el evento al ms historial
            try{
                registrarEvento(idUsuario, soloUsuario, EVENTO_MIEMBRO_AGREGADO);
            }catch(FeignException e){
                log.debug("No se pudo comunicar el evento - agregar miembro -", e);
            }

            // Intenta comunicar el aumento del recuento al ms logros
            try{
                lClient.postIncrementarComunidad(idUsuario, 1);
            }catch(FeignException e){
                log.debug("No se pudo solicitar el aumento del recuento", e);
            }

            return comunidad;
        }

        return null;
    }

    // Elimina a un miembro de una comunidad
    public Comunidad eliminarUsuarioDeComunidad(Long idComunidad, Long idUsuario){
        Comunidad comunidad = getComunidadById(idComunidad);

        if(comunidad != null && comunidad.getIdMiembros().contains(idUsuario)){
            comunidad.getIdMiembros().remove(idUsuario);
            cRepo.save(comunidad);

            ArrayList<Long> soloUsuario = new ArrayList<>();
            soloUsuario.add(idUsuario);

            // Intenta comunicar el evento al ms historial
            try{
                registrarEvento(idUsuario, soloUsuario, EVENTO_MIEMBRO_ELIMINADO);
            }catch(FeignException e){
                log.debug("No se pudo comunicar el evento - eliminar miembro -", e);
            }

            return comunidad;
        }

        return null;
    }

    // Agrega una lista de miembros a una comunidad
    public Comunidad agregarMiembrosAComunidad(Long idComunidad, Set<Long> idMiembros){
        Comunidad comunidad = getComunidadById(idComunidad);

        if(comunidad != null){
            comunidad.getIdMiembros().addAll(idMiembros);
            cRepo.save(comunidad);

            // Intenta comunicar los eventos al ms historial
            try{
                registrarVariosEventos(
                    comunidad.getIdUsuarioCreador(),
                    new ArrayList<>(idMiembros),
                    EVENTO_MIEMBRO_AGREGADO
                );
            }catch(FeignException e){
                log.debug("No se pudo comunicar el evento - agregar miembros -", e);
            }

            // Intenta comunicar el aumento del recuento al ms logros
            try{
                lClient.postIncrementarComunidades(idMiembros);
            }catch(FeignException e){
                log.debug("No se pudo solicitar el aumento del recuento", e);
            }

            return comunidad;
        }

        return null;
    }

    // Elimina una lista de miembros a una comunidad
    public Comunidad eliminarMiembrosDeComunidad(Long idComunidad, Set<Long> idMiembros){
        Comunidad comunidad = getComunidadById(idComunidad);

        if(comunidad != null){
            comunidad.getIdMiembros().removeAll(idMiembros);
            cRepo.save(comunidad);

            // Intenta comunicar los eventos al ms historial
            try{
                registrarVariosEventos(
                    comunidad.getIdUsuarioCreador(),
                    new ArrayList<>(idMiembros),
                    EVENTO_MIEMBRO_ELIMINADO
                );
            }catch(FeignException e){
                log.debug("No se pudo comunicar el evento - eliminar miembros -", e);
            }

            return comunidad;
        }

        return null;
    }

    // --------------------------------------------------------
    // ------------------ Sección DELETE ----------------------
    // --------------------------------------------------------

    public Comunidad deleteComunidadById(Long idComunidad){
        Comunidad comunidad = getComunidadById(idComunidad);

        if(comunidad != null){
            cRepo.delete(comunidad);
        }

        return comunidad;
    }


    // --------------------------------------------------------
    // ------------------ Sección EVENTOS ---------------------
    // --------------------------------------------------------

    private void registrarEvento(Long idUsuarioOrigen, List<Long> idUsuarioDestino, Long idTipoEvento) {
        RequestEventoDTO eventoDTO = new RequestEventoDTO();
        eventoDTO.setIdTipoEvento(idTipoEvento);
        eventoDTO.setIdUsuarioDestino(idUsuarioDestino);
        eventoDTO.setIdUsuarioOrigen(idUsuarioOrigen);
        hClient.postEvento(eventoDTO);
    }

    private void registrarVariosEventos(Long idUsuarioOrigen, List<Long> idsUsuarioDestino, Long idTipoEvento) {
        List<RequestEventoDTO> eventos = new ArrayList<>();
        for (Long idDestino : idsUsuarioDestino) {
            RequestEventoDTO eventoDTO = new RequestEventoDTO();
            eventoDTO.setIdTipoEvento(idTipoEvento);
            eventoDTO.setIdUsuarioOrigen(idUsuarioOrigen);
            ArrayList<Long> destino = new ArrayList<>();
            destino.add(idDestino);
            eventoDTO.setIdUsuarioDestino(destino);
            eventos.add(eventoDTO);
        }
        hClient.postVariosEventos(eventos);
    }

}
