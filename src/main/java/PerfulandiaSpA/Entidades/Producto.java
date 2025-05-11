package PerfulandiaSpA.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PRODUCTO")
public class Producto {
    @Id
    @Column(name = "ID_PRODUCTO", nullable = false)
    private Long id;

    @Column(name = "COD_BARR_PROD", nullable = false)
    private Long codBarrProd;

    @Column(name = "NOM_PROD", nullable = false, length = 50)
    private String nomProd;

    @Column(name = "DESCRIP_PROD", length = 200)
    private String descripProd;

    @Column(name = "PRECIO_PROD", nullable = false)
    private Long precioProd;

}