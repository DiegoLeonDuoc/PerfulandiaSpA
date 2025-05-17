package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Administrador;
import PerfulandiaSpA.Repositorio.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Service
public class AdministradorService {

    @Autowired
    AdministradorRepository administradorRepository;
    // C
    public String crearAdministrador(Administrador administrador) {
        if (administradorRepository.existsById(administrador.getRutUsuario())) {
            return "Ya existe un usuario con ese rut";
        } else {
            administrador.setTipoUsuario("ADMINISTRADOR");
            String password = new BCryptPasswordEncoder(10).encode(administrador.getPassUsuario());
            administrador.setPassUsuario(password);
            administradorRepository.save(administrador);
            return "Administrador agregado con éxito";
        }
    }
    // R
    public String getAdministradores() {
        String output = "";
        for (Administrador administrador : administradorRepository.findAll()) {
            output = datosAdministrador(output, administrador);
        }
        if (output.isEmpty()) {
            return "No hay administradors registrados";
        } else {
            return output;
        }
    }

    public String getAdministradorByRut(int id) {
        String output = "";
        if (administradorRepository.existsById(id)) {
            Administrador administrador = administradorRepository.findById(id).get();
            output = datosAdministrador(output, administrador);
            return output;
        }else{
            return "Administrador no encontrado";
        }
    }

    public List<Administrador> getAdministradorsJSON() {
        return administradorRepository.findAll();
    }

    // U

    public String updateAdministrador(Administrador administrador, int rut) {
        if (administradorRepository.existsById(rut)) {
            Administrador administradorUpdate = administradorRepository.findById(rut).get();
            administradorUpdate.setRutUsuario(administrador.getRutUsuario());
            administradorUpdate.setDvUsuario(administrador.getDvUsuario());
            administradorUpdate.setNomUsuario(administrador.getNomUsuario());
            administradorUpdate.setNom2Usuario(administrador.getNom2Usuario());
            administradorUpdate.setApellidoPaterno(administrador.getApellidoPaterno());
            administradorUpdate.setApellidoMaterno(administrador.getApellidoMaterno());
            administradorUpdate.setSexoUsuario(administrador.getSexoUsuario());
            administradorUpdate.setDirUsuario(administrador.getDirUsuario());
            administradorUpdate.setFechaNacimiento(administrador.getFechaNacimiento());
            administradorUpdate.setTelefonoUsuario(administrador.getTelefonoUsuario());
            administradorUpdate.setTel2Usuario(administrador.getTel2Usuario());
            administradorUpdate.setEmailUsuario(administrador.getEmailUsuario());
            String newPass = new BCryptPasswordEncoder(10).encode(administrador.getPassUsuario());
            administradorUpdate.setPassUsuario(newPass);
            administradorRepository.save(administradorUpdate);
            return "Administrador actualizado con éxito";
        } else {
            return "Administrador no encontrado";
        }
    }

    // U/P
    public String parcharAdministrador(Administrador administrador, int rut) {
        if (administradorRepository.existsById(rut)) {
            Administrador administradorParchado = administradorRepository.findById(rut).get();
            if (administrador.getNomUsuario() != null) {
                administradorParchado.setNomUsuario(administrador.getNomUsuario());
            }
            if (administrador.getNom2Usuario() != null) {
                administradorParchado.setNom2Usuario(administrador.getNom2Usuario());
            }
            if (administrador.getApellidoPaterno() != null) {
                administradorParchado.setApellidoPaterno(administrador.getApellidoPaterno());
            }
            if (administrador.getApellidoMaterno() != null) {
                administradorParchado.setApellidoMaterno(administrador.getApellidoMaterno());
            }
            if (administrador.getSexoUsuario() == 'M' || administrador.getSexoUsuario() == 'F') {
                administradorParchado.setSexoUsuario(administrador.getSexoUsuario());
            }
            if (administrador.getFechaNacimiento() != null) {
                administradorParchado.setFechaNacimiento(administrador.getFechaNacimiento());
            }
            if (administrador.getTelefonoUsuario() != null) {
                administradorParchado.setTelefonoUsuario(administrador.getTelefonoUsuario());
            }
            if (administrador.getTel2Usuario() != null) {
                administradorParchado.setTel2Usuario(administrador.getTel2Usuario());
            }
            if (administrador.getEmailUsuario() != null) {
                administradorParchado.setEmailUsuario(administrador.getEmailUsuario());
            }
            if (administrador.getPassUsuario() != null) {
                String newPass = new BCryptPasswordEncoder(10).encode(administrador.getPassUsuario());
                administradorParchado.setPassUsuario(newPass);
            }
            administradorRepository.save(administradorParchado);
            return "Administrador actualizado con éxito";
        }
        return "Administrador no encontrado";
    }

    // D

    public String deleteAdministrador(int rut) {
        for (Administrador administrador : administradorRepository.findAll()) {
            if (administrador.getRutUsuario() == rut) {
                administradorRepository.delete(administrador);
                return "Administrador eliminado con éxito";
            }
        }
        return "Administrador no existente";
    }

    // Funciones no CRUD

    private String datosAdministrador(String output, Administrador administrador) {
        output += "ID: " + administrador.getRutUsuario() + "\n";
        output += "RUT: " + administrador.getRutUsuario() + "-" + administrador.getDvUsuario() + "\n";
        output += "Nombre completo: " + administrador.getNomUsuario() + " ";
        if (administrador.getNom2Usuario() != null) {
            output += administrador.getNom2Usuario() + " ";
        }
        output += administrador.getApellidoPaterno();
        if (administrador.getNom2Usuario() != null) {
            output += " " + administrador.getApellidoMaterno() + "\n";
        } else {
            output += "\n";
        }
        output += "Sexo: " + administrador.getSexoUsuario() + "\n";
        output += "Fecha de nacimiento: " + administrador.getFechaNacimiento().toString() + "\n";
        output += "Dirección: " + administrador.getDirUsuario() + "\n";
        output += "Número de teléfono: +56" + administrador.getTelefonoUsuario() + "\n";
        if (administrador.getTel2Usuario() != null) {
            output += "Teléfono extra: +56" + administrador.getTel2Usuario() + "\n";
        }
        output += "Email: " + administrador.getEmailUsuario() + "\n";
        output += "\n";
        return output;
    }
}
