package PerfulandiaSpA.Assembler;

import PerfulandiaSpA.Controlador.DescuentoController;
import PerfulandiaSpA.Entidades.Descuento;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DescuentoModelAssembler implements RepresentationModelAssembler<Descuento, EntityModel<Descuento>> {

    @Override
    public EntityModel<Descuento> toModel(Descuento descuento) {
        return EntityModel.of(descuento,
                linkTo(methodOn(DescuentoController.class).getDescuentoById(descuento.getId())).withSelfRel(),
                linkTo(methodOn(DescuentoController.class).getDescuentos()).withRel("descuentos"),
                linkTo(methodOn(DescuentoController.class).updateDescuento(descuento.getId(), null)).withRel("PUT"),
                linkTo(methodOn(DescuentoController.class).parcharDescuento(descuento.getId(), null)).withRel("PATCH"),
                linkTo(methodOn(DescuentoController.class).eliminarDescuento(descuento.getId())).withRel("DELETE")
        );
    }
}
