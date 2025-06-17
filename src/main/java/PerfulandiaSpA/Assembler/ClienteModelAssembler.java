package PerfulandiaSpA.Assembler;

import PerfulandiaSpA.Controlador.ClienteController;
import PerfulandiaSpA.Entidades.Cliente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<Cliente, EntityModel<Cliente>> {

    @Override
    public EntityModel<Cliente> toModel(Cliente cliente) {
        return EntityModel.of(cliente,
                linkTo(methodOn(ClienteController.class).getClienteByRut(cliente.getRutUsuario())).withSelfRel(),
                linkTo(methodOn(ClienteController.class).getClientes()).withRel("clientes"),
                linkTo(methodOn(ClienteController.class).updateCliente(cliente.getRutUsuario(), null)).withRel("PUT"),
                linkTo(methodOn(ClienteController.class).patchCliente(cliente.getRutUsuario(), null)).withRel("PATCH"),
                linkTo(methodOn(ClienteController.class).eliminarCliente(cliente.getRutUsuario())).withRel("DELETE")
        );
    }
}
