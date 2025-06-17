package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Assembler.ReabastecimientoModelAssembler;
import PerfulandiaSpA.DTO.ReabastecimientoDTO;
import PerfulandiaSpA.Entidades.Reabastecimiento;
import PerfulandiaSpA.Servicio.ReabastecimientoService;
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
@RequestMapping("/reabastecimientos")
@Tag(name="Controlador Reabastecimiento", description="Servicios de gestión de reabastecimientos")
public class ReabastecimientoController {

    @Autowired
    ReabastecimientoService reabastecimientoService;

    @Autowired
    ReabastecimientoModelAssembler assembler;

    // C
    @PostMapping
    @Operation(summary = "Agregar Reabastecimiento", description = "Permite registrar un reabastecimiento en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reabastecimiento creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reabastecimiento.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    public ResponseEntity<EntityModel<Reabastecimiento>> crearReabastecimiento(@RequestBody ReabastecimientoDTO reabastecimientoDTO) {
        Reabastecimiento reabastecimiento = reabastecimientoService.saveReabastecimiento(reabastecimientoDTO);
        if (reabastecimientoService.getReabastecimientoByID(reabastecimiento.getId()).isPresent()) {
            return new ResponseEntity<>(assembler.toModel(reabastecimiento), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // R
    @GetMapping
    @Operation(summary= "Obtener reabastecimientos", description = "Obtiene la lista de reabastecimientos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista completa de reabastecimientos"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    public ResponseEntity<CollectionModel<EntityModel<Reabastecimiento>>> getReabastecimientos(){
        List<Reabastecimiento> reabastecimientos = reabastecimientoService.getReabastecimientos();
        if (reabastecimientos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(reabastecimientos), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reabastecimiento por ID", description = "Obtiene un reabastecimiento según la ID registrada en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna Reabastecimiento"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    @Parameter(description = "ID del reabastecimiento", example = "2")
    public ResponseEntity<EntityModel<Reabastecimiento>> getReabastecimientoById(@PathVariable int id) {
        Optional<Reabastecimiento> reabastecimientoOptional = reabastecimientoService.getReabastecimientoByID(id);
        if (reabastecimientoOptional.isPresent()) {
            return new ResponseEntity<>(assembler.toModel(reabastecimientoOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // U
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Reabastecimiento", description = "Permite actualizar los datos de un reabastecimiento según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reabastecimiento modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reabastecimiento.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El ID del reabastecimiento", example = "1")
    public ResponseEntity<Reabastecimiento> updateReabastecimiento(@PathVariable int id, @RequestBody ReabastecimientoDTO reabastecimientoDTO) {
        Optional<Reabastecimiento> reabastecimientoOptional = reabastecimientoService.getReabastecimientoByID(id);
        if (reabastecimientoOptional.isPresent()) {
            Reabastecimiento reabastecimiento = reabastecimientoService.updateReabastecimiento(id, reabastecimientoDTO);
            return new ResponseEntity<>(reabastecimiento, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // D
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar reabastecimiento", description = "Elimina un reabastecimiento específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Retorna el reabastecimiento eliminado"),
            @ApiResponse(responseCode = "404", description="Reabastecimiento no encontrado")
    })
    @Parameter(description = "La ID del reabastecimiento", example = "1")
    public ResponseEntity<EntityModel<Reabastecimiento>> eliminarReabastecimiento(@PathVariable int id) {
        Optional<Reabastecimiento> reabastecimientoOptional = reabastecimientoService.getReabastecimientoByID(id);
        if (reabastecimientoOptional.isPresent()) {
            Reabastecimiento reabastecimiento = reabastecimientoOptional.get();
            reabastecimientoService.deleteReabastecimiento(id);
            return new ResponseEntity<>(assembler.toModel(reabastecimiento), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

