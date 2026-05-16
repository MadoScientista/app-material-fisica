package com.madoscientista.comunidades.model;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Comunidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComunidad;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Long idUsuarioCreador;

    // Crea una tabla muchos a muchos
    // Los idUsuario vendrán de otro microservicio
    // por lo que se usa CollectionTable en vez de JoinTable
    @ElementCollection
    @CollectionTable(
        name = "comunidad_miembro",
        joinColumns = @JoinColumn(name="idComunidad")
    )
    @Column(name = "idMiembro", nullable = false)
    Set<Long> idMiembros;

}
