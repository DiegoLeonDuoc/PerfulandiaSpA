package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Servicio.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
@Tag(name="Servicio Producto", description="Servicios de gestión de productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    //CRUD

    //CREATE producto
    @PostMapping
    @Operation(summary= "Crear producto", description = "Servicio POST para registrar un producto")
    @ApiResponse(responseCode = "200", description="Confirmación sobre la creación del producto")
    public String addProducto(@RequestBody Producto Producto) {
        return productoService.saveProducto(Producto);
    }

    //Obtener en formato ToString
//    @GetMapping
//    @Operation(summary= "Obtener productos", description = "Servicio GET para obtener información sobre los productos en formato String")
//    @ApiResponse(responseCode = "200", description="Registro de productos en formato texto simple")
//    public String listarProductos() {
//        return productoService.getProductos();
//    }

    //obtener en json
    @GetMapping("/json")
    @Operation(summary= "Obtener productos JSON", description = "Servicio GET para obtener información sobre los producto en formato JSON")
    @ApiResponse(responseCode = "200", description="Registro de productos en formato JSON")
    public List<Producto> getProductosJSON(){
        return productoService.getProductosJSON();
    }

    //Buscar por rut
    @GetMapping("/{codBarrProd}")
    @Operation(summary= "Obtener producto por código de barra", description = "Servicio GET para obtener información sobre un producto específico en formato String")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Registro de los productos por un cliente en formato texto simple o información sobre inexistencia del producto"),
            //@ApiResponse(responseCode = "404", description="Producto no encontrado")
    })
    public String getProductoByCodBarr(@PathVariable Long codBarrProd){
        return productoService.getProductoByCodigoBarra(codBarrProd);
    }

    //Update
    @PutMapping("/{id}")
    @Operation(summary= "Modificar producto", description = "Servicio PUT para modificar información sobre un producto específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa o información sobre inexistencia de atributos"),
            //@ApiResponse(responseCode = "404", description="Producto no encontrado")
    })
    public String updateProducto(@RequestBody Producto producto, @PathVariable int id) {
        return productoService.updateProducto(producto, id);
    }

    //deletear producto
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar producto", description = "Servicio DELETE para eliminar registro de un producto específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de eliminación exitosa o información sobre inexistencia del producto"),
            //@ApiResponse(responseCode = "404", description="Producto no encontrado")
    })
    public String deleteProducto(@PathVariable int id) {
        return productoService.deleteProducto(id);
    }
}