package com.madoscientista.notificador.model;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TipoNotificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoNotificacion;
    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "tipoNotificacion")
    private List<Notificacion> notificaciones;
}
