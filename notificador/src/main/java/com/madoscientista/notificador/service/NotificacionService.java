package com.madoscientista.notificador.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.madoscientista.notificador.client.UsuarioClient;
import com.madoscientista.notificador.dto.usuarioDTO.ResponseUsuarioDTO;
import com.madoscientista.notificador.model.Notificacion;
import com.madoscientista.notificador.repository.NotificacionRepository;

@Service
public class NotificacionService {

    // Inyección del repositorio de notificaciones
    @Autowired
    private NotificacionRepository notificacionRepo;

    // Inyección de cliente del ms Usuario
    @Autowired
    private UsuarioClient uClient;


    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------
    
    // Retorna una lista de notificaciones no leídas por un usuario según el ID del usuario
    public List<Notificacion> getNotificacionesNoLeidasByUsuarioId(Long idUsuario){
        return notificacionRepo.findByIdUsuarioOrigenAndLeidoFalse(idUsuario);
    }

     // Retorna una lista de notificaciones leídas por un usuario según el ID del usuario
     public List<Notificacion> getNotificacionesLeidasByUsuarioId(Long idUsuario){
        return notificacionRepo.findByIdUsuarioOrigenAndLeidoTrue(idUsuario);
    }

     // Retorna una lista de todas las notificaciones por un usuario según el ID del usuario
     public List<Notificacion> getAllNotificacionesByUsuarioId(Long idUsuario){
        return notificacionRepo.findByIdUsuarioOrigen(idUsuario);
    }

     // Retorna una notificación específica por su ID
     public Notificacion getNotificacionById(Long idNotificacion){
        return notificacionRepo.findById(idNotificacion).orElse(null);
    }

    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // Crea una nueva notificacion
    public Notificacion postNotificacion(Notificacion n){

        if(n == null) {
            throw new RuntimeException("Se requieren datos de notificación");
        }

        n.setLeido(false);

        // Reemplazo de placeholders en mensaje de notificación
        ResponseUsuarioDTO usuarioOrigen = uClient.getUsuarioById(n.getIdUsuarioOrigen()).getBody();
        ResponseUsuarioDTO usuariosDestino = uClient.getUsuarioById(n.getIdUsuarioDestino()).getBody();

        String mensaje = n.getTipoNotificacion().getPlantillaMensaje();
        mensaje = mensaje.replace("{usuarioOrigen}", usuarioOrigen.getNombreUsuario());
        mensaje = mensaje.replace("{usuarioDestino}", usuariosDestino.getNombreUsuario());

        n.setMensaje(mensaje);

        return notificacionRepo.save(n);
    }

    public List<Notificacion> postNotificaciones(List<Notificacion> listaNotificaciones){
        // 1. Recolectar IDs únicos
        Set<Long> idsUnicos = new HashSet<>();
        for (Notificacion n : listaNotificaciones) {
            idsUnicos.add(n.getIdUsuarioOrigen());
            idsUnicos.add(n.getIdUsuarioDestino());
        }
        // 2. Una sola llamada Feign
        ResponseEntity<List<ResponseUsuarioDTO>> response = uClient.listUsuariosByIds(
            new ArrayList<>(idsUnicos));

        List<ResponseUsuarioDTO> listaUsuarios = response.getBody();
        
        // 3. Construir mapa: idUsuario -> ResponseUsuarioDTO
        Map<Long, ResponseUsuarioDTO> mapaUsuarios = new HashMap<>();
        for (ResponseUsuarioDTO u : listaUsuarios) {
            mapaUsuarios.put(u.getIdUsuario(), u);
        }

        // 4. Procesar todas con el mapa
        for (Notificacion n : listaNotificaciones) {
            n.setLeido(false);
            String mensaje = n.getTipoNotificacion().getPlantillaMensaje();
            mensaje = mensaje.replace("{usuarioOrigen}", mapaUsuarios.get(n.getIdUsuarioOrigen()).getNombreUsuario());
            mensaje = mensaje.replace("{usuarioDestino}", mapaUsuarios.get(n.getIdUsuarioDestino()).getNombreUsuario());
            n.setMensaje(mensaje);
        }
        return notificacionRepo.saveAll(listaNotificaciones);
    }
    
}
