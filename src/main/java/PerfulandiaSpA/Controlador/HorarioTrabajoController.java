package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.HorarioTrabajo;
import PerfulandiaSpA.Servicio.HorarioTrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/HorarioTrabajo")
public class HorarioTrabajoController {

    @Autowired
    HorarioTrabajoService horarioTrabajoService;

    //CRUD

    @PostMapping()
    public String addHorarioTrabajo(@RequestBody HorarioTrabajo horarioTrabajo) {
        return "Debe indicar la sucursal del empleado en el endpoint, para ello diríjase a:\n/HorarioTrabajo/sucursal/{id_sucursal}\nEj: /HorarioTrabajo/sucursal/1";
    }

    //Crear horario
    @PostMapping("/sucursal/{id_sucursal}")
    public String addHorarioTrabajo(@RequestBody HorarioTrabajo horarioTrabajo, @PathVariable int id_sucursal) {
        return horarioTrabajoService.saveHorarioTrabajo(horarioTrabajo, id_sucursal);
    }

    //Obtener en formato ToString
    @GetMapping
    public String listarHorarioTrabajo(){
        return horarioTrabajoService.getHorariosTrabajo();
    }

    //obtener en json
    @GetMapping("/json")
    public List<HorarioTrabajo> getHorariosTrabajoJSON(){
        return horarioTrabajoService.getHorariosTrabajoJSON();
    }

    //Buscar por id
    @GetMapping("/{id}")
    public String getHorarioTrabajoById(@PathVariable int id){
        return horarioTrabajoService.getHorarioTrabajoById(id);
    }

    @PutMapping("/sucursal/{id_sucursal}")
    public String updateHorarioTrabajo(){
        return "Debe indicar la sucursal del empleado en el endpoint, para ello diríjase a:\n/HorarioTrabajo/sucursal/{id_sucursal}\nEj: /HorarioTrabajo/sucursal/1";
    }

    //Update
    @PutMapping("/sucursal/{id_sucursal}")
    public String updateHorarioTrabajoSucursal(@RequestBody HorarioTrabajo horarioTrabajo, @PathVariable int id_sucursal){
        return horarioTrabajoService.updateHorarioTrabajo(horarioTrabajo, id_sucursal);
    }

    //deletear horario
    @DeleteMapping("/{id}")
    public String deleteHorarioTrabajo(@PathVariable int id){
        return horarioTrabajoService.deleteHorarioTrabajo(id);
    }





}
