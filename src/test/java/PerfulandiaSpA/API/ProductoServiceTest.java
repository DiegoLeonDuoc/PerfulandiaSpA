package PerfulandiaSpA.API;

import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Servicio.ProductoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // Hace que Spring levante la app con una base de datos de prueba (h2 tenemos puesta)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Usa la misma instancia de la clase para todos los tests
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // definir el orden de los tests = @Order
public class ProductoServiceTest {

    @Autowired
    ProductoService productoService;

    private Integer idCreado = null; // Para guardar el id del producto que creamos en el test

    // C - Crear un producto nuevo
    @Test
    @Order(1) //corre primero
    public void testSaveProducto() {
        Producto producto = new Producto();
        producto.setNomProd("Corrector");
        producto.setCodBarrProd(10004L);
        producto.setPrecioProd(900);
        producto.setDescripProd("Corrector líquido blanco");

        String resultado = productoService.saveProducto(producto);
        assertEquals("Producto agregado con éxito", resultado);

        assertNotNull(producto.getId()); // Verifica que se le asignó un id
        idCreado = producto.getId(); // Guarda el id para los siguientes tests
    }

    // R -
    @Test
    @Order(2)
    public void testGetProductoById_Lapiz() {
        String resultado = productoService.getProductoById(1);
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
        assertTrue(resultado.contains("Corrector")); // Elque creamos arriba
    }

    // U -
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

    // D -
    @Test
    @Order(5)
    public void testDeleteProducto() {
        String resultado = productoService.deleteProducto(idCreado);
        assertEquals("Producto eliminado con éxito", resultado);

        String productoEliminado = productoService.getProductoById(idCreado);
        assertEquals("Producto no encontrado", productoEliminado);
    }
}
