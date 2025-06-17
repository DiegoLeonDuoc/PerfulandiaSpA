package PerfulandiaSpA.Assembler;

import PerfulandiaSpA.Controlador.DevolucionController;
import PerfulandiaSpA.Entidades.Devolucion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DevolucionModelAssembler implements RepresentationModelAssembler<Devolucion, EntityModel<Devolucion>> {

    @Override
    public EntityModel<Devolucion> toModel(Devolucion devolucion) {
        return EntityModel.of(devolucion,
                linkTo(methodOn(DevolucionController.class).getDevolucionById(devolucion.getId())).withSelfRel(),
                linkTo(methodOn(DevolucionController.class).getDevoluciones()).withRel("devoluciones"),
                linkTo(methodOn(DevolucionController.class).updateDevolucion(devolucion.getId(), null)).withRel("PUT"),
                linkTo(methodOn(DevolucionController.class).parcharDevolucion(devolucion.getId(), null)).withRel("PATCH"),
                linkTo(methodOn(DevolucionController.class).eliminarDevolucion(devolucion.getId())).withRel("DELETE")
        );
    }
}
