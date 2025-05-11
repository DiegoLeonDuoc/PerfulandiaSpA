package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "PEDIDO")
public class Pedido {
    @Id
    @Column(name = "ID_PEDIDO", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_SUC", nullable = false)
    private Sucursal idSuc;

    @Column(name = "FEC_PEDIDO", nullable = false)
    private LocalDate fecPedido;

    @Column(name = "PRECIO_PEDIDO", nullable = false)
    private Long precioPedido;

    @Column(name = "METODO_PAGO", nullable = false, length = 50)
    private String metodoPago;

    @Column(name = "DIR_ENVIO", length = 100)
    private String dirEnvio;

    @Column(name = "DIR_FACTURACION", length = 100)
    private String dirFacturacion;

    @Column(name = "COSTO_ENVIO")
    private Long costoEnvio;

    @Column(name = "ANOTACIONES", length = 75)
    private String anotaciones;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RUT_US", nullable = false)
    private Cliente rutUs;

}