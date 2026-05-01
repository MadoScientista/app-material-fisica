package com.madoscientista.usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.usuarios.model.Usuario;
import com.madoscientista.usuarios.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repo;

    // Sección GET

    // Retorna la lista de usuarios disponibles
    public List<Usuario> getUsuarios(){
        return repo.findAll();
    }

    // Retorna un usuario filtrado por id
    public Usuario getUsuarioById(long id){

        return repo.findById(id).orElse(null);
    }

    // Crea un usuario
    public Usuario postUSuario(Usuario u){
        return repo.save(u);
    }

    // Elimina un usuario por id
    public boolean deleteUsuario(long id){;

        if(repo.existsById(id)){
            repo.deleteById(id);
            return true;
        }

        return false;
    }

    // Actualiza un usuario
    public Usuario putUsuario(long id, Usuario usuario){

        if(repo.existsById(id)){
            Usuario u = repo.findByIdUsuario(id);

            u.setApellido(usuario.getApellido());
            u.setEmail(usuario.getEmail());
            u.setNombre(usuario.getNombre());
            u.setNombreUsuario(usuario.getNombreUsuario());
            u.setPassword(usuario.getPassword());

            return repo.save(u);
        }

        return null;
    }
}
