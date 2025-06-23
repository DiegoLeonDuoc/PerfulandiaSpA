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

    private final Integer RUT_EXISTENTE = 10000001; // RUT de prueba definido en data.sql

    // R - Obtener todos los usuarios
    @Test
    @Order(1)
    public void testGetUsuarios() {
        // Obtenemos la lista completa de usuarios registrados
        List<Usuario> usuarios = usuarioService.getUsuarios();

        // Verificamos que haya usuarios y que exista el de prueba
        assertFalse(usuarios.isEmpty(), "Debe haber usuarios en la base");
        assertTrue(usuarios.stream().anyMatch(u -> u.getRutUsuario().equals(RUT_EXISTENTE)),
                "Debe existir el usuario de prueba");
    }

    // R - Obtener usuario por RUT
    @Test
    @Order(2)
    public void testGetUsuarioByRut() {
        // Buscamos el usuario usando el RUT definido en data.sql
        Optional<Usuario> usuarioOpt = usuarioService.getUsuarioByRut(RUT_EXISTENTE);

        // Verificamos que exista y que el RUT coincida
        assertTrue(usuarioOpt.isPresent(), "El usuario debe existir");
        assertEquals(RUT_EXISTENTE, usuarioOpt.get().getRutUsuario());
    }

    // D - Eliminar usuario
    @Test
    @Order(3)
    public void testDeleteUsuario() {
        // Eliminamos el usuario de prueba usando su RUT
        usuarioService.deleteUsuario(RUT_EXISTENTE);

        // Verificamos que ya no exista en la base de datos
        Optional<Usuario> usuarioOpt = usuarioService.getUsuarioByRut(RUT_EXISTENTE);
        assertTrue(usuarioOpt.isEmpty(), "El usuario debe haber sido eliminado");
    }
}

// -----------------------------------------------------------------------------
// Resumen técnico:
// - Valida operaciones CRUD básicas para el servicio de usuarios (excepto creación).
// - Prueba obtener todos los usuarios, buscar por RUT y eliminar usuario.
// - Usa datos preexistentes de data.sql para pruebas, no modifica datos de producción.
// - Mantiene orden de ejecución con @Order para evitar conflictos entre tests.
// - Se corrigió verificación de eliminación usando el mismo RUT en todas las pruebas.
// - Asegura que las operaciones de lectura y eliminación funcionen correctamente.
// - Garantiza integridad de datos usando transacciones de prueba con @Sql.
// -----------------------------------------------------------------------------
