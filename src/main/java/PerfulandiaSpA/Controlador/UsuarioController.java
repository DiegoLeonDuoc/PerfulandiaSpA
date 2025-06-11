package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Assembler.UsuarioModelAssembler;
import PerfulandiaSpA.Servicio.UsuarioService;
import PerfulandiaSpA.Entidades.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Controlador Usuarios", description = "Servicio de gestion de usuarios Fullstack I")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioModelAssembler assembler;

      // C
//    @PostMapping
//    @Operation(summary= "Crear usuario", description = "Servicio NO disponibilizado. Entrega información acorde.")
//    @ApiResponse(responseCode = "200", description="Entrega información sobre los endpoints disponibilizados para la creación de usuarios")
//    public String crearUsuario(){
//        //return usuarioService.crearUsuario(usuario);
//        return "No puede crear un usuario aquí, dirígase al endpoint correspondiente.\nEndpoints disponibles:\n/clientes\n/empleados\n/administrador";
//    }

    // R
    @GetMapping
    @Operation(summary= "Obtener usuarios", description = "Obtiene la lista de usuarios registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista completa de usuarios"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos")
    })
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> getUsuarios(){
        List<Usuario> usuarios = usuarioService.getUsuarios();
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(usuarios), HttpStatus.OK);
        }
    }

    @GetMapping("/{rut}")
    @Operation(summary = "Buscar usuario por RUT", description = "Obtiene un usuario segun el RUT registrado en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna Usuario"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos")
    })
    @Parameter(description = "El RUT del usuario SIN dígito verificador", example = "12345678")
    public ResponseEntity<EntityModel<Usuario>> getUsuarioById(@PathVariable int rut) {
        if (usuarioService.getUsuarioByRut(rut).isPresent()) {
            Usuario usuario = usuarioService.getUsuarioByRut(rut).get();
            return new ResponseEntity<>(assembler.toModel(usuario), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PutMapping("/{rut}")
//    @Operation(summary= "Modificar usuario", description = "Servicio NO disponibilizado. Entrega información acorde.")
//    @ApiResponse(responseCode = "200", description="Entrega información sobre los endpoints disponibilizados para la modificación de usuarios")
//    public String updateUsuario() {
//        // return usuarioService.updateUsuario(usuario, rut);
//        return "No puede actualizar un usuario aquí, dirígase al endpoint correspondiente.\nEndpoints disponibles:\n/clientes\n/empleados\n/administrador";
//    }
//
//    @PatchMapping("/{rut}")
//    @Operation(summary= "Parchar usuario", description = "Servicio NO disponibilizado. Entrega información acorde.")
//    @ApiResponse(responseCode = "200", description="Entrega información sobre los endpoints disponibilizados para la edición parcial de usuarios")
//    public String parcharUsuario() {
//        // return usuarioService.parcharUsuario(usuario, rut);
//        return "No puede actualizar un usuario aquí, dirígase al endpoint correspondiente.\nEndpoints disponibles:\n/clientes\n/empleados\n/administrador";
//    }

    @DeleteMapping("/{rut}")
    @Operation(summary= "Eliminar usuario", description = "Elimina un usuario específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Retorna el usuario eliminado"),
            @ApiResponse(responseCode = "404", description="Usuario no encontrado")
    })
    @Parameter(description = "El RUT del usuario SIN dígito verificador", example = "12345678")
    public ResponseEntity<EntityModel<Usuario>> eliminarUsuario(@PathVariable int rut) {
        if (usuarioService.getUsuarioByRut(rut).isPresent()) {
            Usuario usuario  = usuarioService.getUsuarioByRut(rut).get();
            usuarioService.deleteUsuario(rut);
            return new ResponseEntity<>(assembler.toModel(usuario), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}