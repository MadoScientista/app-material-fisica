package com.madoscientista.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.madoscientista.usuarios.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Usuario findByIdUsuario(long id);

    public Usuario findByNombreUsuario(String nombreUsuario);

    public Usuario findByEmail(String email);
}
