package PerfulandiaSpA.API;

import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Servicio.SucursalService;
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
public class SucursalServiceTest {

    @Autowired
    SucursalService sucursalService;

    private Integer idCreado;

    // C - Crear sucursal
    @Test
    @Order(1)
    public void testSaveSucursal() {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombreSucursal("Sucursal Test");
        sucursal.setDireccionSucursal("Calle Test 123");

        sucursalService.saveSucursal(sucursal);

        assertNotNull(sucursal.getId(), "La sucursal debe tener ID luego de guardarse");
        idCreado = sucursal.getId();
    }

    // R - Obtener por ID
    @Test
    @Order(2)
    public void testGetSucursalById() {
        assertNotNull(idCreado, "ID de sucursal creada no debe ser null");

        Optional<Sucursal> sucursalOpt = sucursalService.getSucursalByID(idCreado);
        assertTrue(sucursalOpt.isPresent(), "Sucursal creada debe existir");
        assertEquals("Sucursal Test", sucursalOpt.get().getNombreSucursal());
    }

    // R - Listar todas
    @Test
    @Order(3)
    public void testGetSucursales() {
        List<Sucursal> sucursales = sucursalService.getSucursales();
        assertFalse(sucursales.isEmpty(), "Debe haber sucursales en la base");
        assertTrue(sucursales.stream().anyMatch(s -> s.getId().equals(idCreado)), "La lista debe contener la sucursal creada");
    }

    // U - Actualizar sucursal
    @Test
    @Order(4)
    public void testUpdateSucursal() {
        Sucursal actualizada = new Sucursal();
        actualizada.setNombreSucursal("Sucursal Actualizada");
        actualizada.setDireccionSucursal("Nueva Calle 456");

        sucursalService.updateSucursal(actualizada, idCreado);

        Optional<Sucursal> sucursalOpt = sucursalService.getSucursalByID(idCreado);
        assertTrue(sucursalOpt.isPresent(), "Sucursal actualizada debe existir");
        assertEquals("Sucursal Actualizada", sucursalOpt.get().getNombreSucursal());
        assertEquals("Nueva Calle 456", sucursalOpt.get().getDireccionSucursal());
    }

    // D - Eliminar sucursal
    @Test
    @Order(5)
    public void testDeleteSucursal() {
        sucursalService.deleteSucursal(idCreado);
        Optional<Sucursal> sucursalOpt = sucursalService.getSucursalByID(idCreado);
        assertTrue(sucursalOpt.isEmpty(), "La sucursal debe haber sido eliminada");
    }
}
