package PerfulandiaSpA.Entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PRODUCTO")
public class Producto {
    @Id
    @Column(name = "ID_PRODUCTO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "COD_BARR_PROD", nullable = false)
    private Long codBarrProd;

    @Column(name = "NOM_PROD", nullable = false, length = 50)
    private String nomProd;

    @Column(name = "DESCRIP_PROD", length = 200)
    private String descripProd;

    @Column(name = "PRECIO_PROD", nullable = false)
    private Integer precioProd;

    @JsonIgnore
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stock> cantStock = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reabastecimiento> reabastecimientos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Descuento> descuentos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductosCarrito> prodCarritos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "producto")
    private List<ProductosPedido> prodPedidos = new ArrayList<>();

}