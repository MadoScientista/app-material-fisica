package com.madoscientista.notificador.model;

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
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotificacion;

    @Column(nullable = false)
    private Long idUsuarioOrigen;

    @Column(nullable = false)
    private Long idUsuarioDestino;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column
    private LocalDateTime fechaLectura;

    @Column(nullable = false)
    private boolean leido;

    @Column(nullable = false)
    private String mensaje;

    
    
    @ManyToOne
    @JoinColumn(name = "idTipoNotificacion", nullable = false)
    private TipoNotificacion tipoNotificacion;
}
