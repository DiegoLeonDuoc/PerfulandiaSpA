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

    // C - Crear cliente con contraseña hasheada
    public void crearCliente(Cliente cliente) {
        cliente.setTipoUsuario("CLIENTE");
        cliente.setEstadoCuenta("ACTIVO");
        String password = new BCryptPasswordEncoder(10).encode(cliente.getPassUsuario());
        cliente.setPassUsuario(password);
        clienteRepository.save(cliente);
    }

    // R - Obtener todos los clientes
    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    // R - Buscar cliente por RUT
    public Optional<Cliente> getClienteByRut(Integer rut) {
        return clienteRepository.findById(rut);
    }

    // U - PUT: Actualización completa (sobrescribe todos los campos)
    public void updateCliente(Cliente cliente, Integer rut) {
        Cliente clienteExistente = clienteRepository.findById(rut)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        // Sobrescribe TODOS los campos, incluso si son null
        clienteExistente.setDvUsuario(cliente.getDvUsuario());
        clienteExistente.setNomUsuario(cliente.getNomUsuario());
        clienteExistente.setNom2Usuario(cliente.getNom2Usuario());
        clienteExistente.setApellidoPaterno(cliente.getApellidoPaterno());
        clienteExistente.setApellidoMaterno(cliente.getApellidoMaterno());
        clienteExistente.setSexoUsuario(cliente.getSexoUsuario());
        clienteExistente.setDirUsuario(cliente.getDirUsuario());
        clienteExistente.setFechaNacimiento(cliente.getFechaNacimiento());
        clienteExistente.setTelefonoUsuario(cliente.getTelefonoUsuario());
        clienteExistente.setTel2Usuario(cliente.getTel2Usuario());
        clienteExistente.setEmailUsuario(cliente.getEmailUsuario());
        clienteExistente.setEstadoCuenta(cliente.getEstadoCuenta());

        // Contraseña: si es null, se borra (queda null en la base)
        if (cliente.getPassUsuario() != null) {
            String newPass = new BCryptPasswordEncoder(10).encode(cliente.getPassUsuario());
            clienteExistente.setPassUsuario(newPass);
        } else {
            clienteExistente.setPassUsuario(null);
        }

        clienteRepository.save(clienteExistente);
    }

    // U - Actualización parcial (PATCH)
    public void patchCliente(Cliente cliente, Integer rut) {
        Cliente clienteExistente = clienteRepository.findById(rut)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        // Actualiza solo campos proporcionados
        if (cliente.getDvUsuario() != null) clienteExistente.setDvUsuario(cliente.getDvUsuario());
        if (cliente.getNomUsuario() != null) clienteExistente.setNomUsuario(cliente.getNomUsuario());
        if (cliente.getNom2Usuario() != null) clienteExistente.setNom2Usuario(cliente.getNom2Usuario());
        if (cliente.getApellidoPaterno() != null) clienteExistente.setApellidoPaterno(cliente.getApellidoPaterno());
        if (cliente.getApellidoMaterno() != null) clienteExistente.setApellidoMaterno(cliente.getApellidoMaterno());
        if (cliente.getSexoUsuario() != null) clienteExistente.setSexoUsuario(cliente.getSexoUsuario());
        if (cliente.getDirUsuario() != null) clienteExistente.setDirUsuario(cliente.getDirUsuario());
        if (cliente.getFechaNacimiento() != null) clienteExistente.setFechaNacimiento(cliente.getFechaNacimiento());
        if (cliente.getTelefonoUsuario() != null) clienteExistente.setTelefonoUsuario(cliente.getTelefonoUsuario());
        if (cliente.getTel2Usuario() != null) clienteExistente.setTel2Usuario(cliente.getTel2Usuario());
        if (cliente.getEmailUsuario() != null) clienteExistente.setEmailUsuario(cliente.getEmailUsuario());
        if (cliente.getEstadoCuenta() != null) clienteExistente.setEstadoCuenta(cliente.getEstadoCuenta());
        if (cliente.getPassUsuario() != null) {
            String newPass = new BCryptPasswordEncoder(10).encode(cliente.getPassUsuario());
            clienteExistente.setPassUsuario(newPass);
        }

        clienteRepository.save(clienteExistente);
    }

    // D - Eliminar cliente
    public void deleteCliente(int rut) {
        clienteRepository.deleteById(rut);
    }
}
