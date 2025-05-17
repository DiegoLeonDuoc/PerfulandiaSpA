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
@Table(name = "REABASTECIMIENTO")
public class Reabastecimiento {
    @Id
    @Column(name = "ID_REABAS", nullable = false)
    private Long id;

    @Column(name = "CANT_PRODUCTOS", nullable = false)
    private Long cantProductos;

    @Column(name = "FECHA_REABAS", nullable = false)
    private LocalDate fechaReabas;

    @Column(name = "ESTADO_REABAS", nullable = false, length = 10)
    private String estadoReabas;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ID_SUCURSAL", nullable = false)
    private Sucursal idSucursal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ID_PRODUCTO", nullable = false)
    private Producto idProducto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ID_PROVEEDOR", nullable = false)
    private Proveedor idProveedor;

}