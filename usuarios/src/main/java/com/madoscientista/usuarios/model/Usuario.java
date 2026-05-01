package com.madoscientista.usuarios.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    private String nombre;
    private String apellido;

    @Column(unique = true, nullable = false)
    private String nombreUsuario;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private String password;

    // Relación uno a muchos
    // Un usuario puede crear muchos ejercicios
    @OneToMany(mappedBy = "creador")
    private List<Ejercicio> ejerciciosPropios;

    // Relación muchos a muchos
    // Muchos usuarios pueden compartir muchos ejercicios
    @ManyToMany(mappedBy = "usuariosCompartidos")
    private List<Ejercicio> ejerciciosCompartidos;

}
