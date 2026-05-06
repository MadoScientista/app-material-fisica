package com.madoscientista.notificador.model;

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
public class TipoNotificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoNotificacion;

    @Column(nullable = false)
    private Long idEvento;

    @Column(length = 250)
    private String descripcion;
    
    @Column(nullable = false, length = 250)
    private String plantillaMensaje;

    @Column(nullable = false, length = 50)
    private String canal; // Ejemplo: "email", "sms", "push"

    @OneToMany(mappedBy = "tipoNotificacion")
    private List<Notificacion> notificaciones;
}
