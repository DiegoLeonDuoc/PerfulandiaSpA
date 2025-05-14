package PerfulandiaSpA.Repositorio;

import PerfulandiaSpA.Entidades.Empleado;
import PerfulandiaSpA.Entidades.HorarioTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioTrabajoRepository extends JpaRepository<HorarioTrabajo, Integer> {
}
