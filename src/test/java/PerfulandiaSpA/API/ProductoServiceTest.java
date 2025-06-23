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

    private Integer idCreado; // Guardamos el ID del producto creado para usarlo en los siguientes tests

    // C - Crear producto
    @Test
    @Order(1)
    public void testSaveProducto() {
        // Creamos un producto nuevo y lo guardamos usando el servicio
        Producto producto = new Producto();
        producto.setCodBarrProd(99999L);
        producto.setNomProd("Producto Test");
        producto.setDescripProd("Descripción de prueba");
        producto.setPrecioProd(1234);

        productoService.saveProducto(producto);

        // Verificamos que el producto tenga un ID luego de ser guardado (esto indica que se guardó bien en la base)
        assertNotNull(producto.getId(), "El producto debe tener ID luego de guardarse");
        idCreado = producto.getId(); // Guardamos el ID para los siguientes tests
    }

    // R - Obtener por ID (sin tocar relaciones)
    @Test
    @Order(2)
    public void testGetProductoById() {
        // Comprobamos que el ID guardado no sea nulo (el producto se creó correctamente antes)
        assertNotNull(idCreado, "ID de producto creado no debe ser null");

        // Buscamos el producto por su ID usando el servicio
        Optional<Producto> productoOpt = productoService.getProductoByID(idCreado);

        // Verificamos que el producto existe y que los datos principales son correctos
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
        // Obtenemos la lista de todos los productos
        List<Producto> productos = productoService.getProductos();

        // Revisamos que la lista no esté vacía y que incluya el producto que creamos antes
        assertFalse(productos.isEmpty(), "Debe haber productos en la base");
        assertTrue(productos.stream().anyMatch(p -> p.getId().equals(idCreado)), "La lista debe contener el producto creado");
    }

    // U - Actualizar producto
    @Test
    @Order(4)
    public void testUpdateProducto() {
        // Creamos un objeto con los nuevos datos para el producto
        Producto actualizado = new Producto();
        actualizado.setCodBarrProd(88888L);
        actualizado.setNomProd("Producto Actualizado");
        actualizado.setDescripProd("Nueva descripción");
        actualizado.setPrecioProd(4321);

        // Actualizamos el producto usando el ID guardado
        productoService.updateProducto(actualizado, idCreado);

        // Buscamos el producto y verificamos que los datos se hayan actualizado correctamente
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
        // Eliminamos el producto usando el ID guardado
        productoService.deleteProducto(idCreado);

        // Buscamos el producto eliminado y verificamos que ya no exista
        Optional<Producto> productoOpt = productoService.getProductoByID(idCreado);
        assertTrue(productoOpt.isEmpty(), "El producto debe haber sido eliminado");
    }
}

// -----------------------------------------------------------------------------
// Resumen técnico:
// - Este archivo valida todas las operaciones CRUD para el servicio de productos.
// - Se prueba crear, buscar por ID, listar todos, actualizar y eliminar productos.
// - Cada test utiliza el ID generado en la creación para mantener la secuencia y coherencia.
// - Los comentarios explican claramente la lógica y el objetivo de cada prueba.
// - Se asegura que los datos se manipulan correctamente en la base de pruebas y que los cambios se reflejan como corresponde.
// - Se corrigió y aclaró el flujo de pruebas usando anotaciones de orden y verificaciones de existencia después de cada operación.
// - Así se garantiza que el servicio funciona correctamente y responde bien ante operaciones básicas y comunes.
// -----------------------------------------------------------------------------
