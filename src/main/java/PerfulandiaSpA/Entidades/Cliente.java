package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "CLIENTE")
@DiscriminatorValue("CLIENTE")
@PrimaryKeyJoinColumn(name = "RUT_USUARIO")
public class Cliente extends Usuario{

//    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "rut_usuario", referencedColumnName = "rut_usuario")
//    private Usuario usuarioAsociado;

    @Column(name = "ESTADO_CUENTA", nullable = false, length = 20)
    private String estadoCuenta;
}