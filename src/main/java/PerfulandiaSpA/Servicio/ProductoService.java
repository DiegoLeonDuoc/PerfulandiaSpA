package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Repositorio.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    // MÉTODO CREATE
    public String saveProducto(Producto producto) {
        if (producto.getId() == null) {
            productoRepository.save(producto);
            return "Producto agregado con éxito";
        } else {
            if (productoRepository.existsById(producto.getId())){
                return "Producto ya existe";
            } else {
                productoRepository.save(producto);
                return "Producto agregado con éxito";
            }
        }
    }

    // MÉTODO READ (LISTAR TODOS)
    // Retorna una lista de productoes en formato toString
    public String getProductos() {
        String output = "";
        for (Producto producto : productoRepository.findAll()) {
            output = datosProducto(output, producto); // Acumulando datos de cada producto
        }

        if (output.isEmpty()) {
            return "No hay productos registrados";
        } else {
            return output; // retorna lista completa
        }
    }
    // MÉTODO READ (LISTAR TODOS EN FORMATO JSON)
    public List<Producto> getProductosJSON() {
        return productoRepository.findAll();
    }

    // MÉTODO READ (BUSCAR POR ID)
    public String getProductoById(int id) {
        if (productoRepository.existsById(id)) {
            Producto producto = productoRepository.findById(id).get();
            return datosProducto("", producto);
        }
        return "Producto no encontrado";
    }

    // MÉTODO READ (BUSCAR POR codigo de barra)
    public String getProductoByCodigoBarra(long codigoBarra) {
        for (Producto producto : productoRepository.findAll()) {
            if (producto.getCodBarrProd() == codigoBarra) {
                return datosProducto("", producto);
            }
        }
        return "Producto no encontrado";
    }

    // MÉTODO UPDATE
    public String updateProducto(Producto producto, int id) {
        if (productoRepository.existsById(id)) {
            producto.setId(id);
            productoRepository.save(producto);
            return "Producto actualizado con éxito";
        }
        return "Producto no encontrado";
    }

    // MÉTODO DELETE
    public String deleteProducto(int id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return "Producto eliminado con éxito";
        }
        return "Producto no encontrado";
    }

    // MÉTODO toString
    private String datosProducto(String output, Producto producto) {
        output += "ID Producto: " + producto.getId() + "\n";
        output += "Nombre: " + producto.getNomProd() + "\n";
        output += "Codigo de barra: " + producto.getCodBarrProd() + "\n";
        output += "Precio: " + producto.getPrecioProd() + "\n";
        output += "Descripcion: " + producto.getDescripProd() + "\n";
        output += "\n";
        return output;
    }
}