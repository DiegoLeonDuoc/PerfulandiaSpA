package PerfulandiaSpA.Assembler;

import PerfulandiaSpA.Controlador.EnvioController;
import PerfulandiaSpA.Entidades.Envio;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<Envio, EntityModel<Envio>> {

    @Override
    public EntityModel<Envio> toModel(Envio envio) {
        return EntityModel.of(envio,
                linkTo(methodOn(EnvioController.class).getEnvioById(envio.getId())).withSelfRel(),
                linkTo(methodOn(EnvioController.class).getEnvios()).withRel("envios"),
                linkTo(methodOn(EnvioController.class).updateEnvio(envio.getId(), null)).withRel("PUT"),
                linkTo(methodOn(EnvioController.class).parcharEnvio(envio.getId(), null)).withRel("PATCH"),
                linkTo(methodOn(EnvioController.class).eliminarEnvio(envio.getId())).withRel("DELETE")
        );
    }
}
