package PerfulandiaSpA.API;

import PerfulandiaSpA.Entidades.Carrito;
import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Repositorio.ClienteRepository;
import PerfulandiaSpA.Servicio.CarritoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class CarritoServiceTest {

    @Autowired
    CarritoService carritoService;

    @Autowired
    ClienteRepository clienteRepository;

    private Integer idCreado;
    private Integer clienteId;
    private Integer nuevoClienteId;

    @BeforeAll
    public void setupCliente() {
        // Crear cliente base solo una vez para todos los tests
        Cliente cliente = new Cliente();
        cliente.setRutUsuario(99999999);
        cliente.setDvUsuario('9');
        cliente.setNomUsuario("Cliente Test");
        cliente.setApellidoPaterno("Apellido");
        cliente.setApellidoMaterno("Materno");
        cliente.setSexoUsuario('M');
        cliente.setDirUsuario("Dirección 123");
        cliente.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        cliente.setTelefonoUsuario("912345678");
        cliente.setEmailUsuario("cliente@test.com");
        cliente.setPassUsuario("password123");
        cliente.setEstadoCuenta("ACTIVO");
        clienteRepository.save(cliente);
        clienteId = cliente.getRutUsuario();

        // Crear un segundo cliente para pruebas de actualización
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setRutUsuario(88888888);
        nuevoCliente.setDvUsuario('8');
        nuevoCliente.setNomUsuario("Nuevo Cliente");
        nuevoCliente.setApellidoPaterno("NuevoApellido");
        nuevoCliente.setApellidoMaterno("NuevoMaterno");
        nuevoCliente.setSexoUsuario('F');
        nuevoCliente.setDirUsuario("Otra dirección 456");
        nuevoCliente.setFechaNacimiento(LocalDate.of(1995, 5, 5));
        nuevoCliente.setTelefonoUsuario("911111111");
        nuevoCliente.setEmailUsuario("nuevo@cliente.com");
        nuevoCliente.setPassUsuario("nuevopass");
        nuevoCliente.setEstadoCuenta("ACTIVO");
        clienteRepository.save(nuevoCliente);
        nuevoClienteId = nuevoCliente.getRutUsuario();
    }

    @Test
    @Order(1)
    public void testSaveCarrito() {
        Carrito carrito = new Carrito();
        carrito.setCliente(clienteRepository.findById(clienteId).orElseThrow());
        carritoService.saveCarrito(carrito);
        assertNotNull(carrito.getId(), "El carrito debe tener ID después de guardarse");
        idCreado = carrito.getId();
    }

    @Test
    @Order(2)
    public void testGetCarritoById() {
        assertNotNull(idCreado, "El ID del carrito debe estar inicializado");
        Optional<Carrito> carritoOpt = carritoService.getCarritoByID(idCreado);
        assertTrue(carritoOpt.isPresent(), "El carrito creado debe existir");
        assertEquals(clienteId, carritoOpt.get().getCliente().getRutUsuario());
    }

    @Test
    @Order(3)
    public void testGetCarritos() {
        assertNotNull(idCreado, "El ID del carrito debe estar inicializado");
        List<Carrito> carritos = carritoService.getCarritos();
        assertFalse(carritos.isEmpty(), "Debe haber carritos en la base");
        assertTrue(carritos.stream().anyMatch(c -> c.getId().equals(idCreado)), "Debe existir el carrito creado");
    }

    @Test
    @Order(4)
    public void testUpdateCarrito() {
        assertNotNull(idCreado, "El ID del carrito debe estar inicializado");
        Carrito actualizado = new Carrito();
        actualizado.setCliente(clienteRepository.findById(nuevoClienteId).orElseThrow());
        carritoService.updateCarrito(actualizado, idCreado);

        Optional<Carrito> carritoOpt = carritoService.getCarritoByID(idCreado);
        assertTrue(carritoOpt.isPresent(), "Carrito actualizado debe existir");
        assertEquals(nuevoClienteId, carritoOpt.get().getCliente().getRutUsuario());
    }

    @Test
    @Order(5)
    public void testDeleteCarrito() {
        assertNotNull(idCreado, "El ID del carrito debe estar inicializado");
        carritoService.deleteCarrito(idCreado);
        Optional<Carrito> carritoOpt = carritoService.getCarritoByID(idCreado);
        assertTrue(carritoOpt.isEmpty(), "El carrito debe haber sido eliminado");
    }
}

// Resumen:
// - Se crean clientes completos solo una vez para evitar conflictos y errores de integridad.
// - El id del carrito se mantiene entre tests usando variables de instancia.
// - Cada test es autocontenible y sigue el flujo CRUD real.
// - Los comentarios // explican el objetivo de cada bloque.
// - Así se evitan errores de ciclo de vida, IDs y datos incompletos.