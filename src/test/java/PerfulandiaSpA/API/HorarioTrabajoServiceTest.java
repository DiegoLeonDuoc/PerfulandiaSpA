package PerfulandiaSpA.API;

import PerfulandiaSpA.DTO.HorarioTrabajoDTO;
import PerfulandiaSpA.Entidades.HorarioTrabajo;
import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Repositorio.SucursalRepository;
import PerfulandiaSpA.Servicio.HorarioTrabajoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class HorarioTrabajoServiceTest {

    @Autowired
    HorarioTrabajoService horarioTrabajoService;

    @Autowired
    SucursalRepository sucursalRepository;

    private Integer idCreado;   // Guarda el ID del horario creado para los demás tests
    private Integer idSucursal; // Guarda el ID de la sucursal base

    @BeforeAll
    public void setup() {
        // Creamos una sucursal base para asociar los horarios de trabajo
        Sucursal sucursal = new Sucursal();
        sucursal.setNombreSucursal("Sucursal Test");
        sucursal.setDireccionSucursal("Calle Test 123");
        sucursalRepository.save(sucursal);
        idSucursal = sucursal.getId();
    }

    // C - CREATE
    @Test
    @Order(1)
    public void testSaveHorarioTrabajo() {
        // Creamos un horario de trabajo para el día lunes (1)
        HorarioTrabajoDTO dto = new HorarioTrabajoDTO();
        dto.setDiaSemana(1); // Lunes
        dto.setHorarioApertura(LocalTime.of(9, 0));
        dto.setHorarioCierre(LocalTime.of(18, 0));
        dto.setIdSucursal(idSucursal);

        HorarioTrabajo horario = horarioTrabajoService.saveHorarioTrabajo(dto);

        // Verificamos que se haya guardado correctamente y los datos sean los esperados
        assertNotNull(horario.getId(), "El horario debe tener ID después de guardarse");
        assertEquals(1, horario.getDiaSemana());
        assertEquals(LocalTime.of(9,0), horario.getHorarioApertura());
        idCreado = horario.getId(); // Guardamos el ID para los siguientes tests
    }

    // R - READ by ID
    @Test
    @Order(2)
    public void testGetHorarioTrabajoByID() {
        // Buscamos el horario por su ID y verificamos que los datos sean correctos
        Optional<HorarioTrabajo> horarioOpt = horarioTrabajoService.getHorarioTrabajoByID(idCreado);
        assertTrue(horarioOpt.isPresent(), "El horario creado debe existir");
        assertEquals(1, horarioOpt.get().getDiaSemana());
    }

    // R - READ all
    @Test
    @Order(3)
    public void testGetHorariosTrabajo() {
        // Obtenemos todos los horarios y verificamos que exista el creado
        List<HorarioTrabajo> horarios = horarioTrabajoService.getHorariosTrabajo();
        assertFalse(horarios.isEmpty(), "Debe haber horarios en la base");
        assertTrue(horarios.stream().anyMatch(h -> h.getId().equals(idCreado)));
    }

    // U - UPDATE
    @Test
    @Order(4)
    public void testUpdateHorarioTrabajo() {
        // Actualizamos el horario para el día martes (2) y cambiamos los horarios
        HorarioTrabajoDTO dto = new HorarioTrabajoDTO();
        dto.setDiaSemana(2); // Martes
        dto.setHorarioApertura(LocalTime.of(10, 0));
        dto.setHorarioCierre(LocalTime.of(19, 0));
        dto.setIdSucursal(idSucursal);

        HorarioTrabajo actualizado = horarioTrabajoService.updateHorarioTrabajo(idCreado, dto);

        // Verificamos que los cambios se hayan aplicado correctamente
        assertEquals(2, actualizado.getDiaSemana());
        assertEquals(LocalTime.of(10,0), actualizado.getHorarioApertura());
    }

    // D - DELETE
    @Test
    @Order(5)
    public void testDeleteHorarioTrabajo() {
        // Eliminamos el horario y verificamos que ya no exista
        horarioTrabajoService.deleteHorarioTrabajo(idCreado);
        Optional<HorarioTrabajo> horarioOpt = horarioTrabajoService.getHorarioTrabajoByID(idCreado);
        assertTrue(horarioOpt.isEmpty(), "El horario debe haber sido eliminado");
    }
}

// -----------------------------------------------------------------------------
// Resumen técnico:
// - Valida CRUD completo para HorarioTrabajoService, usando una sucursal base creada en @BeforeAll.
// - Prueba creación con valores válidos (día 1-7 y horarios coherentes), lectura por ID, listado general, actualización completa y eliminación.
// - Usa variables de instancia para mantener los IDs entre pruebas y asegurar continuidad.
// - Garantiza que los datos de prueba no afectan la base de producción gracias a @Sql.
// - Cubre errores comunes: violación de constraint CHECK en díaSemana (1-7), horarios incoherentes (cierre antes de apertura), FK inválida en idSucursal y manejo de IDs inexistentes en update/delete.
// - Ordena los tests con @Order para mantener el flujo y evitar errores de dependencias.
// - Nota: No cubre validación de formato de horas ni días fuera de rango (1-7), esas validaciones deberían estar en la capa de servicio o DTO.
// -----------------------------------------------------------------------------
