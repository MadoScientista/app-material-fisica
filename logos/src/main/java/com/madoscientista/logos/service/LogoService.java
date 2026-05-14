package com.madoscientista.logos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.logos.model.Logo;
import com.madoscientista.logos.repository.LogoRepository;

@Service
public class LogoService {

    @Autowired
    private LogoRepository logoRepo;


    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------


    // Retorna un logo por su id
    public Logo getLogoById(Long idLogo){
        return logoRepo.findById(idLogo).orElse(null);
    }


    // --------------------------------------------------------
    // ------------------ Sección POST ------------------------
    // --------------------------------------------------------
    
    
    // --------------------------------------------------------
    // ------------------ Sección PUT -------------------------
    // --------------------------------------------------------

    // --------------------------------------------------------
    // ------------------ Sección DELETE ----------------------
    // --------------------------------------------------------
}
