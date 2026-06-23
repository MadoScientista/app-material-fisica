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

    private final Faker faker = new Faker(Locale.of("es"));

    public List<Usuario> generarUsuarios(int cantidad) {
        List<Usuario> generados = new ArrayList<>();

        for (int i = 0; i < cantidad; i++) {
            Usuario usuario = new Usuario();
            String nombre = faker.name().firstName();
            String apellido = faker.name().lastName();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setNombreUsuario(generarNombreUsuarioUnico(nombre, apellido));
            usuario.setEmail(generarEmailUnico(nombre, apellido));
            usuario.setPassword("1234");

            Usuario guardado = usuarioService.postUSuario(usuario);
            generados.add(guardado);
        }

        log.info("{} usuarios generados con DataFaker", cantidad);
        return generados;
    }

    private String generarNombreUsuarioUnico(String nombre, String apellido) {
        String base = nombre + "." + apellido;
        String usuario = base
            .replaceAll("[^a-zA-Z0-9]", "") // Quita cualquier caracter que no sea alfanumérico
            .toLowerCase();
        int sufijo = 1;
        while (usuarioRepository.findByNombreUsuario(usuario) != null) {
            usuario = usuario + sufijo;
            sufijo++;
        }
        return usuario;
    }

    private String generarEmailUnico(String nombre, String apellido) {
        String base = nombre + "_" + apellido;
        String correoBase = base
            .replaceAll("[^a-zA-Z0-9]", "") // Quita cualquier caracter que no sea alfanumérico
            .toLowerCase();
        String email = correoBase + "@test.com";
        int sufijo = 1;
        while (usuarioRepository.findByEmail(email) != null) {
            email = correoBase + sufijo + "@test.com";
            sufijo++;
        }
        return email;
    }
}
