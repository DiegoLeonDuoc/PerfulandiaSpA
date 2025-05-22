package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.DTO.PedidoDTO;
import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Entidades.Pedido;
import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Repositorio.ClienteRepository;
import PerfulandiaSpA.Repositorio.PedidoRepository;
import PerfulandiaSpA.Repositorio.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {
    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    @Autowired
    ClienteRepository clienteRepository;

    // C
    public String crearPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        if (sucursalRepository.existsById(pedidoDTO.getIdSucursal())) {
            Sucursal sucursal = sucursalRepository.findById(pedidoDTO.getIdSucursal()).get();
            pedido.setSucursal(sucursal);
            if (clienteRepository.existsById(pedidoDTO.getRutCliente())) {
                Cliente cliente = clienteRepository.findById(pedidoDTO.getRutCliente()).get();
                pedido.setMetodoPago(pedidoDTO.getMetodoPago());
                pedido.setDirEnvio(pedidoDTO.getDirEnvio());
                pedido.setDirFacturacion(pedidoDTO.getDirFacturacion());
                pedido.setCostoEnvio(pedidoDTO.getCostoEnvio());
                pedido.setPrecioPedido(pedidoDTO.getPrecioPedido());
                pedido.setAnotaciones(pedidoDTO.getAnotaciones());
                pedido.setCliente(cliente);
                pedido.setFecPedido(LocalDate.now());
                pedidoRepository.save(pedido);
            } else {
                return "Ingrese un RUT de cliente válido";
            }
            return "Pedido agregado con éxito";
        } else {
            return "La sucursal no existe";
        }
    }
    // R
    public String getPedidos() {
        String output = "";
        for (Pedido pedido : pedidoRepository.findAll()) {
            output = datosPedido(output, pedido);
        }
        if (output.isEmpty()) {
            return "No hay pedidos registrados";
        } else {
            return output;
        }
    }

    public String getPedidosByRut(Integer rut) {
        String output = "";
        List<Integer> pedidosIds = new ArrayList<>();
        if (clienteRepository.existsById(rut)) {
            for (Pedido pedido : pedidoRepository.findAll()) {
                if (pedido.getCliente().getRutUsuario().equals(rut)) {
                    pedidosIds.add(pedido.getId());
                }
            }
            if (pedidosIds.isEmpty()) {
                return "Este cliente no tiene pedidos registrados";
            } else {
                for (Integer id: pedidosIds) {
                    output += datosPedido(output, pedidoRepository.findById(id).get());
                }
                return output;
            }
        } else {
            return "Este cliente no existe";
        }
    }

    public List<Pedido> getPedidosJSON() {
        return pedidoRepository.findAll();
    }

    public String getPedidosSucursal(Integer idSucursal) {
        String output = "";
        for (Pedido pedido : pedidoRepository.findAll()) {
            if (pedido.getSucursal().getId().equals(idSucursal)) {
                output = datosPedido(output, pedido);
            }
        }
        if (output.isEmpty()) {
            return "No hay pedidos registrados en esta sucursal";
        } else {
            return output;
        }
    }

    public List<Pedido> getPedidosBySucursalJSON(Integer idSucursal) {
        List<Pedido> pedidosSucursal = new ArrayList<>();
        for (Pedido pedido : pedidoRepository.findAll()) {
            if (pedido.getSucursal().getId().equals(idSucursal)) {
                pedidosSucursal.add(pedido);
            }
        }
        return pedidosSucursal;
    }

    // U

    public String updatePedido(PedidoDTO pedidoDTO) {
        if (pedidoRepository.existsById(pedidoDTO.getId())) {
            Pedido pedidoUpdate = pedidoRepository.findById(pedidoDTO.getId()).get();
            pedidoUpdate.setId(pedidoDTO.getId());
            if (sucursalRepository.existsById(pedidoDTO.getIdSucursal())) {
                Sucursal sucursalUpdate = sucursalRepository.findById(pedidoDTO.getIdSucursal()).get();
                pedidoUpdate.setSucursal(sucursalUpdate);
            } else {
                return "Ingrese una ID de sucursal válida";
            }
            pedidoUpdate.setFecPedido(pedidoDTO.getFecPedido());
            pedidoUpdate.setPrecioPedido(pedidoDTO.getPrecioPedido());
            pedidoUpdate.setMetodoPago(pedidoDTO.getMetodoPago());
            pedidoUpdate.setDirEnvio(pedidoDTO.getDirEnvio());
            pedidoUpdate.setDirFacturacion(pedidoDTO.getDirFacturacion());
            pedidoUpdate.setCostoEnvio(pedidoDTO.getCostoEnvio());
            pedidoUpdate.setAnotaciones(pedidoDTO.getAnotaciones());
            if (clienteRepository.existsById(pedidoDTO.getRutCliente())) {
                Cliente clienteUpdate = clienteRepository.findById(pedidoDTO.getRutCliente()).get();
                pedidoUpdate.setCliente(clienteUpdate);
            } else {
                return "Ingrese un RUT de cliente válido";
            }
            pedidoRepository.save(pedidoUpdate);
            return "Pedido actualizado con éxito";
        } else {
            return "Pedido no encontrado";
        }
    }

    // U/P
    public String parcharPedido(PedidoDTO pedido, int id) {
        if (pedidoRepository.existsById(id)) {
            Pedido pedidoParchado = pedidoRepository.findById(id).get();
            if (pedido.getIdSucursal() != null) {
                Sucursal newSucursal = sucursalRepository.findById(pedido.getIdSucursal()).get();
                pedidoParchado.setSucursal(newSucursal);
            }
            if (pedido.getFecPedido() != null) {
                pedidoParchado.setFecPedido(pedido.getFecPedido());
            }
            if (pedido.getPrecioPedido() != null) {
                pedidoParchado.setPrecioPedido(pedido.getPrecioPedido());
            }
            if (pedido.getMetodoPago() != null) {
                pedidoParchado.setMetodoPago(pedido.getMetodoPago());
            }
            if (pedido.getDirEnvio() != null) {
                pedidoParchado.setDirEnvio(pedido.getDirEnvio());
            }
            if (pedido.getDirFacturacion() != null) {
                pedidoParchado.setDirFacturacion(pedido.getDirFacturacion());
            }
            if (pedido.getCostoEnvio() != null) {
                pedidoParchado.setCostoEnvio(pedido.getCostoEnvio());
            }
            if (pedido.getAnotaciones() != null) {
                pedidoParchado.setAnotaciones(pedido.getAnotaciones());
            }
            if (pedido.getRutCliente() != null) {
                Cliente newCliente = clienteRepository.findById(pedido.getRutCliente()).get();
                pedidoParchado.setCliente(newCliente);
            }
            pedidoRepository.save(pedidoParchado);
            return "Pedido actualizado con éxito";
        }
        return "Pedido no encontrado";
    }

    // D

    public String deletePedido(int id) {
        for (Pedido pedido : pedidoRepository.findAll()) {
            if (pedido.getId() == id) {
                pedidoRepository.delete(pedido);
                return "Pedido eliminado con éxito";
            }
        }
        return "Pedido no existente";
    }

    // Funciones no CRUD

    private String datosPedido(String output, Pedido pedido) {
        output += "ID Pedido: " + pedido.getId() + "\n";
        output += "RUT Cliente: " + pedido.getCliente().getRutUsuario() + "\n";
        output += "Fecha pedido:  " + pedido.getFecPedido() + "\n";
        output += "Precio total pedido: $" + pedido.getPrecioPedido() + "\n";
        output += "Método de pago: " + pedido.getMetodoPago() + "\n";
        if (pedido.getDirEnvio() == null) {
            output += "Dirección Envío: Pedido en tienda\n";
        } else {
            output += "Dirección Envío: " + pedido.getDirEnvio() + "\n";
        }
        if (pedido.getDirFacturacion() == null) {
            if (pedido.getDirEnvio() == null) {
                output += "Dirección Facturación: Pedido en tienda\n";
            } else {
                output += "Dirección Facturación: " + pedido.getDirEnvio() + "\n";
            }
        } else {
            output += "Dirección Facturación: " + pedido.getDirFacturacion() + "\n";
        }
        if (pedido.getCostoEnvio() == null) {
            output += "Coste de envío: Pedido en tienda\n";
        } else {
            output += "Coste de envío: $" + pedido.getCostoEnvio() + "\n";
        }
        if (pedido.getAnotaciones() == null) {
            output += "Anotaciones: Ninguna\n";
        } else {
            output += "Anotaciones: " + pedido.getAnotaciones() + "\n";
        }
        output += "Sucursal asociada:  " + pedido.getSucursal().getNombreSucursal() + "\n";
        return output;
    }
}
