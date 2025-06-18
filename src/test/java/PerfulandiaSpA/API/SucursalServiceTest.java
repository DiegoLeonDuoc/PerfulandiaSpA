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

    private Integer idCreado;  // Aquí guardamos el ID de la sucursal creada para usarlo en los siguientes tests

    // C - Crear sucursal
    @Test
    @Order(1)
    public void testSaveSucursal() {
        // Creamos una nueva sucursal y la guardamos usando el servicio
        Sucursal sucursal = new Sucursal();
        sucursal.setNombreSucursal("Sucursal Test");
        sucursal.setDireccionSucursal("Calle Test 123");

        sucursalService.saveSucursal(sucursal);

        // Verificamos que después de guardar, la sucursal tenga un ID asignado (o sea, se guardó bien en la base)
        assertNotNull(sucursal.getId(), "La sucursal debe tener ID luego de guardarse");
        idCreado = sucursal.getId(); // Guardamos el ID para las siguientes pruebas
    }

    // R - Obtener por ID
    @Test
    @Order(2)
    public void testGetSucursalById() {
        // Revisamos que el ID guardado no sea nulo (la sucursal se creó correctamente en el test anterior)
        assertNotNull(idCreado, "ID de sucursal creada no debe ser null");

        // Buscamos la sucursal por su ID usando el servicio
        Optional<Sucursal> sucursalOpt = sucursalService.getSucursalByID(idCreado);

        // Verificamos que la sucursal existe y que el nombre coincide con el que creamos
        assertTrue(sucursalOpt.isPresent(), "Sucursal creada debe existir");
        assertEquals("Sucursal Test", sucursalOpt.get().getNombreSucursal());
    }

    // R - Listar todas
    @Test
    @Order(3)
    public void testGetSucursales() {
        // Obtenemos la lista de todas las sucursales
        List<Sucursal> sucursales = sucursalService.getSucursales();

        // Revisamos que la lista no esté vacía y que incluya la sucursal que creamos antes
        assertFalse(sucursales.isEmpty(), "Debe haber sucursales en la base");
        assertTrue(sucursales.stream().anyMatch(s -> s.getId().equals(idCreado)), "La lista debe contener la sucursal creada");
    }

    // U - Actualizar sucursal
    @Test
    @Order(4)
    public void testUpdateSucursal() {
        // Creamos un objeto con los nuevos datos para la sucursal
        Sucursal actualizada = new Sucursal();
        actualizada.setNombreSucursal("Sucursal Actualizada");
        actualizada.setDireccionSucursal("Nueva Calle 456");

        // Actualizamos la sucursal usando el ID guardado
        sucursalService.updateSucursal(actualizada, idCreado);

        // Buscamos la sucursal y verificamos que los datos se hayan actualizado correctamente
        Optional<Sucursal> sucursalOpt = sucursalService.getSucursalByID(idCreado);
        assertTrue(sucursalOpt.isPresent(), "Sucursal actualizada debe existir");
        assertEquals("Sucursal Actualizada", sucursalOpt.get().getNombreSucursal());
        assertEquals("Nueva Calle 456", sucursalOpt.get().getDireccionSucursal());
    }

    // D - Eliminar sucursal
    @Test
    @Order(5)
    public void testDeleteSucursal() {
        // Eliminamos la sucursal usando el ID guardado
        sucursalService.deleteSucursal(idCreado);

        // Buscamos la sucursal eliminada y verificamos que ya no exista
        Optional<Sucursal> sucursalOpt = sucursalService.getSucursalByID(idCreado);
        assertTrue(sucursalOpt.isEmpty(), "La sucursal debe haber sido eliminada");
    }
}

// -----------------------------------------------------------------------------
// Resumen técnico:
// - Este archivo prueba todas las operaciones básicas (CRUD) del servicio de sucursales.
// - Se valida: crear, buscar por ID, listar todas, actualizar y eliminar sucursales.
// - Cada prueba usa el ID generado en la creación para asegurar continuidad y coherencia.
// - Se mejoró la claridad de los comentarios para explicar cada paso y lógica de las pruebas.
// - Se asegura que los datos se creen, modifiquen y eliminen correctamente en la base de pruebas.
// - Se corrige el flujo de pruebas usando anotaciones de orden para mantener la secuencia.
// - Así se garantiza que el servicio funciona bien y responde correctamente ante operaciones comunes.
// -----------------------------------------------------------------------------
