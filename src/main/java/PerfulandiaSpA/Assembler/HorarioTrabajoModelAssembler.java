package PerfulandiaSpA.Assembler;

import PerfulandiaSpA.Controlador.HorarioTrabajoController;
import PerfulandiaSpA.Entidades.HorarioTrabajo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HorarioTrabajoModelAssembler implements RepresentationModelAssembler<HorarioTrabajo, EntityModel<HorarioTrabajo>> {

    @Override
    public EntityModel<HorarioTrabajo> toModel(HorarioTrabajo horarioTrabajo) {
        return EntityModel.of(horarioTrabajo,
                linkTo(methodOn(HorarioTrabajoController.class).getHorarioTrabajoById(horarioTrabajo.getId())).withSelfRel(),
                linkTo(methodOn(HorarioTrabajoController.class).getHorarioTrabajos()).withRel("horarioTrabajos"),
                linkTo(methodOn(HorarioTrabajoController.class).updateHorarioTrabajo(horarioTrabajo.getId(), null)).withRel("PUT"),
                linkTo(methodOn(HorarioTrabajoController.class).eliminarHorarioTrabajo(horarioTrabajo.getId())).withRel("DELETE")
        );
    }
}
