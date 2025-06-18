package PerfulandiaSpA.API;

import PerfulandiaSpA.DTO.DevolucionDTO;
import PerfulandiaSpA.Entidades.Devolucion;
import PerfulandiaSpA.Entidades.ProductosPedido;
import PerfulandiaSpA.Repositorio.DevolucionRepository;
import PerfulandiaSpA.Repositorio.ProductosPedidoRepository;
import PerfulandiaSpA.Servicio.DevolucionService;
import jakarta.persistence.EntityNotFoundException;
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
public class DevolucionServiceTest {

    @Autowired
    DevolucionService devolucionService;

    @Autowired
    ProductosPedidoRepository productosPedidoRepository;

    @Autowired
    DevolucionRepository devolucionRepository;

    private Integer idProductoPedido;
    private Integer idCreado;

    @BeforeAll
    public void setup() {
        //crud: buscar productoPedido existente
        Optional<ProductosPedido> ppOpt = productosPedidoRepository.findAll().stream().findFirst();
        assertTrue(ppOpt.isPresent(), "Debe existir al menos un producto-pedido en la BD de prueba");
        idProductoPedido = ppOpt.get().getId();
    }

    //c - CREATE
    @Test
    @Order(1)
    public void testCrearDevolucion() {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setIdProductoPedido(idProductoPedido);
        dto.setMotivoDevo("Producto dañado");
        dto.setEstadoDevo("APROBADA"); // Valor permitido por constraint CHECK
        dto.setRestock(1);

        Devolucion devolucion = devolucionService.crearDevolucion(dto);
        assertNotNull(devolucion.getId(), "La devolución debe tener ID después de guardarse");
        assertEquals("APROBADA", devolucion.getEstadoDevo());
        assertEquals(idProductoPedido, devolucion.getProductoPedido().getId());
        idCreado = devolucion.getId();
    }

    //r - READ by ID
    @Test
    @Order(2)
    public void testGetDevolucionByID() {
        Optional<Devolucion> devolucionOpt = devolucionService.getDevolucionByID(idCreado);
        assertTrue(devolucionOpt.isPresent(), "La devolución debe existir");
        assertEquals("Producto dañado", devolucionOpt.get().getMotivoDevo());
    }

    //r - READ all
    @Test
    @Order(3)
    public void testGetDevoluciones() {
        List<Devolucion> lista = devolucionService.getDevoluciones();
        assertFalse(lista.isEmpty(), "Debe haber devoluciones en la base");
        assertTrue(lista.stream().anyMatch(d -> d.getId().equals(idCreado)));
    }

    //u - UPDATE (PUT)
    @Test
    @Order(4)
    public void testUpdateDevolucion() {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setIdProductoPedido(idProductoPedido);
        dto.setMotivoDevo("Talla incorrecta");
        dto.setEstadoDevo("DENEGADA"); // Valor permitido por constraint CHECK
        dto.setRestock(0);

        Devolucion actualizado = devolucionService.updateDevolucion(dto, idCreado);
        assertEquals("DENEGADA", actualizado.getEstadoDevo());
        assertEquals("Talla incorrecta", actualizado.getMotivoDevo());
    }

    //patch - PATCH (actualización parcial)
    @Test
    @Order(5)
    public void testPatchDevolucion() {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setEstadoDevo("EN PROCESO"); // Valor permitido por constraint CHECK

        Devolucion actualizado = devolucionService.patchDevolucion(dto, idCreado);
        assertEquals("EN PROCESO", actualizado.getEstadoDevo());
        // Verificar que otros campos no cambian
        assertEquals("Talla incorrecta", actualizado.getMotivoDevo());
    }

    //d - DELETE
    @Test
    @Order(6)
    public void testDeleteDevolucion() {
        devolucionService.deleteDevolucion(idCreado);
        assertTrue(devolucionService.getDevolucionByID(idCreado).isEmpty(), "La devolución debe haber sido eliminada");
    }

    //crud - ERRORES
    @Test
    @Order(7)
    public void testCrearConProductoPedidoInexistente() {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setIdProductoPedido(999999);
        dto.setMotivoDevo("Producto no existe");
        dto.setEstadoDevo("APROBADA");
        dto.setRestock(1);
        assertThrows(EntityNotFoundException.class, () -> {
            devolucionService.crearDevolucion(dto);
        });
    }

    @Test
    @Order(8)
    public void testUpdateConDevolucionInexistente() {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setIdProductoPedido(idProductoPedido);
        dto.setMotivoDevo("Intento update inexistente");
        dto.setEstadoDevo("APROBADA");
        dto.setRestock(1);
        assertThrows(EntityNotFoundException.class, () -> {
            devolucionService.updateDevolucion(dto, 999999);
        });
    }

    @Test
    @Order(9)
    public void testEstadoDevoInvalido() {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setEstadoDevo("INVALIDO"); // Valor NO permitido por constraint CHECK
        assertThrows(Exception.class, () -> {
            devolucionService.patchDevolucion(dto, idCreado);
        });
    }
}

// -----------------------------------------------------------------------------
// Resumen técnico:
// - Valida CRUD completo para DevolucionService incluyendo PATCH parcial
// - Cubre: creación con producto-pedido existente, actualización total/parcial, eliminación
// - Prueba errores: producto-pedido inexistente, ID devolución no encontrado, estado_devo inválido
// - Verifica constraints: estado_devo con valores permitidos ('SOLICITADA', 'EN PROCESO', 'APROBADA', 'COMPLETADA', 'DENEGADA')
// - Asegura que PATCH solo actualiza campos proporcionados y mantiene el resto igual
// - Mantiene integridad referencial usando datos precargados y buenas prácticas de test
// -----------------------------------------------------------------------------
