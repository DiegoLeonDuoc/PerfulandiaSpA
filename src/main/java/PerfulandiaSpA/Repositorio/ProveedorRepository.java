package PerfulandiaSpA.Repositorio;

import PerfulandiaSpA.Entidades.Empleado;
import PerfulandiaSpA.Entidades.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
}
