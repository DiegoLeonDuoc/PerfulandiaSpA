package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Proveedor;
import PerfulandiaSpA.Repositorio.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    ProveedorRepository proveedorRepository;

    public void saveProveedor(Proveedor proveedor) {
        proveedorRepository.save(proveedor);
    }

    public List<Proveedor> getProveedores() {
        return proveedorRepository.findAll();
    }

    public Optional<Proveedor> getProveedorByID(int id) {
        return proveedorRepository.findById(id);
    }

    public void updateProveedor(Proveedor proveedor, int id) {
        if (proveedorRepository.existsById(id)) {
            proveedor.setId(id);
            proveedorRepository.save(proveedor);
        }
    }

    public void deleteProveedor(int id) {
        proveedorRepository.deleteById(id);
    }

}
