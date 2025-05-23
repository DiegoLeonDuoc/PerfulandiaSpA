package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.DTO.ProductosPedidoDTO;
import PerfulandiaSpA.Entidades.Descuento;
import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Entidades.ProductosPedido;
import PerfulandiaSpA.Entidades.Pedido;
import PerfulandiaSpA.Repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductosPedidoService {
    @Autowired
    ProductosPedidoRepository productosPedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    DescuentoRepository descuentoRepository;

    // C
    public String crearProductosPedido(ProductosPedidoDTO productosPedido) {
        ProductosPedido newProductosPedido = new ProductosPedido();
        Pedido pedido;
        Producto producto;
        Descuento descuento;
        if (pedidoRepository.existsById(productosPedido.getIdPedido())) {
            pedido = pedidoRepository.findById(productosPedido.getIdPedido()).get();
            newProductosPedido.setPedido(pedido);
        } else {
            return "No existe un pedido asociado a ese ID";
        }
        if (productoRepository.existsById(productosPedido.getIdProducto())) {
            producto = productoRepository.findById(productosPedido.getIdProducto()).get();
            newProductosPedido.setProducto(producto);
        } else {
            return "No existe un producto asociado a ese ID";
        }
        if (productosPedido.getIdDescuento() == null) {
            newProductosPedido.setDescuentoUnitario(0);
            newProductosPedido.setPrecioUnitario(producto.getPrecioProd());
        } else if (descuentoRepository.existsById(productosPedido.getIdDescuento())) {
            descuento = descuentoRepository.findById(productosPedido.getIdDescuento()).get();
            if (descuento.getTipoDescuento().equals("FIJO")) {
                newProductosPedido.setDescuento(descuento);
                Integer montoDescuento = descuento.getValorDescuento();
                newProductosPedido.setDescuentoUnitario(montoDescuento);
                newProductosPedido.setPrecioUnitario(producto.getPrecioProd() - montoDescuento);
            } else if (descuento.getTipoDescuento().equals("PORCENTUAL")) {
                newProductosPedido.setDescuento(descuento);
                Integer montoDescuento = descuento.getValorDescuento();
                Integer descuentoProducto = Math.round(producto.getPrecioProd() * ((float) montoDescuento / 100));
                newProductosPedido.setDescuentoUnitario(descuentoProducto);
                newProductosPedido.setPrecioUnitario(producto.getPrecioProd() - descuentoProducto);
            }
        } else {
            return "No existe un descuento asociado a ese ID";
        }
        newProductosPedido.setCantProd(productosPedido.getCantProd());
        newProductosPedido.setPrecioTotalProd(newProductosPedido.getPrecioUnitario() * productosPedido.getCantProd());
        productosPedidoRepository.save(newProductosPedido);
        return "ProductosPedido agregado con éxito";
    }
    // R
    public String getProductosPedidos() {
        String output = "";
        for (ProductosPedido productosPedido : productosPedidoRepository.findAll()) {
            output = datosProductosPedido(output, productosPedido);
        }
        if (output.isEmpty()) {
            return "No hay productosPedidos registrados";
        } else {
            return output;
        }
    }

    public String getProductosPedidosByRut(Integer rut) {
        String output = "";
        List<Integer> productosPedidosIds = new ArrayList<>();
        if (clienteRepository.existsById(rut)) {
            for (ProductosPedido productosPedido : productosPedidoRepository.findAll()) {
                if (productosPedido.getPedido().getCliente().getRutUsuario().equals(rut)) {
                    productosPedidosIds.add(productosPedido.getId());
                }
            }
            if (productosPedidosIds.isEmpty()) {
                return "Este cliente no tiene productosPedidos registrados";
            } else {
                for (Integer id: productosPedidosIds) {
                    output += datosProductosPedido("", productosPedidoRepository.findById(id).get());
                }
                return output;
            }
        } else {
            return "Este cliente no existe";
        }
    }

    public List<ProductosPedido> getProductosPedidosJSON() {
        return productosPedidoRepository.findAll();
    }
//

//
    // U

    public String updateProductosPedido(ProductosPedidoDTO productosPedidoDTO) {
        ProductosPedido productosPedidoParchado;
        if (productosPedidoRepository.existsById(productosPedidoDTO.getId())) {
            productosPedidoParchado = productosPedidoRepository.findById(productosPedidoDTO.getId()).get();
        } else {
            return "El ProductosPedido a actualizar no existe";
        }

        productosPedidoParchado.setCantProd(productosPedidoDTO.getCantProd());

        Pedido pedido;
        if (pedidoRepository.existsById(productosPedidoDTO.getIdPedido())) {
            pedido = pedidoRepository.findById(productosPedidoDTO.getIdPedido()).get();
            productosPedidoParchado.setPedido(pedido);
        } else {
            return "No existe un pedido asociado a ese ID";
        }

        Producto producto;
        if (productoRepository.existsById(productosPedidoDTO.getIdProducto())) {
            producto = productoRepository.findById(productosPedidoDTO.getIdProducto()).get();
            productosPedidoParchado.setProducto(producto);
        } else {
            return "No existe un producto asociado a ese ID";
        }

        if (productosPedidoDTO.getIdDescuento() == null) {
            productosPedidoParchado.setDescuento(null);
            productosPedidoParchado.setDescuentoUnitario(0);
            productosPedidoParchado.setPrecioUnitario(producto.getPrecioProd());
        } else if (descuentoRepository.existsById(productosPedidoDTO.getIdDescuento())) {
            Descuento descuento = descuentoRepository.findById(productosPedidoDTO.getIdDescuento()).get();
            productosPedidoParchado.setDescuento(descuento);
            if (descuento.getTipoDescuento().equals("FIJO")) {
                Integer montoDescuento = descuento.getValorDescuento();
                productosPedidoParchado.setDescuentoUnitario(montoDescuento);
                productosPedidoParchado.setPrecioUnitario(producto.getPrecioProd() - montoDescuento);
            } else if (descuento.getTipoDescuento().equals("PORCENTUAL")) {
                Integer montoDescuento = descuento.getValorDescuento();
                Integer descuentoProducto = Math.round(producto.getPrecioProd() * ((float) montoDescuento / 100));
                productosPedidoParchado.setDescuentoUnitario(descuentoProducto);
                productosPedidoParchado.setPrecioUnitario(producto.getPrecioProd() - descuentoProducto);
            }
        } else {
            return "No existe un descuento asociado a ese ID";
        }

        productosPedidoParchado.setPrecioTotalProd(productosPedidoParchado.getPrecioUnitario() * productosPedidoParchado.getCantProd());
        productosPedidoRepository.save(productosPedidoParchado);
        return "ProductosPedido actualizado con éxito";
    }

//
//
public String patchProductosPedido(ProductosPedidoDTO productosPedidoDTO, int id) {
    if (!productosPedidoRepository.existsById(id)) {
        return "El ProductosPedido a actualizar no existe";
    }

    ProductosPedido productosPedidoParchado = productosPedidoRepository.findById(id).get();
    productosPedidoParchado.setId(id);

    if (productosPedidoDTO.getCantProd() != null) {
        productosPedidoParchado.setCantProd(productosPedidoDTO.getCantProd());
    }

    if (productosPedidoDTO.getIdPedido() != null) {
        if (pedidoRepository.existsById(productosPedidoDTO.getIdPedido())) {
            Pedido pedido = pedidoRepository.findById(productosPedidoDTO.getIdPedido()).get();
            productosPedidoParchado.setPedido(pedido);
        } else {
            return "No existe un pedido asociado a ese ID";
        }
    }

    if (productosPedidoDTO.getIdProducto() != null) {
        if (productoRepository.existsById(productosPedidoDTO.getIdProducto())) {
            Producto producto = productoRepository.findById(productosPedidoDTO.getIdProducto()).get();
            productosPedidoParchado.setProducto(producto);
        } else {
            return "No existe un producto asociado a ese ID";
        }
    }

    if (productosPedidoDTO.getIdDescuento() != null) {
        if (descuentoRepository.existsById(productosPedidoDTO.getIdDescuento())) {
            Descuento descuento = descuentoRepository.findById(productosPedidoDTO.getIdDescuento()).get();
            productosPedidoParchado.setDescuento(descuento);
            if (descuento.getTipoDescuento().equals("FIJO")) {
                Integer montoDescuento = descuento.getValorDescuento();
                productosPedidoParchado.setDescuentoUnitario(montoDescuento);
                productosPedidoParchado.setPrecioUnitario(productosPedidoParchado.getProducto().getPrecioProd() - montoDescuento);
            } else if (descuento.getTipoDescuento().equals("PORCENTUAL")) {
                Integer montoDescuento = descuento.getValorDescuento();
                Integer descuentoProducto = Math.round(productosPedidoParchado.getProducto().getPrecioProd() * ((float) montoDescuento / 100));
                productosPedidoParchado.setDescuentoUnitario(descuentoProducto);
                productosPedidoParchado.setPrecioUnitario(productosPedidoParchado.getProducto().getPrecioProd() - descuentoProducto);
            }
        } else {
            return "No existe un descuento asociado a ese ID";
        }
    } else {
        productosPedidoParchado.setDescuento(null);
        productosPedidoParchado.setDescuentoUnitario(0);
        productosPedidoParchado.setPrecioUnitario(productosPedidoParchado.getProducto().getPrecioProd());
    }

    productosPedidoRepository.save(productosPedidoParchado);
    return "ProductosPedido actualizado con éxito";
}
//
    // D

    public String deleteProductosPedido(int id) {
        for (ProductosPedido productosPedido : productosPedidoRepository.findAll()) {
            if (productosPedido.getId() == id) {
                productosPedidoRepository.delete(productosPedido);
                return "ProductosPedido eliminado con éxito";
            }
        }
        return "ProductosPedido no existente";
    }

    // Funciones no CRUD
//
    public String datosProductosPedido(String output, ProductosPedido productosPedido) {
        output += "ID ProductosPedido: " + productosPedido.getId() + "\n";
        output += "Cantidad de Producto: " + productosPedido.getCantProd() + "\n";
        output += "Precio Unitario: " + productosPedido.getPrecioUnitario() + "\n";
        output += "Descuento Unitario: " + (productosPedido.getDescuentoUnitario() != null ? productosPedido.getDescuentoUnitario() : "No disponible") + "\n";
        output += "Precio Total Producto: " + productosPedido.getPrecioTotalProd() + "\n";

        if (productosPedido.getDescuento() != null) {
            output += "ID Descuento: " + productosPedido.getDescuento().getId() + "\n";
        } else {
            output += "Descuento: No asociado\n";
        }

        if (productosPedido.getPedido() != null) {
            output += "ID Pedido asociado: " + productosPedido.getPedido().getId() + "\n";
        } else {
            output += "Pedido: No asociado\n";
        }

        if (productosPedido.getProducto() != null) {
            output += "ID Producto: " + productosPedido.getProducto().getId() + "\n";
        } else {
            output += "Producto: No asociado\n";
        }
        output += "\n";

        return output;
    }

}
