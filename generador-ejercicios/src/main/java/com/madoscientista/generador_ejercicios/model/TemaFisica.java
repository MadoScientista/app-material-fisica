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
@Table(name = "tema_fisica")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemaFisica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTemaFisica;

    @Column(unique = true, nullable = false)
    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "tema")
    private List<PlantillaEnunciado> plantillaEnunciado;
}
