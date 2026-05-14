package com.madoscientista.logros.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.logros.client.HistorialClient;
import com.madoscientista.logros.dto.eventoDTO.RequestEventoDTO;
import com.madoscientista.logros.model.Logro;
import com.madoscientista.logros.model.Recuento;
import com.madoscientista.logros.model.TipoLogro;
import com.madoscientista.logros.repository.LogroRepository;
import com.madoscientista.logros.repository.TipoLogroRepository;

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

                    RequestEventoDTO evento = new RequestEventoDTO();
                    evento.setIdTipoEvento(LOGRO_COMPLETADO);
                    evento.setIdUsuarioOrigen(r.getIdUsuario());
                    List<Long> destinos = new ArrayList<>();
                    destinos.add(r.getIdUsuario());
                    evento.setIdUsuarioDestino(destinos);
                    hClient.postEvento(evento);
                }
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
        return 0;
    }
}
