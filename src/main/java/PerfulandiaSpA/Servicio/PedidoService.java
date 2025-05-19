package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Carrito;
import PerfulandiaSpA.Entidades.Cliente;
import PerfulandiaSpA.Entidades.Pedido;
import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Repositorio.ClienteRepository;
import PerfulandiaSpA.Repositorio.PedidoRepository;
import PerfulandiaSpA.Repositorio.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    public String crearPedido(Pedido pedido, Integer id_sucursal) {
        if (pedidoRepository.existsById(pedido.getId())) {
            return "Ya existe un carrito con esta ID";
        } else {
            if (sucursalRepository.existsById(id_sucursal)) {
                Sucursal sucursal = sucursalRepository.findById(id_sucursal).get();
                pedido.setSucursal(sucursal);
                pedidoRepository.save(pedido);
                return "Pedido agregado con éxito";
            } else {
                return "La sucursal no existe";
            }
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
                for (Integer id : pedidosIds) {
                    Pedido pedido = pedidoRepository.findById(id).get();
                    output += datosPedido(output, pedido);
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

    public String getPedidosSucursal(Integer id_sucursal) {
        String output = "";
        for (Pedido pedido : pedidoRepository.findAll()) {
            if (pedido.getSucursal().getId().equals(id_sucursal)) {
                output = datosPedido(output, pedido);
            }
        }
        if (output.isEmpty()) {
            return "No hay pedidos registrados";
        } else {
            return output;
        }
    }

    public List<Pedido> getPedidosSucursalJSON(Integer id_sucursal) {
        List<Pedido> pedidosSucursal = new ArrayList<>();
        for (Pedido pedido : pedidoRepository.findAll()) {
            if (pedido.getSucursal().getId().equals(id_sucursal)) {
                pedidosSucursal.add(pedido);
            }
        }
        return pedidosSucursal;
    }

    // U

    public String updatePedido(Pedido pedido, Integer idPedido, Integer idSucursal, Integer rutCliente) {
        if (pedidoRepository.existsById(idPedido)) {
            Pedido pedidoUpdate = pedidoRepository.findById(idPedido).get();
            pedidoUpdate.setId(pedido.getId());
            if (sucursalRepository.existsById(idSucursal)) {
                Sucursal sucursalUpdate = sucursalRepository.findById(idSucursal).get();
                pedidoUpdate.setSucursal(sucursalUpdate);
            }
            pedidoUpdate.setFecPedido(pedido.getFecPedido());
            pedidoUpdate.setPrecioPedido(pedido.getPrecioPedido());
            pedidoUpdate.setMetodoPago(pedido.getMetodoPago());
            pedidoUpdate.setDirEnvio(pedido.getDirEnvio());
            pedidoUpdate.setDirFacturacion(pedido.getDirFacturacion());
            pedidoUpdate.setCostoEnvio(pedido.getCostoEnvio());
            pedidoUpdate.setAnotaciones(pedido.getAnotaciones());
            if (clienteRepository.existsById(rutCliente)) {
                Cliente clienteUpdate = clienteRepository.findById(rutCliente).get();
                pedidoUpdate.setCliente(clienteUpdate);
            }
            pedidoRepository.save(pedidoUpdate);
            return "Pedido actualizado con éxito";
        } else {
            return "Pedido no encontrado";
        }
    }

    public String cambiarSucursalPedido(Pedido pedido, int id_sucursal) {
        if (pedidoRepository.existsById(pedido.getRutUsuario())) {
            if (sucursalRepository.existsById(id_sucursal)) {
                pedido = pedidoRepository.findById(pedido.getRutUsuario()).get();
                Sucursal sucursal = sucursalRepository.findById(id_sucursal).get();
                pedido.setSucursalAsociada(sucursal);
                pedidoRepository.save(pedido);
                return "Sucursal del pedido actualizada con éxito";
            } else {
                return "Sucursal no existente";
            }
        } else {
            return "Pedido no existente";
        }
    }

    // U/P
    public String parcharPedido(Pedido pedido, int rut) {
        if (pedidoRepository.existsById(rut)) {
            Pedido pedidoParchado = pedidoRepository.findById(rut).get();
            if (pedido.getNomUsuario() != null) {
                pedidoParchado.setNomUsuario(pedido.getNomUsuario());
            }
            if (pedido.getNom2Usuario() != null) {
                pedidoParchado.setNom2Usuario(pedido.getNom2Usuario());
            }
            if (pedido.getApellidoPaterno() != null) {
                pedidoParchado.setApellidoPaterno(pedido.getApellidoPaterno());
            }
            if (pedido.getApellidoMaterno() != null) {
                pedidoParchado.setApellidoMaterno(pedido.getApellidoMaterno());
            }
            if (pedido.getSexoUsuario() == 'M' || pedido.getSexoUsuario() == 'F') {
                pedidoParchado.setSexoUsuario(pedido.getSexoUsuario());
            }
            if (pedido.getFechaNacimiento() != null) {
                pedidoParchado.setFechaNacimiento(pedido.getFechaNacimiento());
            }
            if (pedido.getTelefonoUsuario() != null) {
                pedidoParchado.setTelefonoUsuario(pedido.getTelefonoUsuario());
            }
            if (pedido.getTel2Usuario() != null) {
                pedidoParchado.setTel2Usuario(pedido.getTel2Usuario());
            }
            if (pedido.getEmailUsuario() != null) {
                pedidoParchado.setEmailUsuario(pedido.getEmailUsuario());
            }
            if (pedido.getPassUsuario() != null) {
                String newPass = new BCryptPasswordEncoder(10).encode(pedido.getPassUsuario());
                pedidoParchado.setPassUsuario(newPass);
            }
            pedidoRepository.save(pedidoParchado);
            return "Pedido actualizado con éxito";
        }
        return "Pedido no encontrado";
    }

    // D

    public String deletePedido(int rut) {
        for (Pedido pedido : pedidoRepository.findAll()) {
            if (pedido.getRutUsuario() == rut) {
                pedidoRepository.delete(pedido);
                return "Pedido eliminado con éxito";
            }
        }
        return "Pedido no existente";
    }

    // Funciones no CRUD

    private String datosPedido(String output, Pedido pedido) {
        output += "RUT: " + pedido.getRutUsuario() + "-" + pedido.getDvUsuario() + "\n";
        output += "Nombre completo: " + pedido.getNomUsuario() + " ";
        if (pedido.getNom2Usuario() != null) {
            output += pedido.getNom2Usuario() + " ";
        }
        output += pedido.getApellidoPaterno();
        if (pedido.getNom2Usuario() != null) {
            output += " " + pedido.getApellidoMaterno() + "\n";
        } else {
            output += "\n";
        }
        output += "Sexo: " + pedido.getSexoUsuario() + "\n";
        output += "Fecha de nacimiento: " + pedido.getFechaNacimiento().toString() + "\n";
        output += "Dirección: " + pedido.getDirUsuario() + "\n";
        output += "Número de teléfono: +56" + pedido.getTelefonoUsuario() + "\n";
        if (pedido.getTel2Usuario() != null) {
            output += "Teléfono extra: +56" + pedido.getTel2Usuario() + "\n";
        }
        output += "Email: " + pedido.getEmailUsuario() + "\n";
        output += "Nombre Sucursal: " + pedido.getSucursalAsociada().getNombreSucursal() + "\n";
        output += "Dirección sucursal: " + pedido.getSucursalAsociada().getDireccionSucursal() + "\n";
        output += "\n";
        return output;
    }
}
