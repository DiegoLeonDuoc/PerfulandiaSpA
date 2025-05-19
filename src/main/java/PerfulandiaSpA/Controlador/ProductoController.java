package PerfulandiaSpA.Controlador;


import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Servicio.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Producto")
public class ProductoController {

    @Autowired
    ProductoService ProductoService;

    //CRUD

    //Crear Producto
    @PostMapping()
    public String addProducto(@RequestBody Producto Producto) {
        return ProductoService.saveProducto(Producto);
    }

    //read
    @GetMapping("/json")
    public List<Producto> getHorariosTrabajoJSON(){
        return ProductoService.getProductosJSON();
    }

    //read
    @GetMapping("/{id}")
    public String getProductoById(@PathVariable int id){
        return ProductoService.getProductoById(id);
    }

    //read
    @GetMapping("/{codBarrProd}")
    public String getProductoByCodBarr(@PathVariable int codBarrProd){
        return ProductoService.getProductoById(codBarrProd);
    }

    //Update
    @PutMapping("/{id}")
    public String updateProductoSucursal(@RequestBody Producto Producto, @PathVariable int id){
        return ProductoService.updateProducto(Producto, id);
    }

    //deletear horario
    @DeleteMapping("/{id}")
    public String deleteProducto(@PathVariable int id){
        return ProductoService.deleteProducto(id);
    }
    
    //Obtener en formato ToString
    @GetMapping
    public String listarProductoes(){
        return ProductoService.getProductos();
    }

}
