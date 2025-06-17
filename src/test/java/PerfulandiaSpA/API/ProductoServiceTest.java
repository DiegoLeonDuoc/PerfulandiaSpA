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

    private Integer idCreado;

    // C - Crear producto
    @Test
    @Order(1)
    public void testSaveProducto() {
        Producto producto = new Producto();
        producto.setCodBarrProd(99999L);
        producto.setNomProd("Producto Test");
        producto.setDescripProd("Descripción de prueba");
        producto.setPrecioProd(1234);

        productoService.saveProducto(producto);

        assertNotNull(producto.getId(), "El producto debe tener ID luego de guardarse");
        idCreado = producto.getId();
    }

    // R - Obtener por ID (sin tocar relaciones)
    @Test
    @Order(2)
    public void testGetProductoById() {
        assertNotNull(idCreado, "ID de producto creado no debe ser null");

        Optional<Producto> productoOpt = productoService.getProductoByID(idCreado);
        assertTrue(productoOpt.isPresent(), "Producto creado debe existir");
        assertEquals("Producto Test", productoOpt.get().getNomProd());
        assertEquals(99999L, productoOpt.get().getCodBarrProd());
        assertEquals("Descripción de prueba", productoOpt.get().getDescripProd());
        assertEquals(1234, productoOpt.get().getPrecioProd());
    }

    // R - Listar todos (sin tocar relaciones)
    @Test
    @Order(3)
    public void testGetProductos() {
        List<Producto> productos = productoService.getProductos();
        assertFalse(productos.isEmpty(), "Debe haber productos en la base");
        assertTrue(productos.stream().anyMatch(p -> p.getId().equals(idCreado)), "La lista debe contener el producto creado");
    }

    // U - Actualizar producto
    @Test
    @Order(4)
    public void testUpdateProducto() {
        Producto actualizado = new Producto();
        actualizado.setCodBarrProd(88888L);
        actualizado.setNomProd("Producto Actualizado");
        actualizado.setDescripProd("Nueva descripción");
        actualizado.setPrecioProd(4321);

        productoService.updateProducto(actualizado, idCreado);

        Optional<Producto> productoOpt = productoService.getProductoByID(idCreado);
        assertTrue(productoOpt.isPresent(), "Producto actualizado debe existir");
        assertEquals("Producto Actualizado", productoOpt.get().getNomProd());
        assertEquals("Nueva descripción", productoOpt.get().getDescripProd());
        assertEquals(4321, productoOpt.get().getPrecioProd());
        assertEquals(88888L, productoOpt.get().getCodBarrProd());
    }

    // D - Eliminar producto
    @Test
    @Order(5)
    public void testDeleteProducto() {
        productoService.deleteProducto(idCreado);
        Optional<Producto> productoOpt = productoService.getProductoByID(idCreado);
        assertTrue(productoOpt.isEmpty(), "El producto debe haber sido eliminado");
    }
}
