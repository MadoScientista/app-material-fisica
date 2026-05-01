package com.madoscientista.generador_ejercicios.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "magnitud_fisica")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagnitudFisica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMagnitudFisica;

    @Column(unique = true, nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String simbolo;


    // Relaciones con VariableFisica y UnidadDeMedida
    @OneToMany(mappedBy = "magnitudFisica")
    private List<VariableFisica> variablesFisicas;

    @OneToMany(mappedBy = "magnitudFisica")
    private List<UnidadDeMedida> unidadesDeMedida;

    
}
