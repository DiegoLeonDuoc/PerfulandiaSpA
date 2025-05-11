package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public String saveCliente(Cliente cliente) {
        clienteRepository.save(cliente);
        return "Cliente agregado con éxito";
    }

    public String deleteCliente(int rut) {
        clienteRepository.deleteById(rut);
        return "Cliente eliminado con éxito";
    }

    public String updateCliente(Cliente cliente, int rut) {
        if (clienteRepository.existsById(rut)) {
           clienteRepository.save(cliente);
           return "Cliente actualizado con éxito";
        }
        return "Cliente no encontrado";
    }

    public String getClientes() {
        String output = "";
        for (Cliente cliente : clienteRepository.findAll()) {
            output += datosCliente(output, cliente);
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

    private String datosCliente(String output, Cliente cliente) {
        output+="RUT: "+cliente.getRutUs()+"-"+cliente.getDvUs()+"\n";
        output+="Nombre completo: "+cliente.getNomUs()+" ";
        if (cliente.getNom2Us() != null) {
            output+=cliente.getNom2Us()+" ";
        }
        output+=cliente.getApPat();
        if (cliente.getNom2Us() != null) {
            output+=" "+cliente.getApMat()+"\n";
        } else {
            output+="\n";
        }
        output+="Sexo: "+cliente.getSexoUs()+"\n";
        output+="Fecha de nacimiento: "+cliente.getFecNac().toString()+"\n";
        output+="Dirección: "+cliente.getDirUs()+"\n";
        output+="Número de teléfono: +569"+cliente.getTel1Us()+"\n";
        if (cliente.getTel2Us() != null) {
            output+="Teléfono extra: +569"+cliente.getTel2Us()+"\n";
        }
        output+="Email: "+cliente.getEmailUs()+"\n";
        output+="Estado de la cuenta: "+cliente.getEstadoCuenta()+"\n";
        return output;
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



}
