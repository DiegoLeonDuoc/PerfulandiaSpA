package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Usuario;
import PerfulandiaSpA.Repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public String saveUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
        return "Usuario agregado con éxito";
    }

    public String deleteUsuario(int id) {
        //return String.valueOf(usuarioRepository.existsById(id));
        for (Usuario usuario : usuarioRepository.findAll()) {
            if (usuario.getId() == id) {
                usuarioRepository.delete(usuario);
                return "Usuario eliminado con éxito";
            }
        }
        return "Usuario no eliminado";

    }

    public String updateUsuario(Usuario usuario, int id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.save(usuario);
            return "Usuario actualizado con éxito";
        }
        return "Usuario no encontrado";
    }

    public String getUsuarios() {
        String output = "";
        for (Usuario usuario : usuarioRepository.findAll()) {
            output = datosUsuario(output, usuario);
        }

        if (output.isEmpty()) {
            //return usuarioRepository.findAll().toString();
            return "No hay usuarios registrados";
        } else {
            return output;
        }
    }

    public List<Usuario> getUsuariosJSON() {

        return usuarioRepository.findAll();
    }

    private String datosUsuario(String output, Usuario usuario) {
        output += "ID: " + usuario.getId() + "\n";
        output += "RUT: " + usuario.getRutUsuario() + "-" + usuario.getDvUsuario() + "\n";
        output += "Nombre completo: " + usuario.getNomUsuario() + " ";
        if (usuario.getNom2Usuario() != null) {
            output += usuario.getNom2Usuario() + " ";
        }
        output += usuario.getApellidoPaterno();
        if (usuario.getNom2Usuario() != null) {
            output += " " + usuario.getApellidoMaterno() + "\n";
        } else {
            output += "\n";
        }
        output += "Sexo: " + usuario.getSexoUsuario() + "\n";
        output += "Fecha de nacimiento: " + usuario.getFechaNacimiento().toString() + "\n";
        output += "Dirección: " + usuario.getDirUsuario() + "\n";
        output += "Número de teléfono: +569" + usuario.getTelefonoUsuario() + "\n";
        if (usuario.getTel2Usuario() != null) {
            output += "Teléfono extra: +569" + usuario.getTel2Usuario() + "\n";
        }
        output += "Email: " + usuario.getEmailUsuario() + "\n";
        return output;
    }

    public String getUsuarioByRut(int id) {
        String output = "";
        if (usuarioRepository.existsById(id)) {
            Usuario usuario = usuarioRepository.findById(id).get();
            output = datosUsuario(output, usuario);
            return output;
        }else{
            return "Usuario no encontrado";
        }
    }



}
