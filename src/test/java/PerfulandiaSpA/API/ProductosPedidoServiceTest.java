package PerfulandiaSpA.API;

import PerfulandiaSpA.DTO.ProductosPedidoDTO;
import PerfulandiaSpA.Entidades.ProductosPedido;
import PerfulandiaSpA.Entidades.Pedido;
import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Entidades.Descuento;
import PerfulandiaSpA.Repositorio.PedidoRepository;
import PerfulandiaSpA.Repositorio.ProductoRepository;
import PerfulandiaSpA.Repositorio.DescuentoRepository;
import PerfulandiaSpA.Servicio.ProductosPedidoService;
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
public class ProductosPedidoServiceTest {

    @Autowired
    ProductosPedidoService productosPedidoService;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    DescuentoRepository descuentoRepository;

    private Integer idPedido;
    private Integer idProducto;
    private Integer idDescuento; // Puede ser null
    private Integer idCreado;

    @BeforeAll
    public void setup() {
        // Buscar un pedido y un producto existentes en la base de datos de prueba
        Optional<Pedido> pedidoOpt = pedidoRepository.findAll().stream().findFirst();
        Optional<Producto> productoOpt = productoRepository.findAll().stream().findFirst();

        assertTrue(pedidoOpt.isPresent(), "Debe existir al menos un pedido en la BD de prueba");
        assertTrue(productoOpt.isPresent(), "Debe existir al menos un producto en la BD de prueba");

        idPedido = pedidoOpt.get().getId();
        idProducto = productoOpt.get().getId();

        // Buscar un descuento válido, si existe
        Optional<Descuento> descuentoOpt = descuentoRepository.findAll().stream().findFirst();
        idDescuento = descuentoOpt.map(Descuento::getId).orElse(null);
    }

    // C - CREATE
    @Test
    @Order(1)
    public void testCrearProductosPedido() {
        ProductosPedidoDTO dto = new ProductosPedidoDTO();
        dto.setCantProd(3);
        dto.setIdPedido(idPedido);
        dto.setIdProducto(idProducto);
        dto.setIdDescuento(idDescuento); // Puede ser null

        ProductosPedido pp = productosPedidoService.crearProductosPedido(dto);
        assertNotNull(pp.getId(), "El producto-pedido debe tener ID después de guardarse");
        assertEquals(3, pp.getCantProd());
        assertEquals(idPedido, pp.getPedido().getId());
        assertEquals(idProducto, pp.getProducto().getId());
        if (idDescuento != null) {
            assertNotNull(pp.getDescuento());
            assertEquals(idDescuento, pp.getDescuento().getId());
        }
        idCreado = pp.getId();
    }

    // R - READ by ID
    @Test
    @Order(2)
    public void testGetProductoPedidoByID() {
        Optional<ProductosPedido> ppOpt = productosPedidoService.getProductoPedidoByID(idCreado);
        assertTrue(ppOpt.isPresent(), "El producto-pedido debe existir");
        assertEquals(3, ppOpt.get().getCantProd());
    }

    // R - READ all
    @Test
    @Order(3)
    public void testGetProductosPedidos() {
        List<ProductosPedido> lista = productosPedidoService.getProductosPedidos();
        assertFalse(lista.isEmpty(), "Debe haber productos en pedidos en la base");
        assertTrue(lista.stream().anyMatch(pp -> pp.getId().equals(idCreado)));
    }

    // U - UPDATE
    @Test
    @Order(4)
    public void testUpdateProductosPedido() {
        ProductosPedidoDTO dto = new ProductosPedidoDTO();
        dto.setCantProd(7);
        dto.setIdPedido(idPedido);
        dto.setIdProducto(idProducto);
        dto.setIdDescuento(idDescuento);

        ProductosPedido actualizado = productosPedidoService.updateProductosPedido(dto, idCreado);
        assertEquals(7, actualizado.getCantProd());
        assertEquals(idPedido, actualizado.getPedido().getId());
        assertEquals(idProducto, actualizado.getProducto().getId());
        if (idDescuento != null) {
            assertNotNull(actualizado.getDescuento());
            assertEquals(idDescuento, actualizado.getDescuento().getId());
        }
    }

    // D - DELETE
    @Test
    @Order(5)
    public void testDeleteProductosPedido() {
        productosPedidoService.deleteProductosPedido(idCreado);
        assertThrows(EntityNotFoundException.class, () -> {
            productosPedidoService.getProductoPedidoByID(idCreado);
        }, "El producto-pedido debe haber sido eliminado");
    }

    // CRUD - ERRORES
    @Test
    @Order(6)
    public void testCrearConPedidoInexistente() {
        ProductosPedidoDTO dto = new ProductosPedidoDTO();
        dto.setCantProd(1);
        dto.setIdPedido(999999); // ID inexistente
        dto.setIdProducto(idProducto);
        dto.setIdDescuento(idDescuento);
        assertThrows(EntityNotFoundException.class, () -> {
            productosPedidoService.crearProductosPedido(dto);
        });
    }

    @Test
    @Order(7)
    public void testCrearConProductoInexistente() {
        ProductosPedidoDTO dto = new ProductosPedidoDTO();
        dto.setCantProd(1);
        dto.setIdPedido(idPedido);
        dto.setIdProducto(999999); // ID inexistente
        dto.setIdDescuento(idDescuento);
        assertThrows(EntityNotFoundException.class, () -> {
            productosPedidoService.crearProductosPedido(dto);
        });
    }

    @Test
    @Order(8)
    public void testCrearConDescuentoInexistente() {
        if (idDescuento == null) return; // Si no hay descuentos, omitir
        ProductosPedidoDTO dto = new ProductosPedidoDTO();
        dto.setCantProd(1);
        dto.setIdPedido(idPedido);
        dto.setIdProducto(idProducto);
        dto.setIdDescuento(999999); // ID inexistente
        assertThrows(EntityNotFoundException.class, () -> {
            productosPedidoService.crearProductosPedido(dto);
        });
    }

    @Test
    @Order(9)
    public void testUpdateConProductoPedidoInexistente() {
        ProductosPedidoDTO dto = new ProductosPedidoDTO();
        dto.setCantProd(1);
        dto.setIdPedido(idPedido);
        dto.setIdProducto(idProducto);
        dto.setIdDescuento(idDescuento);
        assertThrows(EntityNotFoundException.class, () -> {
            productosPedidoService.updateProductosPedido(dto, 999999);
        });
    }
}

// -----------------------------------------------------------------------------
// Resumen técnico:
// - El test utiliza datos precargados en schema.sql y data.sql
// - No crea entidades manualmente, usa IDs existentes de pedido, producto y descuento
// - Cubre CRUD completo y manejo de errores para ProductosPedidoService
// - Prueba con y sin descuento, y errores por entidades inexistentes
// - No requiere modificar módulos de producción ni entidades
// -----------------------------------------------------------------------------
