package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Servicio.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping
    public String getClientes(){
        return clienteService.getClientes();
    }

    @GetMapping("/json")
    public List<Cliente> getClientesJSON(){
        return clienteService.getClientesJSON();
    }

    @GetMapping("/{id}")
    public String getClienteByRut(@PathVariable int id){
        return clienteService.getClienteByRut(id);
    }

    @PostMapping
    public String addCliente(@RequestBody Cliente cliente){
        return clienteService.saveCliente(cliente);
    }

//    @DeleteMapping("/{id}")
//    public String deleteCliente(@PathVariable int id){
//        return clienteService.deleteCliente(id);
//    }

    @DeleteMapping("/{id}")
    public String deleteUsuario(@PathVariable int id) {
        if (clienteService.getClienteByRut(id) == null) {
            return "No se encontro el cliente con el id: " + id;
        }else{
            return clienteService.deleteCliente(id);
        }
    }

    @PutMapping("/{id}")
    public String updateCliente(@RequestBody Cliente cliente, @PathVariable int id) {return clienteService.updateCliente(cliente, id);}

}
