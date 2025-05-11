package PerfulandiaSpA.Repositorio;

import PerfulandiaSpA.Entidades.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
}
