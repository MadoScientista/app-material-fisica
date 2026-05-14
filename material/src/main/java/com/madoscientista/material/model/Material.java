package com.madoscientista.material.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMaterial;

    @Column(nullable = false, updatable = false)
    private Long idUsuarioCreador;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;


    // Un item puede estar en muchos materiales
    // Un material puede tener muchos items
    @ManyToMany
    @JoinTable(
        name = "item_en_material",   // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "idMaterial"),  // Columna de la entidad en la tabla intermedia
        inverseJoinColumns = @JoinColumn(name = "idItemEjercicio") // Columna de la entidad con la que se emparejará
    )
    private List<ItemEjercicio> itemsEjercicios;
    
}
