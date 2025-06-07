package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Servicio.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sucursal")
@Tag(name="Servicio Sucursal", description="Servicios de gestión de sucursales")
public class SucursalController {

    @Autowired
    SucursalService sucursalService;

    //CRUD

    //Crear sucursal
    @PostMapping()
    @Operation(summary= "Crear sucursal", description = "Servicio POST para crear sucursal")
    @ApiResponse(responseCode = "200", description="Confirmación sobre la creación de la sucursal")
    public String addSucursal(@RequestBody Sucursal sucursal) {
        return sucursalService.saveSucursal(sucursal);
    }

    //Obtener en formato ToString
    @GetMapping
    @Operation(summary= "Obtener sucursales", description = "Servicio GET para obtener información sobre sucursales en formato String")
    @ApiResponse(responseCode = "200", description="Registro de sucursales en formato texto simple")
    public String listarSucursales(){
        return sucursalService.getSucursales();
    }

    //obtener en json
    @GetMapping("/json")
    @Operation(summary= "Obtener sucursales JSON", description = "Servicio GET para obtener información sobre sucursales en formato JSON")
    @ApiResponse(responseCode = "200", description="Registro de sucursales en formato JSON")
    public List<Sucursal> getHorariosTrabajoJSON(){
        return sucursalService.getSucursalesJSON();
    }

    //Buscar por id
    @GetMapping("/{id}")
    @Operation(summary= "Obtener sucursal por ID", description = "Servicio GET para obtener información sobre una sucursal específica en formato String")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Registro de la sucursal en formato texto simple"),
            @ApiResponse(responseCode = "404", description="Sucursal no encontrada")
    })
    public String getSucursalById(@PathVariable int id){
        return sucursalService.getSucursalByID(id);
    }

    //Update
    @PutMapping("/{id}")
    @Operation(summary= "Modificar sucursal", description = "Servicio PUT para modificar información sobre una sucursal específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa"),
            @ApiResponse(responseCode = "404", description="Sucursal no encontrada")
    })
    public String updateSucursalSucursal(@RequestBody Sucursal Sucursal, @PathVariable int id){
        return sucursalService.updateSucursal(Sucursal, id);
    }

    //deletear horario
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar sucursal", description = "Servicio DELETE para eliminar registro de una sucursal específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de eliminación exitosa"),
            @ApiResponse(responseCode = "404", description="Sucursal no encontrada")
    })
    public String deleteSucursal(@PathVariable int id){
        return sucursalService.deleteSucursal(id);
    }

}
