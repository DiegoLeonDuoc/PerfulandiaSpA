package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.DTO.ReabastecimientoDTO;
import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Entidades.Proveedor;
import PerfulandiaSpA.Entidades.Reabastecimiento;
import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Repositorio.ProductoRepository;
import PerfulandiaSpA.Repositorio.ProveedorRepository;
import PerfulandiaSpA.Repositorio.ReabastecimientoRepository;
import PerfulandiaSpA.Repositorio.SucursalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReabastecimientoService {

    @Autowired
    ReabastecimientoRepository reabastecimientoRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    @Autowired
    ProveedorRepository proveedorRepository;

    public Reabastecimiento saveReabastecimiento(ReabastecimientoDTO reabastecimientoDTO) {
        Reabastecimiento reabastecimiento = new Reabastecimiento();
        reabastecimiento.setCantProductos(reabastecimientoDTO.getCantProductos());
        reabastecimiento.setFechaReabas(reabastecimientoDTO.getFechaReabas());
        reabastecimiento.setEstadoReabas(reabastecimientoDTO.getEstadoReabas());

        Optional<Producto> producto = productoRepository.findById(reabastecimientoDTO.getIdProducto());
        Optional<Sucursal> sucursal = sucursalRepository.findById(reabastecimientoDTO.getIdSucursal());
        Optional<Proveedor> proveedor = proveedorRepository.findById(reabastecimientoDTO.getIdProveedor());

        if (producto.isPresent() && sucursal.isPresent() && proveedor.isPresent()) {
            reabastecimiento.setProducto(producto.get());
            reabastecimiento.setSucursal(sucursal.get());
            reabastecimiento.setProveedor(proveedor.get());
            return reabastecimientoRepository.save(reabastecimiento);
        } else {
            throw new EntityNotFoundException("Producto, Sucursal o Proveedor no encontrado");
        }
    }

    public List<Reabastecimiento> getReabastecimientos() {
        return reabastecimientoRepository.findAll();
    }

    public Optional<Reabastecimiento> getReabastecimientoByID(int id) {
        return reabastecimientoRepository.findById(id);
    }

    public Reabastecimiento updateReabastecimiento(Integer id, ReabastecimientoDTO reabastecimientoDTO) {
        Reabastecimiento reabastecimientoExistente = reabastecimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reabastecimiento no encontrado"));

        reabastecimientoExistente.setCantProductos(reabastecimientoDTO.getCantProductos());
        reabastecimientoExistente.setFechaReabas(reabastecimientoDTO.getFechaReabas());
        reabastecimientoExistente.setEstadoReabas(reabastecimientoDTO.getEstadoReabas());

        Optional<Producto> producto = productoRepository.findById(reabastecimientoDTO.getIdProducto());
        Optional<Sucursal> sucursal = sucursalRepository.findById(reabastecimientoDTO.getIdSucursal());
        Optional<Proveedor> proveedor = proveedorRepository.findById(reabastecimientoDTO.getIdProveedor());

        if (producto.isPresent() && sucursal.isPresent() && proveedor.isPresent()) {
            reabastecimientoExistente.setId(id);
            reabastecimientoExistente.setProducto(producto.get());
            reabastecimientoExistente.setSucursal(sucursal.get());
            reabastecimientoExistente.setProveedor(proveedor.get());
            return reabastecimientoRepository.save(reabastecimientoExistente);
        } else {
            throw new EntityNotFoundException("Producto, Sucursal o Proveedor no encontrado");
        }
    }

    public void deleteReabastecimiento(int id) {
        reabastecimientoRepository.deleteById(id);
    }

}
