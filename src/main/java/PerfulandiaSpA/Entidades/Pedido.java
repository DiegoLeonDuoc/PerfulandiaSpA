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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ID_SUCURSAL", nullable = false)
    private Sucursal sucursal;

    @Column(name = "FEC_PEDIDO", nullable = false)
    private LocalDate fecPedido;

    @Column(name = "PRECIO_PEDIDO", nullable = false)
    private Integer precioPedido;

    @Column(name = "METODO_PAGO", nullable = false, length = 50)
    private String metodoPago;

    @Column(name = "DIR_ENVIO", length = 100)
    private String dirEnvio;

    @Column(name = "DIR_FACTURACION", length = 100)
    private String dirFacturacion;

    @Column(name = "COSTO_ENVIO")
    private Integer costoEnvio;

    @Column(name = "ANOTACIONES", length = 75)
    private String anotaciones;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "RUT_USUARIO", nullable = false)
    private Cliente cliente;

}