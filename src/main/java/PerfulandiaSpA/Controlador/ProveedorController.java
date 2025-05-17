package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.Proveedor;
import PerfulandiaSpA.Servicio.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Proveedor")
public class ProveedorController {

    @Autowired
    ProveedorService proveedorService;

    //CRUD

    //Crear proveedor
    @PostMapping()
    public String addProveedor(@RequestBody Proveedor proveedor) {
        return proveedorService.saveProveedor(proveedor);
    }

    //Obtener en formato ToString
    @GetMapping
    public String listarProveedores(){
        return proveedorService.getProveedores();
    }

    //obtener en json
    @GetMapping("/json")
    public List<Proveedor> getHorariosTrabajoJSON(){
        return proveedorService.getProveedoresJSON();
    }

    //Buscar por id
    @GetMapping("/{id}")
    public String getProveedorById(@PathVariable int id){
        return proveedorService.getProveedorById(id);
    }

    //Update
    @PutMapping("/{id}")
    public String updateProveedorSucursal(@RequestBody Proveedor Proveedor, @PathVariable int id){
        return proveedorService.updateProveedor(Proveedor, id);
    }

    //deletear horario
    @DeleteMapping("/{id}")
    public String deleteProveedor(@PathVariable int id){
        return proveedorService.deleteProveedor(id);
    }

}
