package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "DESCUENTO")
public class Descuento {
    @Id
    @Column(name = "ID_DESCUENTO", nullable = false)
    private Long id;

    @Column(name = "TIPO_DESCUENTO", nullable = false, length = 10)
    private String tipoDescuento;

    @Column(name = "VALOR_DESCUENTO", nullable = false)
    private Long valorDescuento;

    @Column(name = "FEC_INI_DESCUENTO", nullable = false)
    private OffsetDateTime fecIniDescuento;

    @Column(name = "FEC_FIN_DESCUENTO")
    private OffsetDateTime fecFinDescuento;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_PROD")
    private Producto idProd;

}