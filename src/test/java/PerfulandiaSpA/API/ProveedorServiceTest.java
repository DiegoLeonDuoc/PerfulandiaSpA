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

    private Integer idCreado;

    // C - Crear proveedor
    @Test
    @Order(1)
    public void testSaveProveedor() {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombreProveedor("Proveedor Test");
        proveedor.setTelefonoProveedor("912345679");
        proveedor.setEmailProveedor("proveedor.test@email.com");

        proveedorService.saveProveedor(proveedor);

        assertNotNull(proveedor.getId(), "El proveedor debe tener ID luego de guardarse");
        idCreado = proveedor.getId();
    }

    // R - Obtener por ID
    @Test
    @Order(2)
    public void testGetProveedorById() {
        assertNotNull(idCreado, "ID de proveedor creado no debe ser null");

        Optional<Proveedor> proveedorOpt = proveedorService.getProveedorByID(idCreado);
        assertTrue(proveedorOpt.isPresent(), "Proveedor creado debe existir");
        assertEquals("Proveedor Test", proveedorOpt.get().getNombreProveedor());
    }

    // R - Listar todos
    @Test
    @Order(3)
    public void testGetProveedores() {
        List<Proveedor> proveedores = proveedorService.getProveedores();
        assertFalse(proveedores.isEmpty(), "Debe haber proveedores en la base");
        assertTrue(proveedores.stream().anyMatch(p -> p.getId().equals(idCreado)), "La lista debe contener el proveedor creado");
    }

    // U - Actualizar proveedor
    @Test
    @Order(4)
    public void testUpdateProveedor() {
        Proveedor actualizado = new Proveedor();
        actualizado.setNombreProveedor("Proveedor Actualizado");
        actualizado.setTelefonoProveedor("987654321");
        actualizado.setEmailProveedor("proveedor.actualizado@email.com");

        proveedorService.updateProveedor(actualizado, idCreado);

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
        proveedorService.deleteProveedor(idCreado);
        Optional<Proveedor> proveedorOpt = proveedorService.getProveedorByID(idCreado);
        assertTrue(proveedorOpt.isEmpty(), "El proveedor debe haber sido eliminado");
    }
}
