package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.Carrito;
import PerfulandiaSpA.Servicio.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carritos")
@Tag(name="Servicio Carritos", description="Servicios de gestión de carritos")
public class CarritoController {

    @Autowired
    CarritoService carritoService;

    @PostMapping
    @Operation(summary= "Crear carrito", description = "Servicio POST para registrar un carrito")
    @ApiResponse(responseCode = "200", description="Confirmación sobre la creación del carrito")
    public String addCarrito(@RequestBody Integer rutCliente){
        return carritoService.crearCarrito(rutCliente);
    }

    @GetMapping
    @Operation(summary= "Obtener carritos", description = "Servicio GET para obtener información sobre los carritos en formato String")
    @ApiResponse(responseCode = "200", description="Registro de carritos en formato texto simple")
    public String getCarritos(){
        return carritoService.getCarritos();
    }

    @GetMapping("/{rut}")
    @Operation(summary= "Obtener carritos por RUT", description = "Servicio GET para obtener información sobre los carritos asociados a un cliente en formato String")
    @ApiResponse(responseCode = "200", description="Registro de carritos en formato texto simple")
    public String getCarritoByRut(@PathVariable Integer rut){
        return carritoService.getCarritoByRut(rut);
    }

    @GetMapping("/json")
    @Operation(summary= "Obtener carritos JSON", description = "Servicio GET para obtener información sobre los carritos en formato JSON")
    @ApiResponse(responseCode = "200", description="Registro de carritos en formato JSON")
    public List<Carrito> getCarritosJSON() { return carritoService.getCarritosJSON(); }
    
    @DeleteMapping("/{rutCliente}")
    @Operation(summary= "Eliminar carrito", description = "Servicio DELETE para eliminar registro de un carrito específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de eliminación exitosa o información sobre inexistencia del carrito"),
            //@ApiResponse(responseCode = "404", description="CarritPedidoo no encontrado")
    })
    public String deleteCarrito(@RequestBody Integer idCarrito, @PathVariable Integer rutCliente){
        return carritoService.deleteCarrito(idCarrito, rutCliente);
    }

}
