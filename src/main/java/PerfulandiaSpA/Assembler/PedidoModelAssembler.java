package PerfulandiaSpA.Assembler;

import PerfulandiaSpA.Controlador.PedidoController;
import PerfulandiaSpA.Entidades.Pedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(pedido,
                linkTo(methodOn(PedidoController.class).getPedidoByID(pedido.getId())).withSelfRel(),
                linkTo(methodOn(PedidoController.class).getPedidos()).withRel("pedidos"),
                linkTo(methodOn(PedidoController.class).updatePedido(pedido.getId(), null)).withRel("PUT"),
                linkTo(methodOn(PedidoController.class).eliminarPedido(pedido.getId())).withRel("DELETE")
        );
    }
}
