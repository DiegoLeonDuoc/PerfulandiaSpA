package PerfulandiaSpA.Assembler;

import PerfulandiaSpA.Controlador.EmpleadoController;
import PerfulandiaSpA.Entidades.Empleado;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmpleadoModelAssembler implements RepresentationModelAssembler<Empleado, EntityModel<Empleado>> {

    @Override
    public EntityModel<Empleado> toModel(Empleado empleado) {
        return EntityModel.of(empleado,
                linkTo(methodOn(EmpleadoController.class).getEmpleadoById(empleado.getRutUsuario())).withSelfRel(),
                linkTo(methodOn(EmpleadoController.class).getEmpleados()).withRel("empleados"),
                linkTo(methodOn(EmpleadoController.class).updateEmpleado(empleado.getRutUsuario(), null)).withRel("PUT"),
                linkTo(methodOn(EmpleadoController.class).parcharEmpleado(empleado.getRutUsuario(), null)).withRel("PATCH"),
                linkTo(methodOn(EmpleadoController.class).eliminarEmpleado(empleado.getRutUsuario())).withRel("DELETE")
        );
    }
}
