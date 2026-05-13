package com.madoscientista.logros.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.logros.model.Recuento;
import com.madoscientista.logros.repository.RecuentoRepository;

import jakarta.transaction.Transactional;

@Service
public class RecuentoService {

    @Autowired
    private RecuentoRepository recuentoRepo;

    private Recuento obtenerOCrear(Long idUsuario) {
        return recuentoRepo.findByIdUsuario(idUsuario).orElseGet(() -> {
            Recuento nuevo = new Recuento();
            nuevo.setIdUsuario(idUsuario);
            nuevo.setNEjerciciosCreados(0L);
            nuevo.setNEjerciciosCompartidos(0L);
            nuevo.setNComunidades(0L);
            return recuentoRepo.save(nuevo);
        });
    }

    public Map<String, String> toMap(Recuento r) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("ejerciciosCreados", r.getNEjerciciosCreados().toString());
        map.put("ejerciciosCompartidos", r.getNEjerciciosCompartidos().toString());
        map.put("nComunidades", r.getNComunidades().toString());
        return map;
    }

    @Transactional
    public Recuento incrementarEjerciciosCreados(Long idUsuario) {
        Recuento r = obtenerOCrear(idUsuario);
        r.setNEjerciciosCreados(r.getNEjerciciosCreados() + 1);
        return recuentoRepo.save(r);
    }

    @Transactional
    public Recuento incrementarEjerciciosCompartidos(Long idUsuario, int cantidad) {
        Recuento r = obtenerOCrear(idUsuario);
        r.setNEjerciciosCompartidos(r.getNEjerciciosCompartidos() + cantidad);
        return recuentoRepo.save(r);
    }

    @Transactional
    public Recuento incrementarComunidad(Long idUsuario, int cantidad) {
        Recuento r = obtenerOCrear(idUsuario);
        r.setNComunidades(r.getNComunidades() + cantidad);
        return recuentoRepo.save(r);
    }
}
