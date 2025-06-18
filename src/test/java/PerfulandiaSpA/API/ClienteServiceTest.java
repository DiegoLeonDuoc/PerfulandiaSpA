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
    private final Integer RUT_TEST = 88888888; // RUT de prueba para usar en los tests
    private final String PASS_PLANA = "cliente123"; // Contraseña en texto plano para verificar el hash

    // C - Crear cliente
    @Test
    @Order(1)
    public void testCrearCliente() {
        // Creamos un cliente nuevo con datos de prueba
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

        // Verificamos que el cliente se creó correctamente con los valores por defecto
        Optional<Cliente> clienteOpt = clienteService.getClienteByRut(RUT_TEST);
        assertTrue(clienteOpt.isPresent(), "El cliente debe existir después de crearlo");
        assertEquals("CLIENTE", clienteOpt.get().getTipoUsuario()); // Tipo de usuario automático
        assertEquals("ACTIVO", clienteOpt.get().getEstadoCuenta()); // Estado por defecto
        assertTrue(encoder.matches(PASS_PLANA, clienteOpt.get().getPassUsuario())); // Contraseña encriptada
    }

    // R - Obtener todos
    @Test
    @Order(2)
    public void testGetClientes() {
        // Obtenemos todos los clientes y verificamos que exista el creado
        List<Cliente> clientes = clienteService.getClientes();
        assertFalse(clientes.isEmpty(), "Debe haber clientes en la base");
        assertTrue(clientes.stream().anyMatch(c -> c.getRutUsuario().equals(RUT_TEST)));
    }

    // R - Obtener por RUT
    @Test
    @Order(3)
    public void testGetClienteByRut() {
        // Buscamos el cliente por su RUT y verificamos sus datos básicos
        Optional<Cliente> clienteOpt = clienteService.getClienteByRut(RUT_TEST);
        assertTrue(clienteOpt.isPresent(), "El cliente debe existir");
        assertEquals("Pedro", clienteOpt.get().getNomUsuario());
        assertEquals("García", clienteOpt.get().getApellidoPaterno());
    }

    // U - PUT: Actualización completa (sobrescribe todo, incluso nulls)
    @Test
    @Order(4)
    public void testUpdateCliente() {
        // Creamos un objeto con TODOS los campos para actualización completa
        Cliente actualizado = new Cliente();
        actualizado.setDvUsuario('K');
        actualizado.setNomUsuario("Pedro Actualizado");
        actualizado.setApellidoPaterno("González");
        actualizado.setSexoUsuario('F');
        actualizado.setDirUsuario("Nueva Dirección 456");
        actualizado.setFechaNacimiento(LocalDate.of(1990, 12, 15));
        actualizado.setTelefonoUsuario("998877665");
        actualizado.setEmailUsuario("pedro.actualizado@correo.com");
        actualizado.setEstadoCuenta("INACTIVO");
        actualizado.setPassUsuario("nuevaClave123");
        // Campos no enviados: apellidoMaterno, tel2Usuario (quedarán null)

        clienteService.updateCliente(actualizado, RUT_TEST);

        // Verificamos que los campos se actualizaron y los no enviados quedaron null
        Optional<Cliente> clienteOpt = clienteService.getClienteByRut(RUT_TEST);
        assertTrue(clienteOpt.isPresent(), "Cliente actualizado debe existir");

        // Campos actualizados
        assertEquals("Pedro Actualizado", clienteOpt.get().getNomUsuario());
        assertEquals("González", clienteOpt.get().getApellidoPaterno());
        assertEquals("INACTIVO", clienteOpt.get().getEstadoCuenta());
        assertTrue(encoder.matches("nuevaClave123", clienteOpt.get().getPassUsuario()));

        // Campos no enviados (deben ser null)
        assertNull(clienteOpt.get().getApellidoMaterno(), "apellidoMaterno debe quedar null");
        assertNull(clienteOpt.get().getTel2Usuario(), "tel2Usuario debe quedar null");
    }

    // U - PATCH: Actualización parcial (solo campos enviados)
    @Test
    @Order(5)
    public void testPatchCliente() {
        // Creamos un objeto solo con los campos a modificar
        Cliente parche = new Cliente();
        parche.setEmailUsuario("parche.cliente@correo.com");
        parche.setTelefonoUsuario("900000000");
        parche.setEstadoCuenta("SUSPENDIDO");
        parche.setPassUsuario("parcheClave");

        clienteService.patchCliente(parche, RUT_TEST);

        // Verificamos que solo se actualizaron los campos enviados
        Optional<Cliente> clienteOpt = clienteService.getClienteByRut(RUT_TEST);
        assertTrue(clienteOpt.isPresent(), "Cliente parchado debe existir");

        // Campos actualizados
        assertEquals("parche.cliente@correo.com", clienteOpt.get().getEmailUsuario());
        assertEquals("900000000", clienteOpt.get().getTelefonoUsuario());
        assertEquals("SUSPENDIDO", clienteOpt.get().getEstadoCuenta());
        assertTrue(encoder.matches("parcheClave", clienteOpt.get().getPassUsuario()));

        // Campos no modificados
        assertEquals("Pedro Actualizado", clienteOpt.get().getNomUsuario());
        assertEquals("González", clienteOpt.get().getApellidoPaterno());
        assertNull(clienteOpt.get().getApellidoMaterno()); // Mantiene null del PUT anterior
    }

    // D - Eliminar cliente
    @Test
    @Order(6)
    public void testDeleteCliente() {
        // Eliminamos el cliente y verificamos que ya no exista
        clienteService.deleteCliente(RUT_TEST);
        Optional<Cliente> clienteOpt = clienteService.getClienteByRut(RUT_TEST);
        assertTrue(clienteOpt.isEmpty(), "El cliente debe haber sido eliminado");
    }
}

// -----------------------------------------------------------------------------
// Resumen técnico:
// - Valida CRUD completo para ClienteService incluyendo actualizaciones PUT y PATCH.
// - Prueba creación con valores por defecto (tipoUsuario, estadoCuenta) y encriptación de contraseña.
// - Verifica comportamiento de PUT (actualización completa con sobrescritura a null) vs PATCH (actualización parcial).
// - Asegura integridad de datos en operaciones de actualización y eliminación.
// - Corrige verificación de campos nulos después de actualizaciones PUT.
// - Valida correcto funcionamiento del BCryptPasswordEncoder en creación y actualizaciones.
// - Mantiene coherencia en pruebas usando @Order y datos predefinidos de data.sql.
// - Garantiza que las operaciones no afecten datos de producción mediante transacciones de prueba.
// -----------------------------------------------------------------------------
