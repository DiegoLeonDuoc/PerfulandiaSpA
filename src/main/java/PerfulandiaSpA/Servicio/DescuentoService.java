package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.DTO.DescuentoDTO;
import PerfulandiaSpA.Entidades.Descuento;
import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Repositorio.DescuentoRepository;
import PerfulandiaSpA.Repositorio.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class DescuentoService {

    @Autowired
    private DescuentoRepository descuentoRepository;
    @Autowired
    private ProductoRepository productoRepository;

    // CREATE
    public Descuento crearDescuento(DescuentoDTO descuentoDTO) {
        Descuento descuento = new Descuento();
        descuento.setTipoDescuento(descuentoDTO.getTipoDescuento());
        descuento.setValorDescuento(descuentoDTO.getValorDescuento());
        descuento.setFecIniDescuento(descuentoDTO.getFecIniDescuento());
        descuento.setFecFinDescuento(descuentoDTO.getFecFinDescuento());

        Optional<Producto> producto = productoRepository.findById(descuentoDTO.getIdProducto());

        if (producto.isPresent()) {
            descuento.setProducto(producto.get());
        } else {
            descuento.setProducto(null);
        }

        return descuentoRepository.save(descuento);
    }

    // READ (JSON)
    public List<Descuento> getDescuentos() {
        return descuentoRepository.findAll();
    }

    // READ (por ID)
    public Optional<Descuento> getDescuentoByID(Integer id) {
        return descuentoRepository.findById(id);
    }

    // UPDATE
    public Descuento updateDescuento(DescuentoDTO descuentoDTO, Integer id) {
        Descuento descuentoExistente = descuentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Descuento no encontrado"));

        descuentoExistente.setId(id);
        descuentoExistente.setTipoDescuento(descuentoDTO.getTipoDescuento());
        descuentoExistente.setValorDescuento(descuentoDTO.getValorDescuento());
        descuentoExistente.setFecIniDescuento(descuentoDTO.getFecIniDescuento());
        descuentoExistente.setFecFinDescuento(descuentoDTO.getFecFinDescuento());

        Optional<Producto> producto = productoRepository.findById(descuentoDTO.getIdProducto());

        if (producto.isPresent()) {
            descuentoExistente.setProducto(producto.get());
        } else {
            descuentoExistente.setProducto(null);
        }

        return descuentoRepository.save(descuentoExistente);
    }

    public Descuento patchDescuento(DescuentoDTO descuentoDTO, Integer id) {
        Descuento descuentoExistente = descuentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Descuento no encontrado"));

        if (descuentoDTO.getTipoDescuento() != null) {
            descuentoExistente.setTipoDescuento(descuentoDTO.getTipoDescuento());
        }
        if (descuentoDTO.getValorDescuento() != null) {
            descuentoExistente.setValorDescuento(descuentoDTO.getValorDescuento());
        }
        if (descuentoDTO.getFecIniDescuento() != null) {
            descuentoExistente.setFecIniDescuento(descuentoDTO.getFecIniDescuento());
        }
        if (descuentoDTO.getFecFinDescuento() != null) {
            descuentoExistente.setFecFinDescuento(descuentoDTO.getFecFinDescuento());
        }

        if (descuentoDTO.getIdProducto() != null) {
            Optional<Producto> producto = productoRepository.findById(descuentoDTO.getIdProducto());
            if (producto.isPresent()) {
                descuentoExistente.setProducto(producto.get());
            } else {
                descuentoExistente.setProducto(null);
            }
        }

        return descuentoRepository.save(descuentoExistente);
    }


    // DELETE
    public void deleteDescuento(Integer id) {
        descuentoRepository.deleteById(id);
    }

}