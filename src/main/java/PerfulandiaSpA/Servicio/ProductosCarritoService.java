package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.DTO.ProductosCarritoDTO;
import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Entidades.ProductosCarrito;
import PerfulandiaSpA.Entidades.Carrito;
import PerfulandiaSpA.Repositorio.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductosCarritoService {
    @Autowired
    ProductosCarritoRepository productosCarritoRepository;

    @Autowired
    CarritoRepository carritoRepository;

    @Autowired
    ProductoRepository productoRepository;

    // C
    public ProductosCarrito crearProductosCarrito(ProductosCarritoDTO productosCarrito) {
        ProductosCarrito newProductosCarrito = new ProductosCarrito();
        Carrito carrito;
        Producto producto;
        if (carritoRepository.existsById(productosCarrito.getIdCarrito())) {
            carrito = carritoRepository.findById(productosCarrito.getIdCarrito()).get();
            newProductosCarrito.setCarrito(carrito);
        } else {
            throw new EntityNotFoundException("Carrito no encontrado");
        }
        if (productoRepository.existsById(productosCarrito.getIdProducto())) {
            producto = productoRepository.findById(productosCarrito.getIdProducto()).get();
            newProductosCarrito.setProducto(producto);
        } else {
            throw new EntityNotFoundException("Producto no encontrado");
        }

        newProductosCarrito.setCantProd(productosCarrito.getCantProd());

        return productosCarritoRepository.save(newProductosCarrito);
    }
    // R
    public List<ProductosCarrito> getProductosCarritos() {
        return productosCarritoRepository.findAll();
    }

    public Optional<ProductosCarrito> getProductoCarritoByID(Integer id) {
        Optional<ProductosCarrito> productosCarrito = productosCarritoRepository.findById(id);
        if (productosCarrito.isPresent()) {
            return productosCarrito;
        } else {
            throw new EntityNotFoundException("Producto en carrito no encontrado");
        }
    }

//    public List<ProductosCarrito> getProductosCarritosByRut(Integer rut) {
//        List<ProductosCarrito>  productosCliente = new ArrayList<>();
//        if (clienteRepository.existsById(rut)) {
//            for (ProductosCarrito productosCarrito : productosCarritoRepository.findAll()) {
//                if (productosCarrito.getCarrito().getCliente().getRutUsuario().equals(rut)) {
//                    productosCliente.add(productosCarrito);
//                }
//            }
//            return productosCliente;
//        } else {
//            throw new EntityNotFoundException("Cliente no encontrado");
//        }
//    }

    // U

    public ProductosCarrito updateProductosCarrito(ProductosCarritoDTO productosCarritoDTO, int id) {
        ProductosCarrito productosCarritoParchado;
        Optional<ProductosCarrito> productoCarrito = productosCarritoRepository.findById(id);
        if (productoCarrito.isPresent()) {
            productosCarritoParchado = productoCarrito.get();
            productosCarritoParchado.setCantProd(productosCarritoDTO.getCantProd());

            Optional<Carrito> carrito =  carritoRepository.findById(productosCarritoDTO.getIdCarrito());
            if (carrito.isPresent()) {
                productosCarritoParchado.setCarrito(carrito.get());
            } else {
                throw new EntityNotFoundException("Carrito no encontrado");
            }

            Optional<Producto> producto = productoRepository.findById(productosCarritoDTO.getIdProducto());
            if (producto.isPresent()) {
                productosCarritoParchado.setProducto(producto.get());
            } else {
                throw new EntityNotFoundException("Producto no encontrado");
            }

            return productosCarritoRepository.save(productosCarritoParchado);

        } else {
            throw new EntityNotFoundException("Producto en carrito no encontrado");
        }
    }

    // D

    public void deleteProductosCarrito(int id) {
        productosCarritoRepository.deleteById(id);
    }

}
