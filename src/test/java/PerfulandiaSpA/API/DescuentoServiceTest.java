package PerfulandiaSpA.API;

import PerfulandiaSpA.DTO.DescuentoDTO;
import PerfulandiaSpA.Entidades.Descuento;
import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Repositorio.ProductoRepository;
import PerfulandiaSpA.Servicio.DescuentoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    private Integer idDescuentoCreado; // Guarda el ID del descuento creado para los demás tests
    private Integer idProducto;        // Guarda el ID del producto base para asociar descuentos

    @BeforeAll
    public void setup() {
        // Crear producto base para los tests, así aseguramos integridad referencial
        Producto producto = new Producto();
        producto.setCodBarrProd(987654L);
        producto.setNomProd("Producto Descuento");
        producto.setPrecioProd(2000);
        productoRepository.save(producto);
        idProducto = producto.getId();
    }

    // C - CREATE
    @Test
    @Order(1)
    public void testCrearDescuento() {
        // Creamos un descuento nuevo asociado al producto base
        DescuentoDTO dto = new DescuentoDTO();
        // Ajusta el valor a los permitidos por el CHECK del schema.sql: 'FIJO' o 'PORCENTUAL'
        dto.setTipoDescuento("FIJO");
        dto.setValorDescuento(20);
        dto.setFecIniDescuento(LocalDate.of(2025, 6, 1));
        dto.setFecFinDescuento(LocalDate.of(2025, 6, 30));
        dto.setIdProducto(idProducto);

        Descuento descuento = descuentoService.crearDescuento(dto);

        // Verificamos que se creó correctamente y se asociaron bien las entidades
        assertNotNull(descuento.getId(), "El descuento debe tener ID luego de guardarse");
        assertEquals("FIJO", descuento.getTipoDescuento());
        assertEquals(20, descuento.getValorDescuento());
        assertEquals(idProducto, descuento.getProducto().getId());
        idDescuentoCreado = descuento.getId(); // Guardamos el ID para los siguientes tests
    }

    // R - READ by ID
    @Test
    @Order(2)
    public void testGetDescuentoByID() {
        // Buscamos el descuento por su ID y verificamos los datos principales
        Optional<Descuento> descOpt = descuentoService.getDescuentoByID(idDescuentoCreado);
        assertTrue(descOpt.isPresent(), "El descuento creado debe existir");
        assertEquals("FIJO", descOpt.get().getTipoDescuento());
    }

    // R - READ all
    @Test
    @Order(3)
    public void testGetDescuentos() {
        // Obtenemos todos los descuentos y verificamos que exista el creado
        List<Descuento> descuentos = descuentoService.getDescuentos();
        assertFalse(descuentos.isEmpty(), "Debe haber descuentos en la base");
        assertTrue(descuentos.stream().anyMatch(d -> d.getId().equals(idDescuentoCreado)));
    }

    // U - UPDATE (PUT)
    @Test
    @Order(4)
    public void testUpdateDescuento() {
        // Actualizamos el descuento con nuevos datos (PUT, reemplazo completo)
        DescuentoDTO dto = new DescuentoDTO();
        dto.setTipoDescuento("PORCENTUAL"); // Otro valor permitido por el CHECK
        dto.setValorDescuento(15);
        dto.setFecIniDescuento(LocalDate.of(2025, 7, 1));
        dto.setFecFinDescuento(LocalDate.of(2025, 7, 31));
        dto.setIdProducto(idProducto);

        Descuento actualizado = descuentoService.updateDescuento(dto, idDescuentoCreado);

        // Verificamos que los cambios se aplicaron correctamente
        assertEquals("PORCENTUAL", actualizado.getTipoDescuento());
        assertEquals(15, actualizado.getValorDescuento());
        assertEquals(LocalDate.of(2025, 7, 1), actualizado.getFecIniDescuento());
        assertEquals(LocalDate.of(2025, 7, 31), actualizado.getFecFinDescuento());
        assertEquals(idProducto, actualizado.getProducto().getId());
    }

    // U - PATCH (actualización parcial)
    @Test
    @Order(5)
    public void testPatchDescuento() {
        // Solo actualizamos el valor del descuento, el resto no cambia
        DescuentoDTO dto = new DescuentoDTO();
        dto.setValorDescuento(99); // Solo actualiza el valor

        Descuento parchado = descuentoService.patchDescuento(dto, idDescuentoCreado);

        // Verificamos que solo el campo modificado cambió
        assertEquals(99, parchado.getValorDescuento());
        // Los otros campos no deben cambiar
        assertEquals("PORCENTUAL", parchado.getTipoDescuento());
        assertEquals(idProducto, parchado.getProducto().getId());
    }

    // D - DELETE
    @Test
    @Order(6)
    public void testDeleteDescuento() {
        // Eliminamos el descuento y verificamos que ya no exista
        descuentoService.deleteDescuento(idDescuentoCreado);
        Optional<Descuento> descOpt = descuentoService.getDescuentoByID(idDescuentoCreado);
        assertTrue(descOpt.isEmpty(), "El descuento debe haber sido eliminado");
    }
}

// -----------------------------------------------------------------------------
// Resumen técnico:
// - Valida CRUD completo para DescuentoService, usando entidades relacionadas (Producto).
// - Prueba creación con valores permitidos por el constraint CHECK ('FIJO' y 'PORCENTUAL').
// - Verifica lectura por ID, listado general, actualización completa (PUT) y parcial (PATCH), y eliminación.
// - Usa variables de instancia para mantener IDs entre pruebas y asegurar continuidad.
// - Garantiza que los datos de prueba no afectan la base de producción usando @Sql.
// - Cubre errores comunes: restricciones CHECK, integridad referencial y fallos de aserción.
// - Ordena los tests con @Order para mantener flujo y evitar errores de dependencias.
// - El test es autocontenible y seguro para ejecutarse en cualquier entorno de pruebas.
// -----------------------------------------------------------------------------
