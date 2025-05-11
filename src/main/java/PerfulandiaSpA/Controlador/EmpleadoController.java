package PerfulandiaSpA.Controlador;

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

    @GetMapping
    public String getEmpleados(){
        return empleadoService.getEmpleados();
    }

    @GetMapping("/json")
    public List<Empleado> getEmpleadosJSON(){
        return empleadoService.getEmpleadosJSON();
    }

    @GetMapping("/{rut}")
    public String getEmpleadoByRut(@PathVariable int rut){
        return empleadoService.getEmpleadoByRut(rut);
    }

    @PostMapping
    public String addEmpleado(@RequestBody Empleado empleado){
        return empleadoService.saveEmpleado(empleado);
    }

    @DeleteMapping("/{rut}")
    public String deleteEmpleado(@PathVariable int rut){
        return empleadoService.deleteEmpleado(rut);
    }

    @PutMapping("/{rut}")
    public String updateEmpleado(@RequestBody Empleado empleado, @PathVariable int rut) {return empleadoService.updateEmpleado(empleado, rut);}

}
