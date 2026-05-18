package com.madoscientista.logros.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    // Retorna la lista de logros disponibles en DB
    public List<Logro> getLogros(){
        return lRepo.findAll();
    }


    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // Crea un nuevo logro
    public Logro postLogro(Logro l){
        return lRepo.save(l);
    }

    // Crea los logros faltantes para un usuario según los tipos de logro disponibles
    public List<Logro> postSincronizarLogrosUsuario(Long idUsuario){

        List<TipoLogro> todosTipos = tlRepo.findAll();
        List<Logro> logrosExistentes = lRepo.findAllByIdUsuario(idUsuario);
        
        // IDs de tipoLogro que el usuario ya tiene
        Set<Long> idsExistentes = logrosExistentes.stream()
                .map(l -> l.getTipoLogro().getIdTipoLogro())
                .collect(Collectors.toSet());

        // Crear solo los que faltan
        List<Logro> nuevosLogros = new ArrayList<>();
        for (TipoLogro tl : todosTipos) {
            if (!idsExistentes.contains(tl.getIdTipoLogro())) {
                Logro l = new Logro();
                l.setIdUsuario(idUsuario);
                l.setTipoLogro(tl);
                l.setCompletado(false);
                nuevosLogros.add(l);
            }
        }

        // Retornar todo (viejos + nuevos)
        List<Logro> resultado = new ArrayList<>(logrosExistentes);
        resultado.addAll(lRepo.saveAll(nuevosLogros));

        return resultado;

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
        return lRepo.save(logroActual);
    }


}
