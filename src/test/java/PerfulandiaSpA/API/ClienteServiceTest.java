package PerfulandiaSpA.API;

import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Entidades.Usuario;
import PerfulandiaSpA.Servicio.ClienteService;
import PerfulandiaSpA.Servicio.ProductoService;
import PerfulandiaSpA.Servicio.UsuarioService;
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
public class ClienteServiceTest {

    @Autowired
    UsuarioService usuarioService;

    private Integer idCreado = null;

    @Test
    @Order(1)
    // C Crear un cliente
    public void testCrearCliente() {
        Cliente cliente = new Cliente();
        cliente.setRutUsuario(idCreado);
        cliente.setDvUsuario('k');
        cliente.setNomUsuario("Juanito");
        cliente.setNom2Usuario("Matias");
        cliente.setApellidoPaterno("Perez");
        cliente.setApellidoMaterno("Sanhueza");
        cliente.setSexoUsuario('M');
        cliente.setDirUsuario("Calle 123");
        cliente.setFechaNacimiento(LocalDate.parse("1992-01-01"));
        cliente.setTelefonoUsuario("912525678");
        cliente.setTel2Usuario(null);
        cliente.setEmailUsuario("pepetapia@gmail.com");
        cliente.setPassUsuario("1111");
        cliente.setTipoUsuario("Cliente");
        cliente.setEstadoCuenta("Activo");

    }

    @Test
    @Order(2)
    // R Mostrar clientes por rut
    public void testGetClientes() {
        List<Cliente> clientes = UsuarioService.getClientesJSON();
        Integer Rut = null;
        for (Cliente c : clientes) {
            Rut = c.getRutUsuario();
            break;
        }
    }

}
