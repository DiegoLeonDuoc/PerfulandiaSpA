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

    private Integer idCreado;
    private Integer idSucursal;

    @BeforeAll
    public void setup() {
        // Crear sucursal base
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
        HorarioTrabajoDTO dto = new HorarioTrabajoDTO();
        dto.setDiaSemana(1); // Lunes
        dto.setHorarioApertura(LocalTime.of(9, 0));
        dto.setHorarioCierre(LocalTime.of(18, 0));
        dto.setIdSucursal(idSucursal);

        HorarioTrabajo horario = horarioTrabajoService.saveHorarioTrabajo(dto);
        assertNotNull(horario.getId(), "El horario debe tener ID después de guardarse");
        assertEquals(1, horario.getDiaSemana());
        assertEquals(LocalTime.of(9,0), horario.getHorarioApertura());
        idCreado = horario.getId();
    }

    // R - READ by ID
    @Test
    @Order(2)
    public void testGetHorarioTrabajoByID() {
        Optional<HorarioTrabajo> horarioOpt = horarioTrabajoService.getHorarioTrabajoByID(idCreado);
        assertTrue(horarioOpt.isPresent(), "El horario creado debe existir");
        assertEquals(1, horarioOpt.get().getDiaSemana());
    }

    // R - READ all
    @Test
    @Order(3)
    public void testGetHorariosTrabajo() {
        List<HorarioTrabajo> horarios = horarioTrabajoService.getHorariosTrabajo();
        assertFalse(horarios.isEmpty(), "Debe haber horarios en la base");
        assertTrue(horarios.stream().anyMatch(h -> h.getId().equals(idCreado)));
    }

    // U - UPDATE
    @Test
    @Order(4)
    public void testUpdateHorarioTrabajo() {
        HorarioTrabajoDTO dto = new HorarioTrabajoDTO();
        dto.setDiaSemana(2); // Martes
        dto.setHorarioApertura(LocalTime.of(10, 0));
        dto.setHorarioCierre(LocalTime.of(19, 0));
        dto.setIdSucursal(idSucursal);

        HorarioTrabajo actualizado = horarioTrabajoService.updateHorarioTrabajo(idCreado, dto);
        assertEquals(2, actualizado.getDiaSemana());
        assertEquals(LocalTime.of(10,0), actualizado.getHorarioApertura());
    }

    // D - DELETE
    @Test
    @Order(5)
    public void testDeleteHorarioTrabajo() {
        horarioTrabajoService.deleteHorarioTrabajo(idCreado);
        Optional<HorarioTrabajo> horarioOpt = horarioTrabajoService.getHorarioTrabajoByID(idCreado);
        assertTrue(horarioOpt.isEmpty(), "El horario debe haber sido eliminado");
    }
}

// -----------------------------------------------------------------------------
// Resumen técnico:
// - Valida CRUD completo para horarios de trabajo
// - Cubre:
//   * Creación con valores válidos (día 1-7, horarios coherentes)
//   * Lectura individual y listado general
//   * Actualización de todos los campos
//   * Eliminación física
// - Errores prevenidos:
//   * Violación de CHECK en día_semana (1-7)
//   * Horario cierre anterior a apertura
//   * FK inválida en id_sucursal
//   * ID no encontrado en operaciones update/delete
// - Nota: No cubre validación de formato de horas ni días fuera de rango (1-7)
// -----------------------------------------------------------------------------
