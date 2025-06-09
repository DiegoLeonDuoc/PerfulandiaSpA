package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.DTO.DevolucionDTO;
import PerfulandiaSpA.Entidades.Devolucion;
import PerfulandiaSpA.Servicio.DevolucionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devoluciones")
@Tag(name="Servicio Devolucion", description="Servicios de gestión de devoluciones")
public class DevolucionController {

    @Autowired
    DevolucionService devolucionService;

    @PostMapping
    @Operation(summary= "Crear devolucion", description = "Servicio POST para registrar un devolucion")
    @ApiResponse(responseCode = "200", description="Confirmación sobre la creación del devolucion")
    public String addDevolucion(@RequestBody DevolucionDTO devolucionDTO) {
        return devolucionService.crearDevolucion(devolucionDTO);
    }

    @GetMapping
    @Operation(summary= "Obtener devolucions", description = "Servicio GET para obtener información sobre los devoluciones en formato String")
    @ApiResponse(responseCode = "200", description="Registro de devoluciones en formato texto simple")
    public String getDevoluciones() {
        return devolucionService.getDevoluciones();
    }

    @GetMapping("/{rut}")
    @Operation(summary= "Obtener devolucions por RUT", description = "Servicio GET para obtener información sobre los devoluciones asociados a un cliente en formato String")
    @ApiResponse(responseCode = "200", description="Registro de devoluciones en formato texto simple")
    public String getDevolucionesByRut(@PathVariable Integer rut) {
        return devolucionService.getDevolucionesByRut(rut);
    }

    @GetMapping("/json")
    @Operation(summary= "Obtener devolucions JSON", description = "Servicio GET para obtener información sobre los devoluciones en formato JSON")
    @ApiResponse(responseCode = "200", description="Registro de devoluciones en formato JSON")
    public List<Devolucion> getDevolucionesJSON() {
        return devolucionService.getDevolucionsJSON();
    }

    @PutMapping
    @Operation(summary= "Modificar devolucion", description = "Servicio PUT para modificar información sobre un devolucion específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa o información sobre inexistencia de atributos"),
            //@ApiResponse(responseCode = "404", description="Devolucion no encontrada")
    })
    public String updateDevolucion(@RequestBody DevolucionDTO devolucionDTO) {
        return devolucionService.updateDevolucion(devolucionDTO);
    }

    @PatchMapping("/{id}")
    @Operation(summary= "Modificar devolucion", description = "Servicio PUT para modificar información sobre un devolucion específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa o información sobre inexistencia de atributos"),
            //@ApiResponse(responseCode = "404", description="Devolucion no encontrada")
    })
    public String patchDevolucion(@RequestBody DevolucionDTO devolucion, @PathVariable Integer id) {
        return devolucionService.patchDevolucion(devolucion, id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar devolucion", description = "Servicio DELETE para eliminar registro de un devolucion específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de eliminación exitosa o información sobre inexistencia del devolucion"),
            //@ApiResponse(responseCode = "404", description="Devolucion no encontrada")
    })
    public String deleteDevolucion(@PathVariable int id) {
        return devolucionService.deleteDevolucion(id);
    }
}

