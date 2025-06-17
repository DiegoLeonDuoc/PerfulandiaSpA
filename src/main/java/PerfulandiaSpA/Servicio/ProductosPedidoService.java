package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.DTO.ProductosPedidoDTO;
import PerfulandiaSpA.Entidades.Descuento;
import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Entidades.ProductosPedido;
import PerfulandiaSpA.Entidades.Pedido;
import PerfulandiaSpA.Repositorio.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ProductosPedido crearProductosPedido(ProductosPedidoDTO productosPedido) {
        ProductosPedido newProductosPedido = new ProductosPedido();
        Pedido pedido;
        Producto producto;
        Descuento descuento;
        if (pedidoRepository.existsById(productosPedido.getIdPedido())) {
            pedido = pedidoRepository.findById(productosPedido.getIdPedido()).get();
            newProductosPedido.setPedido(pedido);
        } else {
            throw new EntityNotFoundException("Pedido no encontrado");
        }
        if (productoRepository.existsById(productosPedido.getIdProducto())) {
            producto = productoRepository.findById(productosPedido.getIdProducto()).get();
            newProductosPedido.setProducto(producto);
        } else {
            throw new EntityNotFoundException("Producto no encontrado");
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
            throw new EntityNotFoundException("Descuento no encontrado");
        }
        newProductosPedido.setCantProd(productosPedido.getCantProd());
        newProductosPedido.setPrecioTotalProd(newProductosPedido.getPrecioUnitario() * productosPedido.getCantProd());
        return productosPedidoRepository.save(newProductosPedido);
    }
    // R
    public List<ProductosPedido> getProductosPedidos() {
        return productosPedidoRepository.findAll();
    }

    public Optional<ProductosPedido> getProductoPedidoByID(Integer id) {
        Optional<ProductosPedido> productosPedido = productosPedidoRepository.findById(id);
        if (productosPedido.isPresent()) {
            return productosPedido;
        } else {
            throw new EntityNotFoundException("Producto en pedido no encontrado");
        }
    }

