package PerfulandiaSpA.API;

import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Servicio.ProductoService;
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
public class ProductoServiceTest {

    @Autowired
    ProductoService productoService;

    private Integer idCreado = null;

    // C - Crear un producto nuevo
    @Test
    @Order(1)
    public void testSaveProducto() {
        Producto producto = new Producto();
        producto.setNomProd("Corrector");
        producto.setCodBarrProd(10004L);
        producto.setPrecioProd(900);
        producto.setDescripProd("Corrector líquido blanco");

        String resultado = productoService.saveProducto(producto);
        assertEquals("Producto agregado con éxito", resultado);

        assertNotNull(producto.getId());
        idCreado = producto.getId();
    }

    // R - Buscar por ID (busca el ID de "Lápiz" dinámicamente)
    @Test
    @Order(2)
    public void testGetProductoById_Lapiz() {
        List<Producto> productos = productoService.getProductosJSON();
        Integer idLapiz = null;
        for (Producto p : productos) {
            if ("Lápiz".equalsIgnoreCase(p.getNomProd())) {
                idLapiz = p.getId();
                break;
            }
        }
        assertNotNull(idLapiz, "No se encontró el producto Lápiz en la base de datos de prueba.");
        String resultado = productoService.getProductoById(idLapiz);
        assertTrue(resultado.contains("Lápiz"));
        assertTrue(resultado.contains("grafito"));
        assertTrue(resultado.contains("200"));
    }

    // R - Listar todos (precargados y el nuevo)
    @Test
    @Order(3)
    public void testGetProductos() {
        String resultado = productoService.getProductos();
        assertTrue(resultado.contains("Lápiz"));
        assertTrue(resultado.contains("Cuaderno"));
        assertTrue(resultado.contains("Regla"));
        assertTrue(resultado.contains("Corrector")); // El que creamos arriba
    }

    // U - Actualizar producto creado
    @Test
    @Order(4)
    public void testUpdateProducto() {
        Producto actualizado = new Producto();
        actualizado.setNomProd("Corrector Mejorado");
        actualizado.setCodBarrProd(10004L);
        actualizado.setPrecioProd(1200);
        actualizado.setDescripProd("Corrector líquido premium");

        String resultado = productoService.updateProducto(actualizado, idCreado);
        assertEquals("Producto actualizado con éxito", resultado);

        String productoActual = productoService.getProductoById(idCreado);
        assertTrue(productoActual.contains("Corrector Mejorado"));
        assertTrue(productoActual.contains("premium"));
        assertTrue(productoActual.contains("1200"));
    }

    // D - Eliminar producto creado
    @Test
    @Order(5)
    public void testDeleteProducto() {
        String resultado = productoService.deleteProducto(idCreado);
        assertEquals("Producto eliminado con éxito", resultado);

        String productoEliminado = productoService.getProductoById(idCreado);
        assertEquals("Producto no encontrado", productoEliminado);
    }
}
