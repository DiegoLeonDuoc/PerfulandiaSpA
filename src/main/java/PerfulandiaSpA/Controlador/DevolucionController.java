package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.DTO.DevolucionDTO;
import PerfulandiaSpA.Entidades.Devolucion;
import PerfulandiaSpA.Servicio.DevolucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devoluciones")
public class DevolucionController {

    @Autowired
    DevolucionService devolucionService;

    @PostMapping
    public String addDevolucion(@RequestBody DevolucionDTO devolucionDTO) {
        return devolucionService.crearDevolucion(devolucionDTO);
    }

    @GetMapping
    public String getDevolucions() {
        return devolucionService.getDevoluciones();
    }

    @GetMapping("/{rut}")
    public String getDevolucionsByRut(@PathVariable Integer rut) {
        return devolucionService.getDevolucionesByRut(rut);
    }

    @GetMapping("/json")
    public List<Devolucion> getDevolucionsJSON() {
        return devolucionService.getDevolucionsJSON();
    }

    @PutMapping
    public String updateDevolucion(@RequestBody DevolucionDTO devolucionDTO) {
        return devolucionService.updateDevolucion(devolucionDTO);
    }

    @PatchMapping("/{id}")
    public String patchDevolucion(@RequestBody DevolucionDTO devolucion, @PathVariable Integer id) {
        return devolucionService.patchDevolucion(devolucion, id);
    }

    @DeleteMapping("/{id}")
    public String deleteDevolucion(@PathVariable int id) {
        return devolucionService.deleteDevolucion(id);
    }
}

