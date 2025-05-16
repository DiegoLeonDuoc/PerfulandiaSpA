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

    //Crear horario
    @PostMapping
    public String addHorarioTrabajo(@RequestBody HorarioTrabajo horarioTrabajo){
        return horarioTrabajoService.saveHorarioTrabajo(horarioTrabajo);
    }

    //deletear horario
    @DeleteMapping("/{id}")
    public String deleteHorarioTrabajo(@PathVariable int id){
        return horarioTrabajoService.deleteHorarioTrabajo(id);
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

    //Update
    @PutMapping("/{id}")
    public String updateHorarioTrabajo(@RequestBody HorarioTrabajo horarioTrabajo, @PathVariable int id){
        return horarioTrabajoService.updateHorarioTrabajo(horarioTrabajo, id);
    }

}
