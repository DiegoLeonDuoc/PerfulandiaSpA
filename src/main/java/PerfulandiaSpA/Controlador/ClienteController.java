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

    @PostMapping
    public String addCliente(@RequestBody Cliente cliente){
        return clienteService.crearCliente(cliente);
    }

    @GetMapping
    public String getClientes(){
        return clienteService.getClientes();
    }

    @GetMapping("/{rut}")
    public String getClienteByRut(@PathVariable int rut){
        return clienteService.getClienteByRut(rut);
    }

    @GetMapping("/json")
    public List<Cliente> getClientesJSON() { return clienteService.getClientesJSON(); }

    @PutMapping("/{rut}")
    public String updateCliente(@RequestBody Cliente cliente, @PathVariable int rut) { return clienteService.updateCliente(cliente, rut); }

    @PatchMapping("/{rut}")
    public String parcharCliente(@RequestBody Cliente cliente, @PathVariable int rut) { return clienteService.parcharCliente(cliente, rut); }

    @DeleteMapping("/{rut}")
    public String deleteCliente(@PathVariable int rut){
        return clienteService.deleteCliente(rut);
    }

}
