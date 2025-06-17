package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.DTO.PedidoDTO;
import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Entidades.Pedido;
import PerfulandiaSpA.Entidades.Reabastecimiento;
import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Repositorio.ClienteRepository;
import PerfulandiaSpA.Repositorio.PedidoRepository;
import PerfulandiaSpA.Repositorio.SucursalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    @Autowired
    ClienteRepository clienteRepository;

    // C
    public Pedido crearPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setMetodoPago(pedidoDTO.getMetodoPago());
        pedido.setDirEnvio(pedidoDTO.getDirEnvio());
        pedido.setDirFacturacion(pedidoDTO.getDirFacturacion());
        pedido.setCostoEnvio(pedidoDTO.getCostoEnvio());
        pedido.setPrecioPedido(pedidoDTO.getPrecioPedido());
        pedido.setAnotaciones(pedidoDTO.getAnotaciones());
        pedido.setFecPedido(LocalDate.now());

        Optional<Cliente> cliente = clienteRepository.findById(pedidoDTO.getRutCliente());
        Optional<Sucursal> sucursal = sucursalRepository.findById(pedidoDTO.getIdSucursal());

        if (cliente.isPresent() && sucursal.isPresent()) {
            pedido.setCliente(cliente.get());
            pedido.setSucursal(sucursal.get());
            return pedidoRepository.save(pedido);
        } else {
            throw new EntityNotFoundException("Cliente o Sucursal no encontrado");
        }
    }

    // R

    public List<Pedido> getPedidos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> getPedidoByID(int id) {
        return pedidoRepository.findById(id);
    }

//    public List<Pedido> getPedidosByRut(Integer rut) {
//        List<Pedido> productosCliente = new ArrayList<>();
//        if (clienteRepository.existsById(rut)) {
//            for (Pedido pedido : pedidoRepository.findAll()) {
//                if (pedido.getCliente().getRutUsuario().equals(rut)) {
//                    productosCliente.add(pedido);
//                }
//            }
//            return productosCliente;
//        } else {
//            throw new EntityNotFoundException("Cliente no encontrado");
//        }
//    }

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

    public Pedido updatePedido(PedidoDTO pedidoDTO, int id) {
        Pedido pedidoParchado;
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            pedidoParchado = pedido.get();
            pedidoParchado.setMetodoPago(pedidoDTO.getMetodoPago());
            pedidoParchado.setDirEnvio(pedidoDTO.getDirEnvio());
            pedidoParchado.setDirFacturacion(pedidoDTO.getDirFacturacion());
            pedidoParchado.setCostoEnvio(pedidoDTO.getCostoEnvio());
            pedidoParchado.setPrecioPedido(pedidoDTO.getPrecioPedido());
            pedidoParchado.setAnotaciones(pedidoDTO.getAnotaciones());
            pedidoParchado.setFecPedido(LocalDate.now());

            Optional<Cliente> cliente = clienteRepository.findById(pedidoDTO.getRutCliente());
            Optional<Sucursal> sucursal = sucursalRepository.findById(pedidoDTO.getIdSucursal());

            if (cliente.isPresent() && sucursal.isPresent()) {
                pedidoParchado.setCliente(cliente.get());
                pedidoParchado.setSucursal(sucursal.get());
                return pedidoRepository.save(pedidoParchado);
            } else {
                throw new EntityNotFoundException("Cliente o Sucursal no encontrado");
            }
        } else  {
            throw new EntityNotFoundException("Pedido no encontrado");
        }
    }

    // U/P
    public Pedido patchPedido(PedidoDTO pedidoDTO, int id) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        if (pedidoOptional.isPresent()) {
            Pedido pedidoParchado = pedidoOptional.get();

            if (pedidoDTO.getMetodoPago() != null) {
                pedidoParchado.setMetodoPago(pedidoDTO.getMetodoPago());
            }
            if (pedidoDTO.getDirEnvio() != null) {
                pedidoParchado.setDirEnvio(pedidoDTO.getDirEnvio());
            }
            if (pedidoDTO.getDirFacturacion() != null) {
                pedidoParchado.setDirFacturacion(pedidoDTO.getDirFacturacion());
            }
            if (pedidoDTO.getCostoEnvio() != null) {
                pedidoParchado.setCostoEnvio(pedidoDTO.getCostoEnvio());
            }
            if (pedidoDTO.getPrecioPedido() != null) {
                pedidoParchado.setPrecioPedido(pedidoDTO.getPrecioPedido());
            }
            if (pedidoDTO.getAnotaciones() != null) {
                pedidoParchado.setAnotaciones(pedidoDTO.getAnotaciones());
            }
            if (pedidoDTO.getFecPedido() != null) {
                pedidoParchado.setFecPedido(pedidoDTO.getFecPedido());
            }

            if (pedidoDTO.getRutCliente() != null) {
                Optional<Cliente> cliente = clienteRepository.findById(pedidoDTO.getRutCliente());
                if (cliente.isPresent()) {
                    pedidoParchado.setCliente(cliente.get());
                } else {
                    throw new EntityNotFoundException("Cliente no encontrado");
                }
            }
            if (pedidoDTO.getIdSucursal() != null) {
                Optional<Sucursal> sucursal = sucursalRepository.findById(pedidoDTO.getIdSucursal());
                if (sucursal.isPresent()) {
                    pedidoParchado.setSucursal(sucursal.get());
                } else {
                    throw new EntityNotFoundException("Sucursal no encontrada");
                }
            }

            return pedidoRepository.save(pedidoParchado);
        } else {
            throw new EntityNotFoundException("Pedido no encontrado");
        }
    }

    // D

    public void deletePedido(int id) {
        pedidoRepository.deleteById(id);
    }
}
