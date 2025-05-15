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
    @Column(name = "ID_PROVEEDOR", nullable = false)
    private Integer id;

    @Column(name = "NOMBRE_PROVEEDOR", nullable = false, length = 100)
    private String nombreProveedor;

    @Column(name = "TELEFONO_PROVEEDOR", nullable = false)
    private Integer telefonoProveedor;

    @Column(name = "EMAIL_PROVEEDOR", nullable = false, length = 50)
    private String emailProveedor;

}