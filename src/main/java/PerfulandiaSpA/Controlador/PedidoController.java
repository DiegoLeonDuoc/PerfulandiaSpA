package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.DTO.PedidoDTO;
import PerfulandiaSpA.Entidades.Pedido;
import PerfulandiaSpA.Servicio.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @PostMapping
    public String addPedido(@RequestBody PedidoDTO pedidoDTO){
        return pedidoService.crearPedido(pedidoDTO);
    }

    @GetMapping
    public String getPedidos(){
        return pedidoService.getPedidos();
    }

    @GetMapping("/{rut}")
    public String getPedidoByRut(@PathVariable Integer rut){
        return pedidoService.getPedidosByRut(rut);
    }

    @GetMapping("/sucursal/{idSucursal}")
    public String getPedidosBySucursal(@PathVariable Integer idSucursal){
        return pedidoService.getPedidosSucursal(idSucursal);
    }

    @GetMapping("/sucursal/{idSucursal}/json")
    public List<Pedido> getPedidosBySucursalJSON(@PathVariable Integer idSucursal){
        return pedidoService.getPedidosBySucursalJSON(idSucursal);
    }

    @GetMapping("/json")
    public List<Pedido> getPedidosJSON() { return pedidoService.getPedidosJSON(); }

    @PutMapping
    public String updatePedido(@RequestBody PedidoDTO pedidoDTO) { return pedidoService.updatePedido(pedidoDTO); }

    @PatchMapping("/{id}")
    public String parcharPedido(@RequestBody PedidoDTO pedido, @PathVariable Integer id) { return pedidoService.parcharPedido(pedido, id); }

    @DeleteMapping("/{id}")
    public String deletePedido(@PathVariable int id){
        return pedidoService.deletePedido(id);
    }


}
