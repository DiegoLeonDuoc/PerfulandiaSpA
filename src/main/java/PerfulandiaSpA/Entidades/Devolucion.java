package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "DEVOLUCION")
public class Devolucion {
    @Id
    @Column(name = "ID_DEVOLUCION", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_PEDIDO", nullable = false)
    private Pedido idPedido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_PROD_PEDIDO", nullable = false)
    private ProductosPedido idProdPedido;

    @Column(name = "CANTIDAD_DEVUELTA", nullable = false)
    private Long cantidadDevuelta;

    @Column(name = "MOTIVO_DEVO", length = 100)
    private String motivoDevo;

    @Column(name = "ESTADO_DEVO", nullable = false, length = 30)
    private String estadoDevo;

    @Column(name = "MONTO_DEVUELTO", nullable = false)
    private Long montoDevuelto;

    @Column(name = "RESTOCK", nullable = false)
    private Long restock;

}