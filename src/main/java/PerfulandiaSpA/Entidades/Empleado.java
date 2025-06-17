package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "EMPLEADO")
@DiscriminatorValue("EMPLEADO")
@PrimaryKeyJoinColumn(name = "RUT_USUARIO")
public class Empleado extends Usuario{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_SUCURSAL")
    private Sucursal sucursalAsociada;

}