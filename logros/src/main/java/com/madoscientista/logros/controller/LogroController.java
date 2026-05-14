package com.madoscientista.logros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.logros.dto.logroDTO.RequestLogroDTO;
import com.madoscientista.logros.dto.logroDTO.ResponseLogroDTO;
import com.madoscientista.logros.mapper.LogroMapper;
import com.madoscientista.logros.model.Logro;
import com.madoscientista.logros.model.Recuento;
import com.madoscientista.logros.dto.recuentoDTO.ResponseRecuentoDTO;
import com.madoscientista.logros.service.LogroService;
import com.madoscientista.logros.service.RecuentoService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/logros")
public class LogroController {

    @Autowired
    private LogroService lService;

    @Autowired
    private RecuentoService rService;

    @Autowired
    private LogroMapper logroMapper;


    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    // Retorna todos los logros de un usuario
    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<ResponseLogroDTO>> getLogrosByIdUsuario(@PathVariable Long idUsuario) {
        List<ResponseLogroDTO> response = logroMapper.toDTOs(lService.getLogrosByIdUsuario(idUsuario));
        return ResponseEntity.ok(response);
    }


    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------
    
    // Sincroniza los logros del usuario con los tipos de logro disponibles
    @PostMapping("/sincronizar/{idUsuario}")
    public ResponseEntity<List<ResponseLogroDTO>> postSincronizarLogrosUsuario(@PathVariable Long idUsuario){
        List<ResponseLogroDTO> response = logroMapper.toDTOs(lService.postSincronizarLogrosUsuario(idUsuario));
        return ResponseEntity.ok(response);
    }

    // --------------------------------------------------------
    // ------------------ Sección RECUENTO --------------------
    // --------------------------------------------------------

    // Incrementa en 1 el contador de ejercicios creados para un usuario
    @PostMapping("/recuento/ejercicio-creado/{idUsuario}")
    public ResponseEntity<ResponseRecuentoDTO> postIncrementarEjercicioCreado(@PathVariable Long idUsuario) {
        log.info("Incrementando ejercicios creados para el usuario {}", idUsuario);
        Recuento recuento = rService.incrementarEjerciciosCreados(idUsuario);
        ResponseRecuentoDTO response = new ResponseRecuentoDTO(idUsuario, rService.toMap(recuento));
        return ResponseEntity.ok(response);
    }

    // Incrementa el contador de ejercicios compartidos para un usuario
    @PostMapping("/recuento/ejercicio-compartido/{idUsuario}")
    public ResponseEntity<ResponseRecuentoDTO> postIncrementarEjercicioCompartido(
            @PathVariable Long idUsuario, @RequestBody int cantidad) {
        log.info("Incrementando ejercicios compartidos para el usuario {} en {}", idUsuario, cantidad);
        Recuento recuento = rService.incrementarEjerciciosCompartidos(idUsuario, cantidad);
        ResponseRecuentoDTO response = new ResponseRecuentoDTO(idUsuario, rService.toMap(recuento));
        return ResponseEntity.ok(response);
    }

    // Incrementa el contador de comunidades para un usuario
    @PostMapping("/recuento/comunidad/{idUsuario}")
    public ResponseEntity<ResponseRecuentoDTO> postIncrementarComunidad(
            @PathVariable Long idUsuario, @RequestBody int cantidad) {
        log.info("Incrementando comunidades para el usuario {} en {}", idUsuario, cantidad);
        Recuento recuento = rService.incrementarComunidad(idUsuario, cantidad);
        ResponseRecuentoDTO response = new ResponseRecuentoDTO(idUsuario, rService.toMap(recuento));
        return ResponseEntity.ok(response);
    }

    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    // Actualiza un logro a completado
    @PutMapping
    public ResponseEntity<?> putLogroCompletado(@Valid @RequestBody RequestLogroDTO request){
        Long idUsuario = request.getIdUsuario();
        String nombreTipoUsuario = request.getNombreTipoLogro();

        Logro logroActualizado = lService.putLogroCompletado(idUsuario, nombreTipoUsuario);

        if(logroActualizado != null){
            return ResponseEntity.ok(logroActualizado);
        }

        return ResponseEntity.badRequest().body("Error al actualizar el logro");

    }
}
