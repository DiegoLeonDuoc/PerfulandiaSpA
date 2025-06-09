package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.Administrador;
import PerfulandiaSpA.Servicio.AdministradorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administradores")
@Tag(name="Servicio Administradores", description="Servicios de gestión de administradores")
public class AdministradorController {

    @Autowired
    AdministradorService administradorService;

    @PostMapping
    @Operation(summary= "Crear administrador", description = "Servicio POST para registrar un administrador")
    @ApiResponse(responseCode = "200", description="Confirmación sobre la creación del administrador")
    public String addAdministrador(@RequestBody Administrador administrador){
        return administradorService.crearAdministrador(administrador);
    }

    @GetMapping
    @Operation(summary= "Obtener administradores", description = "Servicio GET para obtener información sobre los administradores en formato String")
    @ApiResponse(responseCode = "200", description="Registro de administradores en formato texto simple")
    public String getAdministradores(){
        return administradorService.getAdministradores();
    }

    @GetMapping("/{rut}")
    @Operation(summary= "Obtener administradores por RUT", description = "Servicio GET para obtener información sobre los administradores asociados a un cliente en formato String")
    @ApiResponse(responseCode = "200", description="Registro de administradores en formato texto simple")
    public String getAdministradorByRut(@PathVariable int rut){
        return administradorService.getAdministradorByRut(rut);
    }

    @GetMapping("/json")
    @Operation(summary= "Obtener administradores JSON", description = "Servicio GET para obtener información sobre los administradores en formato JSON")
    @ApiResponse(responseCode = "200", description="Registro de administradores en formato JSON")
    public List<Administrador> getAdministradoresJSON() { return administradorService.getAdministradoresJSON(); }

    @PutMapping("/{rut}")
    @Operation(summary= "Modificar administrador", description = "Servicio PUT para modificar información sobre un administrador específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa o información sobre inexistencia de atributos"),
            //@ApiResponse(responseCode = "404", description="Administrador no encontrado")
    })
    public String updateAdministrador(@RequestBody Administrador administrador, @PathVariable int rut) { return administradorService.updateAdministrador(administrador, rut); }

    @PatchMapping("/{rut}")
    @Operation(summary= "Modificar administrador", description = "Servicio PUT para modificar información sobre un administrador específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa o información sobre inexistencia de atributos"),
            //@ApiResponse(responseCode = "404", description="Administrador no encontrado")
    })
    public String parcharAdministrador(@RequestBody Administrador administrador, @PathVariable int rut) { return administradorService.parcharAdministrador(administrador, rut); }

    @DeleteMapping("/{rut}")
    @Operation(summary= "Eliminar administrador", description = "Servicio DELETE para eliminar registro de un administrador específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de eliminación exitosa o información sobre inexistencia del administrador"),
            //@ApiResponse(responseCode = "404", description="Administrador no encontrado")
    })
    public String deleteAdministrador(@PathVariable int rut){
        return administradorService.deleteAdministrador(rut);
    }

}
