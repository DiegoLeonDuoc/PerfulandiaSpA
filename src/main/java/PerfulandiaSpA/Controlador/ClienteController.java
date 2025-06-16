package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Assembler.ClienteModelAssembler;
import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Servicio.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
@Tag(name="Controlador Cliente", description="Servicios de gestión de cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @Autowired
    ClienteModelAssembler assembler;

    // C
    @PostMapping
    @Operation(summary = "Agregar Cliente", description = "Permite registrar un cliente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenruto en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    public ResponseEntity<EntityModel<Cliente>> crearCliente(@RequestBody Cliente cliente) {
        clienteService.crearCliente(cliente);
        if (clienteService.getClienteByRut(cliente.getRutUsuario()).isPresent()) {
            return new ResponseEntity<>(assembler.toModel(cliente), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // R
    @GetMapping
    @Operation(summary= "Obtener clientes", description = "Obtiene la lista de clientes registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista completa de clientes"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    public ResponseEntity<CollectionModel<EntityModel<Cliente>>> getClientes(){
        List<Cliente> clientes = clienteService.getClientes();
        if (clientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(clientes), HttpStatus.OK);
        }
    }

    @GetMapping("/{rut}")
    @Operation(summary = "Buscar cliente por RUT", description = "Obtiene un cliente según la RUT registrada en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna Cliente"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    @Parameter(description = "El RUT del cliente", example = "12345678")
    public ResponseEntity<EntityModel<Cliente>> getClienteByRut(@PathVariable int rut) {
        Optional<Cliente> clienteOptional = clienteService.getClienteByRut(rut);
        if (clienteOptional.isPresent()) {
            return new ResponseEntity<>(assembler.toModel(clienteOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // U
    @PutMapping("/{rut}")
    @Operation(summary = "Actualizar cliente", description = "Permite actualizar los datos de un cliente según su RUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenruto en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El RUT del cliente", example = "12345678")
    public ResponseEntity<EntityModel<Cliente>> updateCliente(@PathVariable int rut, @RequestBody Cliente cliente) {
        Optional<Cliente> clienteOptional = clienteService.getClienteByRut(rut);
        if (clienteOptional.isPresent()) {
            clienteService.updateCliente(cliente, rut);
            return new ResponseEntity<>(assembler.toModel(clienteOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{rut}")
    @Operation(summary = "Parchar Cliente", description = "Permite actualizar parcialmente los datos de un cliente según su RUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenruto en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El RUT del cliente", example = "12345678")
    public ResponseEntity<EntityModel<Cliente>> patchCliente(@PathVariable int rut, @RequestBody Cliente cliente) {
        Optional<Cliente> clienteOptional = clienteService.getClienteByRut(rut);
        if (clienteOptional.isPresent()) {
            clienteService.patchCliente(cliente, rut);
            return new ResponseEntity<>(assembler.toModel(cliente), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // D
    @DeleteMapping("/{rut}")
    @Operation(summary= "Eliminar cliente", description = "Elimina un cliente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Retorna el cliente eliminado"),
            @ApiResponse(responseCode = "404", description="Cliente no encontrado")
    })
    @Parameter(description = "El RUT del cliente", example = "12345678")
    public ResponseEntity<EntityModel<Cliente>> eliminarCliente(@PathVariable int rut) {
        Optional<Cliente> clienteOptional = clienteService.getClienteByRut(rut);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            clienteService.deleteCliente(rut);
            return new ResponseEntity<>(assembler.toModel(cliente), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

