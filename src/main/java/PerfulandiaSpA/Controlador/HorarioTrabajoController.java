package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Assembler.HorarioTrabajoModelAssembler;
import PerfulandiaSpA.DTO.HorarioTrabajoDTO;
import PerfulandiaSpA.Entidades.HorarioTrabajo;
import PerfulandiaSpA.Servicio.HorarioTrabajoService;
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
@RequestMapping("/horarioTrabajo")
@Tag(name="Controlador HorarioTrabajo", description="Servicios de gestión de horarioTrabajo")
public class HorarioTrabajoController {

    @Autowired
    HorarioTrabajoService horarioTrabajoService;

    @Autowired
    HorarioTrabajoModelAssembler assembler;

    // C
    @PostMapping
    @Operation(summary = "Agregar horario de trabajo", description = "Permite registrar un horario de trabajo en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Horario de trabajo creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HorarioTrabajo.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    public ResponseEntity<EntityModel<HorarioTrabajo>> crearHorarioTrabajo(@RequestBody HorarioTrabajoDTO horarioTrabajoDTO) {
        HorarioTrabajo horarioTrabajo = horarioTrabajoService.saveHorarioTrabajo(horarioTrabajoDTO);
        if (horarioTrabajoService.getHorarioTrabajoByID(horarioTrabajo.getId()).isPresent()) {
            return new ResponseEntity<>(assembler.toModel(horarioTrabajo), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // R
    @GetMapping
    @Operation(summary= "Obtener horario de trabajo", description = "Obtiene la lista de horarios de trabajo registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista completa de horarioTrabajos"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    public ResponseEntity<CollectionModel<EntityModel<HorarioTrabajo>>> getHorarioTrabajos(){
        List<HorarioTrabajo> horarioTrabajos = horarioTrabajoService.getHorariosTrabajo();
        if (horarioTrabajos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(horarioTrabajos), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar horarioTrabajo por ID", description = "Obtiene un horario de trabajo según la ID registrada en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna Horario de trabajo"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    @Parameter(description = "ID del horario de trabajo", example = "2")
    public ResponseEntity<EntityModel<HorarioTrabajo>> getHorarioTrabajoById(@PathVariable int id) {
        Optional<HorarioTrabajo> horarioTrabajoOptional = horarioTrabajoService.getHorarioTrabajoByID(id);
        if (horarioTrabajoOptional.isPresent()) {
            return new ResponseEntity<>(assembler.toModel(horarioTrabajoOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // U
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar horario de trabajo", description = "Permite actualizar los datos de un horario de trabajo según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Horario de trabajo modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HorarioTrabajo.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El ID del horario de trabajo", example = "1")
    public ResponseEntity<HorarioTrabajo> updateHorarioTrabajo(@PathVariable int id, @RequestBody HorarioTrabajoDTO horarioTrabajoDTO) {
        Optional<HorarioTrabajo> horarioTrabajoOptional = horarioTrabajoService.getHorarioTrabajoByID(id);
        if (horarioTrabajoOptional.isPresent()) {
            HorarioTrabajo horarioTrabajo = horarioTrabajoService.updateHorarioTrabajo(id, horarioTrabajoDTO);
            return new ResponseEntity<>(horarioTrabajo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // D
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar horario de trabajo", description = "Elimina un horario de trabajo específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Retorna el horario de trabajo eliminado"),
            @ApiResponse(responseCode = "404", description="Horario de trabajo no encontrado")
    })
    @Parameter(description = "La ID del horario de trabajo", example = "1")
    public ResponseEntity<EntityModel<HorarioTrabajo>> eliminarHorarioTrabajo(@PathVariable int id) {
        Optional<HorarioTrabajo> horarioTrabajoOptional = horarioTrabajoService.getHorarioTrabajoByID(id);
        if (horarioTrabajoOptional.isPresent()) {
            HorarioTrabajo horarioTrabajo = horarioTrabajoOptional.get();
            horarioTrabajoService.deleteHorarioTrabajo(id);
            return new ResponseEntity<>(assembler.toModel(horarioTrabajo), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

