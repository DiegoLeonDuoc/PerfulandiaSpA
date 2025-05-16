package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.Administrador;
import PerfulandiaSpA.Servicio.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administradors")
public class AdministradorController {

    @Autowired
    AdministradorService administradorService;

    @PostMapping
    public String addAdministrador(@RequestBody Administrador administrador){
        return administradorService.crearAdministrador(administrador);
    }

    @GetMapping
    public String getAdministradors(){
        return administradorService.getAdministradores();
    }

    @GetMapping("/{rut}")
    public String getAdministradorByRut(@PathVariable int rut){
        return administradorService.getAdministradorByRut(rut);
    }

    @GetMapping("/json")
    public List<Administrador> getAdministradorsJSON() { return administradorService.getAdministradorsJSON(); }

    @PutMapping("/{rut}")
    public String updateAdministrador(@RequestBody Administrador administrador, @PathVariable int rut) { return administradorService.updateAdministrador(administrador, rut); }

    @PatchMapping("/{rut}")
    public String parcharAdministrador(@RequestBody Administrador administrador, @PathVariable int rut) { return administradorService.parcharAdministrador(administrador, rut); }

    @DeleteMapping("/{rut}")
    public String deleteAdministrador(@PathVariable int rut){
        return administradorService.deleteAdministrador(rut);
    }

}
