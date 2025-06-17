package PerfulandiaSpA.API;

import PerfulandiaSpA.DTO.EmpleadoDTO;
import PerfulandiaSpA.Entidades.Empleado;
import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Repositorio.SucursalRepository;
import PerfulandiaSpA.Servicio.EmpleadoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

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

    private final Integer RUT_EMPLEADO = 20000002;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    // C - Crear empleado
    @Test
    @Order(1)
    public void testCrearEmpleado() {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setRutUsuario(RUT_EMPLEADO);
        dto.setDvUsuario('2');
        dto.setNomUsuario("Juan");
        dto.setApellidoPaterno("Gómez");
        dto.setSexoUsuario('M');
        dto.setDirUsuario("Calle Falsa 456");
        dto.setFechaNacimiento(LocalDate.of(1985, 5, 10));
        dto.setTelefonoUsuario("987654321");
        dto.setEmailUsuario("juan@correo.com");
        dto.setPassUsuario("claveSegura");
        dto.setIdSucursal(1); // Sucursal existente en data.sql

        Empleado empleado = empleadoService.crearEmpleado(dto);

        assertNotNull(empleado.getRutUsuario());
        assertEquals("EMPLEADO", empleado.getTipoUsuario());
        assertTrue(encoder.matches("claveSegura", empleado.getPassUsuario()));
        assertEquals(1, empleado.getSucursalAsociada().getId());
    }

    // R - Obtener por RUT
    @Test
    @Order(2)
    public void testGetEmpleadoByRut() {
        Optional<Empleado> empleadoOpt = empleadoService.getEmpleadoByRut(RUT_EMPLEADO);

        assertTrue(empleadoOpt.isPresent());
        Empleado empleado = empleadoOpt.get();
        assertEquals("Juan", empleado.getNomUsuario());
        assertEquals("Gómez", empleado.getApellidoPaterno());
        assertEquals(1, empleado.getSucursalAsociada().getId());
    }

    // R - Listar por sucursal
    @Test
    @Order(3)
    public void testGetEmpleadosBySucursal() {
        List<Empleado> empleados = empleadoService.getEmpleadosBySucursal(1);

        assertFalse(empleados.isEmpty());
        assertTrue(empleados.stream().anyMatch(e -> e.getRutUsuario().equals(RUT_EMPLEADO)));
    }

    // U - Actualización completa
    @Test
    @Order(4)
    public void testUpdateEmpleado() {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setRutUsuario(RUT_EMPLEADO);
        dto.setDvUsuario('2');
        dto.setNomUsuario("Juan Carlos");
        dto.setApellidoPaterno("Gómez Pérez");
        dto.setSexoUsuario('M');
        dto.setDirUsuario("Nueva Dirección 789");
        dto.setFechaNacimiento(LocalDate.of(1985, 5, 15));
        dto.setTelefonoUsuario("912345678");
        dto.setEmailUsuario("juan.nuevo@correo.com");
        dto.setPassUsuario("nuevaClaveSegura");
        dto.setIdSucursal(1);

        Empleado actualizado = empleadoService.updateEmpleado(dto, RUT_EMPLEADO);

        assertEquals("Juan Carlos", actualizado.getNomUsuario());
        assertEquals("Gómez Pérez", actualizado.getApellidoPaterno());
        assertTrue(encoder.matches("nuevaClaveSegura", actualizado.getPassUsuario()));
    }

    // U - Actualización parcial (PATCH)
    @Test
    @Order(5)
    public void testPatchEmpleado() {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setEmailUsuario("juan.actualizado@correo.com");
        dto.setTelefonoUsuario("998877665");

        Empleado parcheado = empleadoService.patchEmpleado(dto, RUT_EMPLEADO);

        assertEquals("juan.actualizado@correo.com", parcheado.getEmailUsuario());
        assertEquals("998877665", parcheado.getTelefonoUsuario());
        // Campos no modificados
        assertEquals("Juan Carlos", parcheado.getNomUsuario());
        assertEquals("Gómez Pérez", parcheado.getApellidoPaterno());
    }

    // D - Eliminar empleado
    @Test
    @Order(6)
    public void testDeleteEmpleado() {
        empleadoService.deleteEmpleado(RUT_EMPLEADO);
        Optional<Empleado> empleadoOpt = empleadoService.getEmpleadoByRut(RUT_EMPLEADO);
        assertTrue(empleadoOpt.isEmpty());
    }
}
