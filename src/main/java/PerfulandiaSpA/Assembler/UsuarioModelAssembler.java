package PerfulandiaSpA.Assembler;

import PerfulandiaSpA.Controlador.UsuarioController;
import PerfulandiaSpA.Entidades.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioController.class).getUsuarioByRut(usuario.getRutUsuario())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).getUsuariosJSON()).withRel("usuarios"));
    }
}
