package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Administrador;
import PerfulandiaSpA.Repositorio.AdministradorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService {

    @Autowired
    AdministradorRepository administradorRepository;

    // C
    public void crearAdministrador(Administrador administrador) {
        administrador.setTipoUsuario("ADMINISTRADOR");
        String password = new BCryptPasswordEncoder(10).encode(administrador.getPassUsuario());
        administrador.setPassUsuario(password);
        administradorRepository.save(administrador);
    }

    // R
    public List<Administrador> getAdministradors() {
        return administradorRepository.findAll();
    }

    public Optional<Administrador> getAdministradorByRut(Integer rut) {
        return administradorRepository.findById(rut);
    }

    // U
    public void updateAdministrador(Administrador administrador, Integer rut) {
        if (administradorRepository.existsById(rut)) {
            administrador.setRutUsuario(administrador.getRutUsuario());
            administrador.setDvUsuario(administrador.getDvUsuario());
            administrador.setNomUsuario(administrador.getNomUsuario());
            administrador.setNom2Usuario(administrador.getNom2Usuario());
            administrador.setApellidoPaterno(administrador.getApellidoPaterno());
            administrador.setApellidoMaterno(administrador.getApellidoMaterno());
            administrador.setSexoUsuario(administrador.getSexoUsuario());
            administrador.setDirUsuario(administrador.getDirUsuario());
            administrador.setFechaNacimiento(administrador.getFechaNacimiento());
            administrador.setTelefonoUsuario(administrador.getTelefonoUsuario());
            administrador.setTel2Usuario(administrador.getTel2Usuario());
            administrador.setEmailUsuario(administrador.getEmailUsuario());
            String newPass = new BCryptPasswordEncoder(10).encode(administrador.getPassUsuario());
            administrador.setPassUsuario(newPass);
            administradorRepository.save(administrador);
        } else {
            throw new EntityNotFoundException("Administrador no encontrado");
        }
    }

    // U/P
    public void patchAdministrador(Administrador administrador, Integer rut) {
        if (administradorRepository.existsById(rut)) {
            Administrador administradorExistente = administradorRepository.findById(rut)
                    .orElseThrow(() -> new EntityNotFoundException("Administrador no encontrado"));

            administradorExistente.setRutUsuario(rut);
            if (administrador.getDvUsuario() != null) {
                administradorExistente.setDvUsuario(administrador.getDvUsuario());
            }
            if (administrador.getNomUsuario() != null) {
                administradorExistente.setNomUsuario(administrador.getNomUsuario());
            }
            if (administrador.getNom2Usuario() != null) {
                administradorExistente.setNom2Usuario(administrador.getNom2Usuario());
            }
            if (administrador.getApellidoPaterno() != null) {
                administradorExistente.setApellidoPaterno(administrador.getApellidoPaterno());
            }
            if (administrador.getApellidoMaterno() != null) {
                administradorExistente.setApellidoMaterno(administrador.getApellidoMaterno());
            }
            if (administrador.getSexoUsuario() != null) {
                administradorExistente.setSexoUsuario(administrador.getSexoUsuario());
            }
            if (administrador.getDirUsuario() != null) {
                administradorExistente.setDirUsuario(administrador.getDirUsuario());
            }
            if (administrador.getFechaNacimiento() != null) {
                administradorExistente.setFechaNacimiento(administrador.getFechaNacimiento());
            }
            if (administrador.getTelefonoUsuario() != null) {
                administradorExistente.setTelefonoUsuario(administrador.getTelefonoUsuario());
            }
            if (administrador.getTel2Usuario() != null) {
                administradorExistente.setTel2Usuario(administrador.getTel2Usuario());
            }
            if (administrador.getEmailUsuario() != null) {
                administradorExistente.setEmailUsuario(administrador.getEmailUsuario());
            }
            if (administrador.getPassUsuario() != null) {
                String newPass = new BCryptPasswordEncoder(10).encode(administrador.getPassUsuario());
                administradorExistente.setPassUsuario(newPass);
            }

            administradorRepository.save(administradorExistente);
        } else {
            throw new EntityNotFoundException("Administrador no encontrado");
        }
    }

    // D
    public void deleteAdministrador(int rut) {
        administradorRepository.deleteById(rut);
    }
}
