package PerfulandiaSpA.API;

import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Servicio.SucursalService;
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
public class SucursalServiceTest {

    @Autowired
    private SucursalService sucursalService;



    @Test
    @Order(1)
    // C - crear una sucursal nueva
    public void testSaveSucursal() {
        Sucursal sucursal = new Sucursal();
        sucursal.setId(3);
        sucursal.setNombreSucursal("Teatinos");
        sucursal.setDireccionSucursal("san francisco 2432");

        String resultado = sucursalService.saveSucursal(sucursal);
        assertEquals("Sucursal agregada con éxito", resultado);

        assertNotNull(sucursal.getId());
    }

    @Test
    @Order(2)
    // R Lista todas las sucursales
    public void testGetSucursales() {
        // Creacion de sucursales
        Sucursal a = new Sucursal();
        a.setId(101);
        a.setNombreSucursal("Sucursal Teatinos");
        a.setDireccionSucursal("Teatinos 1231");

        Sucursal b = new Sucursal();
        b.setId(102);
        b.setNombreSucursal("Sucursal SanFran");
        b.setDireccionSucursal("avenida jose 12");

        sucursalService.saveSucursal(a);
        sucursalService.saveSucursal(b);
        String resultado = sucursalService.getSucursales();

        // Validar
        assertNotNull(resultado);
        assertTrue(resultado.contains("Teatinos 1231"));
        assertTrue(resultado.contains("avenida jose 12"));
    }

    @Test
    @Order(3)
    // R - buscar sucursales por id
    public void testGetSucursalesByID() {
        List<Sucursal> sucursals = sucursalService.getSucursalesJSON();
        Integer idsucursal = null;
        for (Sucursal s : sucursals) {
            idsucursal = s.getId();
            break;
        }
        assertNotNull(idsucursal, "Sucursal no encontrada");
        String resultado = sucursalService.getSucursalByID(idsucursal);
        assertTrue(resultado.contains("ID:"), "Falta ID");
        assertTrue(resultado.contains("Nombre sucursal:"), "Falta nombre");
        assertTrue(resultado.contains("Direccion sucursal:"), "Falta direccion");

    }
    @Test
    @Order(4)
    // U - Actualizar sucursal por id
    public void testUpdateSucursal() {
        Sucursal s = new Sucursal();
        s.setId(999);
        s.setNombreSucursal("Sucursal temporal");
        s.setDireccionSucursal("Falsa 123");
        sucursalService.saveSucursal(s);


        s.setId(999);
        s.setNombreSucursal("Actualizada");
        s.setDireccionSucursal("Nueva direccion 123");

        String resultado = sucursalService.updateSucursal(s, 999);
        assertEquals("Sucursal actualizado con éxito", resultado);

        // Verificar que se haya actualizado
        String check = sucursalService.getSucursalByID(999);
        assertTrue(check.contains("Actualizada"));
        assertTrue(check.contains("Nueva direccion 123"));


    }
    @Test
    @Order(5)
    // D Eliminar producto
    public void testDeleteSucursal(){
        Sucursal s = new Sucursal();
        s.setId(999);
        s.setNombreSucursal("Sucursal Por Eliminar");
        s.setDireccionSucursal("Av. Que se va a Borrar");
        sucursalService.saveSucursal(s);

        //Eliminar Por id
        String resultado = sucursalService.deleteSucursal(999);
        assertEquals("Usuario eliminado con éxito",resultado);

        //Comprobar que se elimino
        String check = sucursalService.getSucursalByID(999);
        assertEquals("Sucursal no encontrada",check);
    }



}
