package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "STOCK")
public class Stock {
    @Id
    @Column(name = "ID_STOCK", nullable = false)
    private Long id;

    @Column(name = "CANT_STOCK", nullable = false)
    private Long cantStock;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_PROD", nullable = false)
    private Producto idProd;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_SUC", nullable = false)
    private Sucursal idSuc;

}