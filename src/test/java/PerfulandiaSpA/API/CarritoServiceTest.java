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
        // Prueba crear un nuevo carrito asociado a un cliente
        Carrito carrito = new Carrito();
        carrito.setCliente(clienteRepository.findById(clienteId).orElseThrow());
        carritoService.saveCarrito(carrito);

        // Verificamos que se generó un ID automáticamente
        assertNotNull(carrito.getId(), "El carrito debe tener ID después de guardarse");
        idCreado = carrito.getId(); // Guardamos el ID para usar en otros tests
    }

    @Test
    @Order(2)
    public void testGetCarritoById() {
        // Buscamos el carrito por su ID y verificamos que existe
        assertNotNull(idCreado, "El ID del carrito debe estar inicializado");
        Optional<Carrito> carritoOpt = carritoService.getCarritoByID(idCreado);

        // Confirmamos que el cliente asociado es correcto
        assertTrue(carritoOpt.isPresent(), "El carrito creado debe existir");
        assertEquals(clienteId, carritoOpt.get().getCliente().getRutUsuario());
    }

    @Test
    @Order(3)
    public void testGetCarritos() {
        // Obtenemos todos los carritos y verificamos que el creado está en la lista
        assertNotNull(idCreado, "El ID del carrito debe estar inicializado");
        List<Carrito> carritos = carritoService.getCarritos();

        assertFalse(carritos.isEmpty(), "Debe haber carritos en la base");
        assertTrue(carritos.stream().anyMatch(c -> c.getId().equals(idCreado)), "Debe existir el carrito creado");
    }

    @Test
    @Order(4)
    public void testUpdateCarrito() {
        // Actualizamos el carrito cambiando su cliente asociado
        assertNotNull(idCreado, "El ID del carrito debe estar inicializado");
        Carrito actualizado = new Carrito();
        actualizado.setCliente(clienteRepository.findById(nuevoClienteId).orElseThrow());

        carritoService.updateCarrito(actualizado, idCreado);

        // Verificamos que el cambio se aplicó correctamente
        Optional<Carrito> carritoOpt = carritoService.getCarritoByID(idCreado);
        assertTrue(carritoOpt.isPresent(), "Carrito actualizado debe existir");
        assertEquals(nuevoClienteId, carritoOpt.get().getCliente().getRutUsuario());
    }

    @Test
    @Order(5)
    public void testDeleteCarrito() {
        // Eliminamos el carrito y verificamos que ya no existe
        assertNotNull(idCreado, "El ID del carrito debe estar inicializado");
        carritoService.deleteCarrito(idCreado);

        Optional<Carrito> carritoOpt = carritoService.getCarritoByID(idCreado);
        assertTrue(carritoOpt.isEmpty(), "El carrito debe haber sido eliminado");
    }
}

// -----------------------------------------------------------------------------
// Resumen técnico:
// - Valida CRUD completo para CarritoService con énfasis en relaciones con Cliente
// - Prueba creación, lectura, actualización de cliente asociado y eliminación
// - Usa dos clientes diferentes para probar cambios en relaciones (clienteId y nuevoClienteId)
// - Mantiene estado entre tests con variables idCreado, clienteId y nuevoClienteId
// - Asegura integridad referencial usando @BeforeAll para creación de clientes
// - Verifica persistencia correcta de relaciones cliente-carrito
// - Garantiza limpieza de datos con @Sql y orden de ejecución con @Order
// - Corrige posibles nulls verificando inicialización de IDs con assertNotNull
// -----------------------------------------------------------------------------
