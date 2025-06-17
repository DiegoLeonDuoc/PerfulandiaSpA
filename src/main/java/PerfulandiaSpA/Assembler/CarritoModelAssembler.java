package PerfulandiaSpA.Assembler;

import PerfulandiaSpA.Controlador.CarritoController;
import PerfulandiaSpA.Entidades.Carrito;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CarritoModelAssembler implements RepresentationModelAssembler<Carrito, EntityModel<Carrito>> {

    @Override
    public EntityModel<Carrito> toModel(Carrito carrito) {
        return EntityModel.of(carrito,
                linkTo(methodOn(CarritoController.class).getCarritoById(carrito.getId())).withSelfRel(),
                linkTo(methodOn(CarritoController.class).getCarritos()).withRel("carritos"),
                linkTo(methodOn(CarritoController.class).updateCarrito(carrito.getId(), null)).withRel("PUT"),
                linkTo(methodOn(CarritoController.class).eliminarCarrito(carrito.getId())).withRel("DELETE")
        );
    }
}
