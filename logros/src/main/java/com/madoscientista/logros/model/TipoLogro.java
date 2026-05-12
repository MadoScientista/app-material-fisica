package com.madoscientista.logros.model;


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
public class TipoLogro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoLogro;
    
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String descripcion;

    @OneToMany(mappedBy = "tipoLogro")
    List<Logro> logros;
    
}
