package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Servicio.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@Tag(name="Servicio Clientes", description="Servicios de gestión para clientes")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @PostMapping
    @Operation(summary= "Crear cliente", description = "Servicio POST para crear usuarios tipo cliente")
    @ApiResponse(responseCode = "200", description="Confirmación sobre la creación del usuario")
    public String addCliente(@RequestBody Cliente cliente){
        return clienteService.crearCliente(cliente);
    }

    @GetMapping
    @Operation(summary= "Obtener clientes", description = "Servicio GET para obtener información sobre clientes en formato String")
    @ApiResponse(responseCode = "200", description="Registro de clientes en formato texto simple")
    public String getClientes(){
        return clienteService.getClientes();
    }

    @GetMapping("/{rut}")
    @Operation(summary= "Obtener cliente por RUT", description = "Servicio GET para obtener información sobre un cliente específico en formato String")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Registro del cliente en formato texto simple"),
            @ApiResponse(responseCode = "404", description="Usuario no encontrado")
    })
    public String getClienteByRut(@PathVariable Integer rut){
        return clienteService.getClienteByRut(rut);
    }

    @GetMapping("/json")
    @Operation(summary= "Obtener clientes en formato JSON", description = "Servicio GET para obtener información sobre clientes en formato JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Registro de usuarios en formato JSON"),
            @ApiResponse(responseCode = "404", description="Usuario no encontrado")
    })
    public List<Cliente> getClientesJSON() { return clienteService.getClientesJSON(); }

    @PutMapping("/{rut}")
    @Operation(summary= "Modificar cliente", description = "Servicio PUT para modificar datos de un cliente en específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa."),
            @ApiResponse(responseCode = "404", description="Cliente no encontrado")
    })
    public String updateCliente(@RequestBody Cliente cliente, @PathVariable Integer rut) { return clienteService.updateCliente(cliente, rut); }

    @PatchMapping("/{rut}")
    @Operation(summary= "Parchar cliente", description = "Servicio PATCH para modificar datos de un cliente en específico de forma parcial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa."),
            @ApiResponse(responseCode = "404", description="Cliente no encontrado")
    })
    public String parcharCliente(@RequestBody Cliente cliente, @PathVariable Integer rut) { return clienteService.parcharCliente(cliente, rut); }

    @DeleteMapping("/{rut}")
    @Operation(summary= "Eliminar cliente", description = "Servicio DELETE para un cliente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de la eliminación del cliente"),
            @ApiResponse(responseCode = "404", description="Cliente no encontrado")
    })
    public String deleteCliente(@PathVariable int rut){
        return clienteService.deleteCliente(rut);
    }

}
