package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Entidades.Usuario;
import PerfulandiaSpA.Servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    public String addUsuario(@RequestBody Usuario usuario){
        //return usuarioService.crearUsuario(usuario);
        return "No puede crear un usuario aquí, dirígase al endpoint correspondiente.\nEndpoints disponibles: /clientes\n/empleados\n/administrador";
    }

    @GetMapping
    public String getUsuarios(){
        return usuarioService.getUsuarios();
    }

    @GetMapping("/{rut}")
    public String getUsuarioByRut(@PathVariable int rut){
        return usuarioService.getUsuarioByRut(rut);
    }

    @GetMapping("/json")
    public List<Usuario> getUsuariosJSON(){
        return usuarioService.getUsuariosJSON();
    }

    @PutMapping("/{rut}")
    public String updateUsuario(@RequestBody Usuario usuario, @PathVariable int rut) {
        // return usuarioService.updateUsuario(usuario, rut);
        return "No puede actualizar un usuario aquí, dirígase al endpoint correspondiente.\nEndpoints disponibles:\n/clientes\n/empleados\n/administrador";
    }

    @PatchMapping("/{rut}")
    public String parcharUsuario(@RequestBody Usuario usuario, @PathVariable int rut) {
        // return usuarioService.parcharUsuario(usuario, rut);
        return "No puede actualizar un usuario aquí, dirígase al endpoint correspondiente.\nEndpoints disponibles:\n/clientes\n/empleados\n/administrador";
    }

    @DeleteMapping("/{rut}")
    public String deleteUsuario(@PathVariable int rut){
        return usuarioService.deleteUsuario(rut);
    }

}
