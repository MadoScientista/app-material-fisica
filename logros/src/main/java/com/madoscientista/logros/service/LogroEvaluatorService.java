package com.madoscientista.logros.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.logros.client.HistorialClient;
import com.madoscientista.logros.dto.eventoDTO.RequestEventoDTO;
import com.madoscientista.logros.model.Logro;
import com.madoscientista.logros.model.Recuento;
import com.madoscientista.logros.model.TipoLogro;
import com.madoscientista.logros.repository.LogroRepository;
import com.madoscientista.logros.repository.TipoLogroRepository;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LogroEvaluatorService {

    private static final Long LOGRO_COMPLETADO = 12L;

    @Autowired
    private TipoLogroRepository tlRepo;

    @Autowired
    private LogroRepository lRepo;

    @Autowired
    private HistorialClient hClient;

    public void evaluar(Recuento r) {
        List<TipoLogro> tipos = tlRepo.findAll();
        for (TipoLogro tl : tipos) {
            if (condicionCumplida(tl, r)) {
                Logro logro = lRepo.findByIdUsuarioAndTipoLogroNombre(r.getIdUsuario(), tl.getNombre());
                if (logro != null && !logro.isCompletado()) {
                    logro.setCompletado(true);
                    logro.setFechaCompletado(LocalDateTime.now());
                    lRepo.save(logro);

                    // Intenta comunicar evento al ms historial
                    registrarEvento(r.getIdUsuario(), LOGRO_COMPLETADO);
                }
            }
        }
    }

    public void evaluarVariosUsuarios(List<Recuento> recuentos) {
        if (recuentos.isEmpty()) return;

        List<TipoLogro> tipos = tlRepo.findAll();

        List<Long> ids = new ArrayList<>();
        for (Recuento r : recuentos) {
            ids.add(r.getIdUsuario());
        }
        List<Logro> logrosExistentes = lRepo.findAllByIdUsuarioIn(ids);

        Map<Long, Map<String, Logro>> index = new HashMap<>();
        for (Logro l : logrosExistentes) {
            long idUsuario = l.getIdUsuario();
            if (!index.containsKey(idUsuario)) {
                index.put(idUsuario, new HashMap<>());
            }
            index.get(idUsuario).put(l.getTipoLogro().getNombre(), l);
        }

        List<Logro> aActualizar = new ArrayList<>();
        List<RequestEventoDTO> eventos = new ArrayList<>();

        for (Recuento r : recuentos) {
            Map<String, Logro> logrosUsuario = index.get(r.getIdUsuario());
            if (logrosUsuario == null) continue;

            for (TipoLogro tl : tipos) {
                if (condicionCumplida(tl, r)) {
                    Logro logro = logrosUsuario.get(tl.getNombre());
                    if (logro != null && !logro.isCompletado()) {
                        logro.setCompletado(true);
                        logro.setFechaCompletado(LocalDateTime.now());
                        aActualizar.add(logro);

                        RequestEventoDTO e = new RequestEventoDTO();
                        e.setIdTipoEvento(LOGRO_COMPLETADO);
                        e.setIdUsuarioOrigen(r.getIdUsuario());
                        List<Long> destinos = new ArrayList<>();
                        destinos.add(r.getIdUsuario());
                        e.setIdUsuarioDestino(destinos);
                        eventos.add(e);
                    }
                }
            }
        }

        if (!aActualizar.isEmpty()) {
            lRepo.saveAll(aActualizar);
        }
        if (!eventos.isEmpty()) {
            try{
                hClient.postEventos(eventos);
            }catch(FeignException e){
                log.warn("Error de comunicación con microservicio historial. Evento no registrado");
            }
        }
    }

    private boolean condicionCumplida(TipoLogro tl, Recuento r) {
        if (tl.getCriterio() == null || tl.getOperador() == null || tl.getUmbral() == null) {
            return false;
        }

        long valorReal = obtenerValor(r, tl.getCriterio());
        String op = tl.getOperador();
        long umbral = tl.getUmbral();

        if (op.equals(">=")) return valorReal >= umbral;
        if (op.equals(">"))  return valorReal > umbral;
        if (op.equals("==")) return valorReal == umbral;
        if (op.equals("<=")) return valorReal <= umbral;
        if (op.equals("<"))  return valorReal < umbral;
        return false;
    }

    private long obtenerValor(Recuento r, String criterio) {
        if (criterio.equals("nEjerciciosCreados"))    return r.getNEjerciciosCreados();
        if (criterio.equals("nEjerciciosCompartidos")) return r.getNEjerciciosCompartidos();
        if (criterio.equals("nComunidades"))           return r.getNComunidades();
        if (criterio.equals("nItemsCreados"))          return r.getNItemsCreados();
        if (criterio.equals("nMaterialesCreados"))     return r.getNMaterialesCreados();
        return 0;
    }



    // --------------------------------------------------------
    // ------------------ Sección EVENTOS ---------------------
    // --------------------------------------------------------

    private void registrarEvento(Long idUsuarioOrigen, Long idTipoEvento) {
        RequestEventoDTO eventoDTO = new RequestEventoDTO();
        eventoDTO.setIdTipoEvento(idTipoEvento);
        eventoDTO.setIdUsuarioOrigen(idUsuarioOrigen);
        List<Long> destinos = new ArrayList<>();
        destinos.add(idUsuarioOrigen);
        eventoDTO.setIdUsuarioDestino(destinos);
        try{
            hClient.postEvento(eventoDTO);
        }catch(FeignException e){
            log.warn("Error de comunicación con microservicio historial. Evento no registrado");
        }
    }
}
