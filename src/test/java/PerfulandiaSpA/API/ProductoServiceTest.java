package PerfulandiaSpA.API;

import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Servicio.ProductoService;
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

        productoService.saveProducto(producto);

        assertNotNull(producto.getId());
        idCreado = producto.getId();

        // Verifica que el producto se guardó correctamente
        Optional<Producto> prodOpt = productoService.getProductoByID(idCreado);
        assertTrue(prodOpt.isPresent());
        assertEquals("Corrector", prodOpt.get().getNomProd());
    }

    // R - Buscar por ID (busca el ID de "Lápiz" dinámicamente)
    @Test
    @Order(2)
    public void testGetProductoById_Lapiz() {
        List<Producto> productos = productoService.getProductos();
        Integer idLapiz = null;
        for (Producto p : productos) {
            if ("Lápiz".equalsIgnoreCase(p.getNomProd())) {
                idLapiz = p.getId();
                break;
            }
        }
        assertNotNull(idLapiz, "No se encontró el producto Lápiz en la base de datos de prueba.");

        Optional<Producto> prodOpt = productoService.getProductoByID(idLapiz);
        assertTrue(prodOpt.isPresent());
        Producto producto = prodOpt.get();
        assertEquals("Lápiz", producto.getNomProd());
        assertTrue(producto.getDescripProd().toLowerCase().contains("grafito"));
        assertEquals(200, producto.getPrecioProd());
    }

    // R - Listar todos (precargados y el nuevo)
    @Test
    @Order(3)
    public void testGetProductos() {
        List<Producto> productos = productoService.getProductos();
        assertFalse(productos.isEmpty());

        boolean hayLapiz = productos.stream().anyMatch(p -> "Lápiz".equalsIgnoreCase(p.getNomProd()));
        boolean hayCuaderno = productos.stream().anyMatch(p -> "Cuaderno".equalsIgnoreCase(p.getNomProd()));
        boolean hayRegla = productos.stream().anyMatch(p -> "Regla".equalsIgnoreCase(p.getNomProd()));
        boolean hayCorrector = productos.stream().anyMatch(p -> "Corrector".equalsIgnoreCase(p.getNomProd()));

        assertTrue(hayLapiz);
        assertTrue(hayCuaderno);
        assertTrue(hayRegla);
        assertTrue(hayCorrector);
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

        productoService.updateProducto(actualizado, idCreado);

        Optional<Producto> prodOpt = productoService.getProductoByID(idCreado);
        assertTrue(prodOpt.isPresent());
        Producto producto = prodOpt.get();
        assertEquals("Corrector Mejorado", producto.getNomProd());
        assertTrue(producto.getDescripProd().toLowerCase().contains("premium"));
        assertEquals(1200, producto.getPrecioProd());
    }

    // D - Eliminar producto creado
    @Test
    @Order(5)
    public void testDeleteProducto() {
        productoService.deleteProducto(idCreado);

        Optional<Producto> prodOpt = productoService.getProductoByID(idCreado);
        assertTrue(prodOpt.isEmpty(), "El producto debería haber sido eliminado.");
    }
}
