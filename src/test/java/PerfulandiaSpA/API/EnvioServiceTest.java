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
        //crud: inicialización de datos base
        Optional<Pedido> pedidoOpt = pedidoRepository.findAll().stream().findFirst();
        assertTrue(pedidoOpt.isPresent(), "Debe existir al menos un pedido en la BD de prueba");
        idPedido = pedidoOpt.get().getId();
    }

    //c - CREATE
    @Test
    @Order(1)
    public void testCrearEnvio() {
        EnvioDTO dto = new EnvioDTO();
        dto.setCodigoEnvio(123456789L);
        dto.setFechaEnvio(LocalDate.now());
        dto.setFechaLlegadaEstim(LocalDate.now().plusDays(3));
        dto.setTransportista("Chilexpress");
        dto.setNumSeguimiento(987654321L);
        dto.setMetodoEnvio("EXPRESS");
        dto.setIdPedido(idPedido);

        Envio envio = envioService.crearEnvio(dto);
        assertNotNull(envio.getId(), "El envío debe tener ID después de guardarse");
        assertEquals("Chilexpress", envio.getTransportista());
        assertEquals(idPedido, envio.getPedidoAsociado().getId());
        idCreado = envio.getId();
    }

    //r - READ by ID
    @Test
    @Order(2)
    public void testGetEnvioByID() {
        Optional<Envio> envioOpt = envioService.getEnvioByID(idCreado);
        assertTrue(envioOpt.isPresent(), "El envío debe existir");
        assertEquals(123456789L, envioOpt.get().getCodigoEnvio());
    }

    //r - READ all
    @Test
    @Order(3)
    public void testGetEnvios() {
        List<Envio> lista = envioService.getEnvios();
        assertFalse(lista.isEmpty(), "Debe haber envíos en la base");
        assertTrue(lista.stream().anyMatch(e -> e.getId().equals(idCreado)));
    }

    //u - UPDATE (PUT)
    @Test
    @Order(4)
    public void testUpdateEnvio() {
        EnvioDTO dto = new EnvioDTO();
        dto.setCodigoEnvio(999999999L);
        dto.setFechaEnvio(LocalDate.now().plusDays(1));
        dto.setFechaLlegadaEstim(LocalDate.now().plusDays(5));
        dto.setTransportista("Starken");
        dto.setNumSeguimiento(111111111L);
        dto.setMetodoEnvio("STANDARD");
        dto.setIdPedido(idPedido);

        Envio actualizado = envioService.updateEnvio(idCreado, dto);
        assertEquals("Starken", actualizado.getTransportista());
        assertEquals(999999999L, actualizado.getCodigoEnvio());
    }

    //patch - PATCH (actualización parcial)
    @Test
    @Order(5)
    public void testPatchEnvio() {
        EnvioDTO dto = new EnvioDTO();
        dto.setFechaLlegadaReal(LocalDate.now().plusDays(2)); // Solo actualizar este campo
        dto.setMetodoEnvio("EXPRESS");

        Envio actualizado = envioService.patchEnvio(idCreado, dto);
        assertEquals(LocalDate.now().plusDays(2), actualizado.getFechaLlegadaReal());
        assertEquals("EXPRESS", actualizado.getMetodoEnvio());
        // Verificar que otros campos no cambian
        assertEquals("Starken", actualizado.getTransportista());
    }

    //d - DELETE
    @Test
    @Order(6)
    public void testDeleteEnvio() {
        envioService.deleteEnvio(idCreado);
        assertTrue(envioService.getEnvioByID(idCreado).isEmpty(), "El envío debe haber sido eliminado");
    }

    //crud - ERRORES
    @Test
    @Order(7)
    public void testCrearConPedidoInexistente() {
        EnvioDTO dto = new EnvioDTO();
        dto.setIdPedido(999999);
        assertThrows(EntityNotFoundException.class, () -> {
            envioService.crearEnvio(dto);
        });
    }

    @Test
    @Order(8)
    public void testUpdateConEnvioInexistente() {
        EnvioDTO dto = new EnvioDTO();
        assertThrows(EntityNotFoundException.class, () -> {
            envioService.updateEnvio(999999, dto);
        });
    }
}

// -----------------------------------------------------------------------------
// Resumen técnico:
// - Valida CRUD completo para EnvioService incluyendo PATCH parcial
// - Cubre: creación con pedido existente, actualización total/parcial, eliminación
// - Prueba errores: pedido inexistente, ID envío no encontrado
// - Verifica constraints: usa solo valores válidos para método_envio
// - Asegura que PATCH solo actualiza campos proporcionados y mantiene el resto
// - Usa datos reales de pedidos precargados, manteniendo integridad referencial
// -----------------------------------------------------------------------------
