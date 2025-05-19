package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Carrito;
import PerfulandiaSpA.Entidades.Empleado;
import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Repositorio.CarritoRepository;
import PerfulandiaSpA.Repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoService {

    @Autowired
    CarritoRepository carritoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    // C
    public String crearCarrito(Integer rutCliente) {
        if (clienteRepository.existsById(rutCliente)) {
            Carrito carrito = new Carrito();
            carrito.setCliente(clienteRepository.findById(rutCliente).get());
            carritoRepository.save(carrito);
            return "Carrito agregado con éxito";
        } else {
            return "El cliente no existe";
        }
    }

    public String getCarritos() {
        String output = "";
        for (Carrito carrito : carritoRepository.findAll()) {
            output = datosCarrito(output, carrito);
        }

        if (output.isEmpty()) {
            return "No hay carritoes registrados";
        } else {
            return output;
        }
    }

    public List<Carrito> getCarritosJSON() {
        return carritoRepository.findAll();
    }

    public String getCarritoByRut(Integer rut) {
        String output = "IDs Carritos asociados: (";
        if (clienteRepository.existsById(rut)) {
            for (Carrito carrito : carritoRepository.findAll()) {
                if (carrito.getCliente().getRutUsuario().equals(rut)) {
                    output += carrito.getId() + ",";
                }
            }
            if (output.isEmpty()) {
                return "Este cliente no tiene carritos registrados";
            } else {
                output = output.substring(0, output.length() - 1);
                output = output.concat(")");
                return output;
            }
        } else {
            return "Este cliente no existe";
        }
    }

    // No es necesario actualizar un carrito, pues este es único a cada cliente
//    public String updateCarrito(Carrito carrito, Integer id) {
//        if (carritoRepository.existsById(id)) {
//            carritoRepository.save(carrito);
//            return "Carrito actualizado con éxito";
//        }
//        return "Carrito no encontrada";
//    }

    public String deleteCarrito(Integer idCarrito, Integer rutCliente) {
        if (clienteRepository.existsById(rutCliente)) {
            if (carritoRepository.existsById(idCarrito)) {
                Carrito carrito = carritoRepository.findById(idCarrito).get();
                if (carrito.getCliente().getRutUsuario().equals(rutCliente)) {
                    carritoRepository.deleteById(carrito.getId());
                    return "Carrito eliminado con éxito";
                } else {
                    return "Carrito no asociado al cliente";
                }
            } else {
                return "El carrito no existe";
            }
        }
        return "El cliente no existe";
    }

    private String datosCarrito(String output, Carrito carrito) {
        output += "ID Carrito: " + carrito.getId() + "\n";
        output += "RUT Cliente: " + carrito.getCliente().getRutUsuario() + "\n";
        output += "\n";
        return output;
    }

}
