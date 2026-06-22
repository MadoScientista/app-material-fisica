package com.madoscientista.material.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.madoscientista.material.client.HistorialClient;
import com.madoscientista.material.client.LogrosClient;
import com.madoscientista.material.dto.EventoDTO.RequestEventoDTO;
import com.madoscientista.material.model.Material;
import com.madoscientista.material.repository.MaterialRepository;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaterialService {

    private static final Long MATERIAL_CREADO = 22L;
    private static final Long MATERIAL_ACTUALIZADO = 23L;
    private static final Long MATERIAL_ELIMINADO = 24L;

    private final MaterialRepository mRepo;
    private final HistorialClient hClient;
    private final LogrosClient lClient;

    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    // Retorna todos los materiales de la plataforma
    public List<Material> getMateriales(){
        return mRepo.findAll();
    }

    // Retorna un material filtrado por ID
    public Material getMaterialById(Long idMaterial){
        return mRepo.findById(idMaterial).orElse(null);
    }

    // Retorna todos los materiales creados por un mismo usuario
    public List<Material> getMaterialByUsuarioCreador(Long idUsuarioCreador){
        return mRepo.findByIdUsuarioCreador(idUsuarioCreador);
    }

    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    // Crea un nuevo material
    public Material postMaterial(Material m){
        Material mCreado = mRepo.save(m);

        try{
            registrarEvento(mCreado.getIdUsuarioCreador(), MATERIAL_CREADO);
        }catch(FeignException e){
            log.debug("No se pudo comunicar el evento - crear material -", e);
        }

        try{
            lClient.postIncrementarMaterialCreado(mCreado.getIdUsuarioCreador());
        }catch(FeignException e){
            log.debug("No se pudo solicitar el aumento del recuento de materiales", e);
        }

        return mCreado;
    }

    // Crea una lista de materiales
    public List<Material> postMaterial(List<Material> materiales){
        return mRepo.saveAll(materiales);
    }

    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    // Actualiza un material por su id
    public Material actualizarEjercicios(Long idMaterial, Material materialActualizado){
        Material materialActual = getMaterialById(idMaterial);

        if(materialActual != null){
            materialActual.setItemsEjercicios(materialActualizado.getItemsEjercicios());
            materialActual = mRepo.save(materialActual);

            try{
                registrarEvento(materialActual.getIdUsuarioCreador(), MATERIAL_ACTUALIZADO);
            }catch(FeignException e){
                log.debug("No se pudo comunicar el evento - actualizar material -", e);
            }
        }

        return materialActual;
    }

    // --------------------------------------------------------
    // ------------------ Sección DELETE ----------------------
    // --------------------------------------------------------

    // Elimina un material por su id
    public Material deleteMaterial(Long idMaterial){
        Material material = getMaterialById(idMaterial);
        if(material != null){
            Long idUsuario = material.getIdUsuarioCreador();
            mRepo.delete(material);

            try{
                registrarEvento(idUsuario, MATERIAL_ELIMINADO);
            }catch(FeignException e){
                log.debug("No se pudo comunicar el evento - eliminar material -", e);
            }
        }
        return material;
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
