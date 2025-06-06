package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.DTO.EmpleadoDTO;
import PerfulandiaSpA.Entidades.Empleado;
import PerfulandiaSpA.Servicio.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;

    @PostMapping
    public String crearEmpleado(@RequestBody EmpleadoDTO empleado){
        return empleadoService.crearEmpleado(empleado);
    }

    @GetMapping
    public String getEmpleados(){
        return empleadoService.getEmpleados();
    }

    @GetMapping("/{rut}")
    public String getEmpleadoByRut(@PathVariable int rut){
        return empleadoService.getEmpleadoByRut(rut);
    }

    @GetMapping("/json")
    public List<Empleado> getEmpleadosJSON() { return empleadoService.getEmpleadosJSON(); }

    @GetMapping("/sucursal/{id_sucursal}")
    public String getEmpleadosEnSucursal(@PathVariable int id_sucursal){
        return empleadoService.getEmpleadosSucursal(id_sucursal);
    }

    @GetMapping("/sucursal/{id_sucursal}/json")
    public List<Empleado> getEmpleadosEnSucursalJSON(@PathVariable int id_sucursal){
        return empleadoService.getEmpleadosSucursalJSON(id_sucursal);
    }

    @PutMapping("/{rut}")
    public String updateEmpleado(@RequestBody EmpleadoDTO empleado, @PathVariable int rut) { return empleadoService.updateEmpleado(empleado, rut); }

    @PatchMapping("/{rut}")
    public String parcharEmpleado(@RequestBody EmpleadoDTO empleado, @PathVariable int rut) { return empleadoService.parcharEmpleado(empleado, rut); }

    @DeleteMapping("/{rut}")
    public String deleteEmpleado(@PathVariable int rut){
        return empleadoService.deleteEmpleado(rut);
    }

}
