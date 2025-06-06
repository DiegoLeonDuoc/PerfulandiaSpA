package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Service
public class wClienteService {

    @Autowired
    ClienteRepository clienteRepository;
    // C
    public String crearCliente(Cliente cliente) {
        if (clienteRepository.existsById(cliente.getRutUsuario())) {
            return "Ya existe un usuario con ese rut";
        } else {
            cliente.setTipoUsuario("CLIENTE");
            cliente.setEstadoCuenta("ACTIVO");
            String password = new BCryptPasswordEncoder(10).encode(cliente.getPassUsuario());
            cliente.setPassUsuario(password);
            clienteRepository.save(cliente);
            return "Cliente agregado con éxito";
        }
    }
    // R
    public String getClientes() {
        String output = "";
        for (Cliente cliente : clienteRepository.findAll()) {
            output = datosCliente(output, cliente);
        }
        if (output.isEmpty()) {
            return "No hay clientes registrados";
        } else {
            return output;
        }
    }

    public String getClienteByRut(Integer rut) {
        String output = "";
        if (clienteRepository.existsById(rut)) {
            Cliente cliente = clienteRepository.findById(rut).get();
            output = datosCliente(output, cliente);
            return output;
        }else{
            return "Cliente no encontrado";
        }
    }

    public List<Cliente> getClientesJSON() {
        return clienteRepository.findAll();
    }

    // U

    public String updateCliente(Cliente cliente, Integer rut) {
        if (clienteRepository.existsById(rut)) {
            Cliente clienteUpdate = clienteRepository.findById(rut).get();
            clienteUpdate.setRutUsuario(cliente.getRutUsuario());
            clienteUpdate.setDvUsuario(cliente.getDvUsuario());
            clienteUpdate.setNomUsuario(cliente.getNomUsuario());
            clienteUpdate.setNom2Usuario(cliente.getNom2Usuario());
            clienteUpdate.setApellidoPaterno(cliente.getApellidoPaterno());
            clienteUpdate.setApellidoMaterno(cliente.getApellidoMaterno());
            clienteUpdate.setSexoUsuario(cliente.getSexoUsuario());
            clienteUpdate.setDirUsuario(cliente.getDirUsuario());
            clienteUpdate.setFechaNacimiento(cliente.getFechaNacimiento());
            clienteUpdate.setTelefonoUsuario(cliente.getTelefonoUsuario());
            clienteUpdate.setTel2Usuario(cliente.getTel2Usuario());
            clienteUpdate.setEmailUsuario(cliente.getEmailUsuario());
            clienteUpdate.setEstadoCuenta(cliente.getEstadoCuenta());
            String newPass = new BCryptPasswordEncoder(10).encode(cliente.getPassUsuario());
            clienteUpdate.setPassUsuario(newPass);
            clienteRepository.save(clienteUpdate);
            return "Cliente actualizado con éxito";
        } else {
            return "Cliente no encontrado";
        }
    }

    // U/P
    public String parcharCliente(Cliente cliente, Integer rut) {
        if (clienteRepository.existsById(rut)) {
            Cliente clienteParchado = clienteRepository.findById(rut).get();
            if (cliente.getEstadoCuenta() != null) {
                clienteParchado.setEstadoCuenta(cliente.getEstadoCuenta());
            }
            if (cliente.getNomUsuario() != null) {
                clienteParchado.setNomUsuario(cliente.getNomUsuario());
            }
            if (cliente.getNom2Usuario() != null) {
                clienteParchado.setNom2Usuario(cliente.getNom2Usuario());
            }
            if (cliente.getApellidoPaterno() != null) {
                clienteParchado.setApellidoPaterno(cliente.getApellidoPaterno());
            }
            if (cliente.getApellidoMaterno() != null) {
                clienteParchado.setApellidoMaterno(cliente.getApellidoMaterno());
            }
            if (cliente.getSexoUsuario() == 'M' || cliente.getSexoUsuario() == 'F') {
                clienteParchado.setSexoUsuario(cliente.getSexoUsuario());
            }
            if (cliente.getFechaNacimiento() != null) {
                clienteParchado.setFechaNacimiento(cliente.getFechaNacimiento());
            }
            if (cliente.getTelefonoUsuario() != null) {
                clienteParchado.setTelefonoUsuario(cliente.getTelefonoUsuario());
            }
            if (cliente.getTel2Usuario() != null) {
                clienteParchado.setTel2Usuario(cliente.getTel2Usuario());
            }
            if (cliente.getEmailUsuario() != null) {
                clienteParchado.setEmailUsuario(cliente.getEmailUsuario());
            }
            if (cliente.getPassUsuario() != null) {
                String newPass = new BCryptPasswordEncoder(10).encode(cliente.getPassUsuario());
                clienteParchado.setPassUsuario(newPass);
            }
            clienteRepository.save(clienteParchado);
            return "Cliente actualizado con éxito";
        }
        return "Cliente no encontrado";
    }

    // D

    public String deleteCliente(int rut) {
        for (Cliente cliente : clienteRepository.findAll()) {
            if (cliente.getRutUsuario() == rut) {
                clienteRepository.delete(cliente);
                return "Cliente eliminado con éxito";
            }
        }
        return "Cliente no existente";
    }

    // Funciones no CRUD

    private String datosCliente(String output, Cliente cliente) {
        output += "ID: " + cliente.getRutUsuario() + "\n";
        output += "RUT: " + cliente.getRutUsuario() + "-" + cliente.getDvUsuario() + "\n";
        output += "Nombre completo: " + cliente.getNomUsuario() + " ";
        if (cliente.getNom2Usuario() != null) {
            output += cliente.getNom2Usuario() + " ";
        }
        output += cliente.getApellidoPaterno();
        if (cliente.getNom2Usuario() != null) {
            output += " " + cliente.getApellidoMaterno() + "\n";
        } else {
            output += "\n";
        }
        output += "Sexo: " + cliente.getSexoUsuario() + "\n";
        output += "Fecha de nacimiento: " + cliente.getFechaNacimiento().toString() + "\n";
        output += "Dirección: " + cliente.getDirUsuario() + "\n";
        output += "Número de teléfono: +56" + cliente.getTelefonoUsuario() + "\n";
        if (cliente.getTel2Usuario() != null) {
            output += "Teléfono extra: +56" + cliente.getTel2Usuario() + "\n";
        }
        output += "Email: " + cliente.getEmailUsuario() + "\n";
        output += "Estado de cuenta: " + cliente.getEstadoCuenta() + "\n";
        output += "\n";
        return output;
    }
}
