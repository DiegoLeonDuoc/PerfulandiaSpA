package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.Reabastecimiento;
import PerfulandiaSpA.Servicio.ReabastecimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reabastecimientos")
public class ReabastecimientoController {

    @Autowired
    private ReabastecimientoService reabastecimientoService;

    //CRUD

    //CREATE reabastecimiento
    @PostMapping
    @Operation(summary= "Crear reabastecimiento", description = "Servicio POST para crear reabastecimiento")
    @ApiResponse(responseCode = "200", description="Confirmación sobre la creación del reabastecimiento")
    public String addReabastecimiento(@RequestBody Reabastecimiento reabastecimiento) {
        return reabastecimientoService.saveReabastecimiento(reabastecimiento);
    }

    //Obtener en formato ToString
    @GetMapping
    @Operation(summary= "Obtener reabastecimientos", description = "Servicio GET para obtener información sobre los reabastecimientos en formato String")
    @ApiResponse(responseCode = "200", description="Registro de reabastecimientos en formato texto simple")
    public String listarReabastecimientos() {
        return reabastecimientoService.getReabastecimientos();
    }

    //obtener en json
    @GetMapping("/json")
    @Operation(summary= "Obtener reabastecimientos JSON", description = "Servicio GET para obtener información sobre los reabastecimientos en formato JSON")
    @ApiResponse(responseCode = "200", description="Registro de reabastecimientos en formato JSON")
    public List<Reabastecimiento> getReabastecimientoJSON() {
        return reabastecimientoService.getReabastecimientosJSON();
    }

    //Buscar por id
    @GetMapping("/{id}")
    @Operation(summary= "Obtener reabastecimientos por ID", description = "Servicio GET para obtener información sobre un reabastecimiento específico en formato String")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Registro del reabastecimiento en formato texto simple"),
            @ApiResponse(responseCode = "404", description="Reabastecimiento no encontrado")
    })
    public String getReabastecimientoById(@PathVariable int id) {
        return reabastecimientoService.getReabastecimientoById(id);
    }

    //Update
    @PutMapping("/{id}")
    @Operation(summary= "Modificar reabastecimiento", description = "Servicio PUT para modificar información sobre un reabastecimiento específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa"),
            @ApiResponse(responseCode = "404", description="Reabastecimiento no encontrado")
    })
    public String updateReabastecimiento(@RequestBody Reabastecimiento Reabastecimiento, @PathVariable int id) {
        return reabastecimientoService.updateReabastecimiento(Reabastecimiento, id);
    }

    //deletear reabastecimiento
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar reabastecimiento", description = "Servicio DELETE para eliminar registro de un reabastecimiento en específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de eliminación exitosa"),
            @ApiResponse(responseCode = "404", description="Reabastecimiento no encontrado")
    })
    public String deleteReabastecimiento(@PathVariable int id) {
        return reabastecimientoService.deleteReabastecimiento(id);
    }
}

