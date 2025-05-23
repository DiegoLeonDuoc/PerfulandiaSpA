package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.DTO.ProductosPedidoDTO;
import PerfulandiaSpA.Entidades.ProductosPedido;
import PerfulandiaSpA.Servicio.ProductosPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productosPedido")
public class ProductosPedidoController {

    @Autowired
    ProductosPedidoService productosPedidoService;

    @PostMapping
    public String addProductosPedido(@RequestBody ProductosPedidoDTO productosPedidoDTO) {
        return productosPedidoService.crearProductosPedido(productosPedidoDTO);
    }

    @GetMapping
    public String listarProductosPedido() {
        return productosPedidoService.getProductosPedidos();
    }
    @GetMapping("/json")
    public List<ProductosPedido> getProductosPedidosJSON() {
        return productosPedidoService.getProductosPedidosJSON();
    }

    @GetMapping("/{rut}")
    public String getProductosPedidoByRut(@PathVariable Integer rut) {
        return productosPedidoService.getProductosPedidosByRut(rut); // Placeholder
    }

    @PutMapping
    public String updateProductosPedido(@RequestBody ProductosPedidoDTO productosPedidoDTO) {
        return productosPedidoService.updateProductosPedido(productosPedidoDTO);
    }

    @PatchMapping("/{id}")
    public String parcharProductosPedido(@RequestBody ProductosPedidoDTO productosPedidoDTO, @PathVariable Integer id) {
        return productosPedidoService.patchProductosPedido(productosPedidoDTO, id);
    }

    @DeleteMapping("/{id}")
    public String deleteProductosPedido(@PathVariable int id) {
        return productosPedidoService.deleteProductosPedido(id);
    }
}