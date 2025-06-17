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
    private final Integer RUT_TEST = 88888888;
    private final String PASS_PLANA = "cliente123";

    // C - Crear cliente
    @Test
    @Order(1)
    public void testCrearCliente() {
        Cliente cliente = new Cliente();
        cliente.setRutUsuario(RUT_TEST);
        cliente.setDvUsuario('8');
        cliente.setNomUsuario("Pedro");
        cliente.setApellidoPaterno("García");
        cliente.setSexoUsuario('M');
        cliente.setDirUsuario("Calle Cliente 123");
        cliente.setFechaNacimiento(LocalDate.of(1992, 3, 10));
        cliente.setTelefonoUsuario("912345678");
        cliente.setEmailUsuario("pedro@correo.com");
        cliente.setPassUsuario(PASS_PLANA);

        clienteService.crearCliente(cliente);

        Optional<Cliente> clienteOpt = clienteService.getClienteByRut(RUT_TEST);
        assertTrue(clienteOpt.isPresent(), "El cliente debe existir después de crearlo");
        assertEquals("CLIENTE", clienteOpt.get().getTipoUsuario());
        assertEquals("ACTIVO", clienteOpt.get().getEstadoCuenta());
        assertTrue(encoder.matches(PASS_PLANA, clienteOpt.get().getPassUsuario()));
    }

    // R - Obtener todos
    @Test
    @Order(2)
    public void testGetClientes() {
        List<Cliente> clientes = clienteService.getClientes();
        assertFalse(clientes.isEmpty(), "Debe haber clientes en la base");
        assertTrue(clientes.stream().anyMatch(c -> c.getRutUsuario().equals(RUT_TEST)));
    }

    // R - Obtener por RUT
    @Test
    @Order(3)
    public void testGetClienteByRut() {
        Optional<Cliente> clienteOpt = clienteService.getClienteByRut(RUT_TEST);
        assertTrue(clienteOpt.isPresent(), "El cliente debe existir");
        assertEquals("Pedro", clienteOpt.get().getNomUsuario());
        assertEquals("García", clienteOpt.get().getApellidoPaterno());
    }

    // U - Actualización completa (PUT)
    @Test
    @Order(4)
    public void testUpdateCliente() {
        Cliente actualizado = new Cliente();
        actualizado.setDvUsuario('K');
        actualizado.setNomUsuario("Pedro Actualizado");
        actualizado.setApellidoPaterno("González");
        actualizado.setApellidoMaterno("López");
        actualizado.setSexoUsuario('F');
        actualizado.setDirUsuario("Nueva Dirección 456");
        actualizado.setFechaNacimiento(LocalDate.of(1990, 12, 15));
        actualizado.setTelefonoUsuario("998877665");
        actualizado.setTel2Usuario("911223344");
        actualizado.setEmailUsuario("pedro.actualizado@correo.com");
        actualizado.setEstadoCuenta("INACTIVO");
        actualizado.setPassUsuario("nuevaClave123");

        clienteService.updateCliente(actualizado, RUT_TEST);

        Optional<Cliente> clienteOpt = clienteService.getClienteByRut(RUT_TEST);
        assertTrue(clienteOpt.isPresent(), "Cliente actualizado debe existir");
        assertEquals("Pedro Actualizado", clienteOpt.get().getNomUsuario());
        assertEquals("González", clienteOpt.get().getApellidoPaterno());
        assertEquals("INACTIVO", clienteOpt.get().getEstadoCuenta());
        assertTrue(encoder.matches("nuevaClave123", clienteOpt.get().getPassUsuario()));
    }

    // U - Actualización parcial (PATCH)
    @Test
    @Order(5)
    public void testPatchCliente() {
        Cliente parche = new Cliente();
        parche.setEmailUsuario("parche.cliente@correo.com");
        parche.setTelefonoUsuario("900000000");
        parche.setEstadoCuenta("SUSPENDIDO");
        parche.setPassUsuario("parcheClave");

        clienteService.patchCliente(parche, RUT_TEST);

        Optional<Cliente> clienteOpt = clienteService.getClienteByRut(RUT_TEST);
        assertTrue(clienteOpt.isPresent(), "Cliente parchado debe existir");
        assertEquals("parche.cliente@correo.com", clienteOpt.get().getEmailUsuario());
        assertEquals("900000000", clienteOpt.get().getTelefonoUsuario());
        assertEquals("SUSPENDIDO", clienteOpt.get().getEstadoCuenta());
        assertTrue(encoder.matches("parcheClave", clienteOpt.get().getPassUsuario()));
        // Campos no modificados
        assertEquals("Pedro Actualizado", clienteOpt.get().getNomUsuario());
        assertEquals("González", clienteOpt.get().getApellidoPaterno());
    }

    // D - Eliminar cliente
    @Test
    @Order(6)
    public void testDeleteCliente() {
        clienteService.deleteCliente(RUT_TEST);
        Optional<Cliente> clienteOpt = clienteService.getClienteByRut(RUT_TEST);
        assertTrue(clienteOpt.isEmpty(), "El cliente debe haber sido eliminado");
    }
}
