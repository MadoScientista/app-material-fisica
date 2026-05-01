package com.madoscientista.logros.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.logros.repository.LogroRepository;

@Service
public class LogroService {

    @Autowired
    private LogroRepository repo;
}
