package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.DTO.PedidoDTO;
import PerfulandiaSpA.Entidades.Pedido;
import PerfulandiaSpA.Servicio.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@Tag(name="Servicio Pedidos", description="Servicios de gestión de pedidos")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @PostMapping
    @Operation(summary= "Crear pedido", description = "Servicio POST para registrar un pedido")
    @ApiResponse(responseCode = "200", description="Confirmación sobre la creación del pedido")
    public String addPedido(@RequestBody PedidoDTO pedidoDTO){
        return pedidoService.crearPedido(pedidoDTO);
    }

    @GetMapping
    @Operation(summary= "Obtener pedidos", description = "Servicio GET para obtener información sobre los pedidos en formato String")
    @ApiResponse(responseCode = "200", description="Registro de pedidos en formato texto simple")
    public String getPedidos(){
        return pedidoService.getPedidos();
    }

    @GetMapping("/{rut}")
    @Operation(summary= "Obtener pedidos por RUT", description = "Servicio GET para obtener información sobre los pedidos asociados a un cliente en formato String")
    @ApiResponse(responseCode = "200", description="Registro de pedidos en formato texto simple")
    public String getPedidoByRut(@PathVariable Integer rut){
        return pedidoService.getPedidosByRut(rut);
    }

    @GetMapping("/sucursal/{idSucursal}")
    @Operation(summary= "Obtener pedidos por sucursal", description = "Servicio GET para obtener información sobre los pedidos asociados a una sucursal específica en formato String")
    @ApiResponse(responseCode = "200", description="Registro de pedidos en formato texto simple")
    public String getPedidosBySucursal(@PathVariable Integer idSucursal){
        return pedidoService.getPedidosSucursal(idSucursal);
    }

    @GetMapping("/sucursal/{idSucursal}/json")
    @Operation(summary= "Obtener pedidos por sucursal JSON", description = "Servicio GET para obtener información sobre los pedidos asociados a una sucursal específica en formato JSON")
    @ApiResponse(responseCode = "200", description="Registro de pedidos en formato JSON")
    public List<Pedido> getPedidosBySucursalJSON(@PathVariable Integer idSucursal){
        return pedidoService.getPedidosBySucursalJSON(idSucursal);
    }

    @GetMapping("/json")
    @Operation(summary= "Obtener pedidos JSON", description = "Servicio GET para obtener información sobre los pedidos en formato JSON")
    @ApiResponse(responseCode = "200", description="Registro de pedidos en formato JSON")
    public List<Pedido> getPedidosJSON() { return pedidoService.getPedidosJSON(); }

    @PutMapping
    @Operation(summary= "Modificar pedido", description = "Servicio PUT para modificar información sobre un pedido específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa o información sobre inexistencia de atributos"),
            //@ApiResponse(responseCode = "404", description="Pedido no encontrado")
    })
    public String updatePedido(@RequestBody PedidoDTO pedidoDTO) { return pedidoService.updatePedido(pedidoDTO); }

    @PatchMapping("/{id}")
    @Operation(summary= "Modificar pedido", description = "Servicio PUT para modificar información sobre un pedido específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa o información sobre inexistencia de atributos"),
            //@ApiResponse(responseCode = "404", description="Pedido no encontrado")
    })
    public String parcharPedido(@RequestBody PedidoDTO pedido, @PathVariable Integer id) { return pedidoService.parcharPedido(pedido, id); }

    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar pedido", description = "Servicio DELETE para eliminar registro de un pedido específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de eliminación exitosa o información sobre inexistencia del pedido"),
            //@ApiResponse(responseCode = "404", description="Pedido no encontrado")
    })
    public String deletePedido(@PathVariable int id){
        return pedidoService.deletePedido(id);
    }


}
