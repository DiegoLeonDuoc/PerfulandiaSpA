package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Carrito;
import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Repositorio.CarritoRepository;
import PerfulandiaSpA.Repositorio.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    CarritoRepository carritoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    // C - Crear carrito
    public void saveCarrito(Carrito carrito) {
        if (carrito.getCliente() == null || carrito.getCliente().getRutUsuario() == null) {
            throw new IllegalArgumentException("El carrito debe tener un cliente asociado válido");
        }
        Cliente cliente = clienteRepository.findById(carrito.getCliente().getRutUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        carrito.setCliente(cliente);
        carritoRepository.save(carrito);
    }

    // R - Listar todos
    public List<Carrito> getCarritos() {
        return carritoRepository.findAll();
    }

    // R - Buscar por ID
    public Optional<Carrito> getCarritoByID(int id) {
        return carritoRepository.findById(id);
    }

    // U - PUT: Actualización completa (reemplazo total)
    public void updateCarrito(Carrito carrito, int id) {
        Carrito carritoExistente = carritoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));

        // Reemplaza el cliente (obligatorio en PUT)
        if (carrito.getCliente() == null || carrito.getCliente().getRutUsuario() == null) {
            throw new IllegalArgumentException("El carrito debe tener un cliente asociado válido para PUT");
        }
        Cliente cliente = clienteRepository.findById(carrito.getCliente().getRutUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        carritoExistente.setCliente(cliente);

        // Si tienes más campos en Carrito, aquí los reemplazas todos (incluyendo nulls)
        // Ejemplo: carritoExistente.setCampo(carrito.getCampo());

        carritoRepository.save(carritoExistente);
    }

    // U - PATCH: Actualización parcial (solo campos enviados)
    public void patchCarrito(Carrito carrito, int id) {
        Carrito carritoExistente = carritoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));

        // Solo actualiza el cliente si se envía uno nuevo
        if (carrito.getCliente() != null && carrito.getCliente().getRutUsuario() != null) {
            Cliente cliente = clienteRepository.findById(carrito.getCliente().getRutUsuario())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
            carritoExistente.setCliente(cliente);
        }

        // Si tienes más campos en Carrito, aquí los actualizas solo si no son null
        // Ejemplo:
        // if (carrito.getCampo() != null) carritoExistente.setCampo(carrito.getCampo());

        carritoRepository.save(carritoExistente);
    }

    // D - Eliminar carrito
    public void deleteCarrito(int id) {
        carritoRepository.deleteById(id);
    }
}
