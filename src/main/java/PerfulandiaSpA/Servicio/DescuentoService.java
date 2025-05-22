package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Descuento;
import PerfulandiaSpA.Repositorio.DescuentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DescuentoService {

    @Autowired
    private DescuentoRepository descuentoRepository;

    // CREATE
    public String saveDescuento(Descuento descuento) {
        if (descuento.getId() == null) {
            descuentoRepository.save(descuento);
            return "Descuento agregado con éxito";
        } else {
            if (descuentoRepository.existsById(descuento.getId())) {
                return "Descuento ya existe";
            } else {
                descuentoRepository.save(descuento);
                return "Descuento agregado con éxito";
            }
        }
    }

    // READ (Formato toString)
    public String getDescuentos() {
        String output = "";
        for (Descuento d : descuentoRepository.findAll()) {
            output = datosDescuento(output, d);
        }

        if (output.isEmpty()) {
            return "No hay descuentos registrados";
        } else {
            return output;
        }
    }

    // READ (JSON)
    public List<Descuento> getDescuentosJSON() {
        return descuentoRepository.findAll();
    }

    // READ (por ID)
    public String getDescuentoById(Integer id) {
        if (descuentoRepository.existsById(id)) {
            Descuento d = descuentoRepository.findById(id).get();
            return datosDescuento("", d);
        }
        return "Descuento no encontrado";
    }

    // UPDATE
    public String updateDescuento(Descuento d, Integer id) {
        if (descuentoRepository.existsById(id)) {
            d.setId(id);
            descuentoRepository.save(d);
            return "Descuento actualizado con éxito";
        }
        return "Descuento no encontrado";
    }

    // DELETE
    public String deleteDescuento(Integer id) {
        if (descuentoRepository.existsById(id)) {
            descuentoRepository.deleteById(id);
            return "Descuento eliminado con éxito";
        }
        return "Descuento no encontrado";
    }

    // Formatear texto
    private String datosDescuento(String output, Descuento d) {
        output += "ID Descuento: " + d.getId() + "\n";
        output += "Tipo: " + d.getTipoDescuento() + "\n";
        output += "Valor: " + d.getValorDescuento() + "\n";
        output += "Fecha de inicio: " + d.getFecIniDescuento() + "\n";
        output += "Fecha de fin: " + (d.getFecFinDescuento() != null ? d.getFecFinDescuento() : "No definida") + "\n";
        output += "ID Producto: " + (d.getIdProducto() != null ? d.getIdProducto() : "No asignado") + "\n\n";
        return output;
    }
}