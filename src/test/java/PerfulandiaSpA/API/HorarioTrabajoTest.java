package PerfulandiaSpA.API;

import PerfulandiaSpA.Entidades.HorarioTrabajo;
import PerfulandiaSpA.Servicio.HorarioTrabajoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class HorarioTrabajoTest {
    @Autowired
    HorarioTrabajoService horarioTrabajoService;

    private HorarioTrabajo HorarioBase() {
        HorarioTrabajo horario = new HorarioTrabajo();
        horario.setDiaSemana(3);
        horario.setHorarioApertura(Time.valueOf("09:00:00").toLocalTime());
        horario.setHorarioCierre(Time.valueOf("18:00:00").toLocalTime());
        String resultado = horarioTrabajoService.saveHorarioTrabajo(horario, 1);
        System.out.println("Resultado: " + resultado);
        return horario;
    }


    @Test
    @Order(1)
    // C crear horario de trabajo
    public void testSaveHorarioTrabajo() {
        HorarioTrabajo horario = new HorarioTrabajo();
        horario.setDiaSemana(3);
        horario.setHorarioApertura(Time.valueOf("09:00:00").toLocalTime());
        horario.setHorarioCierre(Time.valueOf("18:00:00").toLocalTime());
        String resultado = horarioTrabajoService.saveHorarioTrabajo(horario, 1);
        assertEquals("Horario de trabajo agregado con éxito", resultado);

        // sucursal no existente
        String failResult = horarioTrabajoService.saveHorarioTrabajo(HorarioBase(), 999);
        assertEquals("La sucursal no existe", failResult);
    }
    @Test
    @Order(2)
    // D eliminar horario
    public void testDeleteHorarioTrabajo() {
        HorarioBase();
        String resultado = horarioTrabajoService.deleteHorarioTrabajo(1);
        assertEquals("Horario de trabajo eliminado con éxito", resultado);

        // ya no existe
        String fallo = horarioTrabajoService.deleteHorarioTrabajo(1);
        assertEquals("Horario de trabajo no encontrado", fallo);

    }
    @Test
    @Order(3)
    // U actualizar horario
    public void testUpdateHorarioTrabajo() {
        HorarioTrabajo horario = HorarioBase();
        horario.setHorarioCierre(LocalTime.of(20, 0));
        String resultado = horarioTrabajoService.updateHorarioTrabajo(horario, 1);
        assertEquals("Horario de trabajo actualizado con éxito", resultado);

        // Validar
        horario.setId(999);
        String fail = horarioTrabajoService.updateHorarioTrabajo(horario, 1);
        assertEquals("Horario de trabajo no encontrado", fail);
    }

    @Test
    @Order(4)
    // R obtener todos los horarios
    public void testGetHorariosTrabajo() {
        HorarioBase();
        String resultado = horarioTrabajoService.getHorariosTrabajo();

        // Valdar
        assertNotNull(resultado);
        assertFalse(resultado.isBlank(), "El resultado no debería estar vacío");
        assertTrue(resultado.contains("Día semana") || resultado.contains("diaSemana"));

    }
    @Test
    @Order(5)
    // R obtener todos los horarios JSON
    public void testGetHorariosTrabajoJSON() {
        List<HorarioTrabajo> lista = horarioTrabajoService.getHorariosTrabajoJSON();
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    @Order(6)
    // R obtener horario por ID
    public void testGetHorarioById() {
        String resultado = horarioTrabajoService.getHorarioTrabajoById(1);
        assertNotNull(resultado);
        assertTrue(resultado.contains("Día semana"));

        // Validar
        String fail = horarioTrabajoService.getHorarioTrabajoById(999);
        assertEquals("Horario de trabajo no encontrado", fail);
    }
}