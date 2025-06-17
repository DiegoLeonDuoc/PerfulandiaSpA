package PerfulandiaSpA.Assembler;

import PerfulandiaSpA.Controlador.ProductosCarritoController;
import PerfulandiaSpA.Entidades.ProductosCarrito;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductosCarritoModelAssembler implements RepresentationModelAssembler<ProductosCarrito, EntityModel<ProductosCarrito>> {

    @Override
    public EntityModel<ProductosCarrito> toModel(ProductosCarrito productoCarrito) {
        return EntityModel.of(productoCarrito,
                linkTo(methodOn(ProductosCarritoController.class).getProductoCarritoByID(productoCarrito.getId())).withSelfRel(),
                linkTo(methodOn(ProductosCarritoController.class).getProductosCarritos()).withRel("Productos en carritos"),
                linkTo(methodOn(ProductosCarritoController.class).updateProductosCarrito(productoCarrito.getId(), null)).withRel("PUT"),
                linkTo(methodOn(ProductosCarritoController.class).eliminarProductosCarrito(productoCarrito.getId())).withRel("DELETE")
        );
    }
}