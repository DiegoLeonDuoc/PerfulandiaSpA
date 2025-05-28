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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ID_PRODUCTO_PEDIDO", nullable = false)
    private ProductosPedido productoPedido;

    @Column(name = "CANTIDAD_DEVUELTA", nullable = false)
    private Integer cantidadDevuelta;

    @Column(name = "MOTIVO_DEVO", length = 100)
    private String motivoDevo;

    @Column(name = "ESTADO_DEVO", nullable = false, length = 30)
    private String estadoDevo;

    @Column(name = "MONTO_DEVUELTO", nullable = false)
    private Integer montoDevuelto;

    @Column(name = "RESTOCK", nullable = false)
    private Integer restock;

}