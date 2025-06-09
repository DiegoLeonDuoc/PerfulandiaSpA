package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.HorarioTrabajo;
import PerfulandiaSpA.Servicio.HorarioTrabajoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/HorarioTrabajo")
@Tag(name="Servicio Horario de Trabajo", description="Servicios de gestión de horarios de trabajo")
public class HorarioTrabajoController {

    @Autowired
    HorarioTrabajoService horarioTrabajoService;

    //CRUD

    @PostMapping()
    @Operation(summary= "Crear horario de trabajo", description = "Servicio NO disponibilizado. Entrega información acorde.")
    @ApiResponse(responseCode = "200", description="Entrega información sobre los endpoints disponibilizados para la creación de horarios de trabajo")
    public String addHorarioTrabajo() {
        return "Debe indicar la sucursal del horario en el endpoint, para ello diríjase a:\n/HorarioTrabajo/sucursal/{id_sucursal}\nEj: /HorarioTrabajo/sucursal/1";
    }

    //Crear horario
    @PostMapping("/sucursal/{id_sucursal}")
    @Operation(summary= "Crear horario de trabajo", description = "Servicio POST para registrar un horario de trabajo para una sucursal.")
    @ApiResponse(responseCode = "200", description="Confirmación sobre la creación del pedido")
    public String addHorarioTrabajo(@RequestBody HorarioTrabajo horarioTrabajo, @PathVariable int id_sucursal) {
        return horarioTrabajoService.saveHorarioTrabajo(horarioTrabajo, id_sucursal);
    }

    //Obtener en formato ToString
    @GetMapping
    @Operation(summary= "Obtener horarios de trabajos", description = "Servicio GET para obtener información sobre los horarios de trabajo en formato String")
    @ApiResponse(responseCode = "200", description="Registro de horarios en formato texto simple")
    public String listarHorarioTrabajo(){
        return horarioTrabajoService.getHorariosTrabajo();
    }

    //obtener en json
    @GetMapping("/json")
    @Operation(summary= "Obtener horarios de trabajos JSON", description = "Servicio GET para obtener información sobre los horarios de trabajo en formato JSON")
    @ApiResponse(responseCode = "200", description="Registro de horarios en formato JSON")
    public List<HorarioTrabajo> getHorariosTrabajoJSON(){
        return horarioTrabajoService.getHorariosTrabajoJSON();
    }

    //Buscar por id
    @GetMapping("/{id}")
    @Operation(summary= "Obtener horario de trabajo por id", description = "Servicio GET para obtener información sobre un horario de trabajo específico en formato String")
    @ApiResponse(responseCode = "200", description="Registro de horario en formato texto simple")
    public String getHorarioTrabajoById(@PathVariable int id){
        return horarioTrabajoService.getHorarioTrabajoById(id);
    }

    //Buscar por sucursal
    @GetMapping("/sucursal/{idSucursal}/json")
    @Operation(summary= "Obtener horarios de trabajo por sucursal", description = "Servicio GET para obtener información sobre los horarios de trabajo de una sucursal específica en formato JSON")
    @ApiResponse(responseCode = "200", description="Registro de horario en formato JSON")
    public List<HorarioTrabajo> getHorarioTrabajoBySucursalJSON(@PathVariable int idSucursal){
        return horarioTrabajoService.getHorarioTrabajoBySucursalJSON(idSucursal);
    }

    @PutMapping("/{id}")
    @Operation(summary= "Modificar horario de trabajo", description = "Servicio NO disponibilizado. Entrega información acorde.")
    @ApiResponse(responseCode = "200", description="Entrega información sobre los endpoints disponibilizados para la modificación de horarios de trabajo")
    public String updateHorarioTrabajo(){
        return "Debe indicar la sucursal del empleado en el endpoint, para ello diríjase a:\n/HorarioTrabajo/sucursal/{id_sucursal}\nEj: /HorarioTrabajo/sucursal/1";
    }

    //Update
    @PutMapping("/sucursal/{idSucursal}")
    @Operation(summary= "Modificar horario de trabajo", description = "Servicio PUT para modificar información sobre un horario de trabajo específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa o información sobre inexistencia de atributos"),
            //@ApiResponse(responseCode = "404", description="Horario no encontrado")
    })
    public String updateHorarioTrabajoSucursal(@RequestBody HorarioTrabajo horarioTrabajo, @PathVariable int idSucursal){
        return horarioTrabajoService.updateHorarioTrabajo(horarioTrabajo, idSucursal);
    }

    //deletear horario
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar pedido", description = "Servicio DELETE para eliminar registro de un horario de trabajo específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de eliminación exitosa o información sobre inexistencia del pedido"),
            //@ApiResponse(responseCode = "404", description="Horario no encontrado")
    })
    public String deleteHorarioTrabajo(@PathVariable int id){
        return horarioTrabajoService.deleteHorarioTrabajo(id);
    }





}
