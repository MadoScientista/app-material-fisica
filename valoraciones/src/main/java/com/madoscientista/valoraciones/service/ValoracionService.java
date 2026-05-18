package com.madoscientista.valoraciones.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.valoraciones.client.HistorialClient;
import com.madoscientista.valoraciones.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.valoraciones.dto.ValoracionDTO.PromedioValoracionDTO;
import com.madoscientista.valoraciones.dto.ValoracionDTO.RequestValoracionDTO;
import com.madoscientista.valoraciones.model.Valoracion;
import com.madoscientista.valoraciones.repository.ValoracionRepository;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ValoracionService {

    @Autowired
    private ValoracionRepository valoracionRepo;

    @Autowired
    private HistorialClient hClient;

    private static final Long VALORACION_CREADA = 13L;
    private static final Long VALORACION_ACTUALIZADA = 14L;
    private static final Long VALORACION_ELIMINADA = 15L;

    public Valoracion getValoracionById(Long id) {
        return valoracionRepo.findById(id).orElse(null);
    }

    public List<Valoracion> getValoracionesByEjercicio(Long idEjercicio) {
        return valoracionRepo.findByIdEjercicio(idEjercicio);
    }

    public List<Valoracion> getValoracionesByUsuario(Long idUsuario) {
        return valoracionRepo.findByIdUsuario(idUsuario);
    }

    public PromedioValoracionDTO getPromedioByEjercicio(Long idEjercicio) {
        List<Valoracion> valoraciones = valoracionRepo.findByIdEjercicio(idEjercicio);
        if (valoraciones.isEmpty()) {
            return new PromedioValoracionDTO(idEjercicio, 0.0, 0L);
        }
        Double promedio = valoraciones.stream()
            .mapToInt(Valoracion::getPuntuacion)
            .average()
            .orElse(0.0);
        return new PromedioValoracionDTO(idEjercicio, promedio, (long) valoraciones.size());
    }

    public Valoracion postValoracion(RequestValoracionDTO request) {
        Valoracion existente = valoracionRepo
            .findByIdEjercicioAndIdUsuario(request.getIdEjercicio(), request.getIdUsuario())
            .orElse(null);
        if (existente != null) {
            return null;
        }

        Valoracion valoracion = new Valoracion();
        valoracion.setIdEjercicio(request.getIdEjercicio());
        valoracion.setIdUsuario(request.getIdUsuario());
        valoracion.setPuntuacion(request.getPuntuacion());
        valoracion.setComentario(request.getComentario());

        Valoracion creada = valoracionRepo.save(valoracion);

        List<Long> idUsuarioDestino = new ArrayList<>();
        idUsuarioDestino.add(request.getIdUsuario());
        registrarEvento(request.getIdUsuario(), idUsuarioDestino, VALORACION_CREADA);

        return creada;
    }

    public Valoracion putValoracion(Long id, RequestValoracionDTO request) {
        Valoracion valoracion = valoracionRepo.findById(id).orElse(null);
        if (valoracion == null) {
            return null;
        }

        valoracion.setPuntuacion(request.getPuntuacion());
        valoracion.setComentario(request.getComentario());

        Valoracion actualizada = valoracionRepo.save(valoracion);

        List<Long> idUsuarioDestino = new ArrayList<>();
        idUsuarioDestino.add(valoracion.getIdUsuario());
        registrarEvento(valoracion.getIdUsuario(), idUsuarioDestino, VALORACION_ACTUALIZADA);

        return actualizada;
    }

    public boolean deleteValoracion(Long id) {
        Valoracion valoracion = valoracionRepo.findById(id).orElse(null);
        if (valoracion == null) {
            return false;
        }

        valoracionRepo.deleteById(id);

        List<Long> idUsuarioDestino = new ArrayList<>();
        idUsuarioDestino.add(valoracion.getIdUsuario());
        registrarEvento(valoracion.getIdUsuario(), idUsuarioDestino, VALORACION_ELIMINADA);

        return true;
    }

    private void registrarEvento(Long idUsuarioOrigen, List<Long> idUsuarioDestino, Long idTipoEvento) {
        RequestEventoDTO eventoDTO = new RequestEventoDTO();
        eventoDTO.setIdTipoEvento(idTipoEvento);
        eventoDTO.setIdUsuarioDestino(idUsuarioDestino);
        eventoDTO.setIdUsuarioOrigen(idUsuarioOrigen);
        try{
            hClient.postEvento(eventoDTO);
        }catch(FeignException e){
            log.warn("Error de comunicación con microservicio historial. Evento no registrado");
        }
    }
}
