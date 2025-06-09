package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.DTO.ProductosPedidoDTO;
import PerfulandiaSpA.Entidades.ProductosPedido;
import PerfulandiaSpA.Servicio.ProductosPedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productosPedido")
@Tag(name="Servicio ProductosPedido", description="Servicios de gestión de productos en pedidos")
public class ProductosPedidoController {

    @Autowired
    private ProductosPedidoService productosPedidoService;

    //CRUD

    //CREATE productosPedido
    @PostMapping
    @Operation(summary= "Crear productoPedido", description = "Servicio POST para registrar un producto en un pedido")
    @ApiResponse(responseCode = "200", description="Confirmación sobre la creación del productoPedido")
    public String addProductosPedido(@RequestBody ProductosPedidoDTO productosPedidoDTO) {
        return productosPedidoService.crearProductosPedido(productosPedidoDTO);
    }

    //Obtener en formato ToString
    @GetMapping
    @Operation(summary= "Obtener productosPedidos", description = "Servicio GET para obtener información sobre los productos en los pedidos en formato String")
    @ApiResponse(responseCode = "200", description="Registro de productos pedidos en formato texto simple")
    public String listarProductosPedidos() {
        return productosPedidoService.getProductosPedidos();
    }

    //obtener en json
    @GetMapping("/json")
    @Operation(summary= "Obtener productosPedidos JSON", description = "Servicio GET para obtener información sobre los producto pedidos en formato JSON")
    @ApiResponse(responseCode = "200", description="Registro de productos pedidos en formato JSON")
    public List<ProductosPedido> getProductosPedidoJSON() {
        return productosPedidoService.getProductosPedidosJSON();
    }

    //Buscar por rut
    @GetMapping("/{rut}")
    @Operation(summary= "Obtener productosPedidos por RUT", description = "Servicio GET para obtener información sobre los productos pedidos asociados a un RUT en formato String")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Registro de los productos pedidos por un cliente en formato texto simple o información sobre inexistencia del producto en pedido"),
            //@ApiResponse(responseCode = "404", description="El cliente no posee productos pedidos asociados")
    })
    public String getProductosPedidoById(@PathVariable int rut) {
        return productosPedidoService.getProductosPedidosByRut(rut);
    }

    //Update
    @PutMapping
    @Operation(summary= "Modificar productoPedido", description = "Servicio PUT para modificar información sobre un producto pedido específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa o información sobre inexistencia de atributos"),
            //@ApiResponse(responseCode = "404", description="Producto pedido no encontrado")
    })
    public String updateProductosPedido(@RequestBody ProductosPedidoDTO productosPedidoDTO) {
        return productosPedidoService.updateProductosPedido(productosPedidoDTO);
    }

    @PatchMapping("/{id}")
    @Operation(summary= "Parchar productoPedido", description = "Servicio PATCH para modificar información sobre un producto pedido específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa o información sobre inexistencia de atributos"),
            //@ApiResponse(responseCode = "404", description="Producto pedido no encontrado")
    })
    public String parcharProductosPedido(@RequestBody ProductosPedidoDTO productosPedidoDTO, @PathVariable Integer id) {
        return productosPedidoService.patchProductosPedido(productosPedidoDTO, id);
    }

    //deletear productosPedido
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar productoPedido", description = "Servicio DELETE para eliminar registro de un producto pedido específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de eliminación exitosa"),
            //@ApiResponse(responseCode = "404", description="Producto pedido no encontrado")
    })
    public String deleteProductosPedido(@PathVariable int id) {
        return productosPedidoService.deleteProductosPedido(id);
    }
}