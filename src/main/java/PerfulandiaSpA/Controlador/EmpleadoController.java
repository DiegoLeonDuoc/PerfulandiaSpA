package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Assembler.EmpleadoModelAssembler;
import PerfulandiaSpA.DTO.EmpleadoDTO;
import PerfulandiaSpA.Entidades.Empleado;
import PerfulandiaSpA.Servicio.EmpleadoService;
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
@RequestMapping("/empleado")
@Tag(name="Controlador Empleado", description="Servicios de gestión de empleado")
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;

    @Autowired
    EmpleadoModelAssembler assembler;

    // C
    @PostMapping
    @Operation(summary = "Agregar Empleado", description = "Permite registrar un empleado en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Empleado.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenruto en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    public ResponseEntity<EntityModel<Empleado>> crearEmpleado(@RequestBody EmpleadoDTO empleadoDTO) {
        Empleado empleado = empleadoService.crearEmpleado(empleadoDTO);
        if (empleadoService.getEmpleadoByRut(empleado.getRutUsuario()).isPresent()) {
            return new ResponseEntity<>(assembler.toModel(empleado), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // R
    @GetMapping
    @Operation(summary= "Obtener empleados", description = "Obtiene la lista de empleados registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista completa de empleados"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    public ResponseEntity<CollectionModel<EntityModel<Empleado>>> getEmpleados(){
        List<Empleado> empleados = empleadoService.getEmpleados();
        if (empleados.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(empleados), HttpStatus.OK);
        }
    }

    @GetMapping("/{rut}")
    @Operation(summary = "Buscar empleado por ID", description = "Obtiene un empleado según la ID registrada en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna Empleado"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    @Parameter(description = "El RUT del empleado", example = "12345678")
    public ResponseEntity<EntityModel<Empleado>> getEmpleadoById(@PathVariable int rut) {
        Optional<Empleado> empleadoOptional = empleadoService.getEmpleadoByRut(rut);
        if (empleadoOptional.isPresent()) {
            return new ResponseEntity<>(assembler.toModel(empleadoOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sucursal/{id}")
    @Operation(summary = "Buscar empleados por ID de sucursal", description = "Obtiene los empleados según la ID de la sucursal registrada en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista de Empleados"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    @Parameter(description = "El RUT del empleado", example = "12345678")
    public ResponseEntity<CollectionModel<EntityModel<Empleado>>> getEmpleadoBySucursal(@PathVariable int id) {
        List<Empleado> empleados = empleadoService.getEmpleadosBySucursal(id);
        if (empleados.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(empleados), HttpStatus.OK);
        }
    }

    // U
    @PutMapping("/{rut}")
    @Operation(summary = "Actualizar empleado", description = "Permite actualizar los datos de un empleado según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Empleado.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenruto en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El RUT del empleado", example = "12345678")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable int rut, @RequestBody EmpleadoDTO empleadoDTO) {
        Optional<Empleado> empleadoOptional = empleadoService.getEmpleadoByRut(rut);
        if (empleadoOptional.isPresent()) {
            Empleado empleado = empleadoService.updateEmpleado(empleadoDTO, rut);
            return new ResponseEntity<>(empleado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{rut}")
    @Operation(summary = "Parchar Empleado", description = "Permite actualizar parcialmente los datos de un empleado según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Empleado.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenruto en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El RUT del empleado", example = "12345678")
    public ResponseEntity<Empleado> parcharEmpleado(@PathVariable int rut, @RequestBody EmpleadoDTO empleadoDTO) {
        Optional<Empleado> empleadoOptional = empleadoService.getEmpleadoByRut(rut);
        if (empleadoOptional.isPresent()) {
            Empleado empleado = empleadoService.patchEmpleado(empleadoDTO, rut);
            return new ResponseEntity<>(empleado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // D
    @DeleteMapping("/{rut}")
    @Operation(summary= "Eliminar empleado", description = "Elimina un empleado específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Retorna el empleado eliminado"),
            @ApiResponse(responseCode = "404", description="Empleado no encontrado")
    })
    @Parameter(description = "El RUT del empleado", example = "12345678")
    public ResponseEntity<EntityModel<Empleado>> eliminarEmpleado(@PathVariable int rut) {
        Optional<Empleado> empleadoOptional = empleadoService.getEmpleadoByRut(rut);
        if (empleadoOptional.isPresent()) {
            Empleado empleado = empleadoOptional.get();
            empleadoService.deleteEmpleado(rut);
            return new ResponseEntity<>(assembler.toModel(empleado), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

