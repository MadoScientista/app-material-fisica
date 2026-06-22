package com.madoscientista.usuarios.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.madoscientista.usuarios.model.Usuario;
import com.madoscientista.usuarios.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneradorUsuariosService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    private final Faker faker = new Faker(new Locale("es"));

    public List<Usuario> generarUsuarios(int cantidad) {
        List<Usuario> generados = new ArrayList<>();

        for (int i = 0; i < cantidad; i++) {
            Usuario usuario = new Usuario();
            usuario.setNombre(faker.name().firstName());
            usuario.setApellido(faker.name().lastName());
            usuario.setNombreUsuario(generarNombreUsuarioUnico());
            usuario.setEmail(generarEmailUnico());
            usuario.setPassword("1234");

            Usuario guardado = usuarioService.postUSuario(usuario);
            generados.add(guardado);
        }

        log.info("{} usuarios generados con DataFaker", cantidad);
        return generados;
    }

    private String generarNombreUsuarioUnico() {
        String base = faker.name().username()
            .replaceAll("[^a-zA-Z0-9]", "")
            .toLowerCase();
        String usuario = base;
        int sufijo = 1;
        while (usuarioRepository.findByNombreUsuario(usuario) != null) {
            usuario = base + sufijo;
            sufijo++;
        }
        return usuario;
    }

    private String generarEmailUnico() {
        String base = faker.name().firstName().toLowerCase()
            + "." + faker.name().lastName().toLowerCase();
        String email = base + "@test.com";
        int sufijo = 1;
        while (usuarioRepository.findByEmail(email) != null) {
            email = base + sufijo + "@test.com";
            sufijo++;
        }
        return email;
    }
}
