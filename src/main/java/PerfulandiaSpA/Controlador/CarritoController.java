package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.Carrito;
import PerfulandiaSpA.Servicio.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carritos")
public class CarritoController {

    @Autowired
    CarritoService carritoService;

    @PostMapping
    public String addCarrito(@RequestBody Integer rutCliente){
        return carritoService.crearCarrito(rutCliente);
    }

    @GetMapping
    public String getCarritos(){
        return carritoService.getCarritos();
    }

    @GetMapping("/{rut}")
    public String getCarritoByRut(@PathVariable Integer rut){
        return carritoService.getCarritoByRut(rut);
    }

    @GetMapping("/json")
    public List<Carrito> getCarritosJSON() { return carritoService.getCarritosJSON(); }


    @DeleteMapping("/{rutCliente}")
    public String deleteCarrito(@RequestBody Integer idCarrito, @PathVariable Integer rutCliente){
        return carritoService.deleteCarrito(idCarrito, rutCliente);
    }

}
