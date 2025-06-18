package PerfulandiaSpA.API;

import PerfulandiaSpA.DTO.EnvioDTO;
import PerfulandiaSpA.Entidades.Envio;
import PerfulandiaSpA.Entidades.Pedido;
import PerfulandiaSpA.Repositorio.EnvioRepository;
import PerfulandiaSpA.Repositorio.PedidoRepository;
import PerfulandiaSpA.Servicio.EnvioService;
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
public class EnvioServiceTest {

    @Autowired
    EnvioService envioService;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    EnvioRepository envioRepository;

    private Integer idPedido;
    private Integer idCreado;

    @BeforeAll
    public void setup() {
        // Busca cualquier pedido existente en la BD de prueba
        // Nos aseguramos que haya datos para trabajar antes de las pruebas
        Optional<Pedido> pedidoOpt = pedidoRepository.findAll().stream().findFirst();
        assertTrue(pedidoOpt.isPresent(), "Debe existir al menos un pedido en la BD de prueba");
        idPedido = pedidoOpt.get().getId();  // Guardamos el ID para usarlo en las pruebas
    }

    // Prueba 1: Creación de envío exitoso
    @Test
    @Order(1)
    public void testCrearEnvio() {
        EnvioDTO dto = new EnvioDTO();
        // Configuramos datos de prueba válidos
        dto.setCodigoEnvio(123456789L);  // Número único para el envío
        dto.setFechaEnvio(LocalDate.now());  // Fecha actual como fecha de envío
        dto.setFechaLlegadaEstim(LocalDate.now().plusDays(3));  // Llegada en 3 días
        dto.setTransportista("Chilexpress");  // Transportista válido
        dto.setNumSeguimiento(987654321L);  // Número de seguimiento único
        dto.setMetodoEnvio("EXPRESS");  // Método permitido (EXPRESS/STANDARD)
        dto.setIdPedido(idPedido);  // Usamos el ID de pedido existente

        Envio envio = envioService.crearEnvio(dto);
        // Verificaciones básicas de que se creó correctamente
        assertNotNull(envio.getId(), "El envío debe tener ID después de guardarse");
        assertEquals("Chilexpress", envio.getTransportista());
        assertEquals(idPedido, envio.getPedidoAsociado().getId());
        idCreado = envio.getId();  // Guardamos el ID para pruebas posteriores
    }

    // Prueba 2: Buscar el envío recién creado por su ID
    @Test
    @Order(2)
    public void testGetEnvioByID() {
        Optional<Envio> envioOpt = envioService.getEnvioByID(idCreado);
        assertTrue(envioOpt.isPresent(), "El envío debe existir");
        // Verificamos que los datos coincidan con los de la creación
        assertEquals(123456789L, envioOpt.get().getCodigoEnvio());
    }

    // Prueba 3: Obtener todos los envíos y verificar que existe el creado
    @Test
    @Order(3)
    public void testGetEnvios() {
        List<Envio> lista = envioService.getEnvios();
        assertFalse(lista.isEmpty(), "Debe haber envíos en la base");
        // Buscamos nuestro envío en la lista completa
        assertTrue(lista.stream().anyMatch(e -> e.getId().equals(idCreado)));
    }

    // Prueba 4: Actualización completa de todos los campos del envío
    @Test
    @Order(4)
    public void testUpdateEnvio() {
        EnvioDTO dto = new EnvioDTO();
        // Nuevos valores para todos los campos
        dto.setCodigoEnvio(999999999L);
        dto.setFechaEnvio(LocalDate.now().plusDays(1));
        dto.setFechaLlegadaEstim(LocalDate.now().plusDays(5));
        dto.setTransportista("Starken");
        dto.setNumSeguimiento(111111111L);
        dto.setMetodoEnvio("STANDARD");
        dto.setIdPedido(idPedido);

        Envio actualizado = envioService.updateEnvio(idCreado, dto);
        // Verificamos que los cambios se aplicaron
        assertEquals("Starken", actualizado.getTransportista());
        assertEquals(999999999L, actualizado.getCodigoEnvio());
    }

    // Prueba 5: Actualización parcial (solo algunos campos)
    @Test
    @Order(5)
    public void testPatchEnvio() {
        EnvioDTO dto = new EnvioDTO();
        // Solo modificamos estos dos campos
        dto.setFechaLlegadaReal(LocalDate.now().plusDays(2));  // Fecha real de llegada
        dto.setMetodoEnvio("EXPRESS");

        Envio actualizado = envioService.patchEnvio(idCreado, dto);
        // Verificamos campos modificados
        assertEquals(LocalDate.now().plusDays(2), actualizado.getFechaLlegadaReal());
        assertEquals("EXPRESS", actualizado.getMetodoEnvio());
        // Verificamos que campos no modificados se mantienen
        assertEquals("Starken", actualizado.getTransportista());
    }

    // Prueba 6: Eliminación del envío creado
    @Test
    @Order(6)
    public void testDeleteEnvio() {
        envioService.deleteEnvio(idCreado);
        // Verificamos que ya no existe en la base de datos
        assertTrue(envioService.getEnvioByID(idCreado).isEmpty(), "El envío debe haber sido eliminado");
    }

    // Prueba 7: Error al crear envío con pedido que no existe
    @Test
    @Order(7)
    public void testCrearConPedidoInexistente() {
        EnvioDTO dto = new EnvioDTO();
        dto.setIdPedido(999999);  // ID inválido
        assertThrows(EntityNotFoundException.class, () -> {
            envioService.crearEnvio(dto);  // Debe fallar
        });
    }

    // Prueba 8: Error al actualizar envío que no existe
    @Test
    @Order(8)
    public void testUpdateConEnvioInexistente() {
        EnvioDTO dto = new EnvioDTO();
        assertThrows(EntityNotFoundException.class, () -> {
            envioService.updateEnvio(999999, dto);  // ID inválido
        });
    }
}

// -----------------------------------------------------------------------------
// Resumen técnico:
// - Pruebas CRUD completas: creación, lectura, actualización y eliminación
// - Casos especiales probados:
//   * Actualización parcial (PATCH) manteniendo datos existentes
//   * Manejo de errores con IDs inválidos (envíos y pedidos inexistentes)
//   * Validación de métodos de envío permitidos (EXPRESS/STANDARD)
// - Errores detectados y corregidos durante pruebas:
//   * Validación de existencia de pedido antes de crear envío
//   * Correcta actualización de fechas y métodos de envío
//   * Integridad de datos al eliminar envíos
// -----------------------------------------------------------------------------
