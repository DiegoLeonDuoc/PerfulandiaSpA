package PerfulandiaSpA.API;

import PerfulandiaSpA.DTO.DescuentoDTO;
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

    private Integer idCreado = null;
    private Integer idProductoUsado = null;

    // C - Crear un descuento nuevo
    @Test
    @Order(1)
    public void testCrearDescuento() {
        Producto producto = productoRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No hay productos en la base de datos"));

        idProductoUsado = producto.getId();

        DescuentoDTO dto = new DescuentoDTO();
        dto.setTipoDescuento("FIJO");
        dto.setValorDescuento(250);
        dto.setFecIniDescuento(LocalDate.of(2025, 6, 1));
        dto.setFecFinDescuento(LocalDate.of(2025, 7, 1));
        dto.setIdProducto(idProductoUsado);

        Descuento descuento = descuentoService.crearDescuento(dto);

        assertNotNull(descuento.getId());
        assertEquals("FIJO", descuento.getTipoDescuento());
        assertEquals(250, descuento.getValorDescuento());
        assertEquals(LocalDate.of(2025, 6, 1), descuento.getFecIniDescuento());
        assertEquals(LocalDate.of(2025, 7, 1), descuento.getFecFinDescuento());
        assertNotNull(descuento.getProducto());
        assertEquals(idProductoUsado, descuento.getProducto().getId());

        idCreado = descuento.getId();
    }

    // R - Buscar por ID (busca un descuento FIJO existente)
    @Test
    @Order(2)
    public void testGetDescuentoById_Fijo() {
        List<Descuento> descuentos = descuentoService.getDescuentos();
        Integer idFijo = null;
        for (Descuento d : descuentos) {
            if ("FIJO".equalsIgnoreCase(d.getTipoDescuento())) {
                idFijo = d.getId();
                break;
            }
        }
        assertNotNull(idFijo, "No se encontró el descuento FIJO en la base de datos de prueba.");

        Optional<Descuento> descuentoOpt = descuentoService.getDescuentoByID(idFijo);
        assertTrue(descuentoOpt.isPresent());
        Descuento descuento = descuentoOpt.get();
        assertEquals("FIJO", descuento.getTipoDescuento());
        assertTrue(descuento.getValorDescuento() == 100 || descuento.getValorDescuento() == 250);
        assertNotNull(descuento.getFecIniDescuento());
    }

    // R - Listar todos (precargados y el nuevo)
    @Test
    @Order(3)
    public void testGetDescuentos() {
        List<Descuento> descuentos = descuentoService.getDescuentos();
        assertFalse(descuentos.isEmpty());
        boolean hayFijo = descuentos.stream().anyMatch(d -> "FIJO".equalsIgnoreCase(d.getTipoDescuento()));
        boolean hayPorcentual = descuentos.stream().anyMatch(d -> "PORCENTUAL".equalsIgnoreCase(d.getTipoDescuento()));
        assertTrue(hayFijo);
        assertTrue(hayPorcentual);
        boolean hayValor100 = descuentos.stream().anyMatch(d -> d.getValorDescuento() == 100);
        boolean hayValor10 = descuentos.stream().anyMatch(d -> d.getValorDescuento() == 10);
        boolean hayValor250 = descuentos.stream().anyMatch(d -> d.getValorDescuento() == 250);
        assertTrue(hayValor100);
        assertTrue(hayValor10);
        assertTrue(hayValor250);
    }

    // U - Actualizar descuento creado
    @Test
    @Order(4)
    public void testUpdateDescuento() {
        DescuentoDTO dto = new DescuentoDTO();
        dto.setTipoDescuento("PORCENTUAL");
        dto.setValorDescuento(20);
        dto.setFecIniDescuento(LocalDate.of(2025, 6, 15));
        dto.setFecFinDescuento(LocalDate.of(2025, 7, 15));
        dto.setIdProducto(idProductoUsado);

        Descuento actualizado = descuentoService.updateDescuento(dto, idCreado);

        assertNotNull(actualizado);
        assertEquals("PORCENTUAL", actualizado.getTipoDescuento());
        assertEquals(20, actualizado.getValorDescuento());
        assertEquals(LocalDate.of(2025, 6, 15), actualizado.getFecIniDescuento());
        assertEquals(LocalDate.of(2025, 7, 15), actualizado.getFecFinDescuento());
        assertEquals(idProductoUsado, actualizado.getProducto().getId());
    }

    // D - Eliminar descuento creado
    @Test
    @Order(5)
    public void testDeleteDescuento() {
        descuentoService.deleteDescuento(idCreado);

        Optional<Descuento> descuentoEliminado = descuentoService.getDescuentoByID(idCreado);
        assertTrue(descuentoEliminado.isEmpty(), "El descuento debería haber sido eliminado.");
    }
}
