package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Entidades.Empleado;
import PerfulandiaSpA.Entidades.Usuario;
import PerfulandiaSpA.Repositorio.ClienteRepository;
import PerfulandiaSpA.Repositorio.EmpleadoRepository;
import PerfulandiaSpA.Repositorio.SucursalRepository;
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

    @Autowired
    SucursalRepository sucursalRepository;

    public String saveUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
//        if (usuario.getTipoUsuario().equals("CLIENTE")) {
//            Cliente cliente = new Cliente();
//            cliente.setEstadoCuenta("ACTIVO");
//            clienteRepository.save(cliente);
//        } else if (usuario.getTipoUsuario().equals("EMPLEADO")) {
//            Empleado empleado = new Empleado();
//            empleado.setSucursalAsociada(sucursalRepository.getById(1));
//            empleadoRepository.save(empleado);
//        }
        return "Usuario agregado con éxito";
    }

    public String deleteUsuario(int rut) {
        for (Usuario usuario : usuarioRepository.findAll()) {
            if (usuario.getRutUsuario() == rut) {
                usuarioRepository.delete(usuario);
                return "Usuario eliminado con éxito";
            }
        }
        return "Usuario no eliminado";
    }

    public String updateUsuario(Usuario usuario, int id) {
//        if (usuarioRepository.existsById(id)) {
//            usuarioRepository.save(usuario);
//            return "Usuario actualizado con éxito";
//        }
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
        output += "ID: " + usuario.getRutUsuario() + "\n";
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
//        if (usuario.getTipoUsuario().equals("CLIENTE")) {
//            for (Cliente cliente : clienteRepository.findAll()) {
//                if (cliente.getUsuarioAsociado().getId() == usuario.getId()) {
//                    output += "Estado de cuenta: " + cliente.getEstadoCuenta() + "\n";
//                    break;
//                }
//            }
//        } else if (usuario.getTipoUsuario().equals("EMPLEADO")) {
//            for (Empleado empleado : empleadoRepository.findAll()) {
//                if (empleado.getUsuarioAsociado().getId() == usuario.getRutUsuario()) {
//                    output += "ID Sucursal: " + empleado.getSucursalAsociada().getId() + "\n";
//                    output += "Nombre Sucursal: " + empleado.getSucursalAsociada().getNombreSucursal() + "\n";
//                    output += "Dirección Sucursal: " + empleado.getSucursalAsociada().getDireccionSucursal() + "\n";
//                    break;
//                }
//            }
//        }
        output += "\n";
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
