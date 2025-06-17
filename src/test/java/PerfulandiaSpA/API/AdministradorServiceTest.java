package PerfulandiaSpA.API;

import PerfulandiaSpA.Entidades.Administrador;
import PerfulandiaSpA.Servicio.AdministradorService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
    private final Integer RUT_TEST = 77777777;
    private final String PASS_PLANA = "admin123";

    // C - Crear administrador
    @Test
    @Order(1)
    public void testCrearAdministrador() {
        Administrador admin = new Administrador();
        admin.setRutUsuario(RUT_TEST);
        admin.setDvUsuario('7');
        admin.setNomUsuario("Super Admin");
        admin.setApellidoPaterno("Pérez");
        admin.setSexoUsuario('M');
        admin.setDirUsuario("Calle Admin 123");
        admin.setFechaNacimiento(LocalDate.of(1980, 5, 15));
        admin.setTelefonoUsuario("912345670");
        admin.setEmailUsuario("admin@correo.com");
        admin.setPassUsuario(PASS_PLANA);

        administradorService.crearAdministrador(admin);

        Optional<Administrador> adminOpt = administradorService.getAdministradorByRut(RUT_TEST);
        assertTrue(adminOpt.isPresent(), "El administrador debe existir después de crearlo");
        assertEquals("ADMINISTRADOR", adminOpt.get().getTipoUsuario());
        assertTrue(encoder.matches(PASS_PLANA, adminOpt.get().getPassUsuario()));
    }

    // R - Obtener todos
    @Test
    @Order(2)
    public void testGetAdministradors() {
        List<Administrador> admins = administradorService.getAdministradors();
        assertFalse(admins.isEmpty(), "Debe haber administradores en la base");
        assertTrue(admins.stream().anyMatch(a -> a.getRutUsuario().equals(RUT_TEST)));
    }

    // R - Obtener por RUT
    @Test
    @Order(3)
    public void testGetAdministradorByRut() {
        Optional<Administrador> adminOpt = administradorService.getAdministradorByRut(RUT_TEST);
        assertTrue(adminOpt.isPresent(), "El administrador debe existir");
        assertEquals("Super Admin", adminOpt.get().getNomUsuario());
        assertEquals("Pérez", adminOpt.get().getApellidoPaterno());
    }

    // U - Actualización completa (PUT)
    @Test
    @Order(4)
    public void testUpdateAdministrador() {
        Administrador actualizado = new Administrador();
        actualizado.setDvUsuario('K');
        actualizado.setNomUsuario("Admin Actualizado");
        actualizado.setApellidoPaterno("Gómez");
        actualizado.setApellidoMaterno("López");
        actualizado.setSexoUsuario('F');
        actualizado.setDirUsuario("Nueva Dirección 789");
        actualizado.setFechaNacimiento(LocalDate.of(1985, 10, 20));
        actualizado.setTelefonoUsuario("987654320");
        actualizado.setEmailUsuario("nuevo.admin@correo.com");
        actualizado.setPassUsuario("nuevaClaveAdmin");

        administradorService.updateAdministrador(actualizado, RUT_TEST);

        Optional<Administrador> adminOpt = administradorService.getAdministradorByRut(RUT_TEST);
        assertTrue(adminOpt.isPresent(), "Administrador actualizado debe existir");
        assertEquals("Admin Actualizado", adminOpt.get().getNomUsuario());
        assertEquals("Gómez", adminOpt.get().getApellidoPaterno());
        assertTrue(encoder.matches("nuevaClaveAdmin", adminOpt.get().getPassUsuario()));
    }

    // U - Actualización parcial (PATCH)
    @Test
    @Order(5)
    public void testPatchAdministrador() {
        Administrador parche = new Administrador();
        parche.setEmailUsuario("parche.admin@correo.com");
        parche.setTelefonoUsuario("900000001");
        parche.setPassUsuario("parcheClaveAdmin");

        administradorService.patchAdministrador(parche, RUT_TEST);

        Optional<Administrador> adminOpt = administradorService.getAdministradorByRut(RUT_TEST);
        assertTrue(adminOpt.isPresent(), "Administrador parchado debe existir");
        assertEquals("parche.admin@correo.com", adminOpt.get().getEmailUsuario());
        assertEquals("900000001", adminOpt.get().getTelefonoUsuario());
        assertTrue(encoder.matches("parcheClaveAdmin", adminOpt.get().getPassUsuario()));
        // Campos no modificados
        assertEquals("Admin Actualizado", adminOpt.get().getNomUsuario());
        assertEquals("Gómez", adminOpt.get().getApellidoPaterno());
    }

    // D - Eliminar administrador
    @Test
    @Order(6)
    public void testDeleteAdministrador() {
        administradorService.deleteAdministrador(RUT_TEST);
        Optional<Administrador> adminOpt = administradorService.getAdministradorByRut(RUT_TEST);
        assertTrue(adminOpt.isEmpty(), "El administrador debe haber sido eliminado");
    }
}
