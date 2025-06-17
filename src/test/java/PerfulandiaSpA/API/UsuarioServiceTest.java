package PerfulandiaSpA.API;

import PerfulandiaSpA.Entidades.Usuario;
import PerfulandiaSpA.Servicio.UsuarioService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class UsuarioServiceTest {

    @Autowired
    UsuarioService usuarioService;

    private final Integer RUT_EXISTENTE = 10000001; // Debe existir en data.sql

    // R - Obtener todos los usuarios
    @Test
    @Order(1)
    public void testGetUsuarios() {
        List<Usuario> usuarios = usuarioService.getUsuarios();
        assertFalse(usuarios.isEmpty(), "Debe haber usuarios en la base");
        assertTrue(usuarios.stream().anyMatch(u -> u.getRutUsuario().equals(RUT_EXISTENTE)), "Debe existir el usuario de prueba");
    }

    // R - Obtener usuario por RUT
    @Test
    @Order(2)
    public void testGetUsuarioByRut() {
        Optional<Usuario> usuarioOpt = usuarioService.getUsuarioByRut(RUT_EXISTENTE);
        assertTrue(usuarioOpt.isPresent(), "El usuario debe existir");
        assertEquals(RUT_EXISTENTE, usuarioOpt.get().getRutUsuario());
    }

    // D - Eliminar usuario
    @Test
    @Order(3)
    public void testDeleteUsuario() {
        usuarioService.deleteUsuario(RUT_EXISTENTE);
        Optional<Usuario> usuarioOpt = usuarioService.getUsuarioByRut(RUT_EXISTENTE);
        assertTrue(usuarioOpt.isEmpty(), "El usuario debe haber sido eliminado");
    }
}
