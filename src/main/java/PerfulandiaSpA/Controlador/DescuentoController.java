package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.Descuento;
import PerfulandiaSpA.Servicio.DescuentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/descuentos")
@Tag(name="Servicio Descuentos", description="Servicios de gestión de descuentos")
public class DescuentoController {

    @Autowired
    DescuentoService descuentoService;

    // CREATE
    @PostMapping
    @Operation(summary= "Crear descuento", description = "Servicio POST para registrar un descuento")
    @ApiResponse(responseCode = "200", description="Confirmación sobre la creación del descuento")
    public String addDescuento(@RequestBody Descuento descuento) {
        return descuentoService.saveDescuento(descuento);
    }

    // READ - formato toString
    @GetMapping
    @Operation(summary= "Obtener descuentos", description = "Servicio GET para obtener información sobre los descuentos en formato String")
    @ApiResponse(responseCode = "200", description="Registro de descuentos en formato texto simple")
    public String listarDescuentos() {
        return descuentoService.getDescuentos();
    }

    // READ - formato JSON
    @GetMapping("/json")
    @Operation(summary= "Obtener descuentos JSON", description = "Servicio GET para obtener información sobre los descuentos en formato JSON")
    @ApiResponse(responseCode = "200", description="Registro de descuentos en formato JSON")
    public List<Descuento> getDescuentosJSON() {
        return descuentoService.getDescuentosJSON();
    }

    // READ por ID
    @GetMapping("/{id}")
    @Operation(summary= "Obtener descuentos por ID", description = "Servicio GET para obtener información sobre los descuentos en formato String")
    @ApiResponse(responseCode = "200", description="Registro de descuentos en formato texto simple")
    public String getDescuentoById(@PathVariable Integer id) {
        return descuentoService.getDescuentoById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    @Operation(summary= "Modificar descuento", description = "Servicio PUT para modificar información sobre un descuento específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa o información sobre inexistencia de atributos"),
            //@ApiResponse(responseCode = "404", description="Descuento no encontrado")
    })
    public String updateDescuento(@RequestBody Descuento descuento, @PathVariable Integer id) {
        return descuentoService.updateDescuento(descuento, id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar descuento", description = "Servicio DELETE para eliminar registro de un descuento específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de eliminación exitosa o información sobre inexistencia del descuento"),
            //@ApiResponse(responseCode = "404", description="Descuento no encontrado")
    })
    public String deleteDescuento(@PathVariable Integer id) {
        return descuentoService.deleteDescuento(id);
    }
}

