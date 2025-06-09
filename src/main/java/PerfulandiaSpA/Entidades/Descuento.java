package PerfulandiaSpA.Entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "DESCUENTO")
public class Descuento {
    @Id
    @Column(name = "ID_DESCUENTO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TIPO_DESCUENTO", nullable = false, length = 10)
    private String tipoDescuento;

    @Column(name = "VALOR_DESCUENTO", nullable = false)
    private Integer valorDescuento;

    @Column(name = "FEC_INI_DESCUENTO", nullable = false)
    private LocalDate fecIniDescuento;

    @Column(name = "FEC_FIN_DESCUENTO")
    private LocalDate fecFinDescuento;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ID_PRODUCTO")
    private Producto producto;

    @JsonIgnore
    @OneToMany(mappedBy = "descuento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductosPedido> pedidosDescontados = new ArrayList<>();

}