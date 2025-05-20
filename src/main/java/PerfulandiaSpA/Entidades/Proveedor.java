package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PROVEEDOR")
public class Proveedor {
    @Id
    @Column(name = "ID_PROVEEDOR", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOMBRE_PROVEEDOR", nullable = false, length = 100)
    private String nombreProveedor;

    @Column(name = "TELEFONO_PROVEEDOR", nullable = false, length = 9)
    private String telefonoProveedor;

    @Column(name = "EMAIL_PROVEEDOR", nullable = false, length = 50)
    private String emailProveedor;

}