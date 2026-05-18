package com.madoscientista.usuarios.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madoscientista.usuarios.client.LogrosClient;
import com.madoscientista.usuarios.model.Usuario;
import com.madoscientista.usuarios.repository.UsuarioRepository;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private LogrosClient logrosClient;

    // --------------------------------------------------------
    // ------------------ Sección GET -------------------------
    // --------------------------------------------------------

    // Retorna la lista de usuarios disponibles
    public List<Usuario> getUsuarios(){
        return repo.findAll();
    }

    // Retorna un usuario filtrado por id
    public Usuario getUsuarioById(long id){

        return repo.findById(id).orElse(null);
    }

    // Retorna una lista de usuarios filtrados por id
    public List<Usuario> getUsuariosByIds(List<Long> ids){
        List<Usuario> usuarios = new ArrayList<>();
        
        for(Long id : ids){
            Usuario u = repo.findById(id).orElse(null);
            if(u != null){
                usuarios.add(u);
            }
        }
        return usuarios;
    }

    // Crea un usuario y sincroniza sus logros
    public Usuario postUSuario(Usuario u){
        Usuario usuarioCreado = repo.save(u);

        try{
            log.info("Intentando sincronizar logros");
            logrosClient.postSincronizarLogrosUsuario(usuarioCreado.getIdUsuario());
        }catch(FeignException e){
            log.info("No se pudo comunicar con el microservicio Logros");
        }
        
        log.info("Logros sincronizados");
        return usuarioCreado;
    }

    // Elimina un usuario por id
    public boolean deleteUsuario(long id){

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
