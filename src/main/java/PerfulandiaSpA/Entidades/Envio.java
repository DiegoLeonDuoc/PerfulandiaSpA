package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ENVIO")
public class Envio {
    @Id
    @Column(name = "COD_ENVIO", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PEDIDO")
    private Pedido idPedido;

    @Column(name = "FEC_ENVIO", nullable = false)
    private LocalDate fecEnvio;

    @Column(name = "FEC_LLEGADA_ESTIM", nullable = false)
    private LocalDate fecLlegadaEstim;

    @Column(name = "FEC_LLEGADA_REAL")
    private LocalDate fecLlegadaReal;

    @Column(name = "TRANSPORTISTA", nullable = false, length = 30)
    private String transportista;

    @Column(name = "NUM_SEGUI", nullable = false)
    private Long numSegui;

    @ColumnDefault("'STANDARD'")
    @Column(name = "METODO_ENVIO", length = 20)
    private String metodoEnvio;

    @Column(name = "COSTO_ENVIO", nullable = false)
    private Long costoEnvio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_SUC", nullable = false)
    private Sucursal idSuc;

    @Column(name = "DIR_ENVIO", nullable = false, length = 200)
    private String dirEnvio;

    @Column(name = "ANOTACIONES", nullable = false, length = 75)
    private String anotaciones;

}