package PerfulandiaSpA.API;

import PerfulandiaSpA.DTO.StockDTO;
import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Entidades.Stock;
import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Servicio.StockService;
import PerfulandiaSpA.Repositorio.ProductoRepository;
import PerfulandiaSpA.Repositorio.SucursalRepository;
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

    private Integer idCreado = null;
    private Integer idProducto = 1; // Según data.sql
    private Integer idSucursal = 1; // Según data.sql

    // C - Crear un stock nuevo
    @Test
    @Order(1)
    public void testSaveStock() {
        StockDTO dto = new StockDTO();
        dto.setCantStock(150);
        dto.setIdProducto(idProducto);
        dto.setIdSucursal(idSucursal);

        Stock stock = stockService.saveStock(dto);

        assertNotNull(stock.getId());
        assertEquals(150, stock.getCantStock());
        assertEquals(idProducto, stock.getProducto().getId());
        assertEquals(idSucursal, stock.getSucursal().getId());

        idCreado = stock.getId();
    }

    // R - Buscar por ID (stock precargado en data.sql)
    @Test
    @Order(2)
    public void testGetStockById() {
        // Busca el stock original de data.sql (Producto 1 en Sucursal 1)
        Optional<Stock> stockOpt = stockService.getStockByID(1);
        assertTrue(stockOpt.isPresent());

        Stock stock = stockOpt.get();
        assertEquals(100, stock.getCantStock());
        assertEquals(idProducto, stock.getProducto().getId());
        assertEquals(idSucursal, stock.getSucursal().getId());
    }

    // R - Listar todos (precargados y el nuevo)
    @Test
    @Order(3)
    public void testGetStocks() {
        List<Stock> stocks = stockService.getStocks();
        assertFalse(stocks.isEmpty());

        boolean hayOriginal = stocks.stream().anyMatch(s ->
                s.getCantStock() == 100 &&
                        s.getProducto().getId() == idProducto &&
                        s.getSucursal().getId() == idSucursal
        );

        boolean hayNuevo = stocks.stream().anyMatch(s ->
                s.getCantStock() == 150 &&
                        s.getId().equals(idCreado)
        );

        assertTrue(hayOriginal);
        assertTrue(hayNuevo);
    }

    // U - Actualizar stock creado
    @Test
    @Order(4)
    public void testUpdateStock() {
        StockDTO dto = new StockDTO();
        dto.setCantStock(200);
        dto.setIdProducto(idProducto);
        dto.setIdSucursal(idSucursal);

        Stock actualizado = stockService.updateStock(idCreado, dto);

        assertEquals(200, actualizado.getCantStock());
        assertEquals(idProducto, actualizado.getProducto().getId());
        assertEquals(idSucursal, actualizado.getSucursal().getId());
    }

    // D - Eliminar stock creado
    @Test
    @Order(5)
    public void testDeleteStock() {
        stockService.deleteStock(idCreado);

        Optional<Stock> stockOpt = stockService.getStockByID(idCreado);
        assertTrue(stockOpt.isEmpty(), "El stock debería haber sido eliminado");
    }
}