//    public List<ProductosPedido> getProductosPedidosByRut(Integer rut) {
//        List<ProductosPedido> productosCliente = new ArrayList<>();
//        if (clienteRepository.existsById(rut)) {
//            for (ProductosPedido productosPedido : productosPedidoRepository.findAll()) {
//                if (productosPedido.getPedido().getCliente().getRutUsuario().equals(rut)) {
//                    productosCliente.add(productosPedido);
//                }
//            }
//            return productosCliente;
//        } else {
//            throw new EntityNotFoundException("Cliente no encontrado");
//        }
//    }

    // U

    public ProductosPedido updateProductosPedido(ProductosPedidoDTO productosPedidoDTO, int id) {
        ProductosPedido productosPedidoParchado;
        Optional<ProductosPedido> productoPedido = productosPedidoRepository.findById(id);
        if (productoPedido.isPresent()) {
            productosPedidoParchado = productoPedido.get();
            productosPedidoParchado.setCantProd(productosPedidoDTO.getCantProd());

            Optional<Pedido> pedido =  pedidoRepository.findById(productosPedidoDTO.getIdPedido());
            if (pedido.isPresent()) {
                productosPedidoParchado.setPedido(pedido.get());
            } else {
                throw new EntityNotFoundException("Pedido no encontrado");
            }

            Optional<Producto> producto = productoRepository.findById(productosPedidoDTO.getIdProducto());
            if (producto.isPresent()) {
                productosPedidoParchado.setProducto(producto.get());
            } else {
                throw new EntityNotFoundException("Producto no encontrado");
            }

            Optional<Descuento> descuentoOp = descuentoRepository.findById(productosPedidoDTO.getIdDescuento());
            if (productosPedidoDTO.getIdDescuento() == null) {
                productosPedidoParchado.setDescuento(null);
                productosPedidoParchado.setDescuentoUnitario(0);
                productosPedidoParchado.setPrecioUnitario(producto.get().getPrecioProd());
            } else if (descuentoOp.isPresent()) {
                Descuento descuento = descuentoOp.get();
                productosPedidoParchado.setDescuento(descuento);
                if (descuento.getTipoDescuento().equals("FIJO")) {
                    Integer montoDescuento = descuento.getValorDescuento();
                    productosPedidoParchado.setDescuentoUnitario(montoDescuento);
                    productosPedidoParchado.setPrecioUnitario(producto.get().getPrecioProd() - montoDescuento);
                } else if (descuento.getTipoDescuento().equals("PORCENTUAL")) {
                    Integer montoDescuento = descuento.getValorDescuento();
                    Integer descuentoProducto = Math.round(producto.get().getPrecioProd() * ((float) montoDescuento / 100));
                    productosPedidoParchado.setDescuentoUnitario(descuentoProducto);
                    productosPedidoParchado.setPrecioUnitario(producto.get().getPrecioProd() - descuentoProducto);
                }
            } else {
                throw new EntityNotFoundException("Descuento no encontrado");
            }
            productosPedidoParchado.setPrecioTotalProd(productosPedidoParchado.getPrecioUnitario() * productosPedidoParchado.getCantProd());
            return productosPedidoRepository.save(productosPedidoParchado);
        } else {
            throw new EntityNotFoundException("Producto en pedido no encontrado");
        }
    }

    public ProductosPedido patchProductosPedido(ProductosPedidoDTO productosPedidoDTO, int id) {
        Optional<ProductosPedido> productoPedidoOpt = productosPedidoRepository.findById(id);

        if (productoPedidoOpt.isPresent()) {
            ProductosPedido productosPedidoParchado = productoPedidoOpt.get();

            if (productosPedidoDTO.getCantProd() != null) {
                productosPedidoParchado.setCantProd(productosPedidoDTO.getCantProd());
            }

            if (productosPedidoDTO.getIdPedido() != null) {
                Optional<Pedido> pedidoOpt = pedidoRepository.findById(productosPedidoDTO.getIdPedido());
                if (pedidoOpt.isPresent()) {
                    productosPedidoParchado.setPedido(pedidoOpt.get());
                } else {
                    throw new EntityNotFoundException("Pedido no encontrado");
                }
            }

            if (productosPedidoDTO.getIdProducto() != null) {
                Optional<Producto> productoOpt = productoRepository.findById(productosPedidoDTO.getIdProducto());
                if (productoOpt.isPresent()) {
                    productosPedidoParchado.setProducto(productoOpt.get());
                } else {
                    throw new EntityNotFoundException("Producto no encontrado");
                }
            }

            if (productosPedidoDTO.getIdDescuento() != null) {
                Optional<Descuento> descuentoOpt = descuentoRepository.findById(productosPedidoDTO.getIdDescuento());
                if (descuentoOpt.isPresent()) {
                    Descuento descuento = descuentoOpt.get();
                    productosPedidoParchado.setDescuento(descuento);
                    Integer montoDescuento = descuento.getValorDescuento();

                    if (descuento.getTipoDescuento().equals("FIJO")) {
                        productosPedidoParchado.setDescuentoUnitario(montoDescuento);
                        productosPedidoParchado.setPrecioUnitario(productosPedidoParchado.getProducto().getPrecioProd() - montoDescuento);
                    } else if (descuento.getTipoDescuento().equals("PORCENTUAL")) {
                        Integer descuentoProducto = Math.round(productosPedidoParchado.getProducto().getPrecioProd() * ((float) montoDescuento / 100));
                        productosPedidoParchado.setDescuentoUnitario(descuentoProducto);
                        productosPedidoParchado.setPrecioUnitario(productosPedidoParchado.getProducto().getPrecioProd() - descuentoProducto);
                    }
                } else {
                    throw new EntityNotFoundException("Descuento no encontrado");
                }
            } else {
                productosPedidoParchado.setDescuento(null);
                productosPedidoParchado.setDescuentoUnitario(0);
                productosPedidoParchado.setPrecioUnitario(productosPedidoParchado.getProducto().getPrecioProd());
            }

            productosPedidoParchado.setPrecioTotalProd(productosPedidoParchado.getPrecioUnitario() * productosPedidoParchado.getCantProd());
            return productosPedidoRepository.save(productosPedidoParchado);

        } else {
            throw new EntityNotFoundException("Producto en pedido no encontrado");
        }
    }

    // D

    public void deleteProductosPedido(int id) {
        productosPedidoRepository.deleteById(id);
    }

}
