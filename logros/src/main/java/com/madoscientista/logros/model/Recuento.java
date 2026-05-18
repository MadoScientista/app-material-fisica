package com.madoscientista.logros.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Recuento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRecuento;

    @Column(nullable = false)
    private Long idUsuario;

    @Column(nullable = false)
    private Long nEjerciciosCreados;

    @Column(nullable = false)
    private Long nEjerciciosCompartidos;

    @Column(nullable = false)
    private Long nComunidades;

    @Column(nullable = false)
    private Long nItemsCreados;

    @Column(nullable = false)
    private Long nMaterialesCreados;
}
