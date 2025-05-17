package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "EMPLEADO")
@DiscriminatorValue("EMPLEADO")
@PrimaryKeyJoinColumn(name = "RUT_USUARIO")
public class Empleado extends Usuario{

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ID_SUCURSAL", nullable = false)
    private Sucursal sucursalAsociada;
}