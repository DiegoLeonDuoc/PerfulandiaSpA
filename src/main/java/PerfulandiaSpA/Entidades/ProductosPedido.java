package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "PRODUCTOS_PEDIDO")
public class ProductosPedido {
    @Id
    @Column(name = "ID_PROD_PEDIDO", nullable = false)
    private Long id;

    @Column(name = "CANT_PROD", nullable = false)
    private Long cantProd;

    @Column(name = "PRECIO_UNITARIO", nullable = false)
    private Long precioUnitario;

    @Column(name = "DESCUENTO_UNITARIO")
    private Long descuentoUnitario;

    @Column(name = "PRECIO_TOTAL_PROD", nullable = false)
    private Long precioTotalProd;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_DESCUENTO")
    private Descuento idDescuento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_PEDIDO", nullable = false)
    private Pedido idPedido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_PROD", nullable = false)
    private Producto idProd;

}