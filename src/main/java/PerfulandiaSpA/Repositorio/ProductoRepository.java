package PerfulandiaSpA.Repositorio;

import PerfulandiaSpA.Entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
