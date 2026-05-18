package com.madoscientista.logros.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.logros.model.Recuento;
import com.madoscientista.logros.repository.RecuentoRepository;

import jakarta.transaction.Transactional;

@Service
public class RecuentoService {

    @Autowired
    private RecuentoRepository recuentoRepo;

    @Autowired
    private LogroEvaluatorService logroEvaluator;

    // Retorna la lista de recuentos disponible en DB
    public List<Recuento> getRecuentos(){
        return recuentoRepo.findAll();
    }


    public Recuento obtenerOCrear(Long idUsuario) {
        return recuentoRepo.findByIdUsuario(idUsuario).orElseGet(() -> {
            Recuento nuevo = new Recuento();
            nuevo.setIdUsuario(idUsuario);
            nuevo.setNEjerciciosCreados(0L);
            nuevo.setNEjerciciosCompartidos(0L);
            nuevo.setNComunidades(0L);
            nuevo.setNItemsCreados(0L);
            nuevo.setNMaterialesCreados(0L);
            return recuentoRepo.save(nuevo);
        });
    }

    private List<Recuento> obtenerOCrearVariosUsuarios(Set<Long> idUsuarios) {
        List<Recuento> existentes = recuentoRepo.findAllByIdUsuarioIn(idUsuarios);

        Set<Long> idsExistentes = new HashSet<>();
        for (Recuento r : existentes) {
            idsExistentes.add(r.getIdUsuario());
        }

        List<Recuento> nuevos = new ArrayList<>();
        for (Long idUsuario : idUsuarios) {
            if (!idsExistentes.contains(idUsuario)) {
                Recuento nuevo = new Recuento();
                nuevo.setIdUsuario(idUsuario);
                nuevo.setNEjerciciosCreados(0L);
                nuevo.setNEjerciciosCompartidos(0L);
                nuevo.setNComunidades(0L);
                nuevo.setNItemsCreados(0L);
                nuevo.setNMaterialesCreados(0L);
                nuevos.add(nuevo);
            }
        }

        if (!nuevos.isEmpty()) {
            recuentoRepo.saveAll(nuevos);
            existentes.addAll(nuevos);
        }

        return existentes;
    }

    public Map<String, String> toMap(Recuento r) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("ejerciciosCreados", r.getNEjerciciosCreados().toString());
        map.put("ejerciciosCompartidos", r.getNEjerciciosCompartidos().toString());
        map.put("comunidades", r.getNComunidades().toString());
        map.put("itemsCreados", r.getNItemsCreados().toString());
        map.put("materialesCreados", r.getNMaterialesCreados().toString());
        return map;
    }

    @Transactional
    public Recuento incrementarEjerciciosCreados(Long idUsuario) {
        Recuento r = obtenerOCrear(idUsuario);
        r.setNEjerciciosCreados(r.getNEjerciciosCreados() + 1);
        r = recuentoRepo.save(r);
        logroEvaluator.evaluar(r);
        return r;
    }

    @Transactional
    public Recuento incrementarEjerciciosCompartidos(Long idUsuario, int cantidad) {
        Recuento r = obtenerOCrear(idUsuario);
        r.setNEjerciciosCompartidos(r.getNEjerciciosCompartidos() + cantidad);
        r = recuentoRepo.save(r);
        logroEvaluator.evaluar(r);
        return r;
    }

    @Transactional
    public Recuento incrementarComunidad(Long idUsuario, int cantidad) {
        Recuento r = obtenerOCrear(idUsuario);
        r.setNComunidades(r.getNComunidades() + cantidad);
        r = recuentoRepo.save(r);
        logroEvaluator.evaluar(r);
        return r;
    }

    // Aumenta el contador de logro de comunidad para un conjunto de usuarios
    @Transactional
    public Recuento incrementarItemsCreados(Long idUsuario, int cantidad) {
        Recuento r = obtenerOCrear(idUsuario);
        r.setNItemsCreados(r.getNItemsCreados() + cantidad);
        r = recuentoRepo.save(r);
        logroEvaluator.evaluar(r);
        return r;
    }

    @Transactional
    public Recuento incrementarMaterialCreado(Long idUsuario) {
        Recuento r = obtenerOCrear(idUsuario);
        r.setNMaterialesCreados(r.getNMaterialesCreados() + 1);
        r = recuentoRepo.save(r);
        logroEvaluator.evaluar(r);
        return r;
    }

    @Transactional
    public List<Recuento> incrementarComunidadParaUsuarios(Set<Long> idUsuarios, int cantidad) {
        
        List<Recuento> recuentoUsuarios = new ArrayList<>();
        recuentoUsuarios = obtenerOCrearVariosUsuarios(idUsuarios);

        List<Recuento> nuevoRecuento = new ArrayList<>();

        for(Recuento r : recuentoUsuarios){
            r.setNComunidades(r.getNComunidades() + cantidad);
            nuevoRecuento.add(r);
        }

        nuevoRecuento = recuentoRepo.saveAll(nuevoRecuento);
        logroEvaluator.evaluarVariosUsuarios(nuevoRecuento);
        return nuevoRecuento;
    }
    
}
