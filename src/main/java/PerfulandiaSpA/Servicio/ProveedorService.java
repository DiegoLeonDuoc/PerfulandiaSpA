package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Proveedor;
import PerfulandiaSpA.Repositorio.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Esta anotación indica que esta clase es un servicio manejado por Spring (componente de lógica de negocio)
public class ProveedorService {

    // Inyección automática del repositorio de Proveedor
    @Autowired
    ProveedorRepository proveedorRepository;

    // MÉTODO CREATE
    // Guarda un nuevo proveedor en la base de datos
    public String saveProveedor(Proveedor proveedor) {
        proveedorRepository.save(proveedor); // Se guarda el proveedor recibido
        return "Proveedor agregado con éxito"; // Mensaje de confirmación
    }

    // MÉTODO DELETE
    // Elimina un proveedor por su ID
    public String deleteProveedor(int id) {
        if (proveedorRepository.existsById(id)) { // Verifica si existe
            proveedorRepository.deleteById(id); // Elimina por ID
            return "Proveedor eliminado con éxito";
        }
        return "Proveedor no encontrado"; // Si no existe
    }

    // MÉTODO UPDATE
    // Actualiza un proveedor si existe el ID
    public String updateProveedor(Proveedor proveedor, int id) {
        if (proveedorRepository.existsById(id)) {
            proveedor.setId(id); // Se asegura que el ID del objeto corresponda al existente
            proveedorRepository.save(proveedor); // Guarda los cambios (update automático con save)
            return "Proveedor actualizado con éxito";
        }
        return "Proveedor no encontrado"; // No se actualiza si no existe
    }

    // MÉTODO READ (LISTAR TODOS)
    // Retorna una lista de proveedores en formato String legible
    public String getProveedores() {
        String output = "";
        for (Proveedor proveedor : proveedorRepository.findAll()) {
            output = datosProveedor(output, proveedor); // Acumula los datos de cada proveedor
        }

        // Verifica si no hay proveedores registrados
        if (output.isEmpty()) {
            return "No hay proveedores registrados";
        } else {
            return output; // Devuelve la lista completa
        }
    }

    // MÉTODO READ (LISTAR TODOS EN FORMATO JSON)
    // Devuelve todos los proveedores como una lista de objetos (ideal para API REST)
    public List<Proveedor> getProveedoresJSON() {
        return proveedorRepository.findAll();
    }

    // MÉTODO READ (BUSCAR POR ID)
    // Busca un proveedor específico por su ID
    public String getProveedorById(int id) {
        if (proveedorRepository.existsById(id)) {
            Proveedor proveedor = proveedorRepository.findById(id).get(); // Obtiene el proveedor
            return datosProveedor("", proveedor); // Devuelve su información formateada
        }
        return "Proveedor no encontrado";
    }

    // MÉTODO AUXILIAR
    // Agrega los datos formateados de un proveedor al string de salida
    private String datosProveedor(String output, Proveedor proveedor) {
        output += "ID Proveedor: " + proveedor.getId() + "\n";
        output += "Nombre: " + proveedor.getNombreProveedor() + "\n";
        output += "Teléfono: +569" + proveedor.getTelefonoProveedor() + "\n";
        output += "Email: " + proveedor.getEmailProveedor() + "\n";
        output += "\n";
        return output;
    }
}
