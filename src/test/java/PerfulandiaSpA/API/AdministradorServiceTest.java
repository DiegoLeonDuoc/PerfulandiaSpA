package PerfulandiaSpA.API;

import PerfulandiaSpA.Entidades.Administrador;
import PerfulandiaSpA.Servicio.AdministradorService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class AdministradorServiceTest {

    @Autowired
    AdministradorService administradorService;

    private final Integer rutCreado = 12345678;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    // C - Crear administrador (corregido con todos los campos NOT NULL)
    @Test
    @Order(1)
    public void testCrearAdministrador() {
        Administrador admin = new Administrador();
        admin.setRutUsuario(rutCreado);
        admin.setDvUsuario('4');
        admin.setNomUsuario("Juan");
        admin.setApellidoPaterno("Pérez");
        admin.setSexoUsuario('M'); // Campo NOT NULL
        admin.setDirUsuario("Calle Falsa 123"); // Campo NOT NULL
        admin.setFechaNacimiento(LocalDate.of(1980, 1, 1));
        admin.setTelefonoUsuario("912345678"); // Campo NOT NULL
        admin.setEmailUsuario("juan@email.com"); // Campo NOT NULL
        admin.setPassUsuario("123456"); // Campo NOT NULL

        administradorService.crearAdministrador(admin);

        Optional<Administrador> adminOpt = administradorService.getAdministradorByRut(rutCreado);
        assertTrue(adminOpt.isPresent());
        assertEquals("ADMINISTRADOR", adminOpt.get().getTipoUsuario());
        assertTrue(encoder.matches("123456", adminOpt.get().getPassUsuario()));
    }

    // R - Obtener por RUT (sin cambios)
    @Test
    @Order(2)
    public void testGetAdministradorByRut() {
        Optional<Administrador> adminOpt = administradorService.getAdministradorByRut(rutCreado);
        assertTrue(adminOpt.isPresent());
        assertEquals("Juan", adminOpt.get().getNomUsuario());
        assertEquals("Pérez", adminOpt.get().getApellidoPaterno());
        assertEquals("juan@email.com", adminOpt.get().getEmailUsuario()); // Verificación adicional
    }

    // R - Listar todos (sin cambios)
    @Test
    @Order(3)
    public void testGetAdministradores() {
        List<Administrador> admins = administradorService.getAdministradors();
        assertFalse(admins.isEmpty());
        assertTrue(admins.stream().anyMatch(a -> a.getRutUsuario().equals(rutCreado)));
    }

    // U - Actualización completa (corregido)
    @Test
    @Order(4)
    public void testUpdateAdministrador() {
        Administrador actualizado = new Administrador();
        actualizado.setRutUsuario(rutCreado);
        actualizado.setDvUsuario('5');
        actualizado.setNomUsuario("Juan Carlos");
        actualizado.setApellidoPaterno("Gómez");
        actualizado.setSexoUsuario('M');
        actualizado.setDirUsuario("Nueva Dirección 456");
        actualizado.setFechaNacimiento(LocalDate.of(1985, 5, 15));
        actualizado.setTelefonoUsuario("987654321");
        actualizado.setEmailUsuario("juan.nuevo@email.com");
        actualizado.setPassUsuario("nuevaContraseña");

        administradorService.updateAdministrador(actualizado, rutCreado);

        Optional<Administrador> adminOpt = administradorService.getAdministradorByRut(rutCreado);
        assertTrue(adminOpt.isPresent());
        assertEquals("Juan Carlos", adminOpt.get().getNomUsuario());
        assertEquals("juan.nuevo@email.com", adminOpt.get().getEmailUsuario());
        assertTrue(encoder.matches("nuevaContraseña", adminOpt.get().getPassUsuario()));
    }

    // U - Actualización parcial (PATCH) (corregido)
    @Test
    @Order(5)
    public void testPatchAdministrador() {
        Administrador actualizadoParcial = new Administrador();
        actualizadoParcial.setEmailUsuario("nuevo@email.com");
        actualizadoParcial.setTelefonoUsuario("912345678");
        actualizadoParcial.setApellidoMaterno("López"); // Campo opcional

        administradorService.patchAdministrador(actualizadoParcial, rutCreado);

        Optional<Administrador> adminOpt = administradorService.getAdministradorByRut(rutCreado);
        assertTrue(adminOpt.isPresent());
        assertEquals("nuevo@email.com", adminOpt.get().getEmailUsuario());
        assertEquals("912345678", adminOpt.get().getTelefonoUsuario());
        assertEquals("López", adminOpt.get().getApellidoMaterno());
        // Campos no modificados
        assertEquals("Juan Carlos", adminOpt.get().getNomUsuario());
        assertEquals("Gómez", adminOpt.get().getApellidoPaterno());
    }

    // D - Eliminar administrador (sin cambios)
    @Test
    @Order(6)
    public void testDeleteAdministrador() {
        administradorService.deleteAdministrador(rutCreado);
        Optional<Administrador> adminOpt = administradorService.getAdministradorByRut(rutCreado);
        assertTrue(adminOpt.isEmpty());
    }
}
