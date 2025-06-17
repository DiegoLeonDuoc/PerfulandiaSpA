package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Cliente;

import PerfulandiaSpA.Repositorio.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    // C
    public void crearCliente(Cliente cliente) {
        cliente.setTipoUsuario("CLIENTE");
        cliente.setEstadoCuenta("ACTIVO");
        String password = new BCryptPasswordEncoder(10).encode(cliente.getPassUsuario());
        cliente.setPassUsuario(password);
        clienteRepository.save(cliente);
    }

    // R
    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> getClienteByRut(Integer rut) {
        return clienteRepository.findById(rut);
    }

    // U
    public void updateCliente(Cliente cliente, Integer rut) {
       if (clienteRepository.existsById(rut)) {
           cliente.setRutUsuario(cliente.getRutUsuario());
           cliente.setDvUsuario(cliente.getDvUsuario());
           cliente.setNomUsuario(cliente.getNomUsuario());
           cliente.setNom2Usuario(cliente.getNom2Usuario());
           cliente.setApellidoPaterno(cliente.getApellidoPaterno());
           cliente.setApellidoMaterno(cliente.getApellidoMaterno());
           cliente.setSexoUsuario(cliente.getSexoUsuario());
           cliente.setDirUsuario(cliente.getDirUsuario());
           cliente.setFechaNacimiento(cliente.getFechaNacimiento());
           cliente.setTelefonoUsuario(cliente.getTelefonoUsuario());
           cliente.setTel2Usuario(cliente.getTel2Usuario());
           cliente.setEmailUsuario(cliente.getEmailUsuario());
           cliente.setEstadoCuenta(cliente.getEstadoCuenta());
           String newPass = new BCryptPasswordEncoder(10).encode(cliente.getPassUsuario());
           cliente.setPassUsuario(newPass);
           clienteRepository.save(cliente);
       } else {
           throw new EntityNotFoundException("Cliente no encontrado");
       }
    }

    // U/P
    public void patchCliente(Cliente cliente, Integer rut) {
        if (clienteRepository.existsById(rut)) {
            Cliente clienteExistente = clienteRepository.findById(rut)
                    .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

            clienteExistente.setRutUsuario(rut);
            if (cliente.getDvUsuario() != null) {
                clienteExistente.setDvUsuario(cliente.getDvUsuario());
            }
            if (cliente.getNomUsuario() != null) {
                clienteExistente.setNomUsuario(cliente.getNomUsuario());
            }
            if (cliente.getNom2Usuario() != null) {
                clienteExistente.setNom2Usuario(cliente.getNom2Usuario());
            }
            if (cliente.getApellidoPaterno() != null) {
                clienteExistente.setApellidoPaterno(cliente.getApellidoPaterno());
            }
            if (cliente.getApellidoMaterno() != null) {
                clienteExistente.setApellidoMaterno(cliente.getApellidoMaterno());
            }
            if (cliente.getSexoUsuario() != null) {
                clienteExistente.setSexoUsuario(cliente.getSexoUsuario());
            }
            if (cliente.getDirUsuario() != null) {
                clienteExistente.setDirUsuario(cliente.getDirUsuario());
            }
            if (cliente.getFechaNacimiento() != null) {
                clienteExistente.setFechaNacimiento(cliente.getFechaNacimiento());
            }
            if (cliente.getTelefonoUsuario() != null) {
                clienteExistente.setTelefonoUsuario(cliente.getTelefonoUsuario());
            }
            if (cliente.getTel2Usuario() != null) {
                clienteExistente.setTel2Usuario(cliente.getTel2Usuario());
            }
            if (cliente.getEmailUsuario() != null) {
                clienteExistente.setEmailUsuario(cliente.getEmailUsuario());
            }
            if (cliente.getEstadoCuenta() != null) {
                clienteExistente.setEstadoCuenta(cliente.getEstadoCuenta());
            }
            if (cliente.getPassUsuario() != null) {
                String newPass = new BCryptPasswordEncoder(10).encode(cliente.getPassUsuario());
                clienteExistente.setPassUsuario(newPass);
            }

            clienteRepository.save(clienteExistente);
        } else {
            throw new EntityNotFoundException("Cliente no encontrado");
        }
    }

    // D
    public void deleteCliente(int rut) {
        clienteRepository.deleteById(rut);
    }
}
