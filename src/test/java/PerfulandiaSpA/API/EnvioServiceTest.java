package PerfulandiaSpA.API;

import PerfulandiaSpA.DTO.EnvioDTO;
import PerfulandiaSpA.Entidades.Envio;
import PerfulandiaSpA.Servicio.EnvioService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class EnvioServiceTest {

    @Autowired
    EnvioService envioService;

    private void EnvioBase(){
        EnvioDTO envio = new EnvioDTO();
        envio.setCodigoEnvio(1312L);
        envio.setId(2);
        envio.setIdPedido(1);
        envio.setFechaEnvio(LocalDate.parse("2025-02-21"));
        envio.setFechaLlegadaEstim(LocalDate.parse("2025-02-25"));
        envio.setFechaLlegadaReal(null);
        envio.setTransportista("Penex");
        envio.setNumSeguimiento(13222L);
        envio.setMetodoEnvio("EXPRESS");
        envio.setAnotaciones("si");
        String resultado = envioService.crearEnvio(envio);
        System.out.println("resultado del envio:" + resultado);
    }
    //opciones de metodo de envio disponibles 'STANDARD', 'EXPRESS', 'INTERNACIONAL'
    @Test
    @Order(1)
    // C crear un envio
    public void testCrearEnvio(){
        EnvioDTO envio = new EnvioDTO();
        envio.setCodigoEnvio(1312L);
        envio.setId(1);
        envio.setIdPedido(1);
        envio.setFechaEnvio(LocalDate.parse("2025-02-21"));
        envio.setFechaLlegadaEstim(LocalDate.parse("2025-02-25"));
        envio.setFechaLlegadaReal(null);
        envio.setTransportista("Penex");
        envio.setNumSeguimiento(13222L);
        envio.setMetodoEnvio("EXPRESS");
        envio.setAnotaciones("no");
        String resultado = envioService.crearEnvio(envio);
        assertEquals("Envio agregado con éxito", resultado);
    }
    @Test
    @Order(2)
    // R ver todos los envios
    public void testGetEnvios(){
        EnvioBase();

        // Obtener la lista de envíos en formato String
        String resultado = envioService.getEnvios();

        // Validaciones
        assertNotNull(resultado, "El resultado no debería ser null");
        assertFalse(resultado.isEmpty(), "No hay envios registrados");
        assertTrue(resultado.contains("Penex"));
        assertTrue(resultado.contains("EXPRESS"));
    }

    @Test
    @Order(3)
    // R obtener envios por rut
    public void testGetEnviosByRut(){
        EnvioBase();
        Integer rut = 10000001;
        String resultado = envioService.getEnviosByRut(rut);

        assertNotNull(resultado, "El resultado no debería ser null");
        assertFalse(resultado.isEmpty(), "El resultado no debería estar vacío");

        // Validar
        assertTrue(resultado.contains("Penex"));
        assertTrue(resultado.contains("ID Pedido asociado"));

        // Validar
        Integer rutNoExistente = 99999999;
        String resultadoNoExiste = envioService.getEnviosByRut(rutNoExistente);
        assertEquals("Este cliente no existe", resultadoNoExiste);


    }
    @Test
    @Order(4)
    // R obtener envios por sucursal
    public void testGetEnviosSucursal(){
        EnvioBase();
        Integer idSucursal = 1;
        String resultado = envioService.getEnviosSucursal(idSucursal);

        // Validar
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty(),"No hay envios registrados en esta sucursal");

        // Validar
        assertTrue(resultado.contains("Penex"));
        assertTrue(resultado.contains("ID Pedido asociado"));

        // Validar
        Integer idSucursalNoExistente = 999;
        String resultadoNoExiste = envioService.getEnviosSucursal(idSucursalNoExistente);
        assertEquals("No hay envios registrados en esta sucursal", resultadoNoExiste);
    }
    @Test
    @Order(5)
    // R obtener envíos por sucursal en JSON
    public void testGetEnviosBySucursalJSON() {
        EnvioBase();
        Integer idSucursal = 1;
        List<Envio> envios = envioService.getEnviosBySucursalJSON(idSucursal);

        // Validar
        assertNotNull(envios);
        assertFalse(envios.isEmpty());

        // Validar
        for (Envio envio : envios) {
            assertEquals(idSucursal, envio.getPedidoAsociado().getSucursal().getId(),
                    "El envío debe pertenecer a la sucursal " + idSucursal);
        }

        // Validar
        Integer idSucursalNoExistente = 999;
        List<Envio> enviosVacio = envioService.getEnviosBySucursalJSON(idSucursalNoExistente);
        assertTrue(enviosVacio.isEmpty());
    }
    @Test
    @Order(6)
    // U actualizar envío por id
    public void testUpdateEnvio() {
        EnvioBase();

        EnvioDTO envionu = new EnvioDTO();
        envionu.setId(1);
        envionu.setCodigoEnvio(9999L);
        envionu.setIdPedido(1);
        envionu.setFechaEnvio(LocalDate.parse("2025-03-01"));
        envionu.setFechaLlegadaEstim(LocalDate.parse("2025-03-05"));
        envionu.setFechaLlegadaReal(LocalDate.parse("2025-03-04"));
        envionu.setTransportista("NuevoTransportista");
        envionu.setNumSeguimiento(88888L);
        envionu.setMetodoEnvio("STANDARD");

        //valdiar
        String resultado = envioService.updateEnvio(envionu);
        assertEquals("Envio agregado con éxito", resultado);

        //Validar
        EnvioDTO envioNoExiste = new EnvioDTO();
        envioNoExiste.setId(9999);
        String resultadoNoExiste = envioService.updateEnvio(envioNoExiste);
        assertEquals("El envío a actualizar no existe", resultadoNoExiste);

        //Validar
        EnvioDTO envioPedidoNoExiste = new EnvioDTO();
        envioPedidoNoExiste.setId(1);
        envioPedidoNoExiste.setIdPedido(9999);
        String resultadoPedidoNoExiste = envioService.updateEnvio(envioPedidoNoExiste);
        assertEquals("No existe un pedido asociado a ese ID", resultadoPedidoNoExiste);

    }
    @Test
    @Order(7)
    // D eliminar un envío
    public void testDeleteEnvio() {
        EnvioBase();
        int id = 1;

        // Validar
        String resultado = envioService.deleteEnvio(id);
        assertEquals("Envio eliminado con éxito", resultado);

        // Validar
        String resultadoNoExiste = envioService.deleteEnvio(id);
        assertEquals("Envio no existente", resultadoNoExiste);

        // Validar
        int idNoExistente = 9999;
        String resultadoNoExistente2 = envioService.deleteEnvio(idNoExistente);
        assertEquals("Envio no existente", resultadoNoExistente2);
    }

}
