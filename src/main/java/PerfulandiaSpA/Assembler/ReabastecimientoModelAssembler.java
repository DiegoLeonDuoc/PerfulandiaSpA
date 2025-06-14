package PerfulandiaSpA.Assembler;

import PerfulandiaSpA.Controlador.ReabastecimientoController;
import PerfulandiaSpA.Entidades.Reabastecimiento;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ReabastecimientoModelAssembler implements RepresentationModelAssembler<Reabastecimiento, EntityModel<Reabastecimiento>> {

    @Override
    public EntityModel<Reabastecimiento> toModel(Reabastecimiento reabastecimiento) {
        return EntityModel.of(reabastecimiento,
                linkTo(methodOn(ReabastecimientoController.class).getReabastecimientoById(reabastecimiento.getId())).withSelfRel(),
                linkTo(methodOn(ReabastecimientoController.class).getReabastecimientos()).withRel("reabastecimientoes"),
                linkTo(methodOn(ReabastecimientoController.class).updateReabastecimiento(reabastecimiento.getId(), null)).withRel("PUT"),
                linkTo(methodOn(ReabastecimientoController.class).eliminarReabastecimiento(reabastecimiento.getId())).withRel("DELETE")
        );
    }
}
