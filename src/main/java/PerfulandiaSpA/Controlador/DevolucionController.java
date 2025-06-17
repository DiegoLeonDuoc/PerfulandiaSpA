package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Assembler.DevolucionModelAssembler;
import PerfulandiaSpA.DTO.DevolucionDTO;
import PerfulandiaSpA.Entidades.Devolucion;
import PerfulandiaSpA.Servicio.DevolucionService;
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
@RequestMapping("/devolucion")
@Tag(name="Controlador Devolucion", description="Servicios de gestión de devolucion")
public class DevolucionController {

    @Autowired
    DevolucionService devolucionService;

    @Autowired
    DevolucionModelAssembler assembler;

    // C
    @PostMapping
    @Operation(summary = "Agregar Devolucion", description = "Permite registrar un devolucion en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Devolucion creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Devolucion.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    public ResponseEntity<EntityModel<Devolucion>> crearDevolucion(@RequestBody DevolucionDTO devolucionDTO) {
        Devolucion devolucion = devolucionService.crearDevolucion(devolucionDTO);
        if (devolucionService.getDevolucionByID(devolucion.getId()).isPresent()) {
            return new ResponseEntity<>(assembler.toModel(devolucion), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // R
    @GetMapping
    @Operation(summary= "Obtener devolucións", description = "Obtiene la lista de devolucións registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista completa de devoluciones"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    public ResponseEntity<CollectionModel<EntityModel<Devolucion>>> getDevoluciones(){
        List<Devolucion> devoluciones = devolucionService.getDevoluciones();
        if (devoluciones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(devoluciones), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar devolución por ID", description = "Obtiene un devolución según la ID registrada en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna Devolucion"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    @Parameter(description = "ID del devolucion", example = "2")
    public ResponseEntity<EntityModel<Devolucion>> getDevolucionById(@PathVariable int id) {
        Optional<Devolucion> devolucionOptional = devolucionService.getDevolucionByID(id);
        if (devolucionOptional.isPresent()) {
            return new ResponseEntity<>(assembler.toModel(devolucionOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cliente/{rut}")
    @Operation(summary = "Buscar devolucións por RUT de cliente", description = "Obtiene los devolucións según el RUT de un cliente registrado en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista de Devolucións"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    @Parameter(description = "ID del cliente", example = "12345678")
    public ResponseEntity<CollectionModel<EntityModel<Devolucion>>> getDevolucionesByRUT(@PathVariable int rut) {
        List<Devolucion> devoluciones = devolucionService.getDevolucionesByRut(rut);
        if (devoluciones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(devoluciones), HttpStatus.OK);
        }
    }

    // U
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar devolución", description = "Permite actualizar los datos de un devolución según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devolucion modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Devolucion.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El ID del devolucion", example = "1")
    public ResponseEntity<Devolucion> updateDevolucion(@PathVariable int id, @RequestBody DevolucionDTO devolucionDTO) {
        Optional<Devolucion> devolucionOptional = devolucionService.getDevolucionByID(id);
        if (devolucionOptional.isPresent()) {
            Devolucion devolucion = devolucionService.updateDevolucion(devolucionDTO, id);
            return new ResponseEntity<>(devolucion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Parchar Devolucion", description = "Permite actualizar parcialmente los datos de un devolucion según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devolucion modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Devolucion.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El ID del devolucion", example = "1")
    public ResponseEntity<Devolucion> parcharDevolucion(@PathVariable int id, @RequestBody DevolucionDTO devolucionDTO) {
        Optional<Devolucion> devolucionOptional = devolucionService.getDevolucionByID(id);
        if (devolucionOptional.isPresent()) {
            Devolucion devolucion = devolucionService.patchDevolucion(devolucionDTO, id);
            return new ResponseEntity<>(devolucion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // D
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar devolución", description = "Elimina un devolución específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Retorna el devolución eliminado"),
            @ApiResponse(responseCode = "404", description="Devolución no encontrado")
    })
    @Parameter(description = "La ID del devolución", example = "1")
    public ResponseEntity<EntityModel<Devolucion>> eliminarDevolucion(@PathVariable int id) {
        Optional<Devolucion> devolucionOptional = devolucionService.getDevolucionByID(id);
        if (devolucionOptional.isPresent()) {
            Devolucion devolucion = devolucionOptional.get();
            devolucionService.deleteDevolucion(id);
            return new ResponseEntity<>(assembler.toModel(devolucion), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

