package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Repositorio.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalService {
    
    @Autowired
    SucursalRepository sucursalRepository;
    
    public String saveSucursal(Sucursal sucursal) {
        sucursalRepository.save(sucursal);
        return "Sucursal agregada con éxito";
    }

    public String getSucursales() {
        String output = "";
        for (Sucursal sucursal : sucursalRepository.findAll()) {
            output = datosSucursal(output, sucursal);
        }

        if (output.isEmpty()) {
            //return sucursalRepository.findAll().toString();
            return "No hay sucursales registrados";
        } else {
            return output;
        }
    }

    public List<Sucursal> getSucursalesJSON() {
        return sucursalRepository.findAll();
    }

    public String getSucursalByID(int id) {
        String output = "";
        if (sucursalRepository.existsById(id)) {
            Sucursal sucursal = sucursalRepository.findById(id).get();
            output = datosSucursal(output, sucursal);
            return output;
        }else{
            return "Sucursal no encontrado";
        }
    }

    public String updateSucursal(Sucursal sucursal, int id) {
        if (sucursalRepository.existsById(id)) {
            sucursalRepository.save(sucursal);
            return "Sucursal actualizado con éxito";
        }
        return "Sucursal no encontrado";
    }

    public String deleteSucursal(int id) {
        //return String.valueOf(usuarioRepository.existsById(id));
        for (Sucursal sucursal : sucursalRepository.findAll()) {
            if (sucursal.getId() == id) {
                sucursalRepository.delete(sucursal);
                return "Usuario eliminado con éxito";
            }
        }
        return "Usuario no eliminado";
    }

    private String datosSucursal(String output, Sucursal sucursal) {
        output += "ID: " + sucursal.getId() + "\n";
        output += "Nombre sucursal: " + sucursal.getNombreSucursal() + "\n";
        output += "Direccion sucursal: " + sucursal.getDireccionSucursal() + "\n";
        return output;
    }

}
