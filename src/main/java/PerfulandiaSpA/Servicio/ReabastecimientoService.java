package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Reabastecimiento;
import PerfulandiaSpA.Repositorio.ReabastecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReabastecimientoService {

    @Autowired
    ReabastecimientoRepository reabastecimientoRepository;

    // CREATE
    public String saveReabastecimiento(Reabastecimiento reabastecimiento) {
        if (reabastecimiento.getId() == null) {
            reabastecimientoRepository.save(reabastecimiento);
            return "Reabastecimiento agregado con éxito";
        } else {
            if (reabastecimientoRepository.existsById(reabastecimiento.getId())) {
                return "Reabastecimiento ya existe";
            } else {
                reabastecimientoRepository.save(reabastecimiento);
                return "Reabastecimiento agregado con éxito";
            }
        }
    }

    // READ (Formato toString)
    public String getReabastecimientos() {
        String output = "";
        for (Reabastecimiento r : reabastecimientoRepository.findAll()) {
            output = datosReabastecimiento(output, r);
        }

        if (output.isEmpty()) {
            return "No hay reabastecimientos registrados";
        } else {
            return output;
        }
    }

    // READ (JSON)
    public List<Reabastecimiento> getReabastecimientosJSON() {
        return reabastecimientoRepository.findAll();
    }

    // READ (por ID)
    public String getReabastecimientoById(Integer id) {
        if (reabastecimientoRepository.existsById(id)) {
            Reabastecimiento r = reabastecimientoRepository.findById(id).get();
            return datosReabastecimiento("", r);
        }
        return "Reabastecimiento no encontrado";
    }

    // UPDATE
    public String updateReabastecimiento(Reabastecimiento r, Integer id) {
        if (reabastecimientoRepository.existsById(id)) {
            r.setId(id);
            reabastecimientoRepository.save(r);
            return "Reabastecimiento actualizado con éxito";
        }
        return "Reabastecimiento no encontrado";
    }

    // DELETE
    public String deleteReabastecimiento(Integer id) {
        if (reabastecimientoRepository.existsById(id)) {
            reabastecimientoRepository.deleteById(id);
            return "Reabastecimiento eliminado con éxito";
        }
        return "Reabastecimiento no encontrado";
    }

    // Formatear texto
    private String datosReabastecimiento(String output, Reabastecimiento r) {
        output += "ID Reabastecimiento: " + r.getId() + "\n";
        output += "Cantidad: " + r.getCantProductos() + "\n";
        output += "Fecha: " + r.getFechaReabas() + "\n";
        output += "Estado: " + r.getEstadoReabas() + "\n";
        output += "ID Producto: " + r.getIdProducto() + "\n";
        output += "ID Sucursal: " + r.getIdSucursal() + "\n";
        output += "ID Proveedor: " + r.getIdProveedor() + "\n\n";
        return output;
    }
}

