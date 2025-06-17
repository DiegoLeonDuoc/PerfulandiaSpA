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

    // C
    public Devolucion crearDevolucion(DevolucionDTO devolucion) {
        Devolucion newDevolucion = new Devolucion();

        newDevolucion.setMotivoDevo(devolucion.getMotivoDevo());
        newDevolucion.setEstadoDevo(devolucion.getEstadoDevo());
        newDevolucion.setRestock(devolucion.getRestock());

        Optional<ProductosPedido> productoPedidoOp = productosPedidoRepository.findById(devolucion.getIdProductoPedido());

        if (productoPedidoOp.isPresent()) {
            ProductosPedido productoPedido = productoPedidoOp.get();
            newDevolucion.setCantidadDevuelta(productoPedido.getCantProd());
            newDevolucion.setMontoDevuelto(productoPedido.getPrecioTotalProd());
            return devolucionRepository.save(newDevolucion);
        } else {
            throw new EntityNotFoundException("No se encontro el producto");
        }
    }

    // R
    public List<Devolucion> getDevoluciones() {
        return devolucionRepository.findAll();
    }

    public Optional<Devolucion> getDevolucionByID(Integer id) {
        return devolucionRepository.findById(id);
    }

    public List<Devolucion> getDevolucionesByRut(Integer rut) {
        List<Devolucion> devolucionesCliente = new ArrayList<>();
        if (clienteRepository.existsById(rut)) {
            for (Devolucion devolucion : devolucionRepository.findAll()) {
                if (devolucion.getProductoPedido().getPedido().getCliente().getRutUsuario().equals(rut)) {
                    devolucionesCliente.add(devolucion);
                }
            }
            return devolucionesCliente;
        } else {
            throw new EntityNotFoundException("Cliente no encontrado");
        }
    }

    // U

    public Devolucion updateDevolucion(DevolucionDTO devolucionDTO, Integer id) {
        Devolucion devolucionParchada = devolucionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Devolución no encontrada"));

        devolucionParchada.setMotivoDevo(devolucionDTO.getMotivoDevo());
        devolucionParchada.setEstadoDevo(devolucionDTO.getEstadoDevo());
        devolucionParchada.setRestock(devolucionDTO.getRestock());

        Optional<ProductosPedido> productoPedidoOp = productosPedidoRepository.findById(devolucionDTO.getIdProductoPedido());

        if (productoPedidoOp.isPresent()) {
            ProductosPedido productoPedido = productoPedidoOp.get();
            devolucionParchada.setCantidadDevuelta(productoPedido.getCantProd());
            devolucionParchada.setMontoDevuelto(productoPedido.getPrecioTotalProd());
            return devolucionRepository.save(devolucionParchada);
        } else {
            throw new EntityNotFoundException("No se encontro el producto");
        }
    }

    public Devolucion patchDevolucion(DevolucionDTO devolucionDTO, Integer id) {
        Devolucion devolucionParchada = devolucionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Devolución no encontrada"));

        if (devolucionDTO.getMotivoDevo() != null) {
            devolucionParchada.setMotivoDevo(devolucionDTO.getMotivoDevo());
        }
        if (devolucionDTO.getEstadoDevo() != null) {
            devolucionParchada.setEstadoDevo(devolucionDTO.getEstadoDevo());
        }
        if (devolucionDTO.getRestock() != null) {
            devolucionParchada.setRestock(devolucionDTO.getRestock());
        }

        if (devolucionDTO.getIdProductoPedido() != null) {
            Optional<ProductosPedido> productoPedidoOp = productosPedidoRepository.findById(devolucionDTO.getIdProductoPedido());
            if (productoPedidoOp.isPresent()) {
                ProductosPedido productoPedido = productoPedidoOp.get();
                devolucionParchada.setCantidadDevuelta(productoPedido.getCantProd());
                devolucionParchada.setMontoDevuelto(productoPedido.getPrecioTotalProd());
            } else {
                throw new EntityNotFoundException("No se encontró el producto");
            }
        }

        return devolucionRepository.save(devolucionParchada);
    }

    // D
    public void deleteDevolucion(int id) {
        devolucionRepository.deleteById(id);
    }
}
