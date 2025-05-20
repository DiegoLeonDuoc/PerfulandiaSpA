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

    @GetMapping("/json")
    public List<Pedido> getPedidosJSON() { return pedidoService.getPedidosJSON(); }

    @PutMapping("/{rut}")
    public String updatePedido(@RequestBody PedidoDTO pedidoDTO) { return pedidoService.updatePedido(pedidoDTO); }

//    @PatchMapping("/{rut}")
//    public String parcharPedido(@RequestBody Pedido pedido, @PathVariable Integer rut) { return pedidoService.parcharPedido(pedido, rut); }

    @DeleteMapping("/{rut}")
    public String deletePedido(@PathVariable int rut){
        return pedidoService.deletePedido(rut);
    }

}
