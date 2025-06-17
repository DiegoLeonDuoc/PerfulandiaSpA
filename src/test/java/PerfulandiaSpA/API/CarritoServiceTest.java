package PerfulandiaSpA.API;

import PerfulandiaSpA.Entidades.Carrito;
import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Repositorio.ClienteRepository;
import PerfulandiaSpA.Servicio.CarritoService;
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
public class CarritoServiceTest {

    @Autowired
    CarritoService carritoService;

    @Autowired
    ClienteRepository clienteRepository;

    private Integer idCreado = null;
    private final Integer RUT_CLIENTE_EXISTENTE = 10000001; // Cliente de data.sql

    // C - Crear carrito (para un cliente que no tenga carrito en data.sql)
    @Test
    @Order(1)
    public void testSaveCarrito() {
        // Busca un cliente que NO tenga carrito en data.sql, o elimina el carrito existente antes de crear uno nuevo
        Cliente cliente = clienteRepository.findById(RUT_CLIENTE_EXISTENTE)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado en data.sql"));

        // Elimina carritos existentes de este cliente (solo para test, así nunca hay conflicto de PK)
        List<Carrito> existentes = carritoService.getCarritos();
        for (Carrito c : existentes) {
            if (c.getCliente().getRutUsuario().equals(RUT_CLIENTE_EXISTENTE)) {
                carritoService.deleteCarrito(c.getId());
            }
        }

        Carrito carrito = new Carrito();
        carrito.setCliente(cliente);

        carritoService.saveCarrito(carrito);

        assertNotNull(carrito.getId(), "El carrito debe tener ID después de guardarse");
        idCreado = carrito.getId();
    }

    // R - Obtener por ID
    @Test
    @Order(2)
    public void testGetCarritoById() {
        assertNotNull(idCreado, "El ID del carrito creado no debe ser null");
        Optional<Carrito> carritoOpt = carritoService.getCarritoByID(idCreado);

        assertTrue(carritoOpt.isPresent(), "El carrito creado debe existir");
        assertEquals(RUT_CLIENTE_EXISTENTE, carritoOpt.get().getCliente().getRutUsuario(),
                "El cliente asociado debe coincidir");
    }

    // R - Listar todos
    @Test
    @Order(3)
    public void testGetCarritos() {
        List<Carrito> carritos = carritoService.getCarritos();

        assertFalse(carritos.isEmpty(), "Debe haber carritos en la base de datos");
        assertTrue(carritos.stream().anyMatch(c -> c.getId().equals(idCreado)),
                "La lista debe contener el carrito creado");
    }

    // U - Actualizar carrito (sin cambiar cliente)
    @Test
    @Order(4)
    public void testUpdateCarrito() {
        assertNotNull(idCreado, "No se puede actualizar: el carrito no fue creado exitosamente");
        Cliente cliente = clienteRepository.findById(RUT_CLIENTE_EXISTENTE)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Carrito actualizado = new Carrito();
        actualizado.setCliente(cliente);

        carritoService.updateCarrito(actualizado, idCreado);

        Optional<Carrito> carritoOpt = carritoService.getCarritoByID(idCreado);
        assertTrue(carritoOpt.isPresent(), "El carrito actualizado debe existir");
        assertEquals(RUT_CLIENTE_EXISTENTE, carritoOpt.get().getCliente().getRutUsuario(),
                "El cliente asociado debe seguir siendo el mismo");
    }

    // D - Eliminar carrito
    @Test
    @Order(5)
    public void testDeleteCarrito() {
        assertNotNull(idCreado, "El ID del carrito no debe ser null antes de eliminar");
        carritoService.deleteCarrito(idCreado);

        Optional<Carrito> carritoOpt = carritoService.getCarritoByID(idCreado);
        assertTrue(carritoOpt.isEmpty(), "El carrito debe haber sido eliminado");
    }
}
