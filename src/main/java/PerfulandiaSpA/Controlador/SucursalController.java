package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Assembler.SucursalModelAssembler;
import PerfulandiaSpA.Servicio.SucursalService;
import PerfulandiaSpA.Entidades.Sucursal;
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
@RequestMapping("/sucursales")
@Tag(name = "Controlador Sucursales", description = "Servicio de gestión de sucursales")
public class SucursalController {

    @Autowired
    SucursalService sucursalService;

    @Autowired
    SucursalModelAssembler assembler;

    // C
    @PostMapping
    @Operation(summary = "Agregar Sucursal", description = "Permite registrar una sucursal en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucursal creada",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Sucursal.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud", content = @Content),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato", content = @Content)
    })
    public ResponseEntity<EntityModel<Sucursal>> crearSucursal(@RequestBody Sucursal sucursal) {
        sucursalService.saveSucursal(sucursal);
        if (sucursalService.getSucursalByID(sucursal.getId()).isPresent()) {
            return new ResponseEntity<>(assembler.toModel(sucursal), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // R
    @GetMapping
    @Operation(summary= "Obtener sucursales", description = "Obtiene la lista de sucursales registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista completa de sucursales"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    public ResponseEntity<CollectionModel<EntityModel<Sucursal>>> getSucursales(){
        List<Sucursal> sucursales = sucursalService.getSucursales();
        if (sucursales.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(sucursales), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sucursal por ID", description = "Obtiene una sucursal según la ID registrada en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna Sucursal"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    @Parameter(description = "ID de la sucursal", example = "2")
    public ResponseEntity<EntityModel<Sucursal>> getSucursalById(@PathVariable int id) {
        if (sucursalService.getSucursalByID(id).isPresent()) {
            Sucursal sucursal = sucursalService.getSucursalByID(id).get();
            return new ResponseEntity<>(assembler.toModel(sucursal), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // U
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Sucursal", description = "Permite actualizar los datos de una sucursal según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursal modificada",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Sucursal.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud", content = @Content),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato", content = @Content)
    })
    @Parameter(description = "El ID de la sucursal", example = "1")
    public ResponseEntity<Sucursal> updateSucursal(@PathVariable int id, @RequestBody Sucursal sucursal) {
        if (sucursalService.getSucursalByID(id).isPresent()) {
            sucursalService.updateSucursal(sucursal, id);
            return new ResponseEntity<>(sucursal, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // D
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar sucursal", description = "Elimina una sucursal específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Retorna la sucursal eliminada"),
            @ApiResponse(responseCode = "404", description="Sucursal no encontrado", content = @Content)
    })
    @Parameter(description = "La ID de la sucursal", example = "1")
    public ResponseEntity<EntityModel<Sucursal>> eliminarSucursal(@PathVariable int id) {
        if (sucursalService.getSucursalByID(id).isPresent()) {
            Sucursal sucursal  = sucursalService.getSucursalByID(id).get();
            sucursalService.deleteSucursal(id);
            return new ResponseEntity<>(assembler.toModel(sucursal), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}