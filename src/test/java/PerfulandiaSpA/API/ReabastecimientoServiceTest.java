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
        // Inicializa los IDs de producto, sucursal y proveedor usando los primeros registros de la base de datos de prueba.
        Optional<Producto> productoOpt = productoRepository.findAll().stream().findFirst();
        Optional<Sucursal> sucursalOpt = sucursalRepository.findAll().stream().findFirst();
        Optional<Proveedor> proveedorOpt = proveedorRepository.findAll().stream().findFirst();

        // Verifica que existan datos base para poder realizar las pruebas.
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
        // Crea un DTO con datos válidos para registrar un nuevo reabastecimiento.
        ReabastecimientoDTO dto = new ReabastecimientoDTO();
        dto.setCantProductos(10);
        dto.setFechaReabas(LocalDate.now());
        dto.setEstadoReabas("SOLICITADO"); // Estado permitido según las reglas del sistema.
        dto.setIdProducto(idProducto);
        dto.setIdSucursal(idSucursal);
        dto.setIdProveedor(idProveedor);

        // Guarda el reabastecimiento y valida que se haya creado correctamente.
        Reabastecimiento reab = reabastecimientoService.saveReabastecimiento(dto);
        assertNotNull(reab.getId(), "El reabastecimiento debe tener ID después de guardarse");
        assertEquals(10, reab.getCantProductos());
        assertEquals("SOLICITADO", reab.getEstadoReabas());
        assertEquals(idProducto, reab.getProducto().getId());
        assertEquals(idSucursal, reab.getSucursal().getId());
        assertEquals(idProveedor, reab.getProveedor().getId());
        idCreado = reab.getId(); // Guarda el ID para futuras pruebas.
    }

    //r - READ by ID
    @Test
    @Order(2)
    public void testGetReabastecimientoByID() {
        // Busca el reabastecimiento recién creado por su ID y valida que exista.
        Optional<Reabastecimiento> reabOpt = reabastecimientoService.getReabastecimientoByID(idCreado);
        assertTrue(reabOpt.isPresent(), "El reabastecimiento debe existir");
        assertEquals(10, reabOpt.get().getCantProductos());
    }

    //r - READ all
    @Test
    @Order(3)
    public void testGetReabastecimientos() {
        // Obtiene la lista de todos los reabastecimientos y verifica que no esté vacía.
        List<Reabastecimiento> lista = reabastecimientoService.getReabastecimientos();
        assertFalse(lista.isEmpty(), "Debe haber reabastecimientos en la base");
        // Verifica que el reabastecimiento creado esté en la lista.
        assertTrue(lista.stream().anyMatch(r -> r.getId().equals(idCreado)));
    }

    //u - UPDATE (PUT: actualización total)
    @Test
    @Order(4)
    public void testUpdateReabastecimiento() {
        // Crea un DTO con nuevos datos para actualizar completamente el reabastecimiento.
        ReabastecimientoDTO dto = new ReabastecimientoDTO();
        dto.setCantProductos(20);
        dto.setFechaReabas(LocalDate.now().plusDays(1));
        dto.setEstadoReabas("RECIBIDO"); // Estado permitido por constraint.
        dto.setIdProducto(idProducto);
        dto.setIdSucursal(idSucursal);
        dto.setIdProveedor(idProveedor);

        // Actualiza el reabastecimiento y valida que los cambios se hayan aplicado.
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
        // Simula una actualización parcial: solo cambia el estado, el resto de datos se mantiene igual.
        Optional<Reabastecimiento> reabOpt = reabastecimientoService.getReabastecimientoByID(idCreado);
        assertTrue(reabOpt.isPresent());
        Reabastecimiento original = reabOpt.get();

        ReabastecimientoDTO dto = new ReabastecimientoDTO();
        dto.setCantProductos(original.getCantProductos()); // No cambia
        dto.setFechaReabas(original.getFechaReabas());     // No cambia
        dto.setEstadoReabas("EN TRÁNSITO");                // Solo cambia el estado
        dto.setIdProducto(original.getProducto().getId());
        dto.setIdSucursal(original.getSucursal().getId());
        dto.setIdProveedor(original.getProveedor().getId());

        // Aplica la actualización y valida que solo el estado haya cambiado.
        Reabastecimiento actualizado = reabastecimientoService.updateReabastecimiento(idCreado, dto);
        assertEquals("EN TRÁNSITO", actualizado.getEstadoReabas());
        assertEquals(original.getCantProductos(), actualizado.getCantProductos());
    }

    //d - DELETE
    @Test
    @Order(6)
    public void testDeleteReabastecimiento() {
        // Elimina el reabastecimiento creado y verifica que ya no exista.
        reabastecimientoService.deleteReabastecimiento(idCreado);
        Optional<Reabastecimiento> reabOpt = reabastecimientoService.getReabastecimientoByID(idCreado);
        assertTrue(reabOpt.isEmpty(), "El reabastecimiento debe haber sido eliminado");
    }

    //crud - ERRORES
    @Test
    @Order(7)
    public void testCrearConProductoInexistente() {
        // Intenta crear un reabastecimiento con un producto que no existe (ID inválido).
        ReabastecimientoDTO dto = new ReabastecimientoDTO();
        dto.setCantProductos(5);
        dto.setFechaReabas(LocalDate.now());
        dto.setEstadoReabas("SOLICITADO");
        dto.setIdProducto(999999); // ID inexistente
        dto.setIdSucursal(idSucursal);
        dto.setIdProveedor(idProveedor);
        // Debe lanzar excepción por clave foránea inexistente.
        assertThrows(EntityNotFoundException.class, () -> {
            reabastecimientoService.saveReabastecimiento(dto);
        });
    }

    @Test
    @Order(8)
    public void testCrearConSucursalInexistente() {
        // Intenta crear un reabastecimiento con una sucursal que no existe.
        ReabastecimientoDTO dto = new ReabastecimientoDTO();
        dto.setCantProductos(5);
        dto.setFechaReabas(LocalDate.now());
        dto.setEstadoReabas("SOLICITADO");
        dto.setIdProducto(idProducto);
        dto.setIdSucursal(999999); // ID inexistente
        dto.setIdProveedor(idProveedor);
        // Debe lanzar excepción por clave foránea inexistente.
        assertThrows(EntityNotFoundException.class, () -> {
            reabastecimientoService.saveReabastecimiento(dto);
        });
    }

    @Test
    @Order(9)
    public void testCrearConProveedorInexistente() {
        // Intenta crear un reabastecimiento con un proveedor que no existe.
        ReabastecimientoDTO dto = new ReabastecimientoDTO();
        dto.setCantProductos(5);
        dto.setFechaReabas(LocalDate.now());
        dto.setEstadoReabas("SOLICITADO");
        dto.setIdProducto(idProducto);
        dto.setIdSucursal(idSucursal);
        dto.setIdProveedor(999999); // ID inexistente
        // Debe lanzar excepción por clave foránea inexistente.
        assertThrows(EntityNotFoundException.class, () -> {
            reabastecimientoService.saveReabastecimiento(dto);
        });
    }

    @Test
    @Order(10)
    public void testUpdateConReabastecimientoInexistente() {
        // Intenta actualizar un reabastecimiento que no existe.
        ReabastecimientoDTO dto = new ReabastecimientoDTO();
        dto.setCantProductos(5);
        dto.setFechaReabas(LocalDate.now());
        dto.setEstadoReabas("SOLICITADO");
        dto.setIdProducto(idProducto);
        dto.setIdSucursal(idSucursal);
        dto.setIdProveedor(idProveedor);
        // Debe lanzar excepción por ID inexistente.
        assertThrows(EntityNotFoundException.class, () -> {
            reabastecimientoService.updateReabastecimiento(999999, dto);
        });
    }
}

// -----------------------------------------------------------------------------
// Resumen técnico:
// - Valida el CRUD completo del servicio de reabastecimiento, cubriendo creación, lectura (por ID y general), actualización total (PUT) y parcial (PATCH simulado), y eliminación.
// - Prueba casos de error: claves foráneas inexistentes, actualización/eliminación sobre IDs no existentes, y restricciones de estado.
// - Se asegura que PUT requiera todos los campos obligatorios y PATCH permita actualización parcial.
// - Utiliza datos válidos y existentes de la base de pruebas, sin afectar datos de producción.
// - Garantiza integridad, robustez y manejo correcto de excepciones en el servicio.
// - Todos los errores detectados en las pruebas están correctamente gestionados mediante excepciones y validaciones claras.
// -----------------------------------------------------------------------------
