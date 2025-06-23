package PerfulandiaSpA.API;

import PerfulandiaSpA.DTO.PedidoDTO;
import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Entidades.Pedido;
import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Repositorio.ClienteRepository;
import PerfulandiaSpA.Repositorio.SucursalRepository;
import PerfulandiaSpA.Servicio.PedidoService;
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
public class PedidoServiceTest {

    @Autowired
    PedidoService pedidoService;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    private Integer idPedidoCreado;
    private Integer idCliente;
    private Integer idSucursal;

    @BeforeAll
    public void setup() {
        // Creamos un cliente con todos los datos obligatorios para que los tests funcionen bien.
        Cliente cliente = new Cliente();
        cliente.setRutUsuario(12345678);
        cliente.setDvUsuario('5');
        cliente.setNomUsuario("Cliente Test");
        cliente.setApellidoPaterno("Apellido");
        cliente.setApellidoMaterno("Materno");
        cliente.setSexoUsuario('M');
        cliente.setDirUsuario("Calle Test 123");
        cliente.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        cliente.setTelefonoUsuario("912345678");
        cliente.setEmailUsuario("cliente@test.com");
        cliente.setPassUsuario("password123");
        cliente.setEstadoCuenta("ACTIVO");
        clienteRepository.save(cliente);
        idCliente = cliente.getRutUsuario();

        // Creamos una sucursal de prueba para asociar al pedido.
        Sucursal sucursal = new Sucursal();
        sucursal.setNombreSucursal("Sucursal Test");
        sucursal.setDireccionSucursal("Calle Test 123");
        sucursal = sucursalRepository.save(sucursal);
        idSucursal = sucursal.getId();
    }

    // C - CREATE
    @Test
    @Order(1)
    public void testCrearPedido() {
        // Preparamos los datos del pedido usando valores válidos.
        PedidoDTO dto = new PedidoDTO();
        dto.setMetodoPago("EFECTIVO"); // Debe ser uno de los valores permitidos por el sistema.
        dto.setDirEnvio("Dirección Envío 123");
        dto.setDirFacturacion("Dirección Facturación 456");
        dto.setCostoEnvio(5000);
        dto.setPrecioPedido(100000);
        dto.setAnotaciones("Sin cebollas");
        dto.setRutCliente(idCliente);
        dto.setIdSucursal(idSucursal);

        // Creamos el pedido y comprobamos que se haya guardado correctamente.
        Pedido pedido = pedidoService.crearPedido(dto);
        assertNotNull(pedido.getId(), "El pedido debe tener ID después de guardarse");
        assertEquals("EFECTIVO", pedido.getMetodoPago());
        assertEquals(idCliente, pedido.getCliente().getRutUsuario());
        idPedidoCreado = pedido.getId();
    }

    // R - READ by ID
    @Test
    @Order(2)
    public void testGetPedidoByID() {
        // Buscamos el pedido recién creado por su ID y verificamos que exista y tenga el precio correcto.
        Optional<Pedido> pedidoOpt = pedidoService.getPedidoByID(idPedidoCreado);
        assertTrue(pedidoOpt.isPresent(), "El pedido creado debe existir");
        assertEquals(100000, pedidoOpt.get().getPrecioPedido());
    }

    // R - READ all
    @Test
    @Order(3)
    public void testGetPedidos() {
        // Obtenemos todos los pedidos y revisamos que la lista no esté vacía y que incluya el pedido creado.
        List<Pedido> pedidos = pedidoService.getPedidos();
        assertFalse(pedidos.isEmpty(), "Debe haber pedidos en la base");
        assertTrue(pedidos.stream().anyMatch(p -> p.getId().equals(idPedidoCreado)));
    }

    // U - UPDATE (PUT)
    @Test
    @Order(4)
    public void testUpdatePedido() {
        // Actualizamos todos los datos del pedido usando PUT, con otro método de pago permitido.
        PedidoDTO dto = new PedidoDTO();
        dto.setMetodoPago("TRANSFERENCIA"); // Otro valor permitido.
        dto.setDirEnvio("Nueva Dirección 789");
        dto.setDirFacturacion("Nueva Facturación 012");
        dto.setCostoEnvio(7000);
        dto.setPrecioPedido(120000);
        dto.setAnotaciones("Urgente");
        dto.setRutCliente(idCliente);
        dto.setIdSucursal(idSucursal);

        Pedido actualizado = pedidoService.updatePedido(dto, idPedidoCreado);
        assertEquals("TRANSFERENCIA", actualizado.getMetodoPago());
        assertEquals(120000, actualizado.getPrecioPedido());
    }

    // U - PATCH (actualización parcial)
    @Test
    @Order(5)
    public void testPatchPedido() {
        // Solo modificamos algunos campos del pedido para probar la actualización parcial.
        PedidoDTO dto = new PedidoDTO();
        dto.setAnotaciones("Con regalo");
        dto.setCostoEnvio(3000);

        Pedido parchado = pedidoService.patchPedido(dto, idPedidoCreado);
        assertEquals("Con regalo", parchado.getAnotaciones());
        assertEquals(3000, parchado.getCostoEnvio());
        // Revisamos que los campos no modificados sigan igual.
        assertEquals("TRANSFERENCIA", parchado.getMetodoPago());
    }

    // D - DELETE
    @Test
    @Order(6)
    public void testDeletePedido() {
        // Eliminamos el pedido y comprobamos que ya no exista en la base de datos.
        pedidoService.deletePedido(idPedidoCreado);
        Optional<Pedido> pedidoOpt = pedidoService.getPedidoByID(idPedidoCreado);
        assertTrue(pedidoOpt.isEmpty(), "El pedido debe haber sido eliminado");
    }
}

// -----------------------------------------------------------------------------
// Resumen técnico:
// - Valida todas las operaciones CRUD para pedidos, incluyendo la relación con Cliente y Sucursal.
// - Se asegura de usar valores válidos para el campo metodo_pago según las restricciones del schema.sql.
// - Cubre creación, lectura individual y general, actualización total (PUT), actualización parcial (PATCH) y eliminación.
// - Evita errores de integridad y cumple con restricciones CHECK y NOT NULL.
// - Si modificas o adaptas el test, usa siempre valores válidos para los campos restringidos.
// - Se corrigieron posibles errores de datos nulos y se mejoraron los comentarios para mayor claridad.
// -----------------------------------------------------------------------------
