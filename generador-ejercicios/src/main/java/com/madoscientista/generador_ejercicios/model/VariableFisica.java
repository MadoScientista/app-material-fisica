package com.madoscientista.generador_ejercicios.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariableFisica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVariableFisica;

    @Column(unique = true, nullable = false)
    private String nombre;   // Posición final
    
    @Column(unique = true, nullable = false)
    private String simbolo;  // xf

    @ManyToOne
    @JoinColumn(name = "idMagnitudFisica", nullable = false)
    private MagnitudFisica magnitudFisica;

    @OneToMany(mappedBy = "incognita")
    private List<PlantillaEnunciado> plantillaEnunciado;
}