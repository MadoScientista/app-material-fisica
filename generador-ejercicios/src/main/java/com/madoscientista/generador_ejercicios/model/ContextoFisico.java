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
@Table(name = "contexto_fisico")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContextoFisico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idContextoFisico;

    @Column(unique = true, nullable = false)
    private String nombre;
    
    private int vMin;
    private int vMax;
    private int xMin;
    private int xMax;
    private double mMin;
    private double mMax;

    @OneToMany(mappedBy = "contexto")
    private List<PlantillaEnunciado> plantillaEnunciado;
}
