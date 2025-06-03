package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.Usuario;
import PerfulandiaSpA.Servicio.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name="Servicio Usuarios", description="Servicios de gestión para usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    public String addUsuario(){
        //return usuarioService.crearUsuario(usuario);
        return "No puede crear un usuario aquí, dirígase al endpoint correspondiente.\nEndpoints disponibles: /clientes\n/empleados\n/administrador";
    }

    @GetMapping
    @Operation(summary= "Obtener usuarios", description = "Servicio GET para obtener información sobre usuarios en formato String")
    @ApiResponse(responseCode = "200", description="Retorna lista de usuario en formato texto simple")
    public String getUsuarios(){
        return usuarioService.getUsuarios();
    }

    @GetMapping("/{rut}")
    @Operation(summary= "Obtener usuario por RUT", description = "Servicio GET para obtener información sobre un usuario específico en formato String")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Retorna registro del usuario en formato texto simple"),
            @ApiResponse(responseCode = "404", description="Usuario no encontrado")
    })
    //@Parameter(description= "RUT del usuario", required = true)
    public String getUsuarioByRut(@PathVariable int rut){
        return usuarioService.getUsuarioByRut(rut);
    }

    @GetMapping("/json")
    public List<Usuario> getUsuariosJSON(){
        return usuarioService.getUsuariosJSON();
    }

    @PutMapping("/{rut}")
    public String updateUsuario() {
        // return usuarioService.updateUsuario(usuario, rut);
        return "No puede actualizar un usuario aquí, dirígase al endpoint correspondiente.\nEndpoints disponibles:\n/clientes\n/empleados\n/administrador";
    }

    @PatchMapping("/{rut}")
    public String parcharUsuario() {
        // return usuarioService.parcharUsuario(usuario, rut);
        return "No puede actualizar un usuario aquí, dirígase al endpoint correspondiente.\nEndpoints disponibles:\n/clientes\n/empleados\n/administrador";
    }

    @DeleteMapping("/{rut}")
    public String deleteUsuario(@PathVariable int rut){
        return usuarioService.deleteUsuario(rut);
    }

}
