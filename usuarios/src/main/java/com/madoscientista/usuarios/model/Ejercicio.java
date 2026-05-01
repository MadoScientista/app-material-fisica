package com.madoscientista.usuarios.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEjercicio;

    @Column(nullable = false)
    private LocalDate fecha;
    
    @Column(nullable = false)
    private String tema;
    
    @Column(nullable = false)
    private String dificultad;
    
    @Column(nullable = false)
    private String incognita;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String enunciado;
    
    @Column(nullable = false)
    private String respuesta;

    // Relación muchos a uno
    // Muchos ejercicios pueden ser creados por un usuario
    @ManyToOne
    @JoinColumn(name = "idCreador")
    private Usuario creador;

    // Relación muchos es a muchos
    // Muchos ejercicios pueden estar compartidos por muchos usuarios
    @ManyToMany
    @JoinTable(
        name = "ejercicio_compartido",
        joinColumns = @JoinColumn(name = "idEjercicio"),
        inverseJoinColumns = @JoinColumn(name = "idUsuario"))
    private List<Usuario> usuariosCompartidos;
}
