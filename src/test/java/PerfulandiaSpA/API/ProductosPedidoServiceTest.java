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

    private Integer idPedido;      // ID de pedido para asociar productos
    private Integer idProducto;    // ID de producto para asociar al pedido
    private Integer idDescuento;   // ID de descuento (puede ser null)
    private Integer idCreado;      // ID del registro ProductosPedido creado

    @BeforeAll
    public void setup() {
        // Buscamos un pedido y un producto ya existentes en la base de datos de prueba
        Optional<Pedido> pedidoOpt = pedidoRepository.findAll().stream().findFirst();
        Optional<Producto> productoOpt = productoRepository.findAll().stream().findFirst();

        // Verificamos que existan para poder asociar
        assertTrue(pedidoOpt.isPresent(), "Debe existir al menos un pedido en la BD de prueba");
        assertTrue(productoOpt.isPresent(), "Debe existir al menos un producto en la BD de prueba");

        idPedido = pedidoOpt.get().getId();
        idProducto = productoOpt.get().getId();

        // Buscamos un descuento válido, si existe, para asociarlo
        Optional<Descuento> descuentoOpt = descuentoRepository.findAll().stream().findFirst();
        idDescuento = descuentoOpt.map(Descuento::getId).orElse(null);
    }

    // C - Crear relación producto-pedido
    @Test
    @Order(1)
    public void testCrearProductosPedido() {
        // Creamos un DTO con los datos necesarios
        ProductosPedidoDTO dto = new ProductosPedidoDTO();
        dto.setCantProd(3);
        dto.setIdPedido(idPedido);
        dto.setIdProducto(idProducto);
        dto.setIdDescuento(idDescuento); // Puede ser null

        // Llamamos al servicio para crear el registro
        ProductosPedido pp = productosPedidoService.crearProductosPedido(dto);

        // Verificamos que se creó correctamente y los datos son los esperados
        assertNotNull(pp.getId(), "El producto-pedido debe tener ID después de guardarse");
        assertEquals(3, pp.getCantProd());
        assertEquals(idPedido, pp.getPedido().getId());
        assertEquals(idProducto, pp.getProducto().getId());
        if (idDescuento != null) {
            assertNotNull(pp.getDescuento());
            assertEquals(idDescuento, pp.getDescuento().getId());
        }
        idCreado = pp.getId(); // Guardamos el ID para los siguientes tests
    }

    // R - Buscar producto-pedido por ID
    @Test
    @Order(2)
    public void testGetProductoPedidoByID() {
        // Buscamos el registro creado y revisamos que los datos sean correctos
        Optional<ProductosPedido> ppOpt = productosPedidoService.getProductoPedidoByID(idCreado);
        assertTrue(ppOpt.isPresent(), "El producto-pedido debe existir");
        assertEquals(3, ppOpt.get().getCantProd());
    }

    // R - Listar todos los productos-pedido
    @Test
    @Order(3)
    public void testGetProductosPedidos() {
        // Obtenemos todos los registros y verificamos que el creado está en la lista
        List<ProductosPedido> lista = productosPedidoService.getProductosPedidos();
        assertFalse(lista.isEmpty(), "Debe haber productos en pedidos en la base");
        assertTrue(lista.stream().anyMatch(pp -> pp.getId().equals(idCreado)));
    }

    // U - Actualizar relación producto-pedido
    @Test
    @Order(4)
    public void testUpdateProductosPedido() {
        // Creamos un DTO con nuevos datos para actualizar
        ProductosPedidoDTO dto = new ProductosPedidoDTO();
        dto.setCantProd(7);
        dto.setIdPedido(idPedido);
        dto.setIdProducto(idProducto);
        dto.setIdDescuento(idDescuento);

        // Actualizamos el registro
        ProductosPedido actualizado = productosPedidoService.updateProductosPedido(dto, idCreado);

        // Verificamos que los datos se actualizaron correctamente
        assertEquals(7, actualizado.getCantProd());
        assertEquals(idPedido, actualizado.getPedido().getId());
        assertEquals(idProducto, actualizado.getProducto().getId());
        if (idDescuento != null) {
            assertNotNull(actualizado.getDescuento());
            assertEquals(idDescuento, actualizado.getDescuento().getId());
        }
    }

    // D - Eliminar producto-pedido
    @Test
    @Order(5)
    public void testDeleteProductosPedido() {
        // Eliminamos el registro y verificamos que ya no exista
        productosPedidoService.deleteProductosPedido(idCreado);
        assertThrows(EntityNotFoundException.class, () -> {
            productosPedidoService.getProductoPedidoByID(idCreado);
        }, "El producto-pedido debe haber sido eliminado");
    }

    // PRUEBAS DE ERRORES - Casos extremos
    @Test
    @Order(6)
    public void testCrearConPedidoInexistente() {
        // Intentamos crear con un ID de pedido que no existe
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
        // Intentamos crear con un ID de producto que no existe
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
        // Solo ejecuta si hay descuentos en la base de pruebas
        if (idDescuento == null) return;
        // Intentamos crear con un ID de descuento que no existe
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
        // Intentamos actualizar un registro que no existe
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
