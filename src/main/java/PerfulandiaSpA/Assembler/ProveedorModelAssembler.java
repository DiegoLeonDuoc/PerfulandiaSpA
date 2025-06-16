package PerfulandiaSpA.Assembler;

import PerfulandiaSpA.Controlador.ProveedorController;
import PerfulandiaSpA.Entidades.Proveedor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProveedorModelAssembler implements RepresentationModelAssembler<Proveedor, EntityModel<Proveedor>> {

    @Override
    public EntityModel<Proveedor> toModel(Proveedor proveedor) {
        return EntityModel.of(proveedor,
                linkTo(methodOn(ProveedorController.class).getProveedorById(proveedor.getId())).withSelfRel(),
                linkTo(methodOn(ProveedorController.class).getProveedores()).withRel("proveedores"),
                linkTo(methodOn(ProveedorController.class).updateProveedor(proveedor.getId(), proveedor)).withRel("PUT"),
                linkTo(methodOn(ProveedorController.class).eliminarProveedor(proveedor.getId())).withRel("DELETE")
        );
    }
}
