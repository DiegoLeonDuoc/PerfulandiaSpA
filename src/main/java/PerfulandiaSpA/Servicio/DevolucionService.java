package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.DTO.DevolucionDTO;
import PerfulandiaSpA.Entidades.*;
import PerfulandiaSpA.Repositorio.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DevolucionService {
    @Autowired
    DevolucionRepository devolucionRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ProductosPedidoRepository productosPedidoRepository;

    // C - Crear devolución (corregido)
    public Devolucion crearDevolucion(DevolucionDTO devolucionDTO) {
        Devolucion newDevolucion = new Devolucion();

        Optional<ProductosPedido> productoPedidoOp = productosPedidoRepository.findById(devolucionDTO.getIdProductoPedido());

        if (productoPedidoOp.isPresent()) {
            ProductosPedido productoPedido = productoPedidoOp.get();

            // Asignar primero el productoPedido
            newDevolucion.setProductoPedido(productoPedido);
            newDevolucion.setMotivoDevo(devolucionDTO.getMotivoDevo());
            newDevolucion.setEstadoDevo(devolucionDTO.getEstadoDevo());
            newDevolucion.setRestock(devolucionDTO.getRestock());
            newDevolucion.setCantidadDevuelta(productoPedido.getCantProd());
            newDevolucion.setMontoDevuelto(productoPedido.getPrecioTotalProd());

            return devolucionRepository.save(newDevolucion);
        } else {
            throw new EntityNotFoundException("No se encontró el producto pedido");
        }
    }

    // R - Obtener todas las devoluciones
    public List<Devolucion> getDevoluciones() {
        return devolucionRepository.findAll();
    }

    // R - Obtener devolución por ID
    public Optional<Devolucion> getDevolucionByID(Integer id) {
        return devolucionRepository.findById(id);
    }

    // R - Obtener devoluciones por RUT de cliente
    public List<Devolucion> getDevolucionesByRut(Integer rut) {
        if (!clienteRepository.existsById(rut)) {
            throw new EntityNotFoundException("Cliente no encontrado");
        }

        List<Devolucion> devolucionesCliente = new ArrayList<>();
        for (Devolucion devolucion : devolucionRepository.findAll()) {
            if (devolucion.getProductoPedido().getPedido().getCliente().getRutUsuario().equals(rut)) {
                devolucionesCliente.add(devolucion);
            }
        }
        return devolucionesCliente;
    }

    // U - Actualización completa
    public Devolucion updateDevolucion(DevolucionDTO devolucionDTO, Integer id) {
        Devolucion devolucionExistente = devolucionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Devolución no encontrada"));

        Optional<ProductosPedido> productoPedidoOp = productosPedidoRepository.findById(devolucionDTO.getIdProductoPedido());
        if (productoPedidoOp.isEmpty()) {
            throw new EntityNotFoundException("Producto pedido no encontrado");
        }

        ProductosPedido productoPedido = productoPedidoOp.get();

        // Actualizar relación y campos
        devolucionExistente.setProductoPedido(productoPedido);
        devolucionExistente.setMotivoDevo(devolucionDTO.getMotivoDevo());
        devolucionExistente.setEstadoDevo(devolucionDTO.getEstadoDevo());
        devolucionExistente.setRestock(devolucionDTO.getRestock());
        devolucionExistente.setCantidadDevuelta(productoPedido.getCantProd());
        devolucionExistente.setMontoDevuelto(productoPedido.getPrecioTotalProd());

        return devolucionRepository.save(devolucionExistente);
    }

    // U - Actualización parcial (PATCH)
    public Devolucion patchDevolucion(DevolucionDTO devolucionDTO, Integer id) {
        Devolucion devolucionExistente = devolucionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Devolución no encontrada"));

        // Actualizar solo campos proporcionados
        if (devolucionDTO.getMotivoDevo() != null) {
            devolucionExistente.setMotivoDevo(devolucionDTO.getMotivoDevo());
        }
        if (devolucionDTO.getEstadoDevo() != null) {
            devolucionExistente.setEstadoDevo(devolucionDTO.getEstadoDevo());
        }
        if (devolucionDTO.getRestock() != null) {
            devolucionExistente.setRestock(devolucionDTO.getRestock());
        }
        if (devolucionDTO.getIdProductoPedido() != null) {
            ProductosPedido productoPedido = productosPedidoRepository.findById(devolucionDTO.getIdProductoPedido())
                    .orElseThrow(() -> new EntityNotFoundException("Producto pedido no encontrado"));
            devolucionExistente.setProductoPedido(productoPedido);
            devolucionExistente.setCantidadDevuelta(productoPedido.getCantProd());
            devolucionExistente.setMontoDevuelto(productoPedido.getPrecioTotalProd());
        }

        return devolucionRepository.save(devolucionExistente);
    }

    // D - Eliminar devolución
    public void deleteDevolucion(int id) {
        devolucionRepository.deleteById(id);
    }
}
