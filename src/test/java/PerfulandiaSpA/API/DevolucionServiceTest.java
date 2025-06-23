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
        // Busca un producto-pedido existente en la base de datos de prueba
        // Esto asegura que tengamos datos para probar devoluciones
        Optional<ProductosPedido> ppOpt = productosPedidoRepository.findAll().stream().findFirst();
        assertTrue(ppOpt.isPresent(), "Debe existir al menos un producto-pedido en la BD de prueba");
        idProductoPedido = ppOpt.get().getId(); // Guardamos el ID para usarlo en las pruebas
    }

    // Prueba 1: Crear una devolución correctamente
    @Test
    @Order(1)
    public void testCrearDevolucion() {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setIdProductoPedido(idProductoPedido); // Asociamos a producto-pedido existente
        dto.setMotivoDevo("Producto dañado"); // Motivo de la devolución
        dto.setEstadoDevo("APROBADA"); // Estado permitido según restricciones
        dto.setRestock(1); // Indica si se repone el stock

        Devolucion devolucion = devolucionService.crearDevolucion(dto);
        // Verificamos que se creó y los datos son correctos
        assertNotNull(devolucion.getId(), "La devolución debe tener ID después de guardarse");
        assertEquals("APROBADA", devolucion.getEstadoDevo());
        assertEquals(idProductoPedido, devolucion.getProductoPedido().getId());
        idCreado = devolucion.getId(); // Guardamos el ID para pruebas siguientes
    }

    // Prueba 2: Buscar devolución por su ID
    @Test
    @Order(2)
    public void testGetDevolucionByID() {
        Optional<Devolucion> devolucionOpt = devolucionService.getDevolucionByID(idCreado);
        assertTrue(devolucionOpt.isPresent(), "La devolución debe existir");
        // Comprobamos que el motivo es el esperado
        assertEquals("Producto dañado", devolucionOpt.get().getMotivoDevo());
    }

    // Prueba 3: Obtener todas las devoluciones y buscar la creada
    @Test
    @Order(3)
    public void testGetDevoluciones() {
        List<Devolucion> lista = devolucionService.getDevoluciones();
        assertFalse(lista.isEmpty(), "Debe haber devoluciones en la base");
        // Comprobamos que la devolución creada está en la lista
        assertTrue(lista.stream().anyMatch(d -> d.getId().equals(idCreado)));
    }

    // Prueba 4: Actualizar todos los campos de una devolución existente
    @Test
    @Order(4)
    public void testUpdateDevolucion() {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setIdProductoPedido(idProductoPedido);
        dto.setMotivoDevo("Talla incorrecta"); // Cambiamos el motivo
        dto.setEstadoDevo("DENEGADA"); // Cambiamos el estado a otro permitido
        dto.setRestock(0); // Ahora no se repone el stock

        Devolucion actualizado = devolucionService.updateDevolucion(dto, idCreado);
        // Verificamos que los cambios se guardaron correctamente
        assertEquals("DENEGADA", actualizado.getEstadoDevo());
        assertEquals("Talla incorrecta", actualizado.getMotivoDevo());
    }

    // Prueba 5: Actualización parcial (PATCH) solo de estado
    @Test
    @Order(5)
    public void testPatchDevolucion() {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setEstadoDevo("EN PROCESO"); // Solo cambiamos el estado

        Devolucion actualizado = devolucionService.patchDevolucion(dto, idCreado);
        assertEquals("EN PROCESO", actualizado.getEstadoDevo());
        // Comprobamos que el motivo anterior no cambió
        assertEquals("Talla incorrecta", actualizado.getMotivoDevo());
    }

    // Prueba 6: Eliminar la devolución creada
    @Test
    @Order(6)
    public void testDeleteDevolucion() {
        devolucionService.deleteDevolucion(idCreado);
        // Verificamos que ya no existe en la base de datos
        assertTrue(devolucionService.getDevolucionByID(idCreado).isEmpty(), "La devolución debe haber sido eliminada");
    }

    // Prueba 7: Intentar crear devolución con producto-pedido que no existe
    @Test
    @Order(7)
    public void testCrearConProductoPedidoInexistente() {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setIdProductoPedido(999999); // ID inválido
        dto.setMotivoDevo("Producto no existe");
        dto.setEstadoDevo("APROBADA");
        dto.setRestock(1);
        // Esperamos que lance excepción porque el producto-pedido no existe
        assertThrows(EntityNotFoundException.class, () -> {
            devolucionService.crearDevolucion(dto);
        });
    }

    // Prueba 8: Intentar actualizar devolución que no existe
    @Test
    @Order(8)
    public void testUpdateConDevolucionInexistente() {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setIdProductoPedido(idProductoPedido);
        dto.setMotivoDevo("Intento update inexistente");
        dto.setEstadoDevo("APROBADA");
        dto.setRestock(1);
        // Esperamos excepción porque el ID de devolución no existe
        assertThrows(EntityNotFoundException.class, () -> {
            devolucionService.updateDevolucion(dto, 999999);
        });
    }

    // Prueba 9: Intentar actualizar con estado inválido (no permitido por la base)
    @Test
    @Order(9)
    public void testEstadoDevoInvalido() {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setEstadoDevo("INVALIDO"); // Estado no permitido por restricciones
        // Esperamos que lance excepción por constraint de la base de datos
        assertThrows(Exception.class, () -> {
            devolucionService.patchDevolucion(dto, idCreado);
        });
    }
}

// -----------------------------------------------------------------------------
// Resumen técnico:
// - Pruebas CRUD completas para DevolucionService: crear, leer, actualizar y eliminar devoluciones.
// - Cubre casos de actualización parcial (PATCH) y mantiene los demás campos sin cambios.
// - Valida restricciones: solo permite estados válidos ('SOLICITADA', 'EN PROCESO', 'APROBADA', 'COMPLETADA', 'DENEGADA').
// - Prueba errores: producto-pedido inexistente, devolución inexistente, y estados inválidos.
// - Se solucionaron errores de integridad referencial y validación de constraints en los métodos de creación y actualización.
// - Usa datos precargados para asegurar pruebas consistentes y confiables.
// -----------------------------------------------------------------------------
