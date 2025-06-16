package PerfulandiaSpA.Assembler;

import PerfulandiaSpA.Controlador.AdministradorController;
import PerfulandiaSpA.Entidades.Administrador;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AdministradorModelAssembler implements RepresentationModelAssembler<Administrador, EntityModel<Administrador>> {

    @Override
    public EntityModel<Administrador> toModel(Administrador administrador) {
        return EntityModel.of(administrador,
                linkTo(methodOn(AdministradorController.class).getAdministradorByRut(administrador.getRutUsuario())).withSelfRel(),
                linkTo(methodOn(AdministradorController.class).getAdministradors()).withRel("administradores"),
                linkTo(methodOn(AdministradorController.class).updateAdministrador(administrador.getRutUsuario(), null)).withRel("PUT"),
                linkTo(methodOn(AdministradorController.class).patchAdministrador(administrador.getRutUsuario(), null)).withRel("PATCH"),
                linkTo(methodOn(AdministradorController.class).eliminarAdministrador(administrador.getRutUsuario())).withRel("DELETE")
        );
    }
}
