package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Assembler.DescuentoModelAssembler;
import PerfulandiaSpA.DTO.DescuentoDTO;
import PerfulandiaSpA.Entidades.Descuento;
import PerfulandiaSpA.Servicio.DescuentoService;
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
@RequestMapping("/descuento")
@Tag(name="Controlador Descuento", description="Servicios de gestión de descuento")
public class DescuentoController {

    @Autowired
    DescuentoService descuentoService;

    @Autowired
    DescuentoModelAssembler assembler;

    // C
    @PostMapping
    @Operation(summary = "Agregar Descuento", description = "Permite registrar un descuento en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Descuento creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Descuento.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    public ResponseEntity<EntityModel<Descuento>> crearDescuento(@RequestBody DescuentoDTO descuentoDTO) {
        Descuento descuento = descuentoService.crearDescuento(descuentoDTO);
        if (descuentoService.getDescuentoByID(descuento.getId()).isPresent()) {
            return new ResponseEntity<>(assembler.toModel(descuento), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // R
    @GetMapping
    @Operation(summary= "Obtener descuentos", description = "Obtiene la lista de descuentos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista completa de descuentos"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    public ResponseEntity<CollectionModel<EntityModel<Descuento>>> getDescuentos(){
        List<Descuento> descuentos = descuentoService.getDescuentos();
        if (descuentos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(descuentos), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar descuento por ID", description = "Obtiene un descuento según la ID registrada en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna Descuento"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    @Parameter(description = "ID del descuento", example = "2")
    public ResponseEntity<EntityModel<Descuento>> getDescuentoById(@PathVariable int id) {
        Optional<Descuento> descuentoOptional = descuentoService.getDescuentoByID(id);
        if (descuentoOptional.isPresent()) {
            return new ResponseEntity<>(assembler.toModel(descuentoOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // U
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar descuento", description = "Permite actualizar los datos de un descuento según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Descuento modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Descuento.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El ID del descuento", example = "1")
    public ResponseEntity<Descuento> updateDescuento(@PathVariable int id, @RequestBody DescuentoDTO descuentoDTO) {
        Optional<Descuento> descuentoOptional = descuentoService.getDescuentoByID(id);
        if (descuentoOptional.isPresent()) {
            Descuento descuento = descuentoService.updateDescuento(descuentoDTO, id);
            return new ResponseEntity<>(descuento, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Parchar Descuento", description = "Permite actualizar parcialmente los datos de un descuento según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Descuento modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Descuento.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El ID del descuento", example = "1")
    public ResponseEntity<Descuento> parcharDescuento(@PathVariable int id, @RequestBody DescuentoDTO descuentoDTO) {
        Optional<Descuento> descuentoOptional = descuentoService.getDescuentoByID(id);
        if (descuentoOptional.isPresent()) {
            Descuento descuento = descuentoService.patchDescuento(descuentoDTO, id);
            return new ResponseEntity<>(descuento, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // D
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar descuento", description = "Elimina un descuento específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Retorna el descuento eliminado"),
            @ApiResponse(responseCode = "404", description="Descuento no encontrado")
    })
    @Parameter(description = "La ID del descuento", example = "1")
    public ResponseEntity<EntityModel<Descuento>> eliminarDescuento(@PathVariable int id) {
        Optional<Descuento> descuentoOptional = descuentoService.getDescuentoByID(id);
        if (descuentoOptional.isPresent()) {
            Descuento descuento = descuentoOptional.get();
            descuentoService.deleteDescuento(id);
            return new ResponseEntity<>(assembler.toModel(descuento), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

