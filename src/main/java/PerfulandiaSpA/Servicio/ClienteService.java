package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    public String deleteCliente(int rut) {
        for (Cliente cliente : clienteRepository.findAll()) {
            if (cliente.getRutUsuario() == rut) {
                clienteRepository.delete(cliente);
                return "Cliente eliminado con éxito";
            }
        }
        return "Cliente no eliminado";
    }

    public String addCliente(Cliente cliente) {
            if (clienteRepository.findById(cliente.getRutUsuario()) != null) {
                return "Cliente ya existe";
            } else {
                clienteRepository.save(cliente);
                return "Cliente actualizado con éxito";
            }
    }


    // Cambiar cliente por usuario en casi todos los campos
    public String updateCliente(Cliente cliente, int id) {
        if (clienteRepository.existsById(id)) {
            Cliente newCliente = new Cliente();
            if (cliente.getEstadoCuenta() != null) {
                newCliente.setEstadoCuenta(cliente.getEstadoCuenta());
            }
            if (cliente.getNomUsuario() != null) {
                newCliente.setNomUsuario(cliente.getNomUsuario());
            }
            if (cliente.getNom2Usuario() != null) {
                newCliente.setNom2Usuario(cliente.getNom2Usuario());
            }
            if (cliente.getApellidoPaterno() != null) {
                newCliente.setApellidoPaterno(cliente.getApellidoPaterno());
            }
            if (cliente.getApellidoMaterno() != null) {
                newCliente.setApellidoMaterno(cliente.getApellidoMaterno());
            }
            if (cliente.getSexoUsuario() == 'M' || cliente.getSexoUsuario() == 'F') {
                newCliente.setSexoUsuario(cliente.getSexoUsuario());
            }
            if (cliente.getFechaNacimiento() != null) {
                newCliente.setFechaNacimiento(cliente.getFechaNacimiento());
            }
            if (cliente.getTelefonoUsuario() != null) {
                newCliente.setTelefonoUsuario(cliente.getTelefonoUsuario());
            }
            if (cliente.getTel2Usuario() != null) {
                newCliente.setTel2Usuario(cliente.getTel2Usuario());
            }
            if (cliente.getEmailUsuario() != null) {
                newCliente.setEmailUsuario(cliente.getEmailUsuario());
            }
            if (cliente.getPassUsuario() != null) {
                String newPass = new BCryptPasswordEncoder(31).encode(cliente.getPassUsuario());
                newCliente.setPassUsuario(newPass);
            }
            clienteRepository.save(newCliente);
            return "Cliente actualizado con éxito";
        }
        return "Cliente no encontrado";
    }

    public String getClientes() {
        String output = "";
        for (Cliente cliente : clienteRepository.findAll()) {
//            output = datosCliente(output, cliente);
            output += cliente.toString() + "\n";
        }

        if (output.isEmpty()) {
            //return clienteRepository.findAll().toString();
            return "No hay clientes registrados";
        } else {
            return output;
        }
    }

    public String getClienteByRut(int id) {
        String output = "";
        if (clienteRepository.existsById(id)) {
            Cliente cliente = clienteRepository.findById(id).get();
            //output = datosUsuario(output, usuario);
            output += cliente.getEstadoCuenta();
            return output;
        }else{
            return "Usuario no encontrado";
        }
    }

}
