package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.DTO.EnvioDTO;
import PerfulandiaSpA.Entidades.Envio;
import PerfulandiaSpA.Servicio.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/envios")
@Tag(name="Servicio Envios", description="Servicios de gestión de envíos")
public class EnvioController {

    @Autowired
    EnvioService envioService;

    @PostMapping
    @Operation(summary= "Crear envio", description = "Servicio POST para registrar un envio para una sucursal.")
    @ApiResponse(responseCode = "200", description="Confirmación sobre la creación del pedido")
    public String addEnvio(@RequestBody EnvioDTO envioDTO) {
        return envioService.crearEnvio(envioDTO);
    }

    @GetMapping
    @Operation(summary= "Obtener envios", description = "Servicio GET para obtener información sobre los envios en formato String")
    @ApiResponse(responseCode = "200", description="Registro de envios en formato texto simple")
    public String getEnvios() {
        return envioService.getEnvios();
    }

    @GetMapping("/{rut}")
    @Operation(summary= "Obtener envio por id", description = "Servicio GET para obtener información sobre un envio específico en formato String")
    @ApiResponse(responseCode = "200", description="Registro de envio en formato texto simple")
    public String getEnviosByRut(@PathVariable Integer rut) {
        return envioService.getEnviosByRut(rut);
    }

    @GetMapping("/sucursal/{idSucursal}")
    @Operation(summary= "Obtener envios por sucursal", description = "Servicio GET para obtener información sobre los envios de una sucursal específica en formato JSON")
    @ApiResponse(responseCode = "200", description="Registro de envio en formato JSON")
    public String getEnviosBySucursal(@PathVariable Integer idSucursal) {
        return envioService.getEnviosSucursal(idSucursal);
    }

    @GetMapping("/sucursal/{idSucursal}/json")
    @Operation(summary= "Obtener envios por sucursal", description = "Servicio GET para obtener información sobre los envios de una sucursal específica en formato JSON")
    @ApiResponse(responseCode = "200", description="Registro de envio en formato JSON")
    public List<Envio> getEnviosBySucursalJSON(@PathVariable Integer idSucursal) {
        return envioService.getEnviosBySucursalJSON(idSucursal);
    }

    @GetMapping("/json")
    @Operation(summary= "Obtener envios JSON", description = "Servicio GET para obtener información sobre los envios en formato JSON")
    @ApiResponse(responseCode = "200", description="Registro de envios en formato JSON")
    public List<Envio> getEnviosJSON() {
        return envioService.getEnviosJSON();
    }

    @PutMapping
    @Operation(summary= "Modificar envio", description = "Servicio PUT para modificar información sobre un envio específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa o información sobre inexistencia de atributos"),
            //@ApiResponse(responseCode = "404", description="Horario no encontrado")
    })
    public String updateEnvio(@RequestBody EnvioDTO envioDTO) {
        return envioService.updateEnvio(envioDTO);
    }

    @PatchMapping("/{id}")
    @Operation(summary= "Modificar envio", description = "Servicio PUT para modificar información sobre un envio específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa o información sobre inexistencia de atributos"),
            //@ApiResponse(responseCode = "404", description="Horario no encontrado")
    })
    public String parcharEnvio(@RequestBody EnvioDTO envio, @PathVariable Integer id) {
        return envioService.parcharEnvio(envio, id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar pedido", description = "Servicio DELETE para eliminar registro de un envio específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de eliminación exitosa o información sobre inexistencia del pedido"),
            //@ApiResponse(responseCode = "404", description="Horario no encontrado")
    })
    public String deleteEnvio(@PathVariable int id) {
        return envioService.deleteEnvio(id);
    }
}

