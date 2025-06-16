package PerfulandiaSpA.Assembler;

import PerfulandiaSpA.Controlador.ProductosPedidoController;
import PerfulandiaSpA.Entidades.ProductosPedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductosPedidoModelAssembler implements RepresentationModelAssembler<ProductosPedido, EntityModel<ProductosPedido>> {

    @Override
    public EntityModel<ProductosPedido> toModel(ProductosPedido productoPedido) {
        return EntityModel.of(productoPedido,
                linkTo(methodOn(ProductosPedidoController.class).getProductoPedidoByID(productoPedido.getId())).withSelfRel(),
                linkTo(methodOn(ProductosPedidoController.class).getProductosPedidos()).withRel("Productos en pedidos"),
                linkTo(methodOn(ProductosPedidoController.class).updateProductosPedido(productoPedido.getId(), null)).withRel("PUT"),
                linkTo(methodOn(ProductosPedidoController.class).patchProductosPedido(productoPedido.getId(), null)).withRel("PATCH"),
                linkTo(methodOn(ProductosPedidoController.class).eliminarProductosPedido(productoPedido.getId())).withRel("DELETE")
        );
    }
}