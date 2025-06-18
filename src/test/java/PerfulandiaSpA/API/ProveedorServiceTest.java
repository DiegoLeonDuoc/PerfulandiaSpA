package PerfulandiaSpA.API;

import PerfulandiaSpA.Entidades.Proveedor;
import PerfulandiaSpA.Servicio.ProveedorService;
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
public class ProveedorServiceTest {

    @Autowired
    ProveedorService proveedorService;

    private Integer idCreado; // Guardamos el ID del proveedor creado para usarlo en los demás tests

    // C - Crear proveedor
    @Test
    @Order(1)
    public void testSaveProveedor() {
        // Creamos un proveedor nuevo y lo guardamos usando el servicio
        Proveedor proveedor = new Proveedor();
        proveedor.setNombreProveedor("Proveedor Test");
        proveedor.setTelefonoProveedor("912345679");
        proveedor.setEmailProveedor("proveedor.test@email.com");

        proveedorService.saveProveedor(proveedor);

        // Verificamos que el proveedor tenga un ID luego de ser guardado (esto indica que se guardó bien en la base)
        assertNotNull(proveedor.getId(), "El proveedor debe tener ID luego de guardarse");
        idCreado = proveedor.getId(); // Guardamos el ID para los siguientes tests
    }

    // R - Obtener por ID
    @Test
    @Order(2)
    public void testGetProveedorById() {
        // Comprobamos que el ID guardado no sea nulo (el proveedor se creó correctamente antes)
        assertNotNull(idCreado, "ID de proveedor creado no debe ser null");

        // Buscamos el proveedor por su ID usando el servicio
        Optional<Proveedor> proveedorOpt = proveedorService.getProveedorByID(idCreado);

        // Verificamos que el proveedor existe y que el nombre es el correcto
        assertTrue(proveedorOpt.isPresent(), "Proveedor creado debe existir");
        assertEquals("Proveedor Test", proveedorOpt.get().getNombreProveedor());
    }

    // R - Listar todos
    @Test
    @Order(3)
    public void testGetProveedores() {
        // Obtenemos la lista de todos los proveedores
        List<Proveedor> proveedores = proveedorService.getProveedores();

        // Revisamos que la lista no esté vacía y que incluya el proveedor que creamos antes
        assertFalse(proveedores.isEmpty(), "Debe haber proveedores en la base");
        assertTrue(proveedores.stream().anyMatch(p -> p.getId().equals(idCreado)), "La lista debe contener el proveedor creado");
    }

    // U - Actualizar proveedor
    @Test
    @Order(4)
    public void testUpdateProveedor() {
        // Creamos un objeto con los nuevos datos para el proveedor
        Proveedor actualizado = new Proveedor();
        actualizado.setNombreProveedor("Proveedor Actualizado");
        actualizado.setTelefonoProveedor("987654321");
        actualizado.setEmailProveedor("proveedor.actualizado@email.com");

        // Actualizamos el proveedor usando el ID guardado
        proveedorService.updateProveedor(actualizado, idCreado);

        // Buscamos el proveedor y verificamos que los datos se hayan actualizado correctamente
        Optional<Proveedor> proveedorOpt = proveedorService.getProveedorByID(idCreado);
        assertTrue(proveedorOpt.isPresent(), "Proveedor actualizado debe existir");
        assertEquals("Proveedor Actualizado", proveedorOpt.get().getNombreProveedor());
        assertEquals("987654321", proveedorOpt.get().getTelefonoProveedor());
        assertEquals("proveedor.actualizado@email.com", proveedorOpt.get().getEmailProveedor());
    }

    // D - Eliminar proveedor
    @Test
    @Order(5)
    public void testDeleteProveedor() {
        // Eliminamos el proveedor usando el ID guardado
        proveedorService.deleteProveedor(idCreado);

        // Buscamos el proveedor eliminado y verificamos que ya no exista
        Optional<Proveedor> proveedorOpt = proveedorService.getProveedorByID(idCreado);
        assertTrue(proveedorOpt.isEmpty(), "El proveedor debe haber sido eliminado");
    }
}

// -----------------------------------------------------------------------------
// Resumen técnico:
// - Este archivo valida todas las operaciones CRUD para el servicio de proveedores.
// - Se prueba crear, buscar por ID, listar todos, actualizar y eliminar proveedores.
// - Cada test utiliza el ID generado en la creación para mantener la secuencia y coherencia.
// - Los comentarios explican claramente la lógica y el objetivo de cada prueba.
// - Se asegura que los datos se manipulan correctamente en la base de pruebas y que los cambios se reflejan como corresponde.
// - Se corrigió y aclaró el flujo de pruebas usando anotaciones de orden y verificaciones de existencia después de cada operación.
// - Así se garantiza que el servicio funciona correctamente y responde bien ante operaciones básicas y comunes.
// -----------------------------------------------------------------------------
