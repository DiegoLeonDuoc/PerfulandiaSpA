package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Assembler.AdministradorModelAssembler;
import PerfulandiaSpA.Entidades.Administrador;
import PerfulandiaSpA.Servicio.AdministradorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/administrador")
@Tag(name="Controlador Administrador", description="Servicios de gestión de administrador")
public class AdministradorController {

    @Autowired
    AdministradorService administradorService;

    @Autowired
    AdministradorModelAssembler assembler;

    // C
    @PostMapping
    @Operation(summary = "Agregar Administrador", description = "Permite registrar un administrador en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Administrador creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Administrador.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenruto en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    public ResponseEntity<EntityModel<Administrador>> crearAdministrador(@RequestBody Administrador administrador) {
        administradorService.crearAdministrador(administrador);
        if (administradorService.getAdministradorByRut(administrador.getRutUsuario()).isPresent()) {
            return new ResponseEntity<>(assembler.toModel(administrador), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // R
    @GetMapping
    @Operation(summary= "Obtener administradors", description = "Obtiene la lista de administradors registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista completa de administradors"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    public ResponseEntity<CollectionModel<EntityModel<Administrador>>> getAdministradors(){
        List<Administrador> administradors = administradorService.getAdministradors();
        if (administradors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(administradors), HttpStatus.OK);
        }
    }

    @GetMapping("/{rut}")
    @Operation(summary = "Buscar administrador por RUT", description = "Obtiene un administrador según la RUT registrada en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna Administrador"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    @Parameter(description = "El RUT del administrador", example = "12345678")
    public ResponseEntity<EntityModel<Administrador>> getAdministradorByRut(@PathVariable int rut) {
        Optional<Administrador> administradorOptional = administradorService.getAdministradorByRut(rut);
        if (administradorOptional.isPresent()) {
            return new ResponseEntity<>(assembler.toModel(administradorOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // U
    @PutMapping("/{rut}")
    @Operation(summary = "Actualizar administrador", description = "Permite actualizar los datos de un administrador según su RUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Administrador.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenruto en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El RUT del administrador", example = "12345678")
    public ResponseEntity<EntityModel<Administrador>> updateAdministrador(@PathVariable int rut, @RequestBody Administrador administrador) {
        Optional<Administrador> administradorOptional = administradorService.getAdministradorByRut(rut);
        if (administradorOptional.isPresent()) {
            administradorService.updateAdministrador(administrador, rut);
            return new ResponseEntity<>(assembler.toModel(administradorOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{rut}")
    @Operation(summary = "Parchar Administrador", description = "Permite actualizar parcialmente los datos de un administrador según su RUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Administrador.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenruto en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El RUT del administrador", example = "12345678")
    public ResponseEntity<EntityModel<Administrador>> patchAdministrador(@PathVariable int rut, @RequestBody Administrador administrador) {
        Optional<Administrador> administradorOptional = administradorService.getAdministradorByRut(rut);
        if (administradorOptional.isPresent()) {
            administradorService.patchAdministrador(administrador, rut);
            return new ResponseEntity<>(assembler.toModel(administrador), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // D
    @DeleteMapping("/{rut}")
    @Operation(summary= "Eliminar administrador", description = "Elimina un administrador específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Retorna el administrador eliminado"),
            @ApiResponse(responseCode = "404", description="Administrador no encontrado")
    })
    @Parameter(description = "El RUT del administrador", example = "12345678")
    public ResponseEntity<EntityModel<Administrador>> eliminarAdministrador(@PathVariable int rut) {
        Optional<Administrador> administradorOptional = administradorService.getAdministradorByRut(rut);
        if (administradorOptional.isPresent()) {
            Administrador administrador = administradorOptional.get();
            administradorService.deleteAdministrador(rut);
            return new ResponseEntity<>(assembler.toModel(administrador), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

