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

    @GetMapping("/{id}")
    public String getEmpleadoByRut(@PathVariable int id){
        return empleadoService.getEmpleadoByRut(id);
    }

    @PostMapping
    public String addEmpleado(@RequestBody Empleado empleado){
        return empleadoService.saveEmpleado(empleado);
    }

    @DeleteMapping("/{id}")
    public String deleteEmpleado(@PathVariable int id){
        return empleadoService.deleteEmpleado(id);
    }

    @PutMapping("/{id}")
    public String updateEmpleado(@RequestBody Empleado empleado, @PathVariable int id) {return empleadoService.updateEmpleado(empleado, id);}

}
