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

    @GetMapping("/{id}")
    public String getUsuarioByRut(@PathVariable int id){
        return usuarioService.getUsuarioByRut(id);
    }

    @PostMapping
    public String addUsuario(@RequestBody Usuario usuario){
        return usuarioService.saveUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    public String deleteUsuario(@PathVariable int id){
        return usuarioService.deleteUsuario(id);
    }

//    @DeleteMapping("/{id}")
//    public String deleteUsuario(@PathVariable int id) {
//        if (usuarioService.getUsuarioByRut(id) == null) {
//            return "No se encontro el usuario con el id: " + id;
//        }else{
//            return usuarioService.deleteUsuario(id);
//        }
//    }

    @PutMapping("/{id}")
    public String updateUsuario(@RequestBody Usuario usuario, @PathVariable int id) {return usuarioService.updateUsuario(usuario, id);}

}
