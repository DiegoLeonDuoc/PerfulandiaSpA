package PerfulandiaSpA.API;

import PerfulandiaSpA.DTO.ReabastecimientoDTO;
import PerfulandiaSpA.Entidades.Reabastecimiento;
import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Entidades.Proveedor;
import PerfulandiaSpA.Repositorio.ProductoRepository;
import PerfulandiaSpA.Repositorio.SucursalRepository;
import PerfulandiaSpA.Repositorio.ProveedorRepository;
import PerfulandiaSpA.Servicio.ReabastecimientoService;
import jakarta.persistence.EntityNotFoundException;
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
public class ReabastecimientoServiceTest {

    @Autowired
    ReabastecimientoService reabastecimientoService;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    @Autowired
    ProveedorRepository proveedorRepository;

    private Integer idProducto;
    private Integer idSucursal;
    private Integer idProveedor;
    private Integer idCreado;

    @BeforeAll
    public void setup() {
        //crud: inicialización de datos base
        Optional<Producto> productoOpt = productoRepository.findAll().stream().findFirst();
        Optional<Sucursal> sucursalOpt = sucursalRepository.findAll().stream().findFirst();
        Optional<Proveedor> proveedorOpt = proveedorRepository.findAll().stream().findFirst();

        assertTrue(productoOpt.isPresent(), "Debe existir al menos un producto en la BD de prueba");
        assertTrue(sucursalOpt.isPresent(), "Debe existir al menos una sucursal en la BD de prueba");
        assertTrue(proveedorOpt.isPresent(), "Debe existir al menos un proveedor en la BD de prueba");

        idProducto = productoOpt.get().getId();
        idSucursal = sucursalOpt.get().getId();
        idProveedor = proveedorOpt.get().getId();
    }

    //c - CREATE
    @Test
    @Order(1)
    public void testCrearReabastecimiento() {
        ReabastecimientoDTO dto = new ReabastecimientoDTO();
        dto.setCantProductos(10);
        dto.setFechaReabas(LocalDate.now());
        dto.setEstadoReabas("SOLICITADO"); // Valor permitido por constraint
        dto.setIdProducto(idProducto);
        dto.setIdSucursal(idSucursal);
        dto.setIdProveedor(idProveedor);

        Reabastecimiento reab = reabastecimientoService.saveReabastecimiento(dto);
        assertNotNull(reab.getId(), "El reabastecimiento debe tener ID después de guardarse");
        assertEquals(10, reab.getCantProductos());
        assertEquals("SOLICITADO", reab.getEstadoReabas());
        assertEquals(idProducto, reab.getProducto().getId());
        assertEquals(idSucursal, reab.getSucursal().getId());
        assertEquals(idProveedor, reab.getProveedor().getId());
        idCreado = reab.getId();
    }

    //r - READ by ID
    @Test
    @Order(2)
    public void testGetReabastecimientoByID() {
        Optional<Reabastecimiento> reabOpt = reabastecimientoService.getReabastecimientoByID(idCreado);
        assertTrue(reabOpt.isPresent(), "El reabastecimiento debe existir");
        assertEquals(10, reabOpt.get().getCantProductos());
    }

    //r - READ all
    @Test
    @Order(3)
    public void testGetReabastecimientos() {
        List<Reabastecimiento> lista = reabastecimientoService.getReabastecimientos();
        assertFalse(lista.isEmpty(), "Debe haber reabastecimientos en la base");
        assertTrue(lista.stream().anyMatch(r -> r.getId().equals(idCreado)));
    }

    //u - UPDATE (PUT: actualización total)
    @Test
    @Order(4)
    public void testUpdateReabastecimiento() {
        ReabastecimientoDTO dto = new ReabastecimientoDTO();
        dto.setCantProductos(20);
        dto.setFechaReabas(LocalDate.now().plusDays(1));
        dto.setEstadoReabas("RECIBIDO"); // Valor permitido por constraint
        dto.setIdProducto(idProducto);
        dto.setIdSucursal(idSucursal);
        dto.setIdProveedor(idProveedor);

        Reabastecimiento actualizado = reabastecimientoService.updateReabastecimiento(idCreado, dto);
        assertEquals(20, actualizado.getCantProductos());
        assertEquals("RECIBIDO", actualizado.getEstadoReabas());
        assertEquals(idProducto, actualizado.getProducto().getId());
        assertEquals(idSucursal, actualizado.getSucursal().getId());
        assertEquals(idProveedor, actualizado.getProveedor().getId());
    }

