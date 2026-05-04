package com.madoscientista.historial.model;

import java.time.LocalDateTime;

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
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvento;
    
    @Column(nullable = false)
    private Long idUsuario;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fecha;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "idTipoEvento", nullable = false)
    private TipoEvento tipoEvento;
}
