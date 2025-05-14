package PerfulandiaSpA.Controlador;

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

    @GetMapping
    public String getUsuarios(){
        return usuarioService.getUsuarios();
    }

    @GetMapping("/json")
    public List<Usuario> getUsuariosJSON(){
        return usuarioService.getUsuariosJSON();
    }

    @GetMapping("/{rut}")
    public String getUsuarioByRut(@PathVariable int rut){
        return usuarioService.getUsuarioByRut(rut);
    }

    @PostMapping
    public String addUsuario(@RequestBody Usuario usuario){
        return usuarioService.saveUsuario(usuario);
    }

    @DeleteMapping("/{rut}")
    public String deleteUsuario(@PathVariable int rut){
        return usuarioService.deleteUsuario(rut);
    }

//    @DeleteMapping("/{rut}")
//    public String deleteUsuario(@PathVariable int rut) {
//        if (usuarioService.getUsuarioByRut(rut) == null) {
//            return "No se encontro el usuario con el rut: " + rut;
//        }else{
//            return usuarioService.deleteUsuario(rut);
//        }
//    }

    @PutMapping("/{rut}")
    public String updateUsuario(@RequestBody Usuario usuario, @PathVariable int rut) {return usuarioService.updateUsuario(usuario, rut);}

}
