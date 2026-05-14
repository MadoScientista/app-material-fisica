package com.madoscientista.material.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ItemEjercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItemEjercicio;

    @Column(nullable = false, updatable = false)
    private Long idUsuarioCreador;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    private String titulo;
    private String descripcion;

    // Los ejercicios solo contendran el enunciado del mismo
    // Esto es para evitar que al eliminar un ejercicio desde
    // otro microservicio pueda generar un error en cadena
    @Column(nullable = false)
    private String textoEjercicios;

    // Un item puede estar en muchos materiales
    // Un material puede tener muchos items
    @ManyToMany(mappedBy = "itemsEjercicios")
    private List<Material> materiales;
}
