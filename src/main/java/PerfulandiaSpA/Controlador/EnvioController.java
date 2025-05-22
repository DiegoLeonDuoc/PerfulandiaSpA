package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.DTO.EnvioDTO;
import PerfulandiaSpA.Entidades.Envio;
import PerfulandiaSpA.Servicio.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/envios")
public class EnvioController {

    @Autowired
    EnvioService envioService;

    @PostMapping
    public String addEnvio(@RequestBody EnvioDTO envioDTO) {
        return envioService.crearEnvio(envioDTO);
    }

    @GetMapping
    public String getEnvios() {
        return envioService.getEnvios();
    }

    @GetMapping("/{rut}")
    public String getEnviosByRut(@PathVariable Integer rut) {
        return envioService.getEnviosByRut(rut);
    }

    @GetMapping("/sucursal/{idSucursal}")
    public String getEnviosBySucursal(@PathVariable Integer idSucursal) {
        return envioService.getEnviosSucursal(idSucursal);
    }

    @GetMapping("/sucursal/{idSucursal}/json")
    public List<Envio> getEnviosBySucursalJSON(@PathVariable Integer idSucursal) {
        return envioService.getEnviosBySucursalJSON(idSucursal);
    }

    @GetMapping("/json")
    public List<Envio> getEnviosJSON() {
        return envioService.getEnviosJSON();
    }

    @PutMapping
    public String updateEnvio(@RequestBody EnvioDTO envioDTO) {
        return envioService.updateEnvio(envioDTO);
    }

    @PatchMapping("/{id}")
    public String parcharEnvio(@RequestBody EnvioDTO envio, @PathVariable Integer id) {
        return envioService.parcharEnvio(envio, id);
    }

    @DeleteMapping("/{id}")
    public String deleteEnvio(@PathVariable int id) {
        return envioService.deleteEnvio(id);
    }
}

