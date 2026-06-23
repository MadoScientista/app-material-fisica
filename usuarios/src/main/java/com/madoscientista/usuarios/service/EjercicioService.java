package com.madoscientista.usuarios.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.madoscientista.usuarios.client.HistorialClient;
import com.madoscientista.usuarios.client.GeneradorEjerciciosClient;
import com.madoscientista.usuarios.client.LogrosClient;
import com.madoscientista.usuarios.client.SuscripcionesClient;
import com.madoscientista.usuarios.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.usuarios.dto.ejercicioDTO.RequestEjercicioDTO;
import com.madoscientista.usuarios.dto.ejercicioDTO.ResponseEjercicioDTO;
import com.madoscientista.usuarios.exception.SuscripcionesException;
import com.madoscientista.usuarios.mapper.EjercicioMapper;
import com.madoscientista.usuarios.model.Ejercicio;
import com.madoscientista.usuarios.model.Usuario;
import com.madoscientista.usuarios.repository.EjercicioRepository;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EjercicioService {

    private static final Long EVENTO_EJERCICIO_CREADO = 3L;
    private static final Long EVENTO_EJERCICIO_COMPARTIDO = 4L;
    private static final Long EVENTO_EJERCICIO_ELIMINADO = 6L;

    private final EjercicioRepository ejercicioRepo;
    private final GeneradorEjerciciosClient geClient;
    private final HistorialClient hClient;
    private final SuscripcionesClient sClient;
    private final LogrosClient lClient;
    private final UsuarioService uService;
    private final EjercicioMapper mapper;


    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    // Retorna la lista de ejercicios disponibles
    public List<Ejercicio> getEjercicios(){
        return ejercicioRepo.findAll();
    }


    public Ejercicio getEjercicioById(Long id){
        return ejercicioRepo.findById(id).orElse(null);
    }

    // Retorna una lista de ejercicios filtrados por el idCreador
    public List<Ejercicio> getEjerciciosCreadosUsuario(long id){
        return ejercicioRepo.findAllByCreadorIdUsuario(id);
    }

    // Retorna una lista de ejercicios que se les ha compartido a un usuario
    public List<Ejercicio> getEjerciciosCompartidosAUsuario(long id){
        return ejercicioRepo.findByUsuariosCompartidosIdUsuario(id);
    }

    // Retorna una lista de ejercicios creados por un usuario
    public List<Ejercicio> getEjerciciosCreadosByUsuario(long id){
        return ejercicioRepo.findAllByCreadorIdUsuario(id);
    }

    // Retorna la cantidad de ejercicios almacenados por un usuario
    public Long contarEjerciciosByIUsuario(Long id){
        return ejercicioRepo.countByCreadorIdUsuario(id);
    }


    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // Solicita al microservicio generador de ejercicios la creación de un nuevo ejercicio
    // Guarda el ejercicio en la base de datos y registra el evento en el microservicio de historial de eventos
    public Ejercicio postEjercicio(RequestEjercicioDTO request, long idUsuario){

        Long nEjerciciosAlmacenados = contarEjerciciosByIUsuario(idUsuario);
        Long maxEjerciciosPermitidos = 0L;

        // Intenta comunicarse con el microservicio de suscripciones
        try{
            maxEjerciciosPermitidos = sClient.getMaxEjerciciosByUsuarioId(idUsuario).getBody();
        }catch(FeignException fe){
            log.warn("Error de comunicación con el microservicio Suscripciones");
            throw new SuscripcionesException("Error de comunicación con microservicio de suscripciones");
        }
        
        // El microservicio de suscripciones responde con null
        if(maxEjerciciosPermitidos == null){
            throw new SuscripcionesException("null para máximo de ejercicios permitidos");
        }

        // Error de máximo ejercicios permitidos
        if(nEjerciciosAlmacenados >= maxEjerciciosPermitidos ){
            throw new SuscripcionesException("Máximo de ejercicios permitidos por suscripción alcanzado");
        }


        // Intenta comunicarse con el microservicio generador-ejercicios
        ResponseEjercicioDTO ejercicioDTO = new ResponseEjercicioDTO();
        try{
            ejercicioDTO = geClient.getEjercicioMRU(request);
        }catch(FeignException fe){
            log.warn("Error de comunicación con el microservicio generador-ejercicio");
            throw fe;
        }
        
        Usuario usuario = uService.getUsuarioById(idUsuario);

        if(usuario == null){
            return null;
        }
        
        Ejercicio ejercicio = mapper.toEntity(ejercicioDTO, usuario);
        Ejercicio ejercicioGuardado = ejercicioRepo.save(ejercicio);

        List<Long> idUsuarioDestino = new ArrayList<>();
        idUsuarioDestino.add(idUsuario);

        // Comunica la creación del ejercicio al microservicio de historial de eventos
        registrarEvento( idUsuario, idUsuarioDestino, EVENTO_EJERCICIO_CREADO);

        // Incrementa el contador de ejercicios creados en el microservicio de logros
        try{
            lClient.postIncrementarEjercicioCreado(idUsuario);
        }catch(FeignException e){
            log.warn("Error de comunicación con el microservicio de logros. Aumento en el recuento de logros no registrado");
        }
        

        return ejercicioGuardado;
    }

    // Crea un registro en la tabla ejercicios compartidos con otros usuarios
    // Utiliza una lista con los IDs de los usuarios con los que se desea compartir el ejercicio
    public Ejercicio compartirEjercicio(long idCreador, long idEjercicio, List<Long> idsUsuariosCompartir){
        
        
        
        Ejercicio ejercicio = ejercicioRepo.findByIdEjercicio(idEjercicio).orElse(null);
        
        // Verificación de errores para debug
        if(ejercicio == null){
            log.info("Ejercicio no encontrado");
            return null;
        }


        if(ejercicio.getCreador().getIdUsuario() != idCreador){
            log.info("Usuario no es el creador del ejercicio, no puede compartirlo");
            return null;
        }


        List<Usuario> usuariosCompartir = uService.getUsuariosByIds(idsUsuariosCompartir);
        ejercicio.setUsuariosCompartidos(new HashSet<>(usuariosCompartir));

        if(ejercicioRepo.save(ejercicio) != null){
            // Comunica el evento de compartir el ejercicio al microservicio de historial de eventos
            registrarEvento(
                idCreador, idsUsuariosCompartir, EVENTO_EJERCICIO_COMPARTIDO);

            // Incrementa el contador de ejercicios compartidos en el microservicio de logros
            try {
                lClient.postIncrementarEjercicioCompartido(idCreador, idsUsuariosCompartir.size());
            } catch (FeignException e) {
                log.warn("Error de comunicación con el microservicio de logros. Aumento en el recuento de logros no registrado");
            }
        }
        return ejercicio;
    }


    // Retorna una lista de ejercicios creados por un Set de usuarios
    public List<Ejercicio> listarEjerciciosDeUSuarios(Set<Long> idUsuarios){
        List<Ejercicio> listaEjercicios = ejercicioRepo.findAllByCreadorIdUsuarioIn(idUsuarios);

        return listaEjercicios;
    }

    // --------------------------------------------------------
    // ------------------ Sección DELETE ----------------------
    // --------------------------------------------------------

    // Elimina un ejercicio creado por un usuario según el ID del ejercicio
    public boolean deleteEjercicio(Long idUsuario, Long idEjercicio){
        Ejercicio ejercicio = ejercicioRepo.findById(idEjercicio).orElse(null);
        if(ejercicio == null || ejercicio.getCreador() == null || ejercicio.getCreador().getIdUsuario() != idUsuario){
            return false;
        }
        
        ejercicioRepo.delete(ejercicio);

        List<Long> idUsuarioDestino = new ArrayList<>();
        idUsuarioDestino.add(idUsuario);
        
        // Comunica el evento de eliminación del ejercicio al microservicio de historial de eventos
        registrarEvento(idUsuario, idUsuarioDestino, EVENTO_EJERCICIO_ELIMINADO);
        
        return true;
    }


    // --------------------------------------------------------
    // ------------------ Sección EVENTOS ---------------------
    // --------------------------------------------------------

    private void registrarEvento(Long idUsuarioOrigen, List<Long> idUsuarioDestino, Long idTipoEvento) {
        RequestEventoDTO eventoDTO = new RequestEventoDTO();
        eventoDTO.setIdTipoEvento(idTipoEvento);
        eventoDTO.setIdUsuarioDestino(idUsuarioDestino);
        eventoDTO.setIdUsuarioOrigen(idUsuarioOrigen);

        try{
            hClient.postEvento(eventoDTO);
        }catch(FeignException e){
            log.warn("Error de comunicación con el microservicio de eventos. Evento no registrado");
        }
        
    }
}
