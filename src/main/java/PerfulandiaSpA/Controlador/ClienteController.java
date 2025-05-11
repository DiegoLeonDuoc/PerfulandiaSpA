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

    @GetMapping("/{rut}")
    public String getClienteByRut(@PathVariable int rut){
        return clienteService.getClienteByRut(rut);
    }

    @PostMapping
    public String addCliente(@RequestBody Cliente cliente){
        return clienteService.saveCliente(cliente);
    }

    @DeleteMapping("/{rut}")
    public String deleteCliente(@PathVariable int rut){
        return clienteService.deleteCliente(rut);
    }

    @PutMapping("/{rut}")
    public String updateCliente(@RequestBody Cliente cliente, @PathVariable int rut) {return clienteService.updateCliente(cliente, rut);}

}
