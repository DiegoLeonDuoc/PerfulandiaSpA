package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.DTO.EnvioDTO;
import PerfulandiaSpA.Entidades.Envio;
import PerfulandiaSpA.Entidades.Pedido;
import PerfulandiaSpA.Repositorio.ClienteRepository;
import PerfulandiaSpA.Repositorio.EnvioRepository;
import PerfulandiaSpA.Repositorio.PedidoRepository;
import PerfulandiaSpA.Repositorio.SucursalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnvioService {
    @Autowired
    EnvioRepository envioRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    @Autowired
    ClienteRepository clienteRepository;

    // C
    public Envio crearEnvio(EnvioDTO envio) {
        Envio newEnvio = new Envio();

        newEnvio.setCodigoEnvio(envio.getCodigoEnvio());
        newEnvio.setFechaEnvio(envio.getFechaEnvio());
        newEnvio.setFechaLlegadaEstim(envio.getFechaLlegadaEstim());
        newEnvio.setFechaLlegadaReal(envio.getFechaLlegadaReal());
        newEnvio.setTransportista(envio.getTransportista());
        newEnvio.setNumSeguimiento(envio.getNumSeguimiento());
        newEnvio.setMetodoEnvio(envio.getMetodoEnvio());

        Optional<Pedido> pedido = pedidoRepository.findById(envio.getIdPedido());

        if  (pedido.isPresent()) {
            newEnvio.setPedidoAsociado(pedido.get());
            return envioRepository.save(newEnvio);
        } else {
            throw new EntityNotFoundException("Pedido no encontrado");
        }
    }

    // R
    public List<Envio> getEnvios() {
        return envioRepository.findAll();
    }

    public List<Envio> getEnviosByRut(Integer rut) {
        List<Envio> enviosCliente = new ArrayList<>();
        if (clienteRepository.existsById(rut)) {
            for (Envio envio : envioRepository.findAll()) {
                if (envio.getPedidoAsociado().getCliente().getRutUsuario().equals(rut)) {
                    enviosCliente.add(envio);
                }
            }
            return enviosCliente;
        } else {
            throw new EntityNotFoundException("Cliente no encontrado");
        }
    }

    public List<Envio> getEnviosBySucursal(Integer id) {
        List<Envio> enviosSucursal = new ArrayList<>();
        if (sucursalRepository.existsById(id)) {
            for (Envio envio : envioRepository.findAll()) {
                if (envio.getPedidoAsociado().getSucursal().getId().equals(id)) {
                    enviosSucursal.add(envio);
                }
            }
            return enviosSucursal;
        } else {
            throw new EntityNotFoundException("Sucursal no encontrada");
        }
    }

    public Optional<Envio> getEnvioByID(Integer id) {
        return envioRepository.findById(id);
    }

    // U

    public Envio updateEnvio(Integer id, EnvioDTO envio) {
        Envio envioExistente = envioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Envío no encontrado"));

        envioExistente.setCodigoEnvio(envio.getCodigoEnvio());
        envioExistente.setFechaEnvio(envio.getFechaEnvio());
        envioExistente.setFechaLlegadaEstim(envio.getFechaLlegadaEstim());
        envioExistente.setFechaLlegadaReal(envio.getFechaLlegadaReal());
        envioExistente.setTransportista(envio.getTransportista());
        envioExistente.setNumSeguimiento(envio.getNumSeguimiento());
        envioExistente.setMetodoEnvio(envio.getMetodoEnvio());

        Optional<Pedido> pedido = pedidoRepository.findById(envio.getIdPedido());

        if (pedido.isPresent()) {
            envioExistente.setPedidoAsociado(pedido.get());
            return envioRepository.save(envioExistente);
        } else {
            throw new EntityNotFoundException("Pedido no encontrado");
        }
    }


    // U/P
    public Envio patchEnvio(Integer id, EnvioDTO envio) {
        Envio envioExistente = envioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Envío no encontrado"));

        if (envio.getCodigoEnvio() != null) {
            envioExistente.setCodigoEnvio(envio.getCodigoEnvio());
        }
        if (envio.getFechaEnvio() != null) {
            envioExistente.setFechaEnvio(envio.getFechaEnvio());
        }
        if (envio.getFechaLlegadaEstim() != null) {
            envioExistente.setFechaLlegadaEstim(envio.getFechaLlegadaEstim());
        }
        if (envio.getFechaLlegadaReal() != null) {
            envioExistente.setFechaLlegadaReal(envio.getFechaLlegadaReal());
        }
        if (envio.getTransportista() != null) {
            envioExistente.setTransportista(envio.getTransportista());
        }
        if (envio.getNumSeguimiento() != null) {
            envioExistente.setNumSeguimiento(envio.getNumSeguimiento());
        }
        if (envio.getMetodoEnvio() != null) {
            envioExistente.setMetodoEnvio(envio.getMetodoEnvio());
        }

        if (envio.getIdPedido() != null) {
            Optional<Pedido> pedido = pedidoRepository.findById(envio.getIdPedido());
            if (pedido.isPresent()) {
                envioExistente.setPedidoAsociado(pedido.get());
            } else {
                throw new EntityNotFoundException("Pedido no encontrado");
            }
        }

        return envioRepository.save(envioExistente);
    }

    // D
    public void deleteEnvio(int id) {
        envioRepository.deleteById(id);
    }
}
