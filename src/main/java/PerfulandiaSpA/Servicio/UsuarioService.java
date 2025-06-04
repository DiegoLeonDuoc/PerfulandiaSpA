package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Entidades.Empleado;
import PerfulandiaSpA.Entidades.Usuario;
import PerfulandiaSpA.Repositorio.ClienteRepository;
import PerfulandiaSpA.Repositorio.EmpleadoRepository;
import PerfulandiaSpA.Repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EmpleadoRepository empleadoRepository;

    // C
//    public String crearUsuario(Usuario usuario) {
//        if (usuarioRepository.existsById(usuario.getRutUsuario())) {
//            return "Ya existe un usuario con ese rut";
//        } else {
//            String password = new BCryptPasswordEncoder(10).encode(usuario.getPassUsuario());
//            usuario.setPassUsuario(password);
//            usuarioRepository.save(usuario);
//            return "Usuario agregado con éxito";
//        }
//    }

    // R
    public String getUsuarios() {
        String output = "";
        for (Usuario usuario : usuarioRepository.findAll()) {
            output = datosUsuario(output, usuario);
        }
        if (output.isEmpty()) {
            return "No hay usuarios registrados";
        } else {
            return output;
        }
    }

    public String getUsuarioByRut(int id) {
        String output = "";
        if (usuarioRepository.existsById(id)) {
            Usuario usuario = usuarioRepository.findById(id).get();
            output = datosUsuario(output, usuario);
            return output;
        }else{
            return "Cliente no encontrado";
        }
    }

    public List<Usuario> getUsuariosJSON() {
        return usuarioRepository.findAll();
    }

//    public String updateUsuario(Usuario usuario, int rut) {
//        if (usuarioRepository.existsById(rut)) {
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
//            return "Usuario actualizado con éxito";
//        } else {
//            return "Usuario no encontrado";
//        }
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

    public String deleteUsuario(int rut) {
        for (Usuario usuario : usuarioRepository.findAll()) {
            if (usuario.getRutUsuario() == rut) {
                usuarioRepository.delete(usuario);
                return "Usuario eliminado con éxito";
            }
        }
        return "Usuario no eliminado";
    }

    private String datosUsuario(String output, Usuario usuario) {
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
        if (usuario.getTipoUsuario().equals("CLIENTE")) {
            for (Cliente cliente : clienteRepository.findAll()) {
                if (cliente.getRutUsuario() == usuario.getRutUsuario()) {
                    output += "Estado de cuenta: " + cliente.getEstadoCuenta() + "\n";
                    break;
                }
            }
        } else if (usuario.getTipoUsuario().equals("EMPLEADO")) {
            for (Empleado empleado : empleadoRepository.findAll()) {
                if (empleado.getRutUsuario() == usuario.getRutUsuario()) {
                    output += "ID Sucursal: " + empleado.getSucursalAsociada().getId() + "\n";
                    output += "Nombre Sucursal: " + empleado.getSucursalAsociada().getNombreSucursal() + "\n";
                    output += "Dirección Sucursal: " + empleado.getSucursalAsociada().getDireccionSucursal() + "\n";
                    break;
                }
            }
        }
        output += "\n";
        return output;
    }




}
