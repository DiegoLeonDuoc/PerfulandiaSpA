package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Usuario;
import PerfulandiaSpA.Repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService2 {

    @Autowired
    UsuarioRepository usuarioRepository;

    // C
//    public void crearUsuario(Usuario usuario) {
//        String password = new BCryptPasswordEncoder(10).encode(usuario.getPassUsuario());
//        usuario.setPassUsuario(password);
//        usuarioRepository.save(usuario);
//    }

    // R
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioByRut(int rut) {
        return usuarioRepository.findById(rut);
    }

    //U
//    public void updateUsuario(int id, Usuario usuario) {
//        Usuario us = usuarioRepository.findById(id).get();
//        us.setRutUsuario(usuario.getRutUsuario());
//        us.setDvUsuario(usuario.getDvUsuario());
//        us.setDirUsuario(usuario.getDirUsuario());
//        us.setSexoUsuario(usuario.getSexoUsuario());
//        us.setTelefonoUsuario(usuario.getTelefonoUsuario());
//        us.setFechaNacimiento(usuario.getFechaNacimiento());
//        us.setTipoUsuario(usuario.getTipoUsuario());
//        us.setNomUsuario(usuario.getNomUsuario());
//        us.setPassUsuario(usuario.getPassUsuario());
//        us.setEmailUsuario(usuario.getEmailUsuario());
//        usuarioRepository.save(us);
//    }

    //    public void updateUsuario(Usuario usuario, int rut) {
//            Usuario usuarioUpdate = usuarioRepository.findById(rut).get();
//            usuarioUpdate.setRutUsuario(usuario.getRutUsuario());
//            usuarioUpdate.setDvUsuario(usuario.getDvUsuario());
//            usuarioUpdate.setNomUsuario(usuario.getNomUsuario());
//            usuarioUpdate.setNom2Usuario(usuario.getNom2Usuario());
//            usuarioUpdate.setApellidoPaterno(usuario.getApellidoPaterno());
//            usuarioUpdate.setApellidoMaterno(usuario.getApellidoMaterno());
//            usuarioUpdate.setSexoUsuario(usuario.getSexoUsuario());
//            usuarioUpdate.setDirUsuario(usuario.getDirUsuario());
//            usuarioUpdate.setFechaNacimiento(usuario.getFechaNacimiento());
//            usuarioUpdate.setTelefonoUsuario(usuario.getTelefonoUsuario());
//            usuarioUpdate.setTel2Usuario(usuario.getTel2Usuario());
//            usuarioUpdate.setEmailUsuario(usuario.getEmailUsuario());
//            String newPass = new BCryptPasswordEncoder(10).encode(usuario.getPassUsuario());
//            usuarioUpdate.setPassUsuario(newPass);
//            usuarioRepository.save(usuarioUpdate);
//    }

//    public String parcharUsuario(Usuario usuario, int rut) {
//        if (usuarioRepository.existsById(rut)) {
//            Usuario usuarioParchado = usuarioRepository.findById(rut).get();
//            if (usuario.getNomUsuario() != null) {
//                usuarioParchado.setNomUsuario(usuario.getNomUsuario());
//            }
//            if (usuario.getNom2Usuario() != null) {
//                usuarioParchado.setNom2Usuario(usuario.getNom2Usuario());
//            }
//            if (usuario.getApellidoPaterno() != null) {
//                usuarioParchado.setApellidoPaterno(usuario.getApellidoPaterno());
//            }
//            if (usuario.getApellidoMaterno() != null) {
//                usuarioParchado.setApellidoMaterno(usuario.getApellidoMaterno());
//            }
//            if (usuario.getSexoUsuario() == 'M' || usuario.getSexoUsuario() == 'F') {
//                usuarioParchado.setSexoUsuario(usuario.getSexoUsuario());
//            }
//            if (usuario.getFechaNacimiento() != null) {
//                usuarioParchado.setFechaNacimiento(usuario.getFechaNacimiento());
//            }
//            if (usuario.getTelefonoUsuario() != null) {
//                usuarioParchado.setTelefonoUsuario(usuario.getTelefonoUsuario());
//            }
//            if (usuario.getTel2Usuario() != null) {
//                usuarioParchado.setTel2Usuario(usuario.getTel2Usuario());
//            }
//            if (usuario.getEmailUsuario() != null) {
//                usuarioParchado.setEmailUsuario(usuario.getEmailUsuario());
//            }
//            if (usuario.getPassUsuario() != null) {
//                String newPass = new BCryptPasswordEncoder(10).encode(usuario.getPassUsuario());
//                usuarioParchado.setPassUsuario(newPass);
//            }
//            usuarioRepository.save(usuarioParchado);
//            return "Usuario actualizado con éxito";
//        }
//        return "Usuario no encontrado";
//    }

    // D
    public void deleteUsuario(int rut) {
        usuarioRepository.deleteById(rut);
    }

}