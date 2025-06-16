package PerfulandiaSpA.Assembler;

import PerfulandiaSpA.Controlador.ProductoController;
import PerfulandiaSpA.Entidades.Producto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoController.class).getProductoById(producto.getId())).withSelfRel(),
                linkTo(methodOn(ProductoController.class).getProductos()).withRel("productos"),
                linkTo(methodOn(ProductoController.class).updateProducto(producto.getId(), producto)).withRel("PUT"),
                linkTo(methodOn(ProductoController.class).eliminarProducto(producto.getId())).withRel("DELETE")
        );
    }
}
