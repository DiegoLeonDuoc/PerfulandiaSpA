package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Assembler.EnvioModelAssembler;
import PerfulandiaSpA.DTO.EnvioDTO;
import PerfulandiaSpA.Entidades.Envio;
import PerfulandiaSpA.Servicio.EnvioService;
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
@RequestMapping("/envio")
@Tag(name="Controlador Envio", description="Servicios de gestión de envio")
public class EnvioController {

    @Autowired
    EnvioService envioService;

    @Autowired
    EnvioModelAssembler assembler;

    // C
    @PostMapping
    @Operation(summary = "Agregar Envio", description = "Permite registrar un envio en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Envio creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Envio.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    public ResponseEntity<EntityModel<Envio>> crearEnvio(@RequestBody EnvioDTO envioDTO) {
        Envio envio = envioService.crearEnvio(envioDTO);
        if (envioService.getEnvioByID(envio.getId()).isPresent()) {
            return new ResponseEntity<>(assembler.toModel(envio), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // R
    @GetMapping
    @Operation(summary= "Obtener envíos", description = "Obtiene la lista de envíos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista completa de envios"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    public ResponseEntity<CollectionModel<EntityModel<Envio>>> getEnvios(){
        List<Envio> envios = envioService.getEnvios();
        if (envios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(envios), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar envío por ID", description = "Obtiene un envío según la ID registrada en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna Envio"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    @Parameter(description = "ID del envio", example = "2")
    public ResponseEntity<EntityModel<Envio>> getEnvioById(@PathVariable int id) {
        Optional<Envio> envioOptional = envioService.getEnvioByID(id);
        if (envioOptional.isPresent()) {
            return new ResponseEntity<>(assembler.toModel(envioOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sucursal/{id}")
    @Operation(summary = "Buscar envíos por ID de sucursal", description = "Obtiene los envíos según la ID de la sucursal registrada en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista de Envíos"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    @Parameter(description = "ID de la sucursal", example = "2")
    public ResponseEntity<CollectionModel<EntityModel<Envio>>> getEnviosBySucursal(@PathVariable int id) {
        List<Envio> envios = envioService.getEnviosBySucursal(id);
        if (envios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(envios), HttpStatus.OK);
        }
    }

    @GetMapping("/cliente/{rut}")
    @Operation(summary = "Buscar envíos por RUT de cliente", description = "Obtiene los envíos según el RUT de un cliente registrado en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista de Envíos"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    @Parameter(description = "ID del cliente", example = "12345678")
    public ResponseEntity<CollectionModel<EntityModel<Envio>>> getEnviosByRUT(@PathVariable int rut) {
        List<Envio> envios = envioService.getEnviosByRut(rut);
        if (envios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(envios), HttpStatus.OK);
        }
    }

    // U
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar envío", description = "Permite actualizar los datos de un envío según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envio modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Envio.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El ID del envio", example = "1")
    public ResponseEntity<Envio> updateEnvio(@PathVariable int id, @RequestBody EnvioDTO envioDTO) {
        Optional<Envio> envioOptional = envioService.getEnvioByID(id);
        if (envioOptional.isPresent()) {
            Envio envio = envioService.updateEnvio(id, envioDTO);
            return new ResponseEntity<>(envio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Parchar Envio", description = "Permite actualizar parcialmente los datos de un envio según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envio modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Envio.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El ID del envio", example = "1")
    public ResponseEntity<Envio> parcharEnvio(@PathVariable int id, @RequestBody EnvioDTO envioDTO) {
        Optional<Envio> envioOptional = envioService.getEnvioByID(id);
        if (envioOptional.isPresent()) {
            Envio envio = envioService.patchEnvio(id, envioDTO);
            return new ResponseEntity<>(envio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // D
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar envío", description = "Elimina un envío específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Retorna el envío eliminado"),
            @ApiResponse(responseCode = "404", description="Envío no encontrado")
    })
    @Parameter(description = "La ID del envío", example = "1")
    public ResponseEntity<EntityModel<Envio>> eliminarEnvio(@PathVariable int id) {
        Optional<Envio> envioOptional = envioService.getEnvioByID(id);
        if (envioOptional.isPresent()) {
            Envio envio = envioOptional.get();
            envioService.deleteEnvio(id);
            return new ResponseEntity<>(assembler.toModel(envio), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

