package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Carrito;
import PerfulandiaSpA.Repositorio.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    CarritoRepository carritoRepository;

    public void saveCarrito(Carrito carrito) {
        carritoRepository.save(carrito);
    }

    public List<Carrito> getCarritos() {
        return carritoRepository.findAll();
    }

    public Optional<Carrito> getCarritoByID(int id) {
        return carritoRepository.findById(id);
    }

    public void updateCarrito(Carrito carrito, int id) {
        if (carritoRepository.existsById(id)) {
            carrito.setId(id);
            carritoRepository.save(carrito);
        }
    }

    public void deleteCarrito(int id) {
        carritoRepository.deleteById(id);
    }

}
