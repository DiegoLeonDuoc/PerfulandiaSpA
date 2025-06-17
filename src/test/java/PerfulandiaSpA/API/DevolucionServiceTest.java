package PerfulandiaSpA.API;

import PerfulandiaSpA.DTO.DevolucionDTO;
import PerfulandiaSpA.Entidades.Devolucion;
import PerfulandiaSpA.Servicio.DevolucionService;
import PerfulandiaSpA.Repositorio.ProductosPedidoRepository;
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

    private Integer idCreado = null;
    private final Integer ID_PRODUCTO_PEDIDO = 1; // Debe existir en data.sql

    // C - Crear devolución
    @Test
    @Order(1)
    public void testCrearDevolucion() {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setMotivoDevo("Producto defectuoso");
        dto.setEstadoDevo("SOLICITADA");
        dto.setRestock(1);
        dto.setIdProductoPedido(ID_PRODUCTO_PEDIDO);

        Devolucion devolucion = devolucionService.crearDevolucion(dto);

        assertNotNull(devolucion.getId());
        assertEquals("Producto defectuoso", devolucion.getMotivoDevo());
        assertEquals("SOLICITADA", devolucion.getEstadoDevo());
        assertEquals(1, devolucion.getRestock());
        assertEquals(ID_PRODUCTO_PEDIDO, devolucion.getProductoPedido().getId());
        idCreado = devolucion.getId();
    }

    // R - Obtener por ID
    @Test
    @Order(2)
    public void testGetDevolucionById() {
        Optional<Devolucion> devolucionOpt = devolucionService.getDevolucionByID(idCreado);
        assertTrue(devolucionOpt.isPresent());
        Devolucion devolucion = devolucionOpt.get();
        assertEquals("Producto defectuoso", devolucion.getMotivoDevo());
        assertEquals(ID_PRODUCTO_PEDIDO, devolucion.getProductoPedido().getId());
    }

    // R - Listar todas
    @Test
    @Order(3)
    public void testGetDevoluciones() {
        List<Devolucion> devoluciones = devolucionService.getDevoluciones();
        assertFalse(devoluciones.isEmpty());
        assertTrue(devoluciones.stream().anyMatch(d -> d.getId().equals(idCreado)));
    }

    // U - Actualizar devolución
    @Test
    @Order(4)
    public void testUpdateDevolucion() {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setMotivoDevo("Cambio de producto");
        dto.setEstadoDevo("APROBADA");
        dto.setRestock(0);
        dto.setIdProductoPedido(ID_PRODUCTO_PEDIDO);

        Devolucion actualizada = devolucionService.updateDevolucion(dto, idCreado);

        assertNotNull(actualizada);
        assertEquals("Cambio de producto", actualizada.getMotivoDevo());
        assertEquals("APROBADA", actualizada.getEstadoDevo());
        assertEquals(0, actualizada.getRestock());
    }

    // U - Patch devolución
    @Test
    @Order(5)
    public void testPatchDevolucion() {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setMotivoDevo("Solo motivo actualizado");

        Devolucion parcheada = devolucionService.patchDevolucion(dto, idCreado);

        assertNotNull(parcheada);
        assertEquals("Solo motivo actualizado", parcheada.getMotivoDevo());
        // Otros campos no deben haberse modificado
        assertEquals("APROBADA", parcheada.getEstadoDevo());
    }

    // D - Eliminar devolución
    @Test
    @Order(6)
    public void testDeleteDevolucion() {
        devolucionService.deleteDevolucion(idCreado);
        Optional<Devolucion> devolucionOpt = devolucionService.getDevolucionByID(idCreado);
        assertTrue(devolucionOpt.isEmpty());
    }
}
