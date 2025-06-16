package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Assembler.CarritoModelAssembler;
import PerfulandiaSpA.Entidades.Carrito;
import PerfulandiaSpA.Servicio.CarritoService;
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

@RestController
@RequestMapping("/carritos")
@Tag(name="Controlador Carritos", description="Servicios de gestión de carritos")
public class CarritoController {

    @Autowired
    CarritoService carritoService;

    @Autowired
    CarritoModelAssembler assembler;

    // C
    @PostMapping
    @Operation(summary = "Agregar Carrito", description = "Permite registrar una carrito en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carrito creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Carrito.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud", content = @Content),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato", content = @Content)
    })
    public ResponseEntity<EntityModel<Carrito>> crearCarrito(@RequestBody Carrito carrito) {
        carritoService.saveCarrito(carrito);
        if (carritoService.getCarritoByID(carrito.getId()).isPresent()) {
            return new ResponseEntity<>(assembler.toModel(carrito), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // R
    @GetMapping
    @Operation(summary= "Obtener carritos", description = "Obtiene la lista de carritos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista completa de carritos"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    public ResponseEntity<CollectionModel<EntityModel<Carrito>>> getCarritos(){
        List<Carrito> carritos = carritoService.getCarritos();
        if (carritos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(carritos), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar carrito por ID", description = "Obtiene un carrito según la ID registrada en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna Carrito"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    @Parameter(description = "ID del carrito", example = "2")
    public ResponseEntity<EntityModel<Carrito>> getCarritoById(@PathVariable int id) {
        if (carritoService.getCarritoByID(id).isPresent()) {
            Carrito carrito = carritoService.getCarritoByID(id).get();
            return new ResponseEntity<>(assembler.toModel(carrito), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // U
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Carrito", description = "Permite actualizar los datos de un carrito según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Carrito.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud", content = @Content),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato", content = @Content)
    })
    @Parameter(description = "El ID del carrito", example = "1")
    public ResponseEntity<EntityModel<Carrito>> updateCarrito(@PathVariable int id, @RequestBody Carrito carrito) {
        if (carritoService.getCarritoByID(id).isPresent()) {
            carritoService.updateCarrito(carrito, id);
            return new ResponseEntity<>(assembler.toModel(carrito), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // D
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar carrito", description = "Elimina un carrito específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Retorna el carrito eliminado"),
            @ApiResponse(responseCode = "404", description="Carrito no encontrado", content = @Content)
    })
    @Parameter(description = "La ID del carrito", example = "1")
    public ResponseEntity<EntityModel<Carrito>> eliminarCarrito(@PathVariable int id) {
        if (carritoService.getCarritoByID(id).isPresent()) {
            Carrito carrito  = carritoService.getCarritoByID(id).get();
            carritoService.deleteCarrito(id);
            return new ResponseEntity<>(assembler.toModel(carrito), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

