package com.madoscientista.usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.usuarios.client.GeneradorEjerciciosClient;
import com.madoscientista.usuarios.dto.ejercicioDTO.RequestEjercicioDTO;
import com.madoscientista.usuarios.dto.ejercicioDTO.ResponseEjercicioDTO;
import com.madoscientista.usuarios.mapper.EjercicioMapper;
import com.madoscientista.usuarios.model.Ejercicio;
import com.madoscientista.usuarios.model.Usuario;
import com.madoscientista.usuarios.repository.EjercicioRepository;

@Service
public class EjercicioService {

    @Autowired
    private EjercicioRepository ejercicioRepo;

    @Autowired
    private GeneradorEjerciciosClient geClient;

    @Autowired
    private UsuarioService uService;

    @Autowired
    private EjercicioMapper mapper;

    // Sección GET

    // Retorna la lista de ejercicios disponibles
    public List<Ejercicio> getEjercicios(){
        return ejercicioRepo.findAll();
    }


    // Retorna una lista de ejercicios filtrados por el idCreador
    public List<Ejercicio> getEjerciciosCreadosUsuario(long id){
        return ejercicioRepo.findAllByCreadorIdUsuario(id);
    }

    // Retorna una lista de ejercicios que se les ha compartido a un usuario
    public List<Ejercicio> getEjerciciosCompartidosAUsuario(long id){
        return ejercicioRepo.findByUsuariosCompartidosIdUsuario(id);
    }

    // Sección Post
    public Ejercicio postEjercicio(RequestEjercicioDTO request, long idUsuario){
        ResponseEjercicioDTO ejercicioDTO = geClient.getEjercicioMRU(request);
        Usuario usuario = uService.getUsuarioById(idUsuario);

        if(usuario == null){
            return null;
        }
        
        Ejercicio ejercicio = mapper.toNewEjercicio(ejercicioDTO, usuario);

        ejercicioRepo.save(ejercicio);

        return ejercicio;
    }

}
