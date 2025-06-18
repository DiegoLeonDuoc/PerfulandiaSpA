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

    private Integer idCreado;
    private Integer idProducto;
    private Integer idSucursal;

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
        StockDTO dto = new StockDTO();
        dto.setCantStock(50);
        dto.setIdProducto(idProducto);
        dto.setIdSucursal(idSucursal);

        Stock stock = stockService.saveStock(dto);
        assertNotNull(stock.getId(), "El stock debe tener ID luego de guardarse");
        assertEquals(50, stock.getCantStock());
        assertEquals(idProducto, stock.getProducto().getId());
        assertEquals(idSucursal, stock.getSucursal().getId());
        idCreado = stock.getId();
    }

    // R - READ by ID
    @Test
    @Order(2)
    public void testGetStockByID() {
        Optional<Stock> stockOpt = stockService.getStockByID(idCreado);
        assertTrue(stockOpt.isPresent(), "El stock creado debe existir");
        assertEquals(50, stockOpt.get().getCantStock());
    }

    // R - READ all
    @Test
    @Order(3)
    public void testGetStocks() {
        List<Stock> stocks = stockService.getStocks();
        assertFalse(stocks.isEmpty(), "Debe haber stocks en la base");
        assertTrue(stocks.stream().anyMatch(s -> s.getId().equals(idCreado)));
    }

    // U - UPDATE (PUT)
    @Test
    @Order(4)
    public void testUpdateStock() {
        StockDTO dto = new StockDTO();
        dto.setCantStock(99);
        dto.setIdProducto(idProducto);
        dto.setIdSucursal(idSucursal);

        Stock actualizado = stockService.updateStock(idCreado, dto);
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
        stockService.deleteStock(idCreado);
        Optional<Stock> stockOpt = stockService.getStockByID(idCreado);
        assertTrue(stockOpt.isEmpty(), "El stock debe haber sido eliminado");
    }
}
