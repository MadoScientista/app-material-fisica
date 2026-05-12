package com.madoscientista.logros.model;


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
@NoArgsConstructor
@AllArgsConstructor
public class Logro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLogro;

    @Column(nullable = false)
    private Long idUsuario;

    @Column(columnDefinition = "BIT")
    private boolean completado;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime fechaCompletado;

    @ManyToOne
    @JoinColumn(name = "idTipoLogro")
    private TipoLogro tipoLogro;
}
