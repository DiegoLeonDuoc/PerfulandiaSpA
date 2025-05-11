package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EMPLEADO")
@PrimaryKeyJoinColumn(name = "rut_us")
public class Empleado extends Usuario{

    @Id
    @Column(name = "rut_us", nullable = false)
    private int rutUs;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_suc", nullable = false)
    private Sucursal sucursal;

}