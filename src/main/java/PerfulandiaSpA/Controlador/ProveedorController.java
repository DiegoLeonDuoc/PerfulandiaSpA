package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.Proveedor;
import PerfulandiaSpA.Servicio.ProveedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedor")
@Tag(name="Servicio Proveedor", description="Servicios de gestión de proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    //CRUD

    //CREATE proveedor
    @PostMapping
    @Operation(summary= "Crear proveedor", description = "Servicio POST para crear proveedor")
    @ApiResponse(responseCode = "200", description="Confirmación sobre la creación del proveedor")
    public String addProveedor(@RequestBody Proveedor proveedor) {
        return proveedorService.saveProveedor(proveedor);
    }

    //Obtener en formato ToString
    @GetMapping
    @Operation(summary= "Obtener proveedores", description = "Servicio GET para obtener información sobre los proveedores en formato String")
    @ApiResponse(responseCode = "200", description="Registro de proveedores en formato texto simple")
    public String listarProveedors() {
        return proveedorService.getProveedores();
    }

    //obtener en json
    @GetMapping("/json")
    @Operation(summary= "Obtener proveedors JSON", description = "Servicio GET para obtener información sobre los proveedors en formato JSON")
    @ApiResponse(responseCode = "200", description="Registro de proveedores en formato JSON")
    public List<Proveedor> getProveedorJSON() {
        return proveedorService.getProveedoresJSON();
    }

    //Buscar por id
    @GetMapping("/{id}")
    @Operation(summary= "Obtener proveedors por ID", description = "Servicio GET para obtener información sobre un proveedor específico en formato String")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Registro del proveedor en formato texto simple o información sobre inexistencia del proveedor"),
            //@ApiResponse(responseCode = "404", description="Proveedor no encontrado")
    })
    public String getProveedorById(@PathVariable int id) {
        return proveedorService.getProveedorById(id);
    }

    //Update
    @PutMapping("/{id}")
    @Operation(summary= "Modificar proveedor", description = "Servicio PUT para modificar información sobre un proveedor específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa o información sobre inexistencia del proveedor"),
            //@ApiResponse(responseCode = "404", description="Proveedor no encontrado")
    })
    public String updateProveedor(@RequestBody Proveedor Proveedor, @PathVariable int id) {
        return proveedorService.updateProveedor(Proveedor, id);
    }

    //deletear proveedor
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar proveedor", description = "Servicio DELETE para eliminar registro de un proveedor en específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de eliminación exitosa o información sobre inexistencia del proveedor"),
            //@ApiResponse(responseCode = "404", description="Proveedor no encontrado")
    })
    public String deleteProveedor(@PathVariable int id) {
        return proveedorService.deleteProveedor(id);
    }
}

