package PerfulandiaSpA.Repositorio;

import PerfulandiaSpA.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
