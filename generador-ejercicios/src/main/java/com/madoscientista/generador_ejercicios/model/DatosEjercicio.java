package com.madoscientista.generador_ejercicios.model;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatosEjercicio {
    private Set<Dato> datos;
    private VariableFisica incognita;

    public DatosEjercicio(){
        datos = new HashSet<>();
    }

    public Set<Dato> getDatos(){
        return datos;
    }
    
    public void agregarDato(Dato dato){
        datos.add(dato);
    }
}
