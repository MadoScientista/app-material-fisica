package com.madoscientista.logros.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.logros.model.Logro;
import com.madoscientista.logros.model.TipoLogro;
import com.madoscientista.logros.repository.LogroRepository;
import com.madoscientista.logros.repository.TipoLogroRepository;

@Service
public class LogroService {

    // Inyecta el repositorio de logros
    @Autowired
    private LogroRepository lRepo;

    // Inyecta el repositorio de tipo de logros
    @Autowired
    private TipoLogroRepository tlRepo;

    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    // Obtiene un logro según su ID
    public Logro getLogroById(Long idLogro){
        return lRepo.findById(idLogro).orElse(null);
    }

    // Obtiene todos los logros de un usuario en particular
    public List<Logro> getLogrosByIdUsuario(Long idUsuario){
        return lRepo.findAllByIdUsuario(idUsuario);
    }


    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // Crea un nuevo logro
    public Logro postLogro(Logro l){
        return lRepo.save(l);
    }

    // Crea todos los logros sin completar para un usuario nuevo
    public List<Logro> postIniciarLogrosUsuarioNuevo(Long idUsuario){

        // Recupera todos los tipos de logros disponibles
        List<TipoLogro> tiposLogros = tlRepo.findAll();

        // Genera y guarda la lista de logros
        List<Logro> listaLogros = new ArrayList<>();
        for(TipoLogro tl : tiposLogros){
            Logro l = new Logro();
            l.setIdUsuario(idUsuario);
            l.setTipoLogro(tl);
            l.setCompletado(false);

            listaLogros.add(postLogro(l));
        }


        return listaLogros;
    }

    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    // Actualiza el estado de logro a completado
    public Logro putLogroCompletado(Long idUsuario, String nombreTipoLogro){
        Logro logroActual = lRepo.findByIdUsuarioAndTipoLogroNombre(idUsuario, nombreTipoLogro);

        if(logroActual == null){
            return null;
        }

        logroActual.setCompletado(true);
        return logroActual;
    }


}
