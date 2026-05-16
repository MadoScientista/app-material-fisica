package com.madoscientista.comunidades.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.comunidades.client.EjercicioClient;
import com.madoscientista.comunidades.dto.comunidadDTO.RequestComunidadDTO;
import com.madoscientista.comunidades.dto.comunidadDTO.ResponseComunidadDTO;
import com.madoscientista.comunidades.dto.ejercicioDTO.ResponseEjercicioDTO;
import com.madoscientista.comunidades.mapper.ComunidadMapper;
import com.madoscientista.comunidades.model.Comunidad;
import com.madoscientista.comunidades.service.ComunidadService;

import feign.FeignException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/comunidades")
public class ComunidadController {

    @Autowired
    ComunidadService cService;

    // Inyección cliente ejercicios
    @Autowired
    EjercicioClient eClient;

    @Autowired
    ComunidadMapper cMapper;

    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    // Retorna una lista con las comunidades disponibles
    @GetMapping
    public ResponseEntity<List<ResponseComunidadDTO>> getComunidades(){
        List<Comunidad> comunidades = cService.getComunidades();

        if(comunidades.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        List<ResponseComunidadDTO> comunidadesDTO = cMapper.toListDTO(comunidades);
        return ResponseEntity.ok(comunidadesDTO);
        
    }

    // Retorna una comunidad filtrada por su id
    @GetMapping("{idComunidad}")
    public ResponseEntity<ResponseComunidadDTO> getComunidadById(@PathVariable Long idComunidad){
        Comunidad comunidad = cService.getComunidadById(idComunidad);

        if(comunidad == null){
            return ResponseEntity.notFound().build();
        }
        ResponseComunidadDTO comunidadDTO = cMapper.toDTO(comunidad);
        return ResponseEntity.ok(comunidadDTO);
    }


    // Retorna un Set con los id de los usuarios miembros
    @GetMapping("{idComunidad}/usuarios")
    public ResponseEntity<Set<Long>> getMiembrosDeComunidad(@PathVariable Long idComunidad){
        Set<Long> idMiembros = cService.getMiembrosDeComunidad(idComunidad);

        if(idMiembros == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(idMiembros);

    }

    
    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // Crea una nueva comunidad
    @PostMapping
    public ResponseEntity<ResponseComunidadDTO> postComunidad(@Valid @RequestBody RequestComunidadDTO request){
        Comunidad comunidad = cMapper.toEntity(request);

        ResponseComunidadDTO response = cMapper.toDTO(cService.postComunidad(comunidad));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    // Permite agregar miembros a una comunidad
    @PutMapping("/agregar-miembros/{idComunidad}")
    public ResponseEntity<ResponseComunidadDTO> agregarMiembrosAComunidad(@PathVariable Long idComunidad, @RequestBody Set<Long> idMiembros){
        Comunidad comunidad = cService.agregarMiembrosAComunidad(idComunidad, idMiembros);

        if(comunidad != null){
            ResponseComunidadDTO response = cMapper.toDTO(comunidad);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
    }


    // Permite eliminar miembros a una comunidad
    @PutMapping("/eliminar-miembros/{idComunidad}")
    public ResponseEntity<ResponseComunidadDTO> eliminarMiembrosDeComunidad(@PathVariable Long idComunidad, @RequestBody Set<Long> idMiembros){
        Comunidad comunidad = cService.eliminarMiembrosDeComunidad(idComunidad, idMiembros);

        if(comunidad != null){
            ResponseComunidadDTO response = cMapper.toDTO(comunidad);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
    }

    // --------------------------------------------------------
    // ------------------ Sección Ejercicios ------------------
    // --------------------------------------------------------

    @GetMapping("ejercicios/{idComunidad}")
    public ResponseEntity<List<ResponseEjercicioDTO>> listarEjerciciosDeComunidad(@PathVariable Long idComunidad){
        Set<Long> idUsuarios = cService.getMiembrosDeComunidad(idComunidad);

        if(idUsuarios == null){
            return ResponseEntity.notFound().build();
        }

        try{
            List<ResponseEjercicioDTO> listaEjercicios = eClient.listarEjerciciosDeUsuarios(idUsuarios).getBody();
            return ResponseEntity.ok(listaEjercicios);
        }catch(FeignException e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
    }
}
