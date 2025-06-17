package PerfulandiaSpA.API;

import PerfulandiaSpA.Entidades.Descuento;
import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Servicio.DescuentoService;
import PerfulandiaSpA.Repositorio.ProductoRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class DescuentoServiceTest {

    @Autowired
    DescuentoService descuentoService;

    @Autowired
    ProductoRepository productoRepository;

    private Integer idCreado = null;

    // C - Crear un descuento nuevo
    @Test
    @Order(1)
    public void testSaveDescuento() {
        Descuento descuento = new Descuento();
        descuento.setTipoDescuento("FIJO");
        descuento.setValorDescuento(250);
        descuento.setFecIniDescuento(LocalDate.of(2025, 6, 1));
        descuento.setFecFinDescuento(LocalDate.of(2025, 7, 1));

        // Busca un producto existente en la base de datos (por ejemplo, el primero)
        Producto producto = productoRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No hay productos en la base de datos"));
        descuento.setProducto(producto);

        String resultado = descuentoService.saveDescuento(descuento);
        assertEquals("Descuento agregado con éxito", resultado);

        assertNotNull(descuento.getId());
        idCreado = descuento.getId();
    }

    // R - Buscar por ID (busca un descuento FIJO existente)
    @Test
    @Order(2)
    public void testGetDescuentoById_Fijo() {
        List<Descuento> descuentos = descuentoService.getDescuentosJSON();
        Integer idFijo = null;
        for (Descuento d : descuentos) {
            if ("FIJO".equalsIgnoreCase(d.getTipoDescuento())) {
                idFijo = d.getId();
                break;
            }
        }
        assertNotNull(idFijo, "No se encontró el descuento FIJO en la base de datos de prueba.");
        String resultado = descuentoService.getDescuentoById(idFijo);
        assertTrue(resultado.contains("FIJO"));
        assertTrue(resultado.contains("100") || resultado.contains("250")); // Puede ser el de data.sql o el creado en el test
        assertTrue(resultado.contains("01-05-2024") || resultado.contains("01-06-2025")); // Fecha del data.sql o del test
    }

    // R - Listar todos (precargados y el nuevo)
    @Test
    @Order(3)
    public void testGetDescuentos() {
        String resultado = descuentoService.getDescuentos();
        assertTrue(resultado.contains("FIJO"));
        assertTrue(resultado.contains("PORCENTUAL"));
        assertTrue(resultado.contains("100"));
        assertTrue(resultado.contains("10"));
        assertTrue(resultado.contains("01-05-2024"));
        assertTrue(resultado.contains("01-06-2025") || resultado.contains("250")); // El que creamos arriba
    }

    // U - Actualizar descuento creado
    @Test
    @Order(4)
    public void testUpdateDescuento() {
        Descuento actualizado = new Descuento();
        actualizado.setTipoDescuento("PORCENTUAL");
        actualizado.setValorDescuento(20);
        actualizado.setFecIniDescuento(LocalDate.of(2025, 6, 15));
        actualizado.setFecFinDescuento(LocalDate.of(2025, 7, 15));

        // Usa el mismo producto que antes
        Producto producto = productoRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No hay productos en la base de datos"));
        actualizado.setProducto(producto);

        String resultado = descuentoService.updateDescuento(actualizado, idCreado);
        assertEquals("Descuento actualizado con éxito", resultado);

        String descuentoActual = descuentoService.getDescuentoById(idCreado);
        assertTrue(descuentoActual.contains("PORCENTUAL"));
        assertTrue(descuentoActual.contains("20"));
        assertTrue(descuentoActual.contains("15-06-2025"));
    }

    // D - Eliminar descuento creado
    @Test
    @Order(5)
    public void testDeleteDescuento() {
        String resultado = descuentoService.deleteDescuento(idCreado);
        assertEquals("Descuento eliminado con éxito", resultado);

        String descuentoEliminado = descuentoService.getDescuentoById(idCreado);
        assertEquals("Descuento no encontrado", descuentoEliminado);
    }
}
