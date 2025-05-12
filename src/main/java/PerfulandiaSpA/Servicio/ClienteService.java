package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Repositorio.ClienteRepository;
import PerfulandiaSpA.Repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    public String saveCliente(Cliente cliente) {
        clienteRepository.save(cliente);
        return "Cliente agregado con éxito";
    }

    public String getClientes() {
        String output = "";
        for (Cliente cliente : clienteRepository.findAll()) {
            output = datosCliente(output, cliente);
        }

        if (output.isEmpty()) {
            //return clienteRepository.findAll().toString();
            return "No hay clientes registrados";
        } else {
            return output;
        }
    }

    public List<Cliente> getClientesJSON() {
        return clienteRepository.findAll();
    }

    public String getClienteByRut(int id) {
        String output = "";
        if (clienteRepository.existsById(id)) {
            Cliente cliente = clienteRepository.findById(id).get();
            output = datosCliente(output, cliente);
            return output;
        }else{
            return "Cliente no encontrado";
        }
    }

    public String updateCliente(Cliente cliente, int id) {
        if (clienteRepository.existsById(id)) {
           clienteRepository.save(cliente);
           return "Cliente actualizado con éxito";
        }
        return "Cliente no encontrado";
    }

    public String deleteCliente(int id) {
        //return String.valueOf(usuarioRepository.existsById(id));
        for (Cliente cliente : clienteRepository.findAll()) {
            if (cliente.getId() == id) {
                clienteRepository.delete(cliente);
                return "Usuario eliminado con éxito";
            }
        }
        return "Usuario no eliminado";

    }

    private String datosCliente(String output, Cliente cliente) {
        output += "ID: " + cliente.getId() + "\n";
        output+="RUT: "+cliente.getUsuarioAsociado().getRutUsuario()+"-"+cliente.getUsuarioAsociado().getDvUsuario()+"\n";
        output+="Nombre completo: "+cliente.getUsuarioAsociado().getNomUsuario()+" ";
        if (cliente.getUsuarioAsociado().getNom2Usuario() != null) {
            output+=cliente.getUsuarioAsociado().getNom2Usuario()+" ";
        }
        output+=cliente.getUsuarioAsociado().getApellidoPaterno();
        if (cliente.getUsuarioAsociado().getApellidoMaterno() != null) {
            output+=" "+cliente.getUsuarioAsociado().getApellidoMaterno()+"\n";
        } else {
            output+="\n";
        }
        output+="Sexo: "+cliente.getUsuarioAsociado().getSexoUsuario()+"\n";
        output+="Fecha de nacimiento: "+cliente.getUsuarioAsociado().getFechaNacimiento().toString()+"\n";
        output+="Dirección: "+cliente.getUsuarioAsociado().getDirUsuario()+"\n";
        output+="Número de teléfono: +569"+cliente.getUsuarioAsociado().getTelefonoUsuario()+"\n";
        if (cliente.getUsuarioAsociado().getTel2Usuario() != null) {
            output+="Teléfono extra: +569"+cliente.getUsuarioAsociado().getTel2Usuario()+"\n";
        }
        output+="Email: "+cliente.getUsuarioAsociado().getEmailUsuario()+"\n";
        output+="Estado de la cuenta: "+cliente.getEstadoCuenta()+"\n";
        return output;
    }
}