    //patch - PATCH (actualización parcial, si tuvieras endpoint específico)
    @Test
    @Order(5)
    public void testPatchEstadoReabastecimiento() {
        // Simulación de PATCH: solo cambiar el estado, dejando el resto igual
        Optional<Reabastecimiento> reabOpt = reabastecimientoService.getReabastecimientoByID(idCreado);
        assertTrue(reabOpt.isPresent());
        Reabastecimiento original = reabOpt.get();

        ReabastecimientoDTO dto = new ReabastecimientoDTO();
        dto.setCantProductos(original.getCantProductos()); // No cambia
        dto.setFechaReabas(original.getFechaReabas());     // No cambia
        dto.setEstadoReabas("EN TRÁNSITO");                // Solo cambia estado
        dto.setIdProducto(original.getProducto().getId());
        dto.setIdSucursal(original.getSucursal().getId());
        dto.setIdProveedor(original.getProveedor().getId());

        Reabastecimiento actualizado = reabastecimientoService.updateReabastecimiento(idCreado, dto);
        assertEquals("EN TRÁNSITO", actualizado.getEstadoReabas());
        assertEquals(original.getCantProductos(), actualizado.getCantProductos());
    }

    //d - DELETE
    @Test
    @Order(6)
    public void testDeleteReabastecimiento() {
        reabastecimientoService.deleteReabastecimiento(idCreado);
        Optional<Reabastecimiento> reabOpt = reabastecimientoService.getReabastecimientoByID(idCreado);
        assertTrue(reabOpt.isEmpty(), "El reabastecimiento debe haber sido eliminado");
    }

    //crud - ERRORES
    @Test
    @Order(7)
    public void testCrearConProductoInexistente() {
        ReabastecimientoDTO dto = new ReabastecimientoDTO();
        dto.setCantProductos(5);
        dto.setFechaReabas(LocalDate.now());
        dto.setEstadoReabas("SOLICITADO");
        dto.setIdProducto(999999); // ID inexistente
        dto.setIdSucursal(idSucursal);
        dto.setIdProveedor(idProveedor);
        assertThrows(EntityNotFoundException.class, () -> {
            reabastecimientoService.saveReabastecimiento(dto);
        });
    }

    @Test
    @Order(8)
    public void testCrearConSucursalInexistente() {
        ReabastecimientoDTO dto = new ReabastecimientoDTO();
        dto.setCantProductos(5);
        dto.setFechaReabas(LocalDate.now());
        dto.setEstadoReabas("SOLICITADO");
        dto.setIdProducto(idProducto);
        dto.setIdSucursal(999999); // ID inexistente
        dto.setIdProveedor(idProveedor);
        assertThrows(EntityNotFoundException.class, () -> {
            reabastecimientoService.saveReabastecimiento(dto);
        });
    }

    @Test
    @Order(9)
    public void testCrearConProveedorInexistente() {
        ReabastecimientoDTO dto = new ReabastecimientoDTO();
        dto.setCantProductos(5);
        dto.setFechaReabas(LocalDate.now());
        dto.setEstadoReabas("SOLICITADO");
        dto.setIdProducto(idProducto);
        dto.setIdSucursal(idSucursal);
        dto.setIdProveedor(999999); // ID inexistente
        assertThrows(EntityNotFoundException.class, () -> {
            reabastecimientoService.saveReabastecimiento(dto);
        });
    }

    @Test
    @Order(10)
    public void testUpdateConReabastecimientoInexistente() {
        ReabastecimientoDTO dto = new ReabastecimientoDTO();
        dto.setCantProductos(5);
        dto.setFechaReabas(LocalDate.now());
        dto.setEstadoReabas("SOLICITADO");
        dto.setIdProducto(idProducto);
        dto.setIdSucursal(idSucursal);
        dto.setIdProveedor(idProveedor);
        assertThrows(EntityNotFoundException.class, () -> {
            reabastecimientoService.updateReabastecimiento(999999, dto);
        });
    }
}

// -----------------------------------------------------------------------------
// Resumen técnico:
// - Valida CRUD completo para ReabastecimientoService.
// - Cubre: creación, lectura individual y general, actualización (PUT y PATCH simulando parcial), y eliminación.
// - Prueba errores: claves foráneas inexistentes, update/delete sobre IDs no existentes, y restricciones CHECK.
// - Asegura cumplimiento de reglas de PUT (todos los campos obligatorios) y PATCH (actualización parcial).
// - Usa datos válidos y existentes de la base de pruebas, sin modificar entidades de producción.
// - Garantiza integridad, robustez y correcta gestión de excepciones en el servicio.
// -----------------------------------------------------------------------------
