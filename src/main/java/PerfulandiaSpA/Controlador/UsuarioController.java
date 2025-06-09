package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.Usuario;
import PerfulandiaSpA.Servicio.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name="Servicio Usuarios", description="Servicios de gestión de usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    @Operation(summary= "Crear usuario", description = "Servicio NO disponibilizado. Entrega información acorde.")
    @ApiResponse(responseCode = "200", description="Entrega información sobre los endpoints disponibilizados para la creación de usuarios")
    public String addUsuario(){
        //return usuarioService.crearUsuario(usuario);
        return "No puede crear un usuario aquí, dirígase al endpoint correspondiente.\nEndpoints disponibles:\n/clientes\n/empleados\n/administrador";
    }

    @GetMapping
    @Operation(summary= "Obtener usuarios", description = "Servicio GET para obtener información sobre usuarios en formato String")
    @ApiResponse(responseCode = "200", description="Registro de usuarios en formato texto simple")
    public String getUsuarios(){
        return usuarioService.getUsuarios();
    }

    @GetMapping("/{rut}")
    @Operation(summary= "Obtener usuario por RUT", description = "Servicio GET para obtener información sobre un usuario específico en formato String")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Registro del usuario en formato texto simple o información de usuario no encontrado."),
            //@ApiResponse(responseCode = "404", description="Usuario no encontrado")
    })
    //@Parameter(description= "RUT del usuario", required = true)
    public String getUsuarioByRut(@PathVariable int rut){
        return usuarioService.getUsuarioByRut(rut);
    }

    @GetMapping("/json")
    @Operation(summary= "Obtener usuario en formato JSON", description = "Servicio GET para obtener información sobre usuarios en formato JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Registro de usuarios en formato JSON"),
            @ApiResponse(responseCode = "404", description="No existen usuarios registrados")
    })
    public List<Usuario> getUsuariosJSON(){
        return usuarioService.getUsuariosJSON();
    }

    @PutMapping("/{rut}")
    @Operation(summary= "Modificar usuario", description = "Servicio NO disponibilizado. Entrega información acorde.")
    @ApiResponse(responseCode = "200", description="Entrega información sobre los endpoints disponibilizados para la modificación de usuarios")
    public String updateUsuario() {
        // return usuarioService.updateUsuario(usuario, rut);
        return "No puede actualizar un usuario aquí, dirígase al endpoint correspondiente.\nEndpoints disponibles:\n/clientes\n/empleados\n/administrador";
    }

    @PatchMapping("/{rut}")
    @Operation(summary= "Parchar usuario", description = "Servicio NO disponibilizado. Entrega información acorde.")
    @ApiResponse(responseCode = "200", description="Entrega información sobre los endpoints disponibilizados para la edición parcial de usuarios")
    public String parcharUsuario() {
        // return usuarioService.parcharUsuario(usuario, rut);
        return "No puede actualizar un usuario aquí, dirígase al endpoint correspondiente.\nEndpoints disponibles:\n/clientes\n/empleados\n/administrador";
    }

    @DeleteMapping("/{rut}")
    @Operation(summary= "Eliminar usuario", description = "Servicio DELETE para eliminar un usuario específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación sobre la eliminación del usuario o información de usuario no encontrado."),
            //@ApiResponse(responseCode = "404", description="Usuario no encontrado")
    })
    public String deleteUsuario(@PathVariable int rut){
        return usuarioService.deleteUsuario(rut);
    }

}
