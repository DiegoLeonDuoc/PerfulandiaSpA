package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.DTO.EnvioDTO;
import PerfulandiaSpA.Entidades.Envio;
import PerfulandiaSpA.Entidades.Pedido;
import PerfulandiaSpA.Repositorio.ClienteRepository;
import PerfulandiaSpA.Repositorio.EnvioRepository;
import PerfulandiaSpA.Repositorio.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnvioService {
    @Autowired
    EnvioRepository envioRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    // C
    public String crearEnvio(EnvioDTO envio) {
        Envio newEnvio = new Envio();
        if (!pedidoRepository.existsById(envio.getIdPedido())) {
            return "No existe un pedido asociado a ese ID";
        }
        if (pedidoRepository.findById(envio.getIdPedido()).get().getDirEnvio() == null) {
            return "El pedido asociado fue entregado en tienda";
        }
        newEnvio.setCodigoEnvio(envio.getCodigoEnvio());
        Pedido pedido = pedidoRepository.findById(envio.getIdPedido()).get();
        newEnvio.setPedidoAsociado(pedido);
        newEnvio.setFechaEnvio(envio.getFechaEnvio());
        newEnvio.setFechaLlegadaEstim(envio.getFechaLlegadaEstim());
        newEnvio.setFechaLlegadaReal(envio.getFechaLlegadaReal());
        newEnvio.setTransportista(envio.getTransportista());
        newEnvio.setNumSeguimiento(envio.getNumSeguimiento());
        newEnvio.setMetodoEnvio(envio.getMetodoEnvio());
        envioRepository.save(newEnvio);
        return "Envio agregado con éxito";
    }
    // R
    public String getEnvios() {
        String output = "";
        for (Envio envio : envioRepository.findAll()) {
            output = datosEnvio(output, envio);
        }
        if (output.isEmpty()) {
            return "No hay envios registrados";
        } else {
            return output;
        }
    }

    public String getEnviosByRut(Integer rut) {
        String output = "";
        List<Integer> enviosIds = new ArrayList<>();
        if (clienteRepository.existsById(rut)) {
            for (Envio envio : envioRepository.findAll()) {
                if (envio.getPedidoAsociado().getCliente().getRutUsuario().equals(rut)) {
                    enviosIds.add(envio.getId());
                }
            }
            if (enviosIds.isEmpty()) {
                return "Este cliente no tiene envios registrados";
            } else {
                for (Integer id: enviosIds) {
                    output += datosEnvio(output, envioRepository.findById(id).get());
                }
                return output;
            }
        } else {
            return "Este cliente no existe";
        }
    }

    public List<Envio> getEnviosJSON() {
        return envioRepository.findAll();
    }

    public String getEnviosSucursal(Integer idSucursal) {
        String output = "";
        for (Envio envio : envioRepository.findAll()) {
            if (envio.getPedidoAsociado().getSucursal().getId().equals(idSucursal)) {
                output = datosEnvio(output, envio);
            }
        }
        if (output.isEmpty()) {
            return "No hay envios registrados en esta sucursal";
        } else {
            return output;
        }
    }

    public List<Envio> getEnviosBySucursalJSON(Integer idSucursal) {
        List<Envio> enviosSucursal = new ArrayList<>();
        for (Envio envio : envioRepository.findAll()) {
            if (envio.getPedidoAsociado().getSucursal().getId().equals(idSucursal)) {
                enviosSucursal.add(envio);
            }
        }
        return enviosSucursal;
    }

    // U

    public String updateEnvio(EnvioDTO envio) {
        if (!envioRepository.existsById(envio.getId())) {
            return "El envío a actualizar no existe";
        }
        if (!pedidoRepository.existsById(envio.getIdPedido())) {
            return "No existe un pedido asociado a ese ID";
        }
        if (pedidoRepository.findById(envio.getIdPedido()).get().getDirEnvio() == null) {
            return "El pedido asociado a ese ID fue entregado en tienda";
        }
        Envio envioParchado = envioRepository.findById(envio.getId()).get();
        envioParchado.setCodigoEnvio(envio.getCodigoEnvio());
        Pedido pedido = pedidoRepository.findById(envio.getIdPedido()).get();
        envioParchado.setPedidoAsociado(pedido);
        envioParchado.setFechaEnvio(envio.getFechaEnvio());
        envioParchado.setFechaLlegadaEstim(envio.getFechaLlegadaEstim());
        envioParchado.setFechaLlegadaReal(envio.getFechaLlegadaReal());
        envioParchado.setTransportista(envio.getTransportista());
        envioParchado.setNumSeguimiento(envio.getNumSeguimiento());
        envioParchado.setMetodoEnvio(envio.getMetodoEnvio());
        envioRepository.save(envioParchado);
        return "Envio agregado con éxito";
    }


    // U/P
    public String parcharEnvio(EnvioDTO envio, int id) {
        if (envioRepository.existsById(id)) {
            Envio envioParchado = envioRepository.findById(id).get();
            if (envio.getCodigoEnvio() != null) {
                envioParchado.setCodigoEnvio(envio.getCodigoEnvio());
            }
            if (envio.getIdPedido() != null) {
                if (!pedidoRepository.existsById(envio.getIdPedido())) {
                    return "No existe un pedido asociado a ese ID";
                }
                Pedido pedido = pedidoRepository.findById(envio.getIdPedido()).get();
                if (pedido.getDirEnvio() == null) {
                    return "El pedido asociado a ese ID fue entregado en tienda";
                }
                envioParchado.setPedidoAsociado(pedido);
            }

            if (envio.getFechaEnvio() != null) {
                envioParchado.setFechaEnvio(envio.getFechaEnvio());
            }

            if (envio.getFechaLlegadaEstim() != null) {
                envioParchado.setFechaLlegadaEstim(envio.getFechaLlegadaEstim());
            }

            if (envio.getFechaLlegadaReal() != null) {
                envioParchado.setFechaLlegadaReal(envio.getFechaLlegadaReal());
            }

            if (envio.getTransportista() != null) {
                envioParchado.setTransportista(envio.getTransportista());
            }

            if (envio.getNumSeguimiento() != null) {
                envioParchado.setNumSeguimiento(envio.getNumSeguimiento());
            }

            if (envio.getMetodoEnvio() != null) {
                envioParchado.setMetodoEnvio(envio.getMetodoEnvio());
            }
            envioRepository.save(envioParchado);
            return "Envio actualizado con éxito";
        }
        return "Envio no encontrado";

    }

    // D

    public String deleteEnvio(int id) {
        for (Envio envio : envioRepository.findAll()) {
            if (envio.getId() == id) {
                envioRepository.delete(envio);
                return "Envio eliminado con éxito";
            }
        }
        return "Envio no existente";
    }

    // Funciones no CRUD

    private String datosEnvio(String output, Envio envio) {
        output += "ID Envio: " + envio.getId() + "\n";
        output += "Código Envío: " + envio.getCodigoEnvio() + "\n";
        output += "Fecha envío: " + envio.getFechaEnvio() + "\n";
        output += "Fecha llegada estimada: " + envio.getFechaLlegadaEstim() + "\n";

        if (envio.getFechaLlegadaReal() == null) {
            output += "Fecha llegada real: No disponible\n";
        } else {
            output += "Fecha llegada real: " + envio.getFechaLlegadaReal() + "\n";
        }

        output += "Transportista: " + envio.getTransportista() + "\n";
        output += "Número de seguimiento: " + envio.getNumSeguimiento() + "\n";
        output += "Método de envío: " + envio.getMetodoEnvio() + "\n";
        output += "ID Pedido asociado: " + envio.getPedidoAsociado().getId() + "\n";

        return output;

    }
}
