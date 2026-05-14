package com.madoscientista.valoraciones.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idEjercicio", "idUsuario"})
})
public class Valoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idValoracion;

    @Column(nullable = false)
    private Long idEjercicio;

    @Column(nullable = false)
    private Long idUsuario;

    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer puntuacion;

    @Column(length = 500)
    private String comentario;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

}
