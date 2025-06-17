package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Repositorio.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SucursalService {
    
    @Autowired
    SucursalRepository sucursalRepository;
    
    public void saveSucursal(Sucursal sucursal) {
        sucursalRepository.save(sucursal);
    }

    public List<Sucursal> getSucursales() {
        return sucursalRepository.findAll();
    }

    public Optional<Sucursal> getSucursalByID(int id) {
        return sucursalRepository.findById(id);
    }

    public void updateSucursal(Sucursal sucursal, int id) {
        if (sucursalRepository.existsById(id)) {
            sucursal.setId(id);
            sucursalRepository.save(sucursal);
        }
    }

    public void deleteSucursal(int id) {
        sucursalRepository.deleteById(id);
    }

}
