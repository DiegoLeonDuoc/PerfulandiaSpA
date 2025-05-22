package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.Descuento;
import PerfulandiaSpA.Servicio.DescuentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/descuentos")
public class DescuentoController {

    @Autowired
    DescuentoService descuentoService;

    // CREATE
    @PostMapping
    public String addDescuento(@RequestBody Descuento descuento) {
        return descuentoService.saveDescuento(descuento);
    }

    // READ - formato toString
    @GetMapping
    public String listarDescuentos() {
        return descuentoService.getDescuentos();
    }

    // READ - formato JSON
    @GetMapping("/json")
    public List<Descuento> getDescuentosJSON() {
        return descuentoService.getDescuentosJSON();
    }

    // READ por ID
    @GetMapping("/{id}")
    public String getDescuentoById(@PathVariable Integer id) {
        return descuentoService.getDescuentoById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public String updateDescuento(@RequestBody Descuento descuento, @PathVariable Integer id) {
        return descuentoService.updateDescuento(descuento, id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteDescuento(@PathVariable Integer id) {
        return descuentoService.deleteDescuento(id);
    }
}

