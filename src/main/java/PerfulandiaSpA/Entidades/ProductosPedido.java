package PerfulandiaSpA.Entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PRODUCTOS_PEDIDO")
public class ProductosPedido {
    @Id
    @Column(name = "ID_PRODUCTO_PEDIDO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "CANT_PROD", nullable = false)
    private Integer cantProd;

    @Column(name = "PRECIO_UNITARIO", nullable = false)
    private Integer precioUnitario;

    @Column(name = "DESCUENTO_UNITARIO")
    private Integer descuentoUnitario;

    @Column(name = "PRECIO_TOTAL_PROD", nullable = false)
    private Integer precioTotalProd;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_DESCUENTO")
    private Descuento descuento;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ID_PEDIDO", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ID_PRODUCTO")
    private Producto producto;

    @JsonIgnore
    @OneToMany(mappedBy = "productoPedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Devolucion> devoluciones = new ArrayList<>();

}