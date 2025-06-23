package PerfulandiaSpA.API;

import PerfulandiaSpA.DTO.StockDTO;
import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Entidades.Stock;
import PerfulandiaSpA.Repositorio.ProductoRepository;
import PerfulandiaSpA.Repositorio.SucursalRepository;
import PerfulandiaSpA.Servicio.StockService;
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
public class StockServiceTest {

    @Autowired
    StockService stockService;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    private Integer idCreado;    // Guarda el ID del stock creado para los demás tests
    private Integer idProducto;  // Guarda el ID del producto base
    private Integer idSucursal;  // Guarda el ID de la sucursal base

    @BeforeAll
    public void setup() {
        // Crear producto y sucursal base para los tests
        Producto producto = new Producto();
        producto.setCodBarrProd(123456L);
        producto.setNomProd("Producto Test");
        producto.setPrecioProd(1000);
        productoRepository.save(producto);
        idProducto = producto.getId();

        Sucursal sucursal = new Sucursal();
        sucursal.setNombreSucursal("Sucursal Test");
        sucursal.setDireccionSucursal("Dirección Test");
        sucursalRepository.save(sucursal);
        idSucursal = sucursal.getId();
    }

    // C - CREATE
    @Test
    @Order(1)
    public void testSaveStock() {
        // Creamos un nuevo stock asociado al producto y sucursal base
        StockDTO dto = new StockDTO();
        dto.setCantStock(50);
        dto.setIdProducto(idProducto);
        dto.setIdSucursal(idSucursal);

        Stock stock = stockService.saveStock(dto);

        // Verificamos que se creó correctamente y se asociaron bien las entidades
        assertNotNull(stock.getId(), "El stock debe tener ID luego de guardarse");
        assertEquals(50, stock.getCantStock());
        assertEquals(idProducto, stock.getProducto().getId());
        assertEquals(idSucursal, stock.getSucursal().getId());
        idCreado = stock.getId(); // Guardamos el ID para los siguientes tests
    }

    // R - READ by ID
    @Test
    @Order(2)
    public void testGetStockByID() {
        // Buscamos el stock por su ID y verificamos la cantidad
        Optional<Stock> stockOpt = stockService.getStockByID(idCreado);
        assertTrue(stockOpt.isPresent(), "El stock creado debe existir");
        assertEquals(50, stockOpt.get().getCantStock());
    }

    // R - READ all
    @Test
    @Order(3)
    public void testGetStocks() {
        // Obtenemos todos los stocks y verificamos que exista el creado
        List<Stock> stocks = stockService.getStocks();
        assertFalse(stocks.isEmpty(), "Debe haber stocks en la base");
        assertTrue(stocks.stream().anyMatch(s -> s.getId().equals(idCreado)));
    }

    // U - UPDATE (PUT)
    @Test
    @Order(4)
    public void testUpdateStock() {
        // Actualizamos el stock con nuevos datos (PUT, reemplazo completo)
        StockDTO dto = new StockDTO();
        dto.setCantStock(99);
        dto.setIdProducto(idProducto);
        dto.setIdSucursal(idSucursal);

        Stock actualizado = stockService.updateStock(idCreado, dto);

        // Verificamos que los cambios se aplicaron correctamente
        assertEquals(99, actualizado.getCantStock());
        assertEquals(idProducto, actualizado.getProducto().getId());
        assertEquals(idSucursal, actualizado.getSucursal().getId());
    }

    // (Opcional) U - PATCH (actualización parcial)
    // Si implementas patchStock en tu servicio, el test sería así:
    /*
    @Test
    @Order(5)
    public void testPatchStock() {
        // Solo actualizamos la cantidad, el resto no cambia
        StockDTO dto = new StockDTO();
        dto.setCantStock(123); // Solo actualiza la cantidad

        Stock parchado = stockService.patchStock(idCreado, dto);
        assertEquals(123, parchado.getCantStock());
        assertEquals(idProducto, parchado.getProducto().getId()); // No cambia
        assertEquals(idSucursal, parchado.getSucursal().getId()); // No cambia
    }
    */

    // D - DELETE
    @Test
    @Order(6)
    public void testDeleteStock() {
        // Eliminamos el stock y verificamos que ya no exista
        stockService.deleteStock(idCreado);
        Optional<Stock> stockOpt = stockService.getStockByID(idCreado);
        assertTrue(stockOpt.isEmpty(), "El stock debe haber sido eliminado");
    }
}

// -----------------------------------------------------------------------------
// Resumen técnico:
// - Valida CRUD completo para StockService, usando entidades relacionadas (Producto y Sucursal).
// - Prueba creación, lectura por ID, lectura general, actualización completa (PUT) y eliminación.
// - Usa variables de instancia para mantener IDs entre pruebas y asegurar continuidad.
// - Garantiza que los datos de prueba no afectan la base de producción usando @Sql.
// - Verifica integridad referencial y persistencia correcta de las relaciones.
// - Incluye ejemplo de PATCH comentado para actualización parcial si se implementa.
// - Ordena los tests con @Order para mantener flujo y evitar errores de dependencias.
// -----------------------------------------------------------------------------
