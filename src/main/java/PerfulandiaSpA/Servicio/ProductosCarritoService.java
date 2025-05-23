package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.DTO.ProductosCarritoDTO;
import PerfulandiaSpA.Entidades.Descuento;
import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Entidades.ProductosCarrito;
import PerfulandiaSpA.Entidades.Carrito;
import PerfulandiaSpA.Repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductosCarritoService {
    @Autowired
    ProductosCarritoRepository productosCarritoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    CarritoRepository carritoRepository;

    @Autowired
    ProductoRepository productoRepository;

    // C
    public String crearProductosCarrito(ProductosCarritoDTO productosCarrito) {
        ProductosCarrito newProductosCarrito = new ProductosCarrito();
        Carrito carrito;
        Producto producto;
        if (carritoRepository.existsById(productosCarrito.getIdCarrito())) {
            carrito = carritoRepository.findById(productosCarrito.getIdCarrito()).get();
            newProductosCarrito.setCarrito(carrito);
        } else {
            return "No existe un carrito asociado a ese ID";
        }
        if (productoRepository.existsById(productosCarrito.getIdProducto())) {
            producto = productoRepository.findById(productosCarrito.getIdProducto()).get();
            newProductosCarrito.setProducto(producto);
        } else {
            return "No existe un producto asociado a ese ID";
        }
        newProductosCarrito.setCantProd(productosCarrito.getCantProd());
        productosCarritoRepository.save(newProductosCarrito);
        return "ProductosCarrito agregado con éxito";
    }
    // R
    public String getProductosCarritos() {
        String output = "";
        for (ProductosCarrito productosCarrito : productosCarritoRepository.findAll()) {
            output = datosProductosCarrito(output, productosCarrito);
        }
        if (output.isEmpty()) {
            return "No hay productosCarritos registrados";
        } else {
            return output;
        }
    }

    public String getProductosCarritosByRut(Integer rut) {
        String output = "";
        List<Integer> productosCarritosIds = new ArrayList<>();
        if (clienteRepository.existsById(rut)) {
            for (ProductosCarrito productosCarrito : productosCarritoRepository.findAll()) {
                if (productosCarrito.getCarrito().getCliente().getRutUsuario().equals(rut)) {
                    productosCarritosIds.add(productosCarrito.getId());
                }
            }
            if (productosCarritosIds.isEmpty()) {
                return "Este cliente no tiene productosCarritos registrados";
            } else {
                for (Integer id: productosCarritosIds) {
                    output += datosProductosCarrito("", productosCarritoRepository.findById(id).get());
                }
                return output;
            }
        } else {
            return "Este cliente no existe";
        }
    }

    public List<ProductosCarrito> getProductosCarritosJSON() {
        return productosCarritoRepository.findAll();
    }
//

//
    // U

    public String updateProductosCarrito(ProductosCarritoDTO productosCarritoDTO) {
        ProductosCarrito productosCarritoParchado;
        if (productosCarritoRepository.existsById(productosCarritoDTO.getId())) {
            productosCarritoParchado = productosCarritoRepository.findById(productosCarritoDTO.getId()).get();
        } else {
            return "El ProductosCarrito a actualizar no existe";
        }

        productosCarritoParchado.setCantProd(productosCarritoDTO.getCantProd());

        Carrito carrito;
        if (carritoRepository.existsById(productosCarritoDTO.getIdCarrito())) {
            carrito = carritoRepository.findById(productosCarritoDTO.getIdCarrito()).get();
            productosCarritoParchado.setCarrito(carrito);
        } else {
            return "No existe un carrito asociado a ese ID";
        }

        Producto producto;
        if (productoRepository.existsById(productosCarritoDTO.getIdProducto())) {
            producto = productoRepository.findById(productosCarritoDTO.getIdProducto()).get();
            productosCarritoParchado.setProducto(producto);
        } else {
            return "No existe un producto asociado a ese ID";
        }
        productosCarritoRepository.save(productosCarritoParchado);
        return "ProductosCarrito actualizado con éxito";
    }

    //
//
    public String patchProductosCarrito(ProductosCarritoDTO productosCarritoDTO, int id) {
        if (!productosCarritoRepository.existsById(id)) {
            return "El ProductosCarrito a actualizar no existe";
        }

        ProductosCarrito productosCarritoParchado = productosCarritoRepository.findById(id).get();
        productosCarritoParchado.setId(id);

        if (productosCarritoDTO.getCantProd() != null) {
            productosCarritoParchado.setCantProd(productosCarritoDTO.getCantProd());
        }

        if (productosCarritoDTO.getIdCarrito() != null) {
            if (carritoRepository.existsById(productosCarritoDTO.getIdCarrito())) {
                Carrito carrito = carritoRepository.findById(productosCarritoDTO.getIdCarrito()).get();
                productosCarritoParchado.setCarrito(carrito);
            } else {
                return "No existe un carrito asociado a ese ID";
            }
        }

        if (productosCarritoDTO.getIdProducto() != null) {
            if (productoRepository.existsById(productosCarritoDTO.getIdProducto())) {
                Producto producto = productoRepository.findById(productosCarritoDTO.getIdProducto()).get();
                productosCarritoParchado.setProducto(producto);
            } else {
                return "No existe un producto asociado a ese ID";
            }
        }

        productosCarritoRepository.save(productosCarritoParchado);
        return "ProductosCarrito actualizado con éxito";
    }
//
    // D

    public String deleteProductosCarrito(int id) {
        for (ProductosCarrito productosCarrito : productosCarritoRepository.findAll()) {
            if (productosCarrito.getId() == id) {
                productosCarritoRepository.delete(productosCarrito);
                return "ProductosCarrito eliminado con éxito";
            }
        }
        return "ProductosCarrito no existente";
    }

    // Funciones no CRUD
//
    public String datosProductosCarrito(String output, ProductosCarrito productosCarrito) {
        output += "ID ProductosCarrito: " + productosCarrito.getId() + "\n";
        output += "Cantidad de Producto: " + productosCarrito.getCantProd() + "\n";

        if (productosCarrito.getCarrito() != null) {
            output += "ID Carrito asociado: " + productosCarrito.getCarrito().getId() + "\n";
        } else {
            output += "Carrito: No asociado\n";
        }

        if (productosCarrito.getProducto() != null) {
            output += "Producto: " + productosCarrito.getProducto().getNomProd() + "\n";
        } else {
            output += "Producto: No asociado\n";
        }
        output += "\n";

        return output;
    }

}
