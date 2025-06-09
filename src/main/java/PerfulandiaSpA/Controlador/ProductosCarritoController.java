package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.DTO.ProductosCarritoDTO;
import PerfulandiaSpA.Entidades.ProductosCarrito;
import PerfulandiaSpA.Servicio.ProductosCarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productosCarrito")
@Tag(name="Servicio ProductosCarrito", description="Servicios de gestión de productos en carritos")
public class ProductosCarritoController {

    @Autowired
    private ProductosCarritoService productosCarritoService;

    //CRUD

    //CREATE productosCarrito
    @PostMapping
    @Operation(summary= "Crear productoCarrito", description = "Servicio POST para registrar un producto en un carrito")
    @ApiResponse(responseCode = "200", description="Confirmación sobre la creación del productoCarrito")
    public String addProductosCarrito(@RequestBody ProductosCarritoDTO productosCarritoDTO) {
        return productosCarritoService.crearProductosCarrito(productosCarritoDTO);
    }

    //Obtener en formato ToString
    @GetMapping
    @Operation(summary= "Obtener productosCarritos", description = "Servicio GET para obtener información sobre los productos en los carritos en formato String")
    @ApiResponse(responseCode = "200", description="Registro de productos carritos en formato texto simple")
    public String listarProductosCarritos() {
        return productosCarritoService.getProductosCarritos();
    }

    //obtener en json
    @GetMapping("/json")
    @Operation(summary= "Obtener productosCarritos JSON", description = "Servicio GET para obtener información sobre los producto carritos en formato JSON")
    @ApiResponse(responseCode = "200", description="Registro de productos carritos en formato JSON")
    public List<ProductosCarrito> getProductosCarritoJSON() {
        return productosCarritoService.getProductosCarritosJSON();
    }

    //Buscar por rut
    @GetMapping("/{rut}")
    @Operation(summary= "Obtener productosCarritos por RUT", description = "Servicio GET para obtener información sobre los productos carritos asociados a un RUT en formato String")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Registro de los productos carritos por un cliente en formato texto simple o información sobre inexistencia del producto en carrito"),
            //@ApiResponse(responseCode = "404", description="El cliente no posee productos carritos asociados")
    })
    public String getProductosCarritoById(@PathVariable int rut) {
        return productosCarritoService.getProductosCarritosByRut(rut);
    }

    //Update
    @PutMapping
    @Operation(summary= "Modificar productoCarrito", description = "Servicio PUT para modificar información sobre un producto carrito específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa o información sobre inexistencia de atributos"),
           // @ApiResponse(responseCode = "404", description="Producto carrito no encontrado")
    })
    public String updateProductosCarrito(@RequestBody ProductosCarritoDTO productosCarritoDTO) {
        return productosCarritoService.updateProductosCarrito(productosCarritoDTO);
    }

    @PatchMapping("/{id}")
    @Operation(summary= "Parchar productoCarrito", description = "Servicio PATCH para modificar información sobre un producto carrito específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa o información sobre inexistencia de atributos"),
            //@ApiResponse(responseCode = "404", description="Producto carrito no encontrado")
    })
    public String parcharProductosCarrito(@RequestBody ProductosCarritoDTO productosCarritoDTO, @PathVariable Integer id) {
        return productosCarritoService.patchProductosCarrito(productosCarritoDTO, id);
    }

    //deletear productosCarrito
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar productoCarrito", description = "Servicio DELETE para eliminar registro de un producto carrito específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de eliminación exitosa o información sobre inexistencia del producto en carrito"),
            //@ApiResponse(responseCode = "404", description="Producto carrito no encontrado")
    })
    public String deleteProductosCarrito(@PathVariable int id) {
        return productosCarritoService.deleteProductosCarrito(id);
    }
}