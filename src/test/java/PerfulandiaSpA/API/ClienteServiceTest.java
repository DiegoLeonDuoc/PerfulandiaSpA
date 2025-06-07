package PerfulandiaSpA.API;

import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Servicio.ClienteService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class ClienteServiceTest {

    @Autowired
    ClienteService clienteService;
    private Integer rutcreado = 12345678;


    @Test
    @Order(1)
    // C Crear un cliente
    public void testCrearCliente() {
        Cliente cliente = new Cliente();
        cliente.setRutUsuario(rutcreado);
        cliente.setDvUsuario('K');
        cliente.setNomUsuario("Juanito");
        cliente.setNom2Usuario("Matias");
        cliente.setApellidoPaterno("Perez");
        cliente.setApellidoMaterno("Sanhueza");
        cliente.setSexoUsuario('M');
        cliente.setDirUsuario("Calle 123");
        cliente.setFechaNacimiento(LocalDate.parse("1992-01-01"));
        cliente.setTelefonoUsuario("912525678");
        cliente.setTel2Usuario(null);
        cliente.setEmailUsuario("pepetapia@gmail.com");
        cliente.setPassUsuario("1111");
        cliente.setTipoUsuario("Cliente");
        cliente.setEstadoCuenta("Activo");
        String resultado = clienteService.crearCliente(cliente);
        Assertions.assertEquals("Cliente agregado con éxito", resultado);
    }

    @Test
    @Order(2)
    // R Mostrar todos los clientes
    public void testGetClientes() {
        Cliente clienteTest = new Cliente();
        clienteTest.setRutUsuario(98765432);
        clienteTest.setDvUsuario('1');
        clienteTest.setNomUsuario("Pedro");
        clienteTest.setNom2Usuario("Juan");
        clienteTest.setApellidoPaterno("Gonzalez");
        clienteTest.setApellidoMaterno("Lopez");
        clienteTest.setSexoUsuario('M');
        clienteTest.setDirUsuario("Calle Falsa 123");
        clienteTest.setFechaNacimiento(LocalDate.parse("1985-05-15"));
        clienteTest.setTelefonoUsuario("912345678");
        clienteTest.setTel2Usuario(null);
        clienteTest.setEmailUsuario("pedro.juan@example.com");
        clienteTest.setPassUsuario("1234");
        clienteTest.setTipoUsuario("Cliente");
        clienteTest.setEstadoCuenta("Activo");
        clienteService.crearCliente(clienteTest);

        List<Cliente> clientes = clienteService.getClientesJSON();

        assertNotNull(clientes);
        assertFalse(clientes.isEmpty(), "No debería estar vacío");

        boolean existe = clientes.stream()
                .anyMatch(c -> c.getRutUsuario().equals(clienteTest.getRutUsuario()) &&
                        c.getEmailUsuario().equals(clienteTest.getEmailUsuario()));

        assertTrue(existe, "El cliente creado debería estar en la lista");

    }
    @Test
    @Order(3)
    // R Buscar cliente por RUT
    public void testGetClienteByRut() {
        List<Cliente> clientes = clienteService.getClientesJSON();
        Integer rutBuscado = null;

        for (Cliente c : clientes) {
            rutBuscado = c.getRutUsuario();
            break;
        }

        assertNotNull(rutBuscado, "No hay clientes en la base de datos");

        String resultado = clienteService.getClienteByRut(rutBuscado);
        assertTrue(resultado.contains("ID:"), "Falta ID");
        assertTrue(resultado.contains("RUT:"), "Falta RUT");
        assertTrue(resultado.contains("Nombre completo:"), "Falta nombre completo");
        assertTrue(resultado.contains("Email:"), "Falta email");


    }
    @Test
    @Order(4)
    // U Actualizar un cliente
    public void testUpdateCliente() {
        Cliente clienteBase = new Cliente();
        clienteBase.setRutUsuario(rutcreado);
        clienteBase.setDvUsuario('K');
        clienteBase.setNomUsuario("Juanito");
        clienteBase.setNom2Usuario("Matias");
        clienteBase.setApellidoPaterno("Perez");
        clienteBase.setApellidoMaterno("Sanhueza");
        clienteBase.setSexoUsuario('M');
        clienteBase.setDirUsuario("Calle 123");
        clienteBase.setFechaNacimiento(LocalDate.parse("1992-01-01"));
        clienteBase.setTelefonoUsuario("912525678");
        clienteBase.setTel2Usuario(null);
        clienteBase.setEmailUsuario("pepetapia@gmail.com");
        clienteBase.setPassUsuario("1111");
        clienteBase.setEstadoCuenta("ACTIVO");
        clienteBase.setTipoUsuario("CLIENTE");
        clienteService.crearCliente(clienteBase); // por si acaso

        // Cliente modificado
        Cliente actualizado = new Cliente();
        actualizado.setRutUsuario(rutcreado);
        actualizado.setDvUsuario('K');
        actualizado.setNomUsuario("ModificadoNombre");
        actualizado.setNom2Usuario("SegundoNombreMod");
        actualizado.setApellidoPaterno("ApellidoPMod");
        actualizado.setApellidoMaterno("ApellidoMMod");
        actualizado.setSexoUsuario('M');
        actualizado.setDirUsuario("Calle Nueva 789");
        actualizado.setFechaNacimiento(LocalDate.parse("1995-06-15"));
        actualizado.setTelefonoUsuario("911223344");
        actualizado.setTel2Usuario("922334455");
        actualizado.setEmailUsuario("modificado@correo.cl");
        actualizado.setPassUsuario("nuevoPass123");
        actualizado.setEstadoCuenta("Inactivo");
        String resultado = clienteService.updateCliente(actualizado, rutcreado);
        assertEquals("Cliente actualizado con éxito", resultado);

        //validar
        String clienteActual = clienteService.getClienteByRut(rutcreado);
        assertTrue(clienteActual.contains("ModificadoNombre"));
        assertTrue(clienteActual.contains("Inactivo"));
        assertTrue(clienteActual.contains("911223344"));
        assertTrue(clienteActual.contains("modificado@correo.cl"));
    }
    @Test
    @Order(5)
    // U
    public void testParcharCliente() {
        Cliente clienteBase = new Cliente();
        clienteBase.setRutUsuario(rutcreado);
        clienteBase.setDvUsuario('K');
        clienteBase.setNomUsuario("Juanito");
        clienteBase.setNom2Usuario("Matias");
        clienteBase.setApellidoPaterno("Perez");
        clienteBase.setApellidoMaterno("Sanhueza");
        clienteBase.setSexoUsuario('M');
        clienteBase.setDirUsuario("Calle 123");
        clienteBase.setFechaNacimiento(LocalDate.parse("1992-01-01"));
        clienteBase.setTelefonoUsuario("912525678");
        clienteBase.setTel2Usuario(null);
        clienteBase.setEmailUsuario("pepetapia@gmail.com");
        clienteBase.setPassUsuario("1111");
        clienteBase.setEstadoCuenta("ACTIVO");
        clienteBase.setTipoUsuario("CLIENTE");
        clienteService.crearCliente(clienteBase);

        Cliente parche = new Cliente();
        parche.setRutUsuario(rutcreado);
        parche.setNomUsuario("Parcheado");
        parche.setEstadoCuenta("Inactivo");

        String resultado = clienteService.parcharCliente(parche, rutcreado);
        assertEquals("Cliente actualizado con éxito", resultado);

        String clienteModificado = clienteService.getClienteByRut(rutcreado);
        assertTrue(clienteModificado.contains("Parcheado"), "El nombre no fue parcheado");
        assertTrue(clienteModificado.contains("Estado de cuenta: Inactivo"), "El estado no fue parcheado correctamente");
    }
    @Test
    @Order(6)
    // D Eliminar cliente
    public void testDeleteCliente() {
        Cliente clienteBase = new Cliente();
        clienteBase.setRutUsuario(rutcreado);
        clienteBase.setDvUsuario('K');
        clienteBase.setNomUsuario("Juanito");
        clienteBase.setNom2Usuario("Matias");
        clienteBase.setApellidoPaterno("Perez");
        clienteBase.setApellidoMaterno("Sanhueza");
        clienteBase.setSexoUsuario('M');
        clienteBase.setDirUsuario("Calle 123");
        clienteBase.setFechaNacimiento(LocalDate.parse("1992-01-01"));
        clienteBase.setTelefonoUsuario("912525678");
        clienteBase.setTel2Usuario(null);
        clienteBase.setEmailUsuario("pepetapia@gmail.com");
        clienteBase.setPassUsuario("1111");
        clienteBase.setEstadoCuenta("ACTIVO");
        clienteBase.setTipoUsuario("CLIENTE");
        clienteService.crearCliente(clienteBase);

        String resultado = clienteService.deleteCliente(rutcreado);
        assertEquals("Cliente eliminado con éxito", resultado);

        String buscar = clienteService.getClienteByRut(rutcreado);
        assertEquals("Cliente no encontrado", buscar);
    }
}
