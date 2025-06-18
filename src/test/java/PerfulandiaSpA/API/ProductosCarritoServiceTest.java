package PerfulandiaSpA.API;

import PerfulandiaSpA.DTO.ProductosCarritoDTO;
import PerfulandiaSpA.Entidades.Carrito;
import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Entidades.ProductosCarrito;
import PerfulandiaSpA.Repositorio.CarritoRepository;
import PerfulandiaSpA.Repositorio.ProductoRepository;
import PerfulandiaSpA.Servicio.ProductosCarritoService;
import jakarta.persistence.EntityNotFoundException;
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
public class ProductosCarritoServiceTest {

    @Autowired
    ProductosCarritoService productosCarritoService;

    @Autowired
    CarritoRepository carritoRepository;

    @Autowired
    ProductoRepository productoRepository;

    private Integer idCarrito;
    private Integer idProducto;
    private Integer idCreado;

    @BeforeAll
    public void setup() {
        // Buscamos un carrito y un producto ya existentes en la base de datos de prueba.
        Optional<Carrito> carritoOpt = carritoRepository.findAll().stream().findFirst();
        Optional<Producto> productoOpt = productoRepository.findAll().stream().findFirst();

        // Validamos que existan datos de prueba para poder ejecutar los tests.
        assertTrue(carritoOpt.isPresent(), "Debe existir al menos un carrito en la BD de prueba");
        assertTrue(productoOpt.isPresent(), "Debe existir al menos un producto en la BD de prueba");

        // Guardamos los IDs para usarlos en los tests siguientes.
        idCarrito = carritoOpt.get().getId();
        idProducto = productoOpt.get().getId();
    }

    // C - CREATE
    @Test
    @Order(1)
    public void testCrearProductosCarrito() {
        // Preparamos el DTO con la cantidad y los IDs válidos de carrito y producto.
        ProductosCarritoDTO dto = new ProductosCarritoDTO();
        dto.setCantProd(2);
        dto.setIdCarrito(idCarrito);
        dto.setIdProducto(idProducto);

        // Creamos el registro y comprobamos que se haya guardado correctamente.
        ProductosCarrito pc = productosCarritoService.crearProductosCarrito(dto);
        assertNotNull(pc.getId(), "El producto-carrito debe tener ID después de guardarse");
        assertEquals(2, pc.getCantProd());
        assertEquals(idCarrito, pc.getCarrito().getId());
        assertEquals(idProducto, pc.getProducto().getId());
        idCreado = pc.getId();
    }

    // R - READ by ID
    @Test
    @Order(2)
    public void testGetProductoCarritoByID() {
        // Buscamos el registro recién creado por su ID y verificamos que exista y tenga la cantidad correcta.
        Optional<ProductosCarrito> pcOpt = productosCarritoService.getProductoCarritoByID(idCreado);
        assertTrue(pcOpt.isPresent(), "El producto-carrito debe existir");
        assertEquals(2, pcOpt.get().getCantProd());
    }

    // R - READ all
    @Test
    @Order(3)
    public void testGetProductosCarritos() {
        // Obtenemos toda la lista de productos en carritos y revisamos que no esté vacía y que incluya el registro creado.
        List<ProductosCarrito> lista = productosCarritoService.getProductosCarritos();
        assertFalse(lista.isEmpty(), "Debe haber productos en carritos en la base");
        assertTrue(lista.stream().anyMatch(pc -> pc.getId().equals(idCreado)));
    }

    // U - UPDATE
    @Test
    @Order(4)
    public void testUpdateProductosCarrito() {
        // Actualizamos la cantidad del producto en el carrito usando los mismos IDs.
        ProductosCarritoDTO dto = new ProductosCarritoDTO();
        dto.setCantProd(5);
        dto.setIdCarrito(idCarrito);
        dto.setIdProducto(idProducto);

        // Comprobamos que los datos se actualicen correctamente.
        ProductosCarrito actualizado = productosCarritoService.updateProductosCarrito(dto, idCreado);
        assertEquals(5, actualizado.getCantProd());
        assertEquals(idCarrito, actualizado.getCarrito().getId());
        assertEquals(idProducto, actualizado.getProducto().getId());
    }

    // D - DELETE
    @Test
    @Order(5)
    public void testDeleteProductosCarrito() {
        // Eliminamos el registro y comprobamos que ya no exista en la base de datos.
        productosCarritoService.deleteProductosCarrito(idCreado);
        assertThrows(EntityNotFoundException.class, () -> {
            productosCarritoService.getProductoCarritoByID(idCreado);
        }, "El producto-carrito debe haber sido eliminado");
    }

    // CRUD - ERRORES
    @Test
    @Order(6)
    public void testCrearConCarritoInexistente() {
        // Intentamos crear un registro con un carrito que no existe y esperamos que lance una excepción.
        ProductosCarritoDTO dto = new ProductosCarritoDTO();
        dto.setCantProd(1);
        dto.setIdCarrito(999999); // ID inexistente
        dto.setIdProducto(idProducto);
        assertThrows(EntityNotFoundException.class, () -> {
            productosCarritoService.crearProductosCarrito(dto);
        });
    }

    @Test
    @Order(7)
    public void testCrearConProductoInexistente() {
        // Intentamos crear un registro con un producto que no existe y verificamos que lance una excepción.
        ProductosCarritoDTO dto = new ProductosCarritoDTO();
        dto.setCantProd(1);
        dto.setIdCarrito(idCarrito);
        dto.setIdProducto(999999); // ID inexistente
        assertThrows(EntityNotFoundException.class, () -> {
            productosCarritoService.crearProductosCarrito(dto);
        });
    }

    @Test
    @Order(8)
    public void testUpdateConProductoCarritoInexistente() {
        // Intentamos actualizar un registro que no existe y comprobamos que lance una excepción.
        ProductosCarritoDTO dto = new ProductosCarritoDTO();
        dto.setCantProd(1);
        dto.setIdCarrito(idCarrito);
        dto.setIdProducto(idProducto);
        assertThrows(EntityNotFoundException.class, () -> {
            productosCarritoService.updateProductosCarrito(dto, 999999);
        });
    }
}

// -----------------------------------------------------------------------------
// Resumen técnico:
// - Valida todas las operaciones CRUD para ProductosCarritoService usando datos de prueba de schema.sql y data.sql.
// - No crea ni modifica entidades de producción, solo utiliza registros de la base de test.
// - Cubre creación, lectura individual y general, actualización y eliminación de productos en carritos.
// - Prueba manejo de errores al intentar usar IDs inexistentes para carrito, producto o producto-carrito.
// - Se corrigieron posibles errores de referencias nulas y se mejoraron los comentarios para mayor claridad y repaso.
// -----------------------------------------------------------------------------
