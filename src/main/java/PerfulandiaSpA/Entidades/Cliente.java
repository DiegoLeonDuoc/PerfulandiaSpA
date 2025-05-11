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
@Table(name = "CLIENTE")
@PrimaryKeyJoinColumn(name = "rut_us")
public class Cliente extends Usuario{

    @Id
    @Column(name = "rut_us", nullable = false)
    private int rutUs;

    @Column(name = "ESTADO_CUENTA", nullable = false, length = 20)
    private String estadoCuenta;

}