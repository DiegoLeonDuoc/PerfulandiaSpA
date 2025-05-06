package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USUARIO")
public class Usuario {
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "CORREO", nullable = false, length = 100)
    private String correo;

    @Column(name = "CONTRASENA", nullable = false, length = 100)
    private String contrasena;

    @Column(name = "ROL", nullable = false, length = 50)
    private String rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "SUCURSAL_ID")
    private Sucursal sucursal;

}