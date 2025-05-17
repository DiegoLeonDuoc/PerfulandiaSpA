package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Proveedor;
import PerfulandiaSpA.Repositorio.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    @Autowired
    ProveedorRepository proveedorRepository;

    // MÉTODO CREATE
    public String saveProveedor(Proveedor proveedor) {
        if (proveedor.getId() == null) {
            proveedorRepository.save(proveedor);
            return "Proveedor agregado con éxito";
        } else {
            if (proveedorRepository.existsById(proveedor.getId())){
                return "Proveedor ya existe";
            } else {
                proveedorRepository.save(proveedor);
                return "Proveedor agregado con éxito";
            }
        }
    }

    // MÉTODO DELETE
    public String deleteProveedor(int id) {
        if (proveedorRepository.existsById(id)) {
            proveedorRepository.deleteById(id);
            return "Proveedor eliminado con éxito";
        }
        return "Proveedor no encontrado";
    }

    // MÉTODO UPDATE
    public String updateProveedor(Proveedor proveedor, int id) {
        if (proveedorRepository.existsById(id)) {
            proveedor.setId(id);
            proveedorRepository.save(proveedor);
            return "Proveedor actualizado con éxito";
        }
        return "Proveedor no encontrado";
    }

    // MÉTODO READ (LISTAR TODOS)
    // Retorna una lista de proveedores en formato toString
    public String getProveedores() {
        String output = "";
        for (Proveedor proveedor : proveedorRepository.findAll()) {
            output = datosProveedor(output, proveedor); // Acumulando datos de cada proveedor
        }

        if (output.isEmpty()) {
            return "No hay proveedores registrados";
        } else {
            return output; // retorna lista completa
        }
    }

    // MÉTODO READ (LISTAR TODOS EN FORMATO JSON)
    public List<Proveedor> getProveedoresJSON() {
        return proveedorRepository.findAll();
    }

    // MÉTODO READ (BUSCAR POR ID)
    public String getProveedorById(int id) {
        if (proveedorRepository.existsById(id)) {
            Proveedor proveedor = proveedorRepository.findById(id).get();
            return datosProveedor("", proveedor);
        }
        return "Proveedor no encontrado";
    }

    // MÉTODO toString
    private String datosProveedor(String output, Proveedor proveedor) {
        output += "ID Proveedor: " + proveedor.getId() + "\n";
        output += "Nombre: " + proveedor.getNombreProveedor() + "\n";
        output += "Teléfono: +569" + proveedor.getTelefonoProveedor() + "\n";
        output += "Email: " + proveedor.getEmailProveedor() + "\n";
        output += "\n";
        return output;
    }
}
