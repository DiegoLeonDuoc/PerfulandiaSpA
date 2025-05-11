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

    @Column(name = "CANT_PROD", nullable = false)
    private Long cantProd;

    @Column(name = "FEC_REABAS", nullable = false)
    private LocalDate fecReabas;

    @Column(name = "ESTADO_REABAS", nullable = false, length = 10)
    private String estadoReabas;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_SUC", nullable = false)
    private Sucursal idSuc;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_PROD", nullable = false)
    private Producto idProd;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_PROV", nullable = false)
    private Proveedor idProv;

}