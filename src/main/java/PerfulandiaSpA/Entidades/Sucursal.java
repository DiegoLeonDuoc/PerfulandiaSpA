package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "SUCURSAL")
public class Sucursal {
    @Id
    @Column(name = "ID_SUCURSAL", nullable = false)
    private Integer id;

    @Column(name = "NOMBRE_SUCURSAL", nullable = false, length = 50)
    private String nombreSucursal;

    @Column(name = "DIRECCION_SUCURSAL", nullable = false, length = 100)
    private String direccionSucursal;

}