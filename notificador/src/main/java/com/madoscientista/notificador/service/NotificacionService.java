package com.madoscientista.notificador.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.madoscientista.notificador.client.UsuarioClient;
import com.madoscientista.notificador.dto.usuarioDTO.ResponseUsuarioDTO;
import com.madoscientista.notificador.model.Notificacion;
import com.madoscientista.notificador.repository.NotificacionRepository;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepo;
    private final UsuarioClient uClient;


    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------
    
    // Retorna una lista de notificaciones no leídas por un usuario según el ID del usuario
    public List<Notificacion> getNotificacionesNoLeidasByUsuarioId(Long idUsuario){
        return notificacionRepo.findByIdUsuarioDestinoAndLeidoFalse(idUsuario);
    }

    // Retorna una lista de notificaciones leídas por un usuario según el ID del usuario
    public List<Notificacion> getNotificacionesLeidasByUsuarioId(Long idUsuario){
        return notificacionRepo.findByIdUsuarioDestinoAndLeidoTrue(idUsuario);
    }

    // Retorna una lista de todas las notificaciones por un usuario según el ID del usuario
    public List<Notificacion> getAllNotificacionesByUsuarioId(Long idUsuario){
        return notificacionRepo.findByIdUsuarioDestino(idUsuario);
    }

     // Retorna una notificación específica por su ID
     public Notificacion getNotificacionById(Long idNotificacion){
        Notificacion n = notificacionRepo.findById(idNotificacion).orElse(null);
        n.setLeido(true);
        Notificacion nActualizada = notificacionRepo.save(n);
        return nActualizada;
    }

    // Retorna todas las notificaciones disponibles en DB
    public List<Notificacion> getNotificaciones(){
        return notificacionRepo.findAll();
    }

    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // Crea un conjunto de notificaciones
    public List<Notificacion> postNotificaciones(List<Notificacion> listaNotificaciones){
        // 1. Recolectar IDs únicos
        Set<Long> idsUnicos = new HashSet<>();
        for (Notificacion n : listaNotificaciones) {
            idsUnicos.add(n.getIdUsuarioOrigen());
            idsUnicos.add(n.getIdUsuarioDestino());
        }
        // 2. Poblar mapa con IDs como fallback
        Map<Long, String> mapaNombres = new HashMap<>();
        for (Notificacion n : listaNotificaciones) {
            mapaNombres.putIfAbsent(n.getIdUsuarioOrigen(), n.getIdUsuarioOrigen().toString());
            mapaNombres.putIfAbsent(n.getIdUsuarioDestino(), n.getIdUsuarioDestino().toString());
        }
        // 3. Intentar obtener nombres reales desde ms-usuarios (sobrescribe fallback si funciona)
        try {
            ResponseEntity<List<ResponseUsuarioDTO>> response = uClient.listUsuariosByIds(new ArrayList<>(idsUnicos));
            List<ResponseUsuarioDTO> listaUsuarios = response.getBody();
            if (listaUsuarios != null) {
                for (ResponseUsuarioDTO u : listaUsuarios) {
                    if (u != null && u.getNombreUsuario() != null) {
                        mapaNombres.put(u.getIdUsuario(), u.getNombreUsuario());
                    }
                }
            }
        } catch (FeignException e) {
            log.warn("ms-usuarios no disponible, usando IDs como nombre de usuario");
        }
        // 4. Procesar todas las notificaciones con el mapa
        for (Notificacion n : listaNotificaciones) {
            n.setLeido(false);
            String mensaje = n.getTipoNotificacion().getPlantillaMensaje();
            mensaje = mensaje.replace("{usuarioOrigen}", mapaNombres.get(n.getIdUsuarioOrigen()));
            mensaje = mensaje.replace("{usuarioDestino}", mapaNombres.get(n.getIdUsuarioDestino()));
            n.setMensaje(mensaje);
        }
        return notificacionRepo.saveAll(listaNotificaciones);
    }
}
