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

    private Integer idCreado = null;

    // C - Crear un proveedor nuevo
    @Test
    @Order(1)
    public void testSaveProveedor() {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombreProveedor("Proveedor Tres");
        proveedor.setTelefonoProveedor("912000003");
        proveedor.setEmailProveedor("prov3@correo.com");

        proveedorService.saveProveedor(proveedor);

        assertNotNull(proveedor.getId());
        idCreado = proveedor.getId();

        // Verifica que el proveedor se guardó correctamente
        Optional<Proveedor> provOpt = proveedorService.getProveedorByID(idCreado);
        assertTrue(provOpt.isPresent());
        assertEquals("Proveedor Tres", provOpt.get().getNombreProveedor());
    }

    // R - Buscar por ID (busca el proveedor "Proveedor Uno" de data.sql)
    @Test
    @Order(2)
    public void testGetProveedorById_Uno() {
        List<Proveedor> proveedores = proveedorService.getProveedores();
        Integer idUno = null;
        for (Proveedor p : proveedores) {
            if ("Proveedor Uno".equalsIgnoreCase(p.getNombreProveedor())) {
                idUno = p.getId();
                break;
            }
        }
        assertNotNull(idUno, "No se encontró el proveedor Uno en la base de datos de prueba.");

        Optional<Proveedor> provOpt = proveedorService.getProveedorByID(idUno);
        assertTrue(provOpt.isPresent());
        Proveedor proveedor = provOpt.get();
        assertEquals("Proveedor Uno", proveedor.getNombreProveedor());
        assertEquals("912000001", proveedor.getTelefonoProveedor());
        assertEquals("prov1@correo.com", proveedor.getEmailProveedor());
    }

    // R - Listar todos (precargados y el nuevo)
    @Test
    @Order(3)
    public void testGetProveedores() {
        List<Proveedor> proveedores = proveedorService.getProveedores();
        assertFalse(proveedores.isEmpty());

        boolean hayUno = proveedores.stream().anyMatch(p -> "Proveedor Uno".equalsIgnoreCase(p.getNombreProveedor()));
        boolean hayDos = proveedores.stream().anyMatch(p -> "Proveedor Dos".equalsIgnoreCase(p.getNombreProveedor()));
        boolean hayTres = proveedores.stream().anyMatch(p -> "Proveedor Tres".equalsIgnoreCase(p.getNombreProveedor()));

        assertTrue(hayUno);
        assertTrue(hayDos);
        assertTrue(hayTres);
    }

    // U - Actualizar proveedor creado
    @Test
    @Order(4)
    public void testUpdateProveedor() {
        Proveedor actualizado = new Proveedor();
        actualizado.setNombreProveedor("Proveedor Tres Editado");
        actualizado.setTelefonoProveedor("912000999");
        actualizado.setEmailProveedor("prov3editado@correo.com");

        proveedorService.updateProveedor(actualizado, idCreado);

        Optional<Proveedor> provOpt = proveedorService.getProveedorByID(idCreado);
        assertTrue(provOpt.isPresent());
        Proveedor proveedor = provOpt.get();
        assertEquals("Proveedor Tres Editado", proveedor.getNombreProveedor());
        assertEquals("912000999", proveedor.getTelefonoProveedor());
        assertEquals("prov3editado@correo.com", proveedor.getEmailProveedor());
    }

    // D - Eliminar proveedor creado
    @Test
    @Order(5)
    public void testDeleteProveedor() {
        proveedorService.deleteProveedor(idCreado);

        Optional<Proveedor> provOpt = proveedorService.getProveedorByID(idCreado);
        assertTrue(provOpt.isEmpty(), "El proveedor debería haber sido eliminado.");
    }
}
