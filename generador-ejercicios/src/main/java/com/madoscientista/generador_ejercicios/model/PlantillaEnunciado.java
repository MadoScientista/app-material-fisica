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
@Table(name = "plantilla_enunciado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantillaEnunciado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlantillaEnunciado;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String enunciado;

    @Column(nullable = false)
    private boolean resultadoPositivo;

    @ManyToOne
    @JoinColumn(name = "idTemaFisica", nullable = false)
    private TemaFisica tema;

    @ManyToOne
    @JoinColumn(name = "idContextoFisico", nullable = false)
    private ContextoFisico contexto;

    @ManyToOne
    @JoinColumn(name = "idIncognita", nullable = false)
    private VariableFisica incognita;

}
