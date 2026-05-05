package com.madoscientista.suscripciones.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Suscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idSuscripcion;

    @Column(nullable = false)
    private Long idUsuario;

    @Column(nullable = false)
    private boolean activo;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    @ManyToOne
    @JoinColumn(name = "idTipoSuscripcion")
    private TipoSuscripcion tipoSuscripcion;
    
}
