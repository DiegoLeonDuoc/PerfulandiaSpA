package PerfulandiaSpA.Repositorio;

import PerfulandiaSpA.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    //List<Usuario> getUsuarioByRutUs(int rutUs);
}
