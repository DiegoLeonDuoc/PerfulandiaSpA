package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Usuario;
import PerfulandiaSpA.Repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService2 {

    @Autowired
    UsuarioRepository usersRepository;

    public List<Usuario> getUsuarios() {
        return usersRepository.findAll();
    }

    public void addUsuario(Usuario user) {
        String password = new BCryptPasswordEncoder(10).encode(user.getPassUsuario());
        user.setPassUsuario(password);
        usersRepository.save(user);
    }

    public Optional<Usuario> getuser(int id) {
        return usersRepository.findById(id);
    }

    public void deleteUsuario(int id) {
        usersRepository.deleteById(id);
    }

    public void updateUsuario(int id, Usuario user) {
        Usuario us = usersRepository.findById(id).get();
        us.setRutUsuario(user.getRutUsuario());
        us.setDvUsuario(user.getDvUsuario());
        us.setDirUsuario(user.getDirUsuario());
        us.setSexoUsuario(user.getSexoUsuario());
        us.setTelefonoUsuario(user.getTelefonoUsuario());
        us.setFechaNacimiento(user.getFechaNacimiento());
        us.setTipoUsuario(user.getTipoUsuario());
        us.setNomUsuario(user.getNomUsuario());
        us.setPassUsuario(user.getPassUsuario());
        us.setEmailUsuario(user.getEmailUsuario());
        usersRepository.save(us);
    }
}