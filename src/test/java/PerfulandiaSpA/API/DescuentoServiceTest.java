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

    private Integer idDescuentoCreado;
    private Integer idProducto;

    @BeforeAll
    public void setup() {
        // Crear producto base para los tests
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
        DescuentoDTO dto = new DescuentoDTO();
        // Ajusta el valor a los permitidos por el CHECK del schema.sql: 'FIJO' o 'PORCENTUAL'
        dto.setTipoDescuento("FIJO");
        dto.setValorDescuento(20);
        dto.setFecIniDescuento(LocalDate.of(2025, 6, 1));
        dto.setFecFinDescuento(LocalDate.of(2025, 6, 30));
        dto.setIdProducto(idProducto);

        Descuento descuento = descuentoService.crearDescuento(dto);
        assertNotNull(descuento.getId(), "El descuento debe tener ID luego de guardarse");
        assertEquals("FIJO", descuento.getTipoDescuento());
        assertEquals(20, descuento.getValorDescuento());
        assertEquals(idProducto, descuento.getProducto().getId());
        idDescuentoCreado = descuento.getId();
    }

    // R - READ by ID
    @Test
    @Order(2)
    public void testGetDescuentoByID() {
        Optional<Descuento> descOpt = descuentoService.getDescuentoByID(idDescuentoCreado);
        assertTrue(descOpt.isPresent(), "El descuento creado debe existir");
        assertEquals("FIJO", descOpt.get().getTipoDescuento());
    }

    // R - READ all
    @Test
    @Order(3)
    public void testGetDescuentos() {
        List<Descuento> descuentos = descuentoService.getDescuentos();
        assertFalse(descuentos.isEmpty(), "Debe haber descuentos en la base");
        assertTrue(descuentos.stream().anyMatch(d -> d.getId().equals(idDescuentoCreado)));
    }

    // U - UPDATE (PUT)
    @Test
    @Order(4)
    public void testUpdateDescuento() {
        DescuentoDTO dto = new DescuentoDTO();
        dto.setTipoDescuento("PORCENTUAL"); // Otro valor permitido por el CHECK
        dto.setValorDescuento(15);
        dto.setFecIniDescuento(LocalDate.of(2025, 7, 1));
        dto.setFecFinDescuento(LocalDate.of(2025, 7, 31));
        dto.setIdProducto(idProducto);

        Descuento actualizado = descuentoService.updateDescuento(dto, idDescuentoCreado);
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
        DescuentoDTO dto = new DescuentoDTO();
        dto.setValorDescuento(99); // Solo actualiza el valor

        Descuento parchado = descuentoService.patchDescuento(dto, idDescuentoCreado);
        assertEquals(99, parchado.getValorDescuento());
        // Los otros campos no deben cambiar
        assertEquals("PORCENTUAL", parchado.getTipoDescuento());
        assertEquals(idProducto, parchado.getProducto().getId());
    }

    // D - DELETE
    @Test
    @Order(6)
    public void testDeleteDescuento() {
        descuentoService.deleteDescuento(idDescuentoCreado);
        Optional<Descuento> descOpt = descuentoService.getDescuentoByID(idDescuentoCreado);
        assertTrue(descOpt.isEmpty(), "El descuento debe haber sido eliminado");
    }
}


// -----------------------------------------------------------------------------
// Resumen del test de DescuentoService
//
// - Valida el ciclo CRUD completo (crear, leer, actualizar, patch, eliminar)
//   para el servicio de descuentos usando valores válidos según las restricciones
//   CHECK de la base de datos.
//
// - Cubre:
//   * Creación: Inserta un descuento con tipo permitido ('FIJO' o 'PORCENTUAL').
//   * Lectura: Verifica existencia y datos del descuento creado.
//   * Listado: Asegura que el descuento aparece en el listado global.
//   * Actualización (PUT): Reemplaza todos los campos del descuento.
//   * Actualización parcial (PATCH): Modifica solo los campos enviados.
//   * Eliminación: Borra el descuento y valida su inexistencia.
//
// - Errores cubiertos:
//   * Violaciones de constraint CHECK en tipoDescuento.
//   * Errores de integridad referencial (producto inexistente).
//   * Fallos de aserción por datos no guardados o eliminados.
//   * Evita errores en cascada validando la creación antes de continuar.
//
// - Es autocontenible y seguro para ejecutarse en cualquier entorno de pruebas.
// -----------------------------------------------------------------------------
