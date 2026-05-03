package com.madoscientista.historial.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class TipoEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoEvento;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(length = 200)
    private String descripcion;
    
    private boolean notificacionActiva;

    @OneToMany(mappedBy = "tipoEvento")
    private List<Evento> eventos;
}
