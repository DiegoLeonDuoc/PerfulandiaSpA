package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Entidades.Usuario;
import PerfulandiaSpA.Repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String updateCliente(Cliente cliente, int id) {
//        if (clienteRepository.existsById(id)) {
//            clienteRepository.save(cliente);
//            return "Cliente actualizado con éxito";
//        }
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
