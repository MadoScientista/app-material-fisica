package com.madoscientista.generador_ejercicios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.madoscientista.generador_ejercicios.model.MagnitudFisica;
import com.madoscientista.generador_ejercicios.model.UnidadDeMedida;

@Repository
public interface UnidadDeMedidaRepository extends JpaRepository<UnidadDeMedida, Integer>{

    UnidadDeMedida findBySimbolo(String simbolo);

    UnidadDeMedida findByMagnitudFisicaAndEsBaseSITrue(MagnitudFisica magnitudFisica);

    UnidadDeMedida findByMagnitudFisicaNombreAndEsBaseSITrue(String nombreMagnitudFisica);
}
