package com.madoscientista.logos.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.madoscientista.logos.client.HistorialClient;
import com.madoscientista.logos.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.logos.model.Logo;
import com.madoscientista.logos.repository.LogoRepository;

import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogoService {

    private static final Long LOGO_CREADO = 25L;
    private static final Long LOGO_ACTUALIZADO = 26L;
    private static final Long LOGO_ELIMINADO = 27L;

    private final LogoRepository logoRepo;
    private final HistorialClient hClient;

    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    public List<Logo> getLogos() {
        return logoRepo.findAll();
    }

    public Logo getLogoById(Long idLogo) {
        return logoRepo.findById(idLogo).orElse(null);
    }

    public List<Logo> getLogoByIdUsuarioCreador(Long idUsuarioCreador) {
        return logoRepo.findAllByIdUsuarioCreador(idUsuarioCreador);
    }

    public List<Logo> getLogosByListId(Set<Long> ids) {
        return logoRepo.findByIdLogoIn(ids);
    }

    public List<Logo> getLogosByListIdUsuarioCreador(Set<Long> ids) {
        return logoRepo.findAllByIdUsuarioCreadorIn(ids);
    }

    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    @Transactional
    public Logo postLogo(Logo logo) {
        logo.setUrl(generarUrl(logo.getIdUsuarioCreador(), logo.getNombre()));
        Logo logoCreado = logoRepo.save(logo);

        try {
            registrarEvento(logoCreado.getIdUsuarioCreador(), LOGO_CREADO);
        } catch (FeignException e) {
            log.debug("No se pudo comunicar el evento - crear logo -", e);
        }

        return logoCreado;
    }

    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    @Transactional
    public Logo putLogo(Long idLogo, Logo logoActualizado) {
        Logo logoActual = getLogoById(idLogo);

        if (logoActual == null) {
            return null;
        }

        logoActual.setNombre(logoActualizado.getNombre());
        logoActual.setDescripcion(logoActualizado.getDescripcion());
        logoActual.setImagen(logoActualizado.getImagen());
        logoActual.setUrl(generarUrl(logoActual.getIdUsuarioCreador(), logoActualizado.getNombre()));

        Logo logoSave = logoRepo.save(logoActual);

        try {
            registrarEvento(logoSave.getIdUsuarioCreador(), LOGO_ACTUALIZADO);
        } catch (FeignException e) {
            log.debug("No se pudo comunicar el evento - actualizar logo -", e);
        }

        return logoSave;
    }

    // --------------------------------------------------------
    // ------------------ Sección DELETE ----------------------
    // --------------------------------------------------------

    @Transactional
    public Logo deleteLogoById(Long idLogo) {
        Logo logo = getLogoById(idLogo);

        if (logo != null) {
            logoRepo.delete(logo);

            try {
                registrarEvento(logo.getIdUsuarioCreador(), LOGO_ELIMINADO);
            } catch (FeignException e) {
                log.debug("No se pudo comunicar el evento - eliminar logo -", e);
            }
        }

        return logo;
    }

    @Transactional
    public List<Logo> deleteLogoByListId(Set<Long> ids) {
        List<Logo> logos = logoRepo.findByIdLogoIn(ids);

        if (!logos.isEmpty()) {
            logoRepo.deleteAll(logos);

            try {
                registrarEvento(logos.get(0).getIdUsuarioCreador(), LOGO_ELIMINADO);
            } catch (FeignException e) {
                log.debug("No se pudo comunicar el evento - eliminar logos -", e);
            }
        }

        return logos;
    }

    // --------------------------------------------------------
    // ------------------ Sección URL -------------------------
    // --------------------------------------------------------

    private String generarUrl(Long idUsuarioCreador, String nombre) {
        String nombreSanitizado = nombre != null
            ? nombre.toLowerCase().replaceAll("\\s+", "-").replaceAll("[^a-z0-9\\-]", "")
            : "sin-nombre";
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
        return "www." + idUsuarioCreador + "-" + nombreSanitizado + "-" + fecha + ".appef";
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
