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
@PrimaryKeyJoinColumn(name = "RUT_USUARIO")
@DiscriminatorValue("CLIENTE")
public class Cliente extends Usuario{

    @Column(name = "ESTADO_CUENTA", nullable = false, length = 20, columnDefinition = "")
    private String estadoCuenta;

}