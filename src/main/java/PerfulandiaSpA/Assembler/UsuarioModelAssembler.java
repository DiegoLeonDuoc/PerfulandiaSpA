package PerfulandiaSpA.Assembler;

import PerfulandiaSpA.Controlador.UsuarioController;
import PerfulandiaSpA.Entidades.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioController.class).getUsuarioById(usuario.getRutUsuario())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).getUsuarios()).withRel("usuarios"),
                linkTo(methodOn(UsuarioController.class).eliminarUsuario(usuario.getRutUsuario())).withRel("DELETE")
        );
    }
}
