package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.DTO.DevolucionDTO;
import PerfulandiaSpA.Entidades.*;
import PerfulandiaSpA.Repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DevolucionService {
    @Autowired
    DevolucionRepository devolucionRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ProductosPedidoRepository productosPedidoRepository;

    // C
    public String crearDevolucion(DevolucionDTO devolucion) {
        Devolucion newDevolucion = new Devolucion();
        ProductosPedido productosPedido;
        if (productosPedidoRepository.existsById(devolucion.getIdProductoPedido())) {
            productosPedido = productosPedidoRepository.findById(devolucion.getIdProductoPedido()).get();
            newDevolucion.setProductoPedido(productosPedido);
        } else {
            return "No existe un productoPedido asociado a ese ID";
        }
        newDevolucion.setCantidadDevuelta(productosPedido.getCantProd());
        newDevolucion.setMotivoDevo(devolucion.getMotivoDevo());
        newDevolucion.setEstadoDevo(devolucion.getEstadoDevo());
        newDevolucion.setMontoDevuelto(productosPedido.getPrecioTotalProd());
        newDevolucion.setRestock(devolucion.getRestock());
        devolucionRepository.save(newDevolucion);
        return "Devolución agregada con éxito";
    }
    // R
    public String getDevoluciones() {
        String output = "";
        for (Devolucion devolucion : devolucionRepository.findAll()) {
            output = datosDevolucion(output, devolucion);
        }
        if (output.isEmpty()) {
            return "No hay devolucions registrados";
        } else {
            return output;
        }
    }

    public String getDevolucionesByRut(Integer rut) {
        String output = "";
        List<Integer> devolucionsIds = new ArrayList<>();
        if (clienteRepository.existsById(rut)) {
            for (Devolucion devolucion : devolucionRepository.findAll()) {
                if (devolucion.getProductoPedido().getPedido().getCliente().getRutUsuario().equals(rut)) {
                    devolucionsIds.add(devolucion.getId());
                }
            }
            if (devolucionsIds.isEmpty()) {
                return "Este cliente no tiene devolucions registrados";
            } else {
                for (Integer id: devolucionsIds) {
                    output += datosDevolucion("", devolucionRepository.findById(id).get());
                }
                return output;
            }
        } else {
            return "Este cliente no existe";
        }
    }

    public List<Devolucion> getDevolucionsJSON() {
        return devolucionRepository.findAll();
    }
//

//
    // U

    public String updateDevolucion(DevolucionDTO devolucionDTO) {
        Devolucion devolucionParchada;
        if (devolucionRepository.existsById(devolucionDTO.getId())) {
            devolucionParchada = devolucionRepository.findById(devolucionDTO.getId()).get();
        } else {
            return "La devolución a actualizar no existe";
        }

        ProductosPedido productosPedido;
        if (productosPedidoRepository.existsById(devolucionDTO.getIdProductoPedido())) {
            productosPedido = productosPedidoRepository.findById(devolucionDTO.getIdProductoPedido()).get();
            devolucionParchada.setProductoPedido(productosPedido);
        } else {
            return "No existe un productoPedido asociado a ese ID";
        }

        devolucionParchada.setCantidadDevuelta(productosPedido.getCantProd());
        devolucionParchada.setMotivoDevo(devolucionDTO.getMotivoDevo());
        devolucionParchada.setEstadoDevo(devolucionDTO.getEstadoDevo());
        devolucionParchada.setMontoDevuelto(productosPedido.getPrecioTotalProd());
        devolucionParchada.setRestock(devolucionDTO.getRestock());

        devolucionRepository.save(devolucionParchada);
        return "Devolucion actualizado con éxito";
    }

    //
//
    public String patchDevolucion(DevolucionDTO devolucionDTO, Integer id) {
        if (!devolucionRepository.existsById(id)) {
            return "La devolución a actualizar no existe";
        }

        Devolucion devolucionParchada;
        if (devolucionRepository.existsById(id)) {
            devolucionParchada = devolucionRepository.findById(id).get();
        } else {
            return "La devolución a actualizar no existe";
        }

        // Update only if the ID of the product order is provided
        if (devolucionDTO.getIdProductoPedido() != null) {
            ProductosPedido productosPedido;
            if (productosPedidoRepository.existsById(devolucionDTO.getIdProductoPedido())) {
                productosPedido = productosPedidoRepository.findById(devolucionDTO.getIdProductoPedido()).get();
                devolucionParchada.setProductoPedido(productosPedido);
                devolucionParchada.setCantidadDevuelta(productosPedido.getCantProd());
                devolucionParchada.setMontoDevuelto(productosPedido.getPrecioTotalProd());
            } else {
                return "No existe un productoPedido asociado a ese ID";
            }
        }

        // Update fields only if they are provided in the DTO
        if (devolucionDTO.getMotivoDevo() != null) {
            devolucionParchada.setMotivoDevo(devolucionDTO.getMotivoDevo());
        }
        if (devolucionDTO.getEstadoDevo() != null) {
            devolucionParchada.setEstadoDevo(devolucionDTO.getEstadoDevo());
        }
        if (devolucionDTO.getRestock() != null) {
            devolucionParchada.setRestock(devolucionDTO.getRestock());
        }

        devolucionRepository.save(devolucionParchada);
        return "Devolucion actualizado con éxito";
    }
//
    // D

    public String deleteDevolucion(int id) {
        for (Devolucion devolucion : devolucionRepository.findAll()) {
            if (devolucion.getId() == id) {
                devolucionRepository.delete(devolucion);
                return "Devolucion eliminado con éxito";
            }
        }
        return "Devolucion no existente";
    }

    // Funciones no CRUD
//
    public String datosDevolucion(String output, Devolucion devolucion) {
        output += "ID Devolucion: " + devolucion.getId() + "\n";
        output += "Cantidad Devuelta: " + devolucion.getCantidadDevuelta() + "\n";
        output += "Motivo de Devolución: " + (devolucion.getMotivoDevo() != null ? devolucion.getMotivoDevo() : "No disponible") + "\n";
        output += "Estado de Devolución: " + devolucion.getEstadoDevo() + "\n";
        output += "Monto Devuelto: " + devolucion.getMontoDevuelto() + "\n";
        output += "Restock: " + devolucion.getRestock() + "\n";

        if (devolucion.getProductoPedido() != null) {
            output += "ID Producto Pedido asociado: " + devolucion.getProductoPedido().getId() + "\n";
        } else {
            output += "Producto Pedido: No asociado\n";
        }

        output += "\n";

        return output;
    }

}
