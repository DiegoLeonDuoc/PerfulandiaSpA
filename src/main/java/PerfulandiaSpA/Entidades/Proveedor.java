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
@Table(name = "PROVEEDOR")
public class Proveedor {
    @Id
    @Column(name = "ID_PROV", nullable = false)
    private Long id;

    @Column(name = "NOM_PROV", nullable = false, length = 100)
    private String nomProv;

    @Column(name = "TEL_CONTACTO_PROV", nullable = false)
    private Integer telContactoProv;

    @Column(name = "EMAIL_PROV", nullable = false, length = 50)
    private String emailProv;

}