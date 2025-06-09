package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.DTO.EmpleadoDTO;
import PerfulandiaSpA.Entidades.Empleado;
import PerfulandiaSpA.Servicio.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleados")
@Tag(name="Servicio Empleados", description="Servicios de gestión de empleados")
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;

    @PostMapping
    @Operation(summary= "Crear empleado", description = "Servicio POST para registrar un empleado")
    @ApiResponse(responseCode = "200", description="Confirmación sobre la creación del empleado")
    public String crearEmpleado(@RequestBody EmpleadoDTO empleado){
        return empleadoService.crearEmpleado(empleado);
    }

    @GetMapping
    @Operation(summary= "Obtener empleados", description = "Servicio GET para obtener información sobre los empleados en formato String")
    @ApiResponse(responseCode = "200", description="Registro de empleados en formato texto simple")
    public String getEmpleados(){
        return empleadoService.getEmpleados();
    }

    @GetMapping("/{rut}")
    @Operation(summary= "Obtener empleados por RUT", description = "Servicio GET para obtener información sobre los empleados en formato String")
    @ApiResponse(responseCode = "200", description="Registro de empleados en formato texto simple")
    public String getEmpleadoByRut(@PathVariable int rut){
        return empleadoService.getEmpleadoByRut(rut);
    }

    @GetMapping("/json")
    @Operation(summary= "Obtener empleados JSON", description = "Servicio GET para obtener información sobre los empleados en formato JSON")
    @ApiResponse(responseCode = "200", description="Registro de empleados en formato JSON")
    public List<Empleado> getEmpleadosJSON() { return empleadoService.getEmpleadosJSON(); }

    @GetMapping("/sucursal/{id_sucursal}")
    @Operation(summary= "Obtener empleados por sucursal", description = "Servicio GET para obtener información sobre los empleados asociados a una sucursal específica en formato String")
    @ApiResponse(responseCode = "200", description="Registro de empleados en formato texto simple")
    public String getEmpleadosEnSucursal(@PathVariable int id_sucursal){
        return empleadoService.getEmpleadosSucursal(id_sucursal);
    }

    @GetMapping("/sucursal/{id_sucursal}/json")
    @Operation(summary= "Obtener empleados por sucursal JSON", description = "Servicio GET para obtener información sobre los empleados asociados a una sucursal específica en formato JSON")
    @ApiResponse(responseCode = "200", description="Registro de empleados en formato JSON")
    public List<Empleado> getEmpleadosEnSucursalJSON(@PathVariable int id_sucursal){
        return empleadoService.getEmpleadosSucursalJSON(id_sucursal);
    }

    @PutMapping("/{rut}")
    @Operation(summary= "Modificar empleado", description = "Servicio PUT para modificar información sobre un empleado específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa o información sobre inexistencia de atributos"),
            //@ApiResponse(responseCode = "404", description="Empleado no encontrado")
    })
    public String updateEmpleado(@RequestBody EmpleadoDTO empleado, @PathVariable int rut) { return empleadoService.updateEmpleado(empleado, rut); }

    @PatchMapping("/{rut}")
    @Operation(summary= "Modificar empleado", description = "Servicio PUT para modificar información sobre un empleado específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa o información sobre inexistencia de atributos"),
            //@ApiResponse(responseCode = "404", description="Empleado no encontrado")
    })
    public String parcharEmpleado(@RequestBody EmpleadoDTO empleado, @PathVariable int rut) { return empleadoService.parcharEmpleado(empleado, rut); }

    @DeleteMapping("/{rut}")
    @Operation(summary= "Eliminar empleado", description = "Servicio DELETE para eliminar registro de un empleado específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de eliminación exitosa o información sobre inexistencia del empleado"),
            //@ApiResponse(responseCode = "404", description="Empleado no encontrado")
    })
    public String deleteEmpleado(@PathVariable int rut){
        return empleadoService.deleteEmpleado(rut);
    }

}
