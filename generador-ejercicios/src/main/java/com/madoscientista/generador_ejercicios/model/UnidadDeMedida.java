package com.madoscientista.generador_ejercicios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unidad_de_medida")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnidadDeMedida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUnidadDeMedida;

    @Column(unique = true, nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String simbolo;

    @Column(nullable = false)
    private boolean esSI;

    @Column(nullable = false)
    private boolean esBaseSI;
    
    @Column(nullable = false)
    private double factorConversionSI;

    @ManyToOne
    @JoinColumn(name="idMagnitudFisica", nullable=false)
    private MagnitudFisica magnitudFisica;

}
