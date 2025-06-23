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

    // C - Crear un nuevo administrador
    public void crearAdministrador(Administrador administrador) {
        administrador.setTipoUsuario("ADMINISTRADOR");
        // Hashea la contraseña antes de guardar
        String password = new BCryptPasswordEncoder(10).encode(administrador.getPassUsuario());
        administrador.setPassUsuario(password);
        administradorRepository.save(administrador);
    }

    // R - Listar todos los administradores
    public List<Administrador> getAdministradors() {
        return administradorRepository.findAll();
    }

    // R - Buscar administrador por RUT
    public Optional<Administrador> getAdministradorByRut(Integer rut) {
        return administradorRepository.findById(rut);
    }

    // U - PUT: Actualización completa (sobrescribe todos los campos enviados, deja igual los no enviados)
    // Si un campo viene null, NO lo pisa (no borra datos), solo actualiza los no-null
    public void updateAdministrador(Administrador administrador, Integer rut) {
        Administrador adminExistente = administradorRepository.findById(rut)
                .orElseThrow(() -> new EntityNotFoundException("Administrador no encontrado"));

        // Sobrescribe TODOS los campos, incluso si son null
        adminExistente.setDvUsuario(administrador.getDvUsuario());
        adminExistente.setNomUsuario(administrador.getNomUsuario());
        adminExistente.setNom2Usuario(administrador.getNom2Usuario());
        adminExistente.setApellidoPaterno(administrador.getApellidoPaterno());
        adminExistente.setApellidoMaterno(administrador.getApellidoMaterno());
        adminExistente.setSexoUsuario(administrador.getSexoUsuario());
        adminExistente.setDirUsuario(administrador.getDirUsuario());
        adminExistente.setFechaNacimiento(administrador.getFechaNacimiento());
        adminExistente.setTelefonoUsuario(administrador.getTelefonoUsuario());
        adminExistente.setTel2Usuario(administrador.getTel2Usuario());
        adminExistente.setEmailUsuario(administrador.getEmailUsuario());

        // Contraseña: si es null, se borra (queda null en la base)
        if (administrador.getPassUsuario() != null) {
            String newPass = new BCryptPasswordEncoder(10).encode(administrador.getPassUsuario());
            adminExistente.setPassUsuario(newPass);
        } else {
            adminExistente.setPassUsuario(null);
        }

        administradorRepository.save(adminExistente);
    }


    // U/P - PATCH: Actualización parcial (solo actualiza los campos enviados, deja intactos los demás)
    // Es igual en este patrón, pero semánticamente se usa para updates parciales
    public void patchAdministrador(Administrador administrador, Integer rut) {
        Administrador administradorExistente = administradorRepository.findById(rut)
                .orElseThrow(() -> new EntityNotFoundException("Administrador no encontrado"));

        // Solo actualiza los campos que no son null (PATCH real)
        if (administrador.getDvUsuario() != null) administradorExistente.setDvUsuario(administrador.getDvUsuario());
        if (administrador.getNomUsuario() != null) administradorExistente.setNomUsuario(administrador.getNomUsuario());
        if (administrador.getNom2Usuario() != null) administradorExistente.setNom2Usuario(administrador.getNom2Usuario());
        if (administrador.getApellidoPaterno() != null) administradorExistente.setApellidoPaterno(administrador.getApellidoPaterno());
        if (administrador.getApellidoMaterno() != null) administradorExistente.setApellidoMaterno(administrador.getApellidoMaterno());
        if (administrador.getSexoUsuario() != null) administradorExistente.setSexoUsuario(administrador.getSexoUsuario());
        if (administrador.getDirUsuario() != null) administradorExistente.setDirUsuario(administrador.getDirUsuario());
        if (administrador.getFechaNacimiento() != null) administradorExistente.setFechaNacimiento(administrador.getFechaNacimiento());
        if (administrador.getTelefonoUsuario() != null) administradorExistente.setTelefonoUsuario(administrador.getTelefonoUsuario());
        if (administrador.getTel2Usuario() != null) administradorExistente.setTel2Usuario(administrador.getTel2Usuario());
        if (administrador.getEmailUsuario() != null) administradorExistente.setEmailUsuario(administrador.getEmailUsuario());
        if (administrador.getPassUsuario() != null) {
            String newPass = new BCryptPasswordEncoder(10).encode(administrador.getPassUsuario());
            administradorExistente.setPassUsuario(newPass);
        }

        administradorRepository.save(administradorExistente);
    }

    // D - Eliminar administrador por RUT
    public void deleteAdministrador(int rut) {
        administradorRepository.deleteById(rut);
    }
}
