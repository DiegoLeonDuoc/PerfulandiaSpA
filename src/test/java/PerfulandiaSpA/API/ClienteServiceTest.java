package PerfulandiaSpA.API;

import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Servicio.ClienteService;
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
public class ClienteServiceTest {

    @Autowired
    ClienteService clienteService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
    private final Integer RUT_TEST = 99999999;
    private final String PASS_PLANA = "cliente123";

    @Test
    @Order(1)
    public void testCrearCliente() {
        Cliente cliente = new Cliente();
        cliente.setRutUsuario(RUT_TEST);
        cliente.setDvUsuario('9');
        cliente.setNomUsuario("Juan");
        cliente.setApellidoPaterno("Pérez");
        cliente.setSexoUsuario('M');
        cliente.setDirUsuario("Calle Test 123");
        cliente.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        cliente.setTelefonoUsuario("912345678");
        cliente.setEmailUsuario("juan@test.com");
        cliente.setPassUsuario(PASS_PLANA);

        clienteService.crearCliente(cliente);

        Optional<Cliente> clienteOpt = clienteService.getClienteByRut(RUT_TEST);
        assertTrue(clienteOpt.isPresent(), "El cliente debe existir después de crearlo");
        assertEquals("CLIENTE", clienteOpt.get().getTipoUsuario());
        assertEquals("ACTIVO", clienteOpt.get().getEstadoCuenta());
        assertTrue(encoder.matches(PASS_PLANA, clienteOpt.get().getPassUsuario()));
    }

    @Test
    @Order(2)
    public void testGetClientes() {
        List<Cliente> clientes = clienteService.getClientes();
        assertFalse(clientes.isEmpty(), "Debe haber clientes en la base");
        assertTrue(clientes.stream().anyMatch(c -> c.getRutUsuario().equals(RUT_TEST)));
    }

    @Test
    @Order(3)
    public void testGetClienteByRut() {
        Optional<Cliente> clienteOpt = clienteService.getClienteByRut(RUT_TEST);
        assertTrue(clienteOpt.isPresent(), "El cliente debe existir");
        assertEquals("Juan", clienteOpt.get().getNomUsuario());
        assertEquals("Pérez", clienteOpt.get().getApellidoPaterno());
    }

    @Test
    @Order(4)
    public void testUpdateCliente() {
        Cliente actualizado = new Cliente();
        actualizado.setDvUsuario('K');
        actualizado.setNomUsuario("Juan Carlos");
        actualizado.setApellidoPaterno("Gómez");
        actualizado.setApellidoMaterno("López");
        actualizado.setSexoUsuario('F');
        actualizado.setDirUsuario("Nueva Dirección 456");
        actualizado.setFechaNacimiento(LocalDate.of(1985, 5, 15));
        actualizado.setTelefonoUsuario("987654321");
        actualizado.setTel2Usuario("912345679");
        actualizado.setEmailUsuario("juan.nuevo@test.com");
        actualizado.setEstadoCuenta("INACTIVO");
        actualizado.setPassUsuario("nuevaPassword123");

        clienteService.updateCliente(actualizado, RUT_TEST);

        Optional<Cliente> clienteOpt = clienteService.getClienteByRut(RUT_TEST);
        assertTrue(clienteOpt.isPresent(), "Cliente actualizado debe existir");
        assertEquals("Juan Carlos", clienteOpt.get().getNomUsuario());
        assertEquals("Gómez", clienteOpt.get().getApellidoPaterno());
        assertEquals("INACTIVO", clienteOpt.get().getEstadoCuenta());
        assertTrue(encoder.matches("nuevaPassword123", clienteOpt.get().getPassUsuario()));
    }

    @Test
    @Order(5)
    public void testPatchCliente() {
        Cliente parche = new Cliente();
        parche.setEmailUsuario("juan.parche@test.com");
        parche.setTelefonoUsuario("998877665");
        parche.setEstadoCuenta("SUSPENDIDO");

        clienteService.patchCliente(parche, RUT_TEST);

        Optional<Cliente> clienteOpt = clienteService.getClienteByRut(RUT_TEST);
        assertTrue(clienteOpt.isPresent(), "Cliente parchado debe existir");
        assertEquals("juan.parche@test.com", clienteOpt.get().getEmailUsuario());
        assertEquals("998877665", clienteOpt.get().getTelefonoUsuario());
        assertEquals("SUSPENDIDO", clienteOpt.get().getEstadoCuenta());
        assertEquals("Juan Carlos", clienteOpt.get().getNomUsuario());
        assertEquals("Gómez", clienteOpt.get().getApellidoPaterno());
    }

    @Test
    @Order(6)
    public void testDeleteCliente() {
        clienteService.deleteCliente(RUT_TEST);
        Optional<Cliente> clienteOpt = clienteService.getClienteByRut(RUT_TEST);
        assertTrue(clienteOpt.isEmpty(), "El cliente debe haber sido eliminado");
    }
}
