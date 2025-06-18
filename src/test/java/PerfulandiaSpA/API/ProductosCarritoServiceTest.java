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
        // Buscar un carrito y un producto existentes en la base de datos de prueba
        Optional<Carrito> carritoOpt = carritoRepository.findAll().stream().findFirst();
        Optional<Producto> productoOpt = productoRepository.findAll().stream().findFirst();

        assertTrue(carritoOpt.isPresent(), "Debe existir al menos un carrito en la BD de prueba");
        assertTrue(productoOpt.isPresent(), "Debe existir al menos un producto en la BD de prueba");

        idCarrito = carritoOpt.get().getId();
        idProducto = productoOpt.get().getId();
    }

    // C - CREATE
    @Test
    @Order(1)
    public void testCrearProductosCarrito() {
        ProductosCarritoDTO dto = new ProductosCarritoDTO();
        dto.setCantProd(2);
        dto.setIdCarrito(idCarrito);
        dto.setIdProducto(idProducto);

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
        Optional<ProductosCarrito> pcOpt = productosCarritoService.getProductoCarritoByID(idCreado);
        assertTrue(pcOpt.isPresent(), "El producto-carrito debe existir");
        assertEquals(2, pcOpt.get().getCantProd());
    }

    // R - READ all
    @Test
    @Order(3)
    public void testGetProductosCarritos() {
        List<ProductosCarrito> lista = productosCarritoService.getProductosCarritos();
        assertFalse(lista.isEmpty(), "Debe haber productos en carritos en la base");
        assertTrue(lista.stream().anyMatch(pc -> pc.getId().equals(idCreado)));
    }

    // U - UPDATE
    @Test
    @Order(4)
    public void testUpdateProductosCarrito() {
        ProductosCarritoDTO dto = new ProductosCarritoDTO();
        dto.setCantProd(5);
        dto.setIdCarrito(idCarrito);
        dto.setIdProducto(idProducto);

        ProductosCarrito actualizado = productosCarritoService.updateProductosCarrito(dto, idCreado);
        assertEquals(5, actualizado.getCantProd());
        assertEquals(idCarrito, actualizado.getCarrito().getId());
        assertEquals(idProducto, actualizado.getProducto().getId());
    }

    // D - DELETE
    @Test
    @Order(5)
    public void testDeleteProductosCarrito() {
        productosCarritoService.deleteProductosCarrito(idCreado);
        assertThrows(EntityNotFoundException.class, () -> {
            productosCarritoService.getProductoCarritoByID(idCreado);
        }, "El producto-carrito debe haber sido eliminado");
    }

    // CRUD - ERRORES
    @Test
    @Order(6)
    public void testCrearConCarritoInexistente() {
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
// - El test se apoya en los datos de prueba de schema.sql y data.sql
// - No crea entidades de producción, solo usa lo que ya existe en la BD de test
// - Valida CRUD completo y manejo de errores para ProductosCarritoService
// - No requiere modificar ninguna entidad ni lógica de producción
// -----------------------------------------------------------------------------
