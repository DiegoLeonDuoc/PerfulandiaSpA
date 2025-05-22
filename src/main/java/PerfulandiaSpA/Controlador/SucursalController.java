package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Servicio.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sucursal")
public class SucursalController {

    @Autowired
    SucursalService sucursalService;

    //CRUD

    //Crear sucursal
    @PostMapping()
    public String addSucursal(@RequestBody Sucursal sucursal) {
        return sucursalService.saveSucursal(sucursal);
    }

    //Obtener en formato ToString
    @GetMapping
    public String listarSucursales(){
        return sucursalService.getSucursales();
    }

    //obtener en json
    @GetMapping("/json")
    public List<Sucursal> getHorariosTrabajoJSON(){
        return sucursalService.getSucursalesJSON();
    }

    //Buscar por id
    @GetMapping("/{id}")
    public String getSucursalById(@PathVariable int id){
        return sucursalService.getSucursalByID(id);
    }

    //Update
    @PutMapping("/{id}")
    public String updateSucursalSucursal(@RequestBody Sucursal Sucursal, @PathVariable int id){
        return sucursalService.updateSucursal(Sucursal, id);
    }

    //deletear horario
    @DeleteMapping("/{id}")
    public String deleteSucursal(@PathVariable int id){
        return sucursalService.deleteSucursal(id);
    }

}
