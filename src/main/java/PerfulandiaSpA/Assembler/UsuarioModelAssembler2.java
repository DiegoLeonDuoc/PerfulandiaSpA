package PerfulandiaSpA.Assembler;

import PerfulandiaSpA.Controlador.UsuarioController2;
import PerfulandiaSpA.Entidades.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioModelAssembler2 implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioController2.class).getUsuarioById(usuario.getRutUsuario())).withSelfRel(),
                linkTo(methodOn(UsuarioController2.class).getUsuarios()).withRel("usuarios"),
                linkTo(methodOn(UsuarioController2.class).eliminarUsuario(usuario.getRutUsuario())).withRel("DELETE")
        );
    }
}
