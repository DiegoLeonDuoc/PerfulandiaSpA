package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Assembler.ProveedorModelAssembler;
import PerfulandiaSpA.Entidades.Proveedor;
import PerfulandiaSpA.Entidades.Proveedor;
import PerfulandiaSpA.Servicio.ProveedorService;
import PerfulandiaSpA.Servicio.ProveedorService;
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
@RequestMapping("/proveedores")
@Tag(name="Controlador Proveedores", description="Servicios de gestión de proveedores")
public class ProveedorController {

    @Autowired
    ProveedorService proveedorService;

    @Autowired
    ProveedorModelAssembler assembler;

    // C
    @PostMapping
    @Operation(summary = "Agregar Proveedor", description = "Permite registrar una proveedor en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Proveedor creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Proveedor.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud", content = @Content),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato", content = @Content)
    })
    public ResponseEntity<EntityModel<Proveedor>> crearProveedor(@RequestBody Proveedor proveedor) {
        proveedorService.saveProveedor(proveedor);
        if (proveedorService.getProveedorByID(proveedor.getId()).isPresent()) {
            return new ResponseEntity<>(assembler.toModel(proveedor), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // R
    @GetMapping
    @Operation(summary= "Obtener proveedores", description = "Obtiene la lista de proveedores registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista completa de proveedores"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    public ResponseEntity<CollectionModel<EntityModel<Proveedor>>> getProveedores(){
        List<Proveedor> proveedores = proveedorService.getProveedores();
        if (proveedores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(proveedores), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar proveedor por ID", description = "Obtiene un proveedor según la ID registrada en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna Proveedor"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    @Parameter(description = "ID del proveedor", example = "2")
    public ResponseEntity<EntityModel<Proveedor>> getProveedorById(@PathVariable int id) {
        if (proveedorService.getProveedorByID(id).isPresent()) {
            Proveedor proveedor = proveedorService.getProveedorByID(id).get();
            return new ResponseEntity<>(assembler.toModel(proveedor), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // U
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Proveedor", description = "Permite actualizar los datos de un proveedor según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Proveedor.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud", content = @Content),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato", content = @Content)
    })
    @Parameter(description = "El ID del proveedor", example = "1")
    public ResponseEntity<Proveedor> updateProveedor(@PathVariable int id, @RequestBody Proveedor proveedor) {
        if (proveedorService.getProveedorByID(id).isPresent()) {
            proveedorService.updateProveedor(proveedor, id);
            return new ResponseEntity<>(proveedor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // D
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar proveedor", description = "Elimina un proveedor específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Retorna el proveedor eliminada"),
            @ApiResponse(responseCode = "404", description="Proveedor no encontrado", content = @Content)
    })
    @Parameter(description = "La ID del proveedor", example = "1")
    public ResponseEntity<EntityModel<Proveedor>> eliminarProveedor(@PathVariable int id) {
        if (proveedorService.getProveedorByID(id).isPresent()) {
            Proveedor proveedor  = proveedorService.getProveedorByID(id).get();
            proveedorService.deleteProveedor(id);
            return new ResponseEntity<>(assembler.toModel(proveedor), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

