package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Repositorio.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    public void saveProducto(Producto producto) {
        productoRepository.save(producto);
    }

    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> getProductoByID(int id) {
        return productoRepository.findById(id);
    }

    public void updateProducto(Producto producto, int id) {
        if (productoRepository.existsById(id)) {
            producto.setId(id);
            productoRepository.save(producto);
        }
    }

    public void deleteProducto(int id) {
        productoRepository.deleteById(id);
    }

}
