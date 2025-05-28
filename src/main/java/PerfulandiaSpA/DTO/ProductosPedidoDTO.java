package PerfulandiaSpA.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductosPedidoDTO {
    private Integer id;
    private Integer cantProd;
    private Integer idDescuento;
    private Integer idPedido;
    private Integer idProducto;
}
