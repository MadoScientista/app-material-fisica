package com.madoscientista.comunidades.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.comunidades.model.Comunidad;
import com.madoscientista.comunidades.repository.ComunidadRepository;

@Service
public class ComunidadService {

    @Autowired
    private ComunidadRepository cRepo;


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
            return cRepo.save(comunidad);
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
            return cRepo.save(comunidad);
        }

        return null;
    }


    // Elimina a un miembro de una comunidad
    public Comunidad eliminarUsuarioDeComunidad(Long idComunidad, Long idUsuario){
        Comunidad comunidad = getComunidadById(idComunidad);

        if(comunidad != null && comunidad.getIdMiembros().contains(idUsuario)){
            comunidad.getIdMiembros().remove(idUsuario);
            return cRepo.save(comunidad);
        }

        return null;
    }

    
    // Agrega una lista de miembros a una comunidad
    public Comunidad agregarMiembrosAComunidad(Long idComunidad, Set<Long> idMiembros){
        Comunidad comunidad = getComunidadById(idComunidad);

        if(comunidad != null){
            comunidad.getIdMiembros().addAll(idMiembros);
            return cRepo.save(comunidad);
        }

        return null;
    }


    // Elimina una lista de miembros a una comunidad
    public Comunidad eliminarMiembrosDeComunidad(Long idComunidad, Set<Long> idMiembros){
        Comunidad comunidad = getComunidadById(idComunidad);

        if(comunidad != null){
            comunidad.getIdMiembros().removeAll(idMiembros);
            return cRepo.save(comunidad);
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

}
