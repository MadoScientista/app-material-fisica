package com.madoscientista.suscripciones.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoSuscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoSuscripcion;

    @Column(unique = true, nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Long nMaxEjercicios;

    @Column(nullable = false)
    private Long precioPorMes;

    @OneToMany(mappedBy = "tipoSuscripcion")
    private List<Suscripcion> suscripciones;
}
