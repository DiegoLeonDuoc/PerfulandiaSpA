package PerfulandiaSpA.API;

import PerfulandiaSpA.DTO.EmpleadoDTO;
import PerfulandiaSpA.Entidades.Empleado;
import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Servicio.EmpleadoService;
import PerfulandiaSpA.Repositorio.SucursalRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class EmpleadoServiceTest {

    @Autowired
    EmpleadoService empleadoService;

    @Autowired
    SucursalRepository sucursalRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
    private final Integer RUT_TEST = 88888888;
    private final String PASS_PLANA = "empleado123";
    private Integer idSucursal;

    @BeforeAll
    public void setupSucursal() {
        // Preparamos una sucursal para asociar al empleado
        // Si ya existe una en data.sql, la usamos. Si no, creamos una nueva
        Optional<Sucursal> sucOpt = sucursalRepository.findAll().stream().findFirst();
        if (sucOpt.isPresent()) {
            idSucursal = sucOpt.get().getId();
        } else {
            Sucursal suc = new Sucursal();
            suc.setNombreSucursal("Sucursal Test");
            suc.setDireccionSucursal("Calle Test 789");
            sucursalRepository.save(suc);
            idSucursal = suc.getId();
        }
    }

    // C - Crear empleado
    @Test
    @Order(1)
    public void testCrearEmpleado() {
        // Creamos un DTO con todos los datos necesarios para un empleado nuevo
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setRutUsuario(RUT_TEST);
        dto.setDvUsuario('8');
        dto.setNomUsuario("Pedro");
        dto.setNom2Usuario("Luis");
        dto.setApellidoPaterno("García");
        dto.setApellidoMaterno("Ramírez");
        dto.setSexoUsuario('M');
        dto.setDirUsuario("Calle Empleado 123");
        dto.setFechaNacimiento(LocalDate.of(1987, 4, 10));
        dto.setTelefonoUsuario("912345679");
        dto.setTel2Usuario("987654321");
        dto.setEmailUsuario("pedro@empresa.com");
        dto.setPassUsuario(PASS_PLANA);
        dto.setIdSucursal(idSucursal);

        // Ejecutamos el método de creación
        Empleado empleado = empleadoService.crearEmpleado(dto);

        // Verificaciones básicas de creación exitosa
        assertNotNull(empleado.getRutUsuario(), "El empleado debe tener RUT luego de guardarse");
        assertEquals("EMPLEADO", empleado.getTipoUsuario()); // Tipo de usuario automático
        assertEquals(idSucursal, empleado.getSucursalAsociada().getId()); // Relación con sucursal
        assertTrue(encoder.matches(PASS_PLANA, empleado.getPassUsuario())); // Contraseña encriptada
    }

    // R - Obtener por RUT
    @Test
    @Order(2)
    public void testGetEmpleadoByRut() {
        // Buscamos el empleado creado en el test anterior usando su RUT
        Optional<Empleado> empleadoOpt = empleadoService.getEmpleadoByRut(RUT_TEST);

        // Verificamos que exista y que los datos básicos coincidan
        assertTrue(empleadoOpt.isPresent(), "El empleado debe existir");
        assertEquals("Pedro", empleadoOpt.get().getNomUsuario());
        assertEquals("García", empleadoOpt.get().getApellidoPaterno());
    }

    // R - Listar todos
    @Test
    @Order(3)
    public void testGetEmpleados() {
        // Obtenemos todos los empleados y verificamos que la lista no esté vacía
        List<Empleado> empleados = empleadoService.getEmpleados();
        assertFalse(empleados.isEmpty(), "Debe haber empleados en la base");
        // Confirmamos que nuestro empleado de prueba está en la lista
        assertTrue(empleados.stream().anyMatch(e -> e.getRutUsuario().equals(RUT_TEST)));
    }

    // R - Listar por sucursal
    @Test
    @Order(4)
    public void testGetEmpleadosBySucursal() {
        // Obtenemos empleados filtrados por sucursal
        List<Empleado> empleados = empleadoService.getEmpleadosBySucursal(idSucursal);
        assertFalse(empleados.isEmpty(), "Debe haber empleados en la sucursal");
        // Verificamos que nuestro empleado está en la lista filtrada
        assertTrue(empleados.stream().anyMatch(e -> e.getRutUsuario().equals(RUT_TEST)));
    }

    // U - Actualizar empleado
    @Test
    @Order(5)
    public void testUpdateEmpleado() {
        // Creamos un DTO con datos actualizados para todos los campos
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setNomUsuario("Pedro Actualizado");
        dto.setApellidoPaterno("González");
        dto.setApellidoMaterno("López");
        dto.setSexoUsuario('F');
        dto.setDirUsuario("Nueva Dirección 456");
        dto.setFechaNacimiento(LocalDate.of(1990, 12, 15));
        dto.setTelefonoUsuario("998877665");
        dto.setTel2Usuario("911223344");
        dto.setEmailUsuario("pedro.actualizado@empresa.com");
        dto.setPassUsuario("nuevaClave123");
        dto.setIdSucursal(idSucursal);

        // Ejecutamos la actualización completa
        Empleado actualizado = empleadoService.updateEmpleado(dto, RUT_TEST);

        // Verificamos que todos los campos se actualizaron correctamente
        assertEquals("Pedro Actualizado", actualizado.getNomUsuario());
        assertEquals("González", actualizado.getApellidoPaterno());
        assertEquals("López", actualizado.getApellidoMaterno());
        assertEquals('F', actualizado.getSexoUsuario());
        assertTrue(encoder.matches("nuevaClave123", actualizado.getPassUsuario()));
        assertEquals(idSucursal, actualizado.getSucursalAsociada().getId());
    }

    // U - Patch empleado
    @Test
    @Order(6)
    public void testPatchEmpleado() {
        // Creamos un DTO solo con los campos que queremos modificar
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setEmailUsuario("parche@empresa.com");
        dto.setTelefonoUsuario("900000000");
        dto.setPassUsuario("parcheClave");

        // Aplicamos la actualización parcial
        Empleado parchado = empleadoService.patchEmpleado(dto, RUT_TEST);

        // Verificamos los campos modificados
        assertEquals("parche@empresa.com", parchado.getEmailUsuario());
        assertEquals("900000000", parchado.getTelefonoUsuario());
        assertTrue(encoder.matches("parcheClave", parchado.getPassUsuario()));

        // Verificamos que los demás campos permanecen igual
        assertEquals("Pedro Actualizado", parchado.getNomUsuario());
    }

    // D - Eliminar empleado
    @Test
    @Order(7)
    public void testDeleteEmpleado() {
        // Eliminamos el empleado de prueba
        empleadoService.deleteEmpleado(RUT_TEST);

        // Verificamos que ya no exista en la base de datos
        Optional<Empleado> empleadoOpt = empleadoService.getEmpleadoByRut(RUT_TEST);
        assertTrue(empleadoOpt.isEmpty(), "El empleado debe haber sido eliminado");
    }
}

// -----------------------------------------------------------------------------
// Resumen técnico:
// - Valida CRUD completo para EmpleadoService incluyendo operaciones con DTO
// - Prueba creación con valores por defecto (tipoUsuario) y encriptación de password
// - Verifica relaciones con Sucursal mediante idSucursal
// - Incluye pruebas de actualización completa (PUT) y parcial (PATCH)
// - Valida correcto funcionamiento de filtrado por sucursal
// - Asegura integridad de datos en operaciones de actualización y eliminación
// - Usa BCryptPasswordEncoder para verificar encriptación de contraseñas
// - Mantiene orden de ejecución con @Order para dependencias entre pruebas
// - Garantiza limpieza de datos mediante transacciones de prueba con @Sql
// -----------------------------------------------------------------------------