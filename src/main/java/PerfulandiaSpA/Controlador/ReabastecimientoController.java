package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.Reabastecimiento;
import PerfulandiaSpA.Servicio.ReabastecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/reabastecimientos")
public class ReabastecimientoController {

    @Autowired
    private ReabastecimientoService reabastecimientoService;

    // CREATE
    @PostMapping
    public String addReabastecimiento(@RequestBody Reabastecimiento reabastecimiento) {
        return reabastecimientoService.saveReabastecimiento(reabastecimiento);
    }

    // READ - formato toString
    @GetMapping
    public String listarReabastecimientos() {
        return reabastecimientoService.getReabastecimientos();
    }

    // READ - formato JSON
    @GetMapping("/json")
    public List<Reabastecimiento> getReabastecimientosJSON() {
        return reabastecimientoService.getReabastecimientosJSON();
    }

    // READ por ID
    @GetMapping("/{id}")
    public String getReabastecimientoById(@PathVariable Integer id) {
        return reabastecimientoService.getReabastecimientoById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public String updateReabastecimiento(@RequestBody Reabastecimiento reabastecimiento, @PathVariable Integer id) {
        return reabastecimientoService.updateReabastecimiento(reabastecimiento, id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteReabastecimiento(@PathVariable Integer id) {
        return reabastecimientoService.deleteReabastecimiento(id);
    }
}