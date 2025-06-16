package PerfulandiaSpA.Assembler;

import PerfulandiaSpA.Controlador.SucursalController;
import PerfulandiaSpA.Entidades.Sucursal;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>> {

    @Override
    public EntityModel<Sucursal> toModel(Sucursal sucursal) {
        return EntityModel.of(sucursal,
                linkTo(methodOn(SucursalController.class).getSucursalById(sucursal.getId())).withSelfRel(),
                linkTo(methodOn(SucursalController.class).getSucursales()).withRel("sucursales"),
                linkTo(methodOn(SucursalController.class).updateSucursal(sucursal.getId(), sucursal)).withRel("PUT"),
                linkTo(methodOn(SucursalController.class).eliminarSucursal(sucursal.getId())).withRel("DELETE")
        );
    }
}
