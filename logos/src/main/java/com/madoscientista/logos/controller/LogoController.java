package com.madoscientista.logos.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madoscientista.logos.dto.logoDTO.RequestLogoDTO;
import com.madoscientista.logos.dto.logoDTO.ResponseLogoDTO;
import com.madoscientista.logos.mapper.LogoMapper;
import com.madoscientista.logos.model.Logo;
import com.madoscientista.logos.service.LogoService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/logos")
public class LogoController {

    @Autowired
    private LogoService logoService;

    @Autowired
    private LogoMapper logoMapper;

    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    @GetMapping
    public ResponseEntity<List<ResponseLogoDTO>> getLogos() {
        log.info("Lista de logos solicitada");
        List<Logo> logos = logoService.getLogos();

        if (logos.isEmpty()) {
            log.info("No se encontraron logos");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<ResponseLogoDTO> dtoList = logoMapper.toDTOList(logos);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("{idLogo}")
    public ResponseEntity<ResponseLogoDTO> getLogoById(@PathVariable Long idLogo) {
        log.info("Logo con id: {} solicitado", idLogo);
        Logo logo = logoService.getLogoById(idLogo);

        if (logo == null) {
            log.info("Logo no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ResponseLogoDTO dto = logoMapper.toDTO(logo);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("usuario/{idUsuarioCreador}")
    public ResponseEntity<List<ResponseLogoDTO>> getLogoByIdUsuarioCreador(
            @PathVariable Long idUsuarioCreador) {
        log.info("Logos del usuario id: {} solicitados", idUsuarioCreador);
        List<Logo> logos = logoService.getLogoByIdUsuarioCreador(idUsuarioCreador);

        if (logos.isEmpty()) {
            log.info("Logos no encontrados para el usuario");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<ResponseLogoDTO> dtoList = logoMapper.toDTOList(logos);
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("lista-ids")
    public ResponseEntity<List<ResponseLogoDTO>> getLogosByListId(
            @Valid @RequestBody Set<Long> ids) {
        log.info("Logos por lista de ids solicitado");
        List<Logo> logos = logoService.getLogosByListId(ids);

        if (logos.isEmpty()) {
            log.info("Logos no encontrados para los ids proporcionados");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<ResponseLogoDTO> dtoList = logoMapper.toDTOList(logos);
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("usuarios-ids")
    public ResponseEntity<List<ResponseLogoDTO>> getLogosByListIdUsuarioCreador(
            @Valid @RequestBody Set<Long> ids) {
        log.info("Logos por lista de ids de usuarios solicitado");
        List<Logo> logos = logoService.getLogosByListIdUsuarioCreador(ids);

        if (logos.isEmpty()) {
            log.info("Logos no encontrados para los ids de usuarios proporcionados");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<ResponseLogoDTO> dtoList = logoMapper.toDTOList(logos);
        return ResponseEntity.ok(dtoList);
    }

    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------

    @PostMapping
    public ResponseEntity<ResponseLogoDTO> postLogo(
            @Valid @RequestBody RequestLogoDTO request) {
        log.debug("Solicitud de creación de logo: {}", request);
        Logo logo = logoMapper.toEntity(request);
        Logo logoCreado = logoService.postLogo(logo);

        if (logoCreado == null) {
            return ResponseEntity.internalServerError().build();
        }

        ResponseLogoDTO dto = logoMapper.toDTO(logoCreado);
        log.debug("Logo creado con éxito: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    @PutMapping("{idLogo}")
    public ResponseEntity<ResponseLogoDTO> putLogo(
            @PathVariable Long idLogo,
            @Valid @RequestBody RequestLogoDTO request) {
        log.debug("Solicitud de actualización de logo: {}", request);
        Logo logo = logoMapper.toEntity(request);
        Logo logoActualizado = logoService.putLogo(idLogo, logo);

        if (logoActualizado == null) {
            log.info("Logo no encontrado");
            return ResponseEntity.notFound().build();
        }

        ResponseLogoDTO dto = logoMapper.toDTO(logoActualizado);
        log.debug("Logo actualizado con éxito: {}", dto);
        return ResponseEntity.ok(dto);
    }

    // --------------------------------------------------------
    // ------------------ Sección DELETE ----------------------
    // --------------------------------------------------------

    @DeleteMapping("{idLogo}")
    public ResponseEntity<ResponseLogoDTO> deleteLogoById(@PathVariable Long idLogo) {
        log.info("Solicitud de eliminación de logo id: {}", idLogo);
        Logo logo = logoService.deleteLogoById(idLogo);

        if (logo == null) {
            log.info("Logo no encontrado");
            return ResponseEntity.notFound().build();
        }

        ResponseLogoDTO dto = logoMapper.toDTO(logo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(dto);
    }

    @PostMapping("eliminar-lista")
    public ResponseEntity<List<ResponseLogoDTO>> deleteLogoByListId(
            @Valid @RequestBody Set<Long> ids) {
        log.info("Solicitud de eliminación de logos por lista de ids");
        List<Logo> logos = logoService.deleteLogoByListId(ids);

        if (logos.isEmpty()) {
            log.info("Logos no encontrados");
            return ResponseEntity.notFound().build();
        }

        List<ResponseLogoDTO> dtoList = logoMapper.toDTOList(logos);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(dtoList);
    }
}
