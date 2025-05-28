package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.DTO.ProductosCarritoDTO;
import PerfulandiaSpA.Entidades.ProductosCarrito;
import PerfulandiaSpA.Servicio.ProductosCarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productosCarrito")
public class ProductosCarritoController {

    @Autowired
    ProductosCarritoService productosCarritoService;

    @PostMapping
    public String addProductosCarrito(@RequestBody ProductosCarritoDTO productosCarritoDTO) {
        return productosCarritoService.crearProductosCarrito(productosCarritoDTO);
    }

    @GetMapping
    public String listarProductosCarrito() {
        return productosCarritoService.getProductosCarritos();
    }
    @GetMapping("/json")
    public List<ProductosCarrito> getProductosCarritosJSON() {
        return productosCarritoService.getProductosCarritosJSON();
    }

    @GetMapping("/{rut}")
    public String getProductosCarritoByRut(@PathVariable Integer rut) {
        return productosCarritoService.getProductosCarritosByRut(rut); // Placeholder
    }

    @PutMapping
    public String updateProductosCarrito(@RequestBody ProductosCarritoDTO productosCarritoDTO) {
        return productosCarritoService.updateProductosCarrito(productosCarritoDTO);
    }

    @PatchMapping("/{id}")
    public String parcharProductosCarrito(@RequestBody ProductosCarritoDTO productosCarritoDTO, @PathVariable Integer id) {
        return productosCarritoService.patchProductosCarrito(productosCarritoDTO, id);
    }

    @DeleteMapping("/{id}")
    public String deleteProductosCarrito(@PathVariable int id) {
        return productosCarritoService.deleteProductosCarrito(id);
    }
}