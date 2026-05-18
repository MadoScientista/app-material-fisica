package com.madoscientista.logos.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
public class Logo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLogo;

    @Column(nullable = false)
    private Long idUsuarioCreador;

    @CreationTimestamp
    private LocalDateTime fechaCreacion;
    
    private String nombre;
    private String descripcion;

    @Column(nullable = false)
    private String imagen;
    
    private String url;
}
