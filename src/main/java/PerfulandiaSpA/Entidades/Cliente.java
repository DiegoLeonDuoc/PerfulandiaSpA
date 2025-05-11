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
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CLIENTE", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "RUT_USUARIO", referencedColumnName = "RUT_USUARIO")
    private Usuario usuarioAsociado;


    @Column(name = "ESTADO_CUENTA", nullable = false, length = 20)
    private String estadoCuenta;

}