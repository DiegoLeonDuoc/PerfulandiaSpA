package PerfulandiaSpA.API;

import PerfulandiaSpA.Entidades.Proveedor;
import PerfulandiaSpA.Servicio.ProveedorService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

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

        String resultado = proveedorService.saveProveedor(proveedor);
        assertEquals("Proveedor agregado con éxito", resultado);

        assertNotNull(proveedor.getId());
        idCreado = proveedor.getId();
    }

    // R - Buscar por ID (busca el proveedor "Proveedor Uno" de data.sql)
    @Test
    @Order(2)
    public void testGetProveedorById_Uno() {
        List<Proveedor> proveedores = proveedorService.getProveedoresJSON();
        Integer idUno = null;
        for (Proveedor p : proveedores) {
            if ("Proveedor Uno".equalsIgnoreCase(p.getNombreProveedor())) {
                idUno = p.getId();
                break;
            }
        }
        assertNotNull(idUno, "No se encontró el proveedor Uno en la base de datos de prueba.");
        String resultado = proveedorService.getProveedorById(idUno);
        assertTrue(resultado.contains("Proveedor Uno"));
        assertTrue(resultado.contains("912000001"));
        assertTrue(resultado.contains("prov1@correo.com"));
    }

    // R - Listar todos (precargados y el nuevo)
    @Test
    @Order(3)
    public void testGetProveedores() {
        String resultado = proveedorService.getProveedores();
        assertTrue(resultado.contains("Proveedor Uno"));
        assertTrue(resultado.contains("Proveedor Dos"));
        assertTrue(resultado.contains("Proveedor Tres")); // El que creamos arriba
    }

    // U - Actualizar proveedor creado
    @Test
    @Order(4)
    public void testUpdateProveedor() {
        Proveedor actualizado = new Proveedor();
        actualizado.setNombreProveedor("Proveedor Tres Editado");
        actualizado.setTelefonoProveedor("912000999");
        actualizado.setEmailProveedor("prov3editado@correo.com");

        String resultado = proveedorService.updateProveedor(actualizado, idCreado);
        assertEquals("Proveedor actualizado con éxito", resultado);

        String proveedorActual = proveedorService.getProveedorById(idCreado);
        assertTrue(proveedorActual.contains("Proveedor Tres Editado"));
        assertTrue(proveedorActual.contains("912000999"));
        assertTrue(proveedorActual.contains("prov3editado@correo.com"));
    }

    // D - Eliminar proveedor creado
    @Test
    @Order(5)
    public void testDeleteProveedor() {
        String resultado = proveedorService.deleteProveedor(idCreado);
        assertEquals("Proveedor eliminado con éxito", resultado);

        String proveedorEliminado = proveedorService.getProveedorById(idCreado);
        assertEquals("Proveedor no encontrado", proveedorEliminado);
    }
}
