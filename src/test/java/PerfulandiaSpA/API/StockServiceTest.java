package PerfulandiaSpA.API;

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

    // C - Crear un stock nuevo
    @Test
    @Order(1)
    public void testSaveStock() {
        Stock stock = new Stock();
        stock.setCantStock(150);

        // Buscar producto y sucursal existentes (según data.sql)
        Producto producto = productoRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Sucursal sucursal = sucursalRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        stock.setProducto(producto);
        stock.setSucursal(sucursal);

        String resultado = stockService.saveStock(stock);
        assertEquals("Stock agregado con éxito", resultado);

        assertNotNull(stock.getId());
        idCreado = stock.getId();
    }

    // R - Buscar por ID (stock precargado en data.sql)
    @Test
    @Order(2)
    public void testGetStockById() {
        // Buscar el stock del producto 1 en sucursal 1 (de data.sql)
        List<Stock> stocks = stockService.getStocksJSON();
        Integer idStock = null;
        for (Stock s : stocks) {
            if (s.getProducto().getId() == 1 && s.getSucursal().getId() == 1) {
                idStock = s.getId();
                break;
            }
        }
        assertNotNull(idStock, "No se encontró el stock en la base de datos de prueba.");

        String resultado = stockService.getStockById(idStock);
        assertTrue(resultado.contains("Cantidad en Stock: 100"));
        assertTrue(resultado.contains("ID Producto: 1"));
        assertTrue(resultado.contains("ID Sucursal: 1"));
    }

    // R - Listar todos (precargados y el nuevo)
    @Test
    @Order(3)
    public void testGetStocks() {
        String resultado = stockService.getStocks();
        assertTrue(resultado.contains("Cantidad en Stock: 100")); // De data.sql
        assertTrue(resultado.contains("Cantidad en Stock: 150")); // El creado en el test
    }

    // U - Actualizar stock creado
    @Test
    @Order(4)
    public void testUpdateStock() {
        Stock actualizado = new Stock();
        actualizado.setCantStock(200);

        // Mantener las mismas relaciones
        Producto producto = productoRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Sucursal sucursal = sucursalRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        actualizado.setProducto(producto);
        actualizado.setSucursal(sucursal);

        String resultado = stockService.updateStock(actualizado, idCreado);
        assertEquals("Stock actualizado con éxito", resultado);

        String stockActual = stockService.getStockById(idCreado);
        assertTrue(stockActual.contains("Cantidad en Stock: 200"));
    }

    // D - Eliminar stock creado
    @Test
    @Order(5)
    public void testDeleteStock() {
        String resultado = stockService.deleteStock(idCreado);
        assertEquals("Stock eliminado con éxito", resultado);

        String stockEliminado = stockService.getStockById(idCreado);
        assertEquals("Stock no encontrado", stockEliminado);
    }
}
